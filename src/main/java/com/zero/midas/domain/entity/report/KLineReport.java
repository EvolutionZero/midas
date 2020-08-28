package com.zero.midas.domain.entity.report;

import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.exception.MidasException;
import com.zero.midas.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author: fengzijian
 * @since: 2020/8/28 11:10
 * @Description:
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class KLineReport {

    private String code;
    private String name;
    private List<KLineNode> kline;

    private Config config;

    public KLineReport(String cod, String name, List<KLineNode> kline, Config config) {
        this.code = cod;
        this.name = name;
        this.kline = kline;
        this.config = config;
    }

    public static void main(String[] args) {
        Config config = new Config().setFocusIndex(1).setFocusBackOffSize(3);
        System.out.println(config.toString());
    }

    public String toHtml() {
        List<KLineNode> showKLineNodes = kline.subList(config.getFocusIndex() - config.getBackOffSize() > 0 ? config.getFocusIndex() - config.getBackOffSize() : 0, config.getFocusIndex() + config.getForwardSize() < kline.size() ? config.getFocusIndex() + config.getForwardSize() : kline.size());
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

        StringWriter stringWriter = new StringWriter();
        Template template = engine.getTemplate(templateName, "UTF-8");
        VelocityContext context = new VelocityContext();
        context.put("klineDatas", kLineDataStr);
        context.put("focusStartDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(kline.get(config.getFocusIndex() - (config.getFocusBackOffSize() - 1) > 0 ? config.getFocusIndex() - (config.getFocusBackOffSize() - 1) : 0).getDate()));
        context.put("focusEndDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(kline.get(config.getFocusIndex() + 0 < kline.size() ? config.getFocusIndex() + 0 : kline.size()).getDate()));
        template.merge(context, stringWriter);
        return stringWriter.toString();
    }

    public void exportFile() {
        String html = toHtml();
        String baseDir = config.getOutputDir().endsWith("/") ? config.getOutputDir() : config.getOutputDir() + "/";
        try {
            FileUtils.forceMkdir(new File(baseDir));
            String filePath = baseDir + code + "_" + name + "_" + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(kline.get(config.getFocusIndex()).getDate()) + ".html";
            FileUtils.writeStringToFile(new File(filePath), html, "UTF-8");
        } catch (IOException e) {
            throw new MidasException(e);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Config {
        /**
         * 聚焦位置索引
         */
        private int focusIndex;

        /**
         * 聚焦回退周期数
         */
        private int focusBackOffSize;

        /**
         * 回退周期数
         */

        private int backOffSize = 60;

        /**
         * 前进预测周期数
         */
        private int forwardSize = 15;

        /**
         * 输出目录
         */
        private String outputDir = "./report/";
    }
}
