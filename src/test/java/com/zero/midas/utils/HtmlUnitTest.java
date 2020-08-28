package com.zero.midas.utils;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.*;

/**
 * @author: fengzijian
 * @since: 2020/8/10 16:32
 * @Description:
 */
public class HtmlUnitTest {
    /**
     * 处理Base64解码并写图片到指定位置
     *
     * @param base64 图片Base64数据
     * @param path   图片保存路径
     * @return
     */
    public static boolean base64ToImageFile(String base64, String path) throws IOException {// 对字节数组字符串进行Base64解码并生成图片
        // 生成jpeg图片
        try {
            OutputStream out = new FileOutputStream(path);
            return base64ToImageOutput(base64, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 处理Base64解码并输出流
     *
     * @param base64
     * @param out
     * @return
     */
    public static boolean base64ToImageOutput(String base64, OutputStream out) throws IOException {
        if (base64 == null) { // 图像数据为空
            return false;
        }
        try {
            // Base64解码
            byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(base64);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            out.write(bytes);
            out.flush();
            return true;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test() throws IOException, InterruptedException {
        String resourceLocation = "classpath:sh501008_互联医C_2019-09-05.html";
        File file = ResourceUtils.getFile(resourceLocation);
        System.out.println(file.getAbsolutePath());
        System.out.println(FileUtils.readFileToString(file, "UTF-8"));

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setDownloadImages(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.waitForBackgroundJavaScript(38000);

        WebRequest request = new WebRequest(ResourceUtils.getURL(resourceLocation));
//        request.setAdditionalHeader("Referer", refer);//设置请求报文头里的refer字段
        request.setAdditionalHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36");
        request.setAdditionalHeader("Connection", "keep-alive");
        request.setAdditionalHeader("Cookie", "ad_play_index=47; CNZZDATA1000215585=2014872656-1449554771-%7C1449572770");

        //获取页面
        HtmlPage page = webClient.getPage(request);
//        while (true){
//            if(page.isBeingParsed()){
//                break;
//            }
//            Thread.sleep(100);
//        }
        ScriptResult scriptResult = page.executeJavaScript("myChart.getDataURL('png')");
        System.out.println(scriptResult);

        String data = scriptResult.getJavaScriptResult().toString().replace("data:image/png;base64,", "");
        System.out.println(data);

        base64ToImageFile(data, "./test.png");
    }

}