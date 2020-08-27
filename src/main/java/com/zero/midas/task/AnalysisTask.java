package com.zero.midas.task;

import com.zero.midas.domain.entity.kline.KLine;
import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.factory.KLineFactory;
import com.zero.midas.model.entity.StockDO;
import com.zero.midas.repository.DailyRepository;
import com.zero.midas.repository.MonthlyRepository;
import com.zero.midas.repository.StockRepository;
import com.zero.midas.utils.JsonUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AnalysisTask {
    private static final Logger log = LoggerFactory.getLogger(AnalysisTask.class);

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private MonthlyRepository monthlyRepository;

    @Autowired
    private DailyRepository dailyRepository;

    @Autowired
    private KLineFactory kLineFactory;

    public void analysisMonthly() {
        List<StockDO> stocks = this.stockRepository.list();
        Monitor monitor = new Monitor();
        monitor.setStart(System.currentTimeMillis());
        stocks.forEach(stock -> analysis(monthlyRepository.listKLine(stock.getCode()), monitor));
        log.info("分析启明星:[{}]TPS", monitor.getCalculate() * 1.0 / ((System.currentTimeMillis() - monitor.getStart()) / 1000.0));
    }

    public void analysisDaily() {
        List<StockDO> stocks = this.stockRepository.list();
        Monitor monitor = new Monitor();
        monitor.setStart(System.currentTimeMillis());
        stocks.forEach(stock -> analysis(dailyRepository.listKLine(stock.getCode()), monitor));
        log.info("分析启明星:[{}]TPS", monitor.getCalculate() * 1.0 / ((System.currentTimeMillis() - monitor.getStart()) / 1000.0));
    }

    private void analysis(List<KLineNode> kline, Monitor monitor) {
        for (int i = 1; i < kline.size(); i++) {
            monitor.conut();
            List<KLineNode> kLineNodes = kline.subList(0, i);
            KLine kLine = kLineFactory.getKLine(kLineNodes);
            if (kLine.isVenus()) {
                log.info("[{}][{}]判断为启明星", kLineNodes.get(kLineNodes.size() - 1).getCode(), kLineNodes.get(kLineNodes.size() - 1).getDate());
                report(kline, i);
            }
        }
    }

    private void report(List<KLineNode> kline, int index) {
        List<KLineNode> showKLineNodes = kline.subList(index - 60 > 0 ? index - 60 : 0, index + 15 < kline.size() ? index + 15 : kline.size());
        List<List<Object>> kLineData = showKLineNodes.stream().map(KLineNode::toReportData).collect(Collectors.toList());
        String kLineDataStr = "\"" + JsonUtils.stringify(kLineData).replace("\"", "\\\"") + "\"";
        Properties properties = new Properties();
        properties.setProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        VelocityEngine engine = new VelocityEngine();
        engine.init(properties);
        String templateName = "velocity/kAndVolumn.vm";
        try {
            String filePath = "./report/" + kline.get(0).getCode() + "_" + DateTimeFormatter.ofPattern("yyyyMMdd").format(kline.get(index).getDate()) + ".html";

//            FileWriter fileWriter = new FileWriter(filePath);
            StringWriter stringWriter = new StringWriter();
            Template template = engine.getTemplate(templateName, "UTF-8");
            VelocityContext context = new VelocityContext();
            context.put("klineDatas", kLineDataStr);
            template.merge(context, stringWriter);
            FileUtils.writeStringToFile(new File(filePath), stringWriter.toString(), "UTF-8");
//            System.out.println(stringWriter.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Data
    public static class Monitor {
        private long start;
        private long calculate;

        public void conut() {
            calculate++;
        }
    }
}
