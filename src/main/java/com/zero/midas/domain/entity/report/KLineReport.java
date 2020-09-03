package com.zero.midas.domain.entity.report;

import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.exception.MidasException;
import com.zero.midas.model._do.WatchWindow;
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
@Data
public class KLineReport {

    private WatchWindow watchWindow;

    private Config config;

    public KLineReport(WatchWindow watchWindow, Config config) {
        this.watchWindow = watchWindow;
        this.config = config;
    }

    public String toHtml() {
        List<List<Object>> kLineData = watchWindow.getNodes().stream().map(KLineNode::toReportData).collect(Collectors.toList());
        String kLineDataStr = "\"" + JsonUtils.stringify(kLineData).replace("\"", "\\\"") + "\"";
        Properties properties = new Properties();
        properties.setProperty("resource.loader.file.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        VelocityEngine engine = new VelocityEngine();
        engine.init(properties);
        String templateName = "velocity/kAndVolumn.vm";

        StringWriter stringWriter = new StringWriter();
        Template template = engine.getTemplate(templateName, "UTF-8");
        VelocityContext context = new VelocityContext();
        context.put("title", watchWindow.getCode() + "_" + watchWindow.getName() + "_" + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(watchWindow.focusNode().getDate()));
        context.put("klineDatas", kLineDataStr);
        context.put("focusStartDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(watchWindow.getFocus() - (config.getFocusBackOffSize() - 1) > 0 ? watchWindow.getNodes().get(watchWindow.getFocus() - (config.getFocusBackOffSize() - 1)).getDate() : watchWindow.getNodes().get(0).getDate()));
        context.put("focusEndDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(watchWindow.focusNode().getDate()));
        template.merge(context, stringWriter);
        return stringWriter.toString();
    }

    public void exportFile() {
        String html = toHtml();
        String baseDir = config.getOutputDir().endsWith("/") ? config.getOutputDir() : config.getOutputDir() + "/";
        try {
            FileUtils.forceMkdir(new File(baseDir));
            String filePath = baseDir + watchWindow.getCode() + "_" + watchWindow.getName().replace("*", "星") + "_" + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(watchWindow.focusNode().getDate()) + ".html";
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
         * 聚焦回退周期数
         */
        private int focusBackOffSize;

        /**
         * 输出目录
         */
        private String outputDir = "./report/";
    }
}
