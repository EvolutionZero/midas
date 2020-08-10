//package com.zero.midas;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.Properties;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;
//import org.slf4j.LoggerFactory;
//
//import ch.qos.logback.classic.LoggerContext;
//import ch.qos.logback.classic.joran.JoranConfigurator;
//
//public class Initialization {
//
//	private static volatile AtomicBoolean isInit = new AtomicBoolean(false);
//
//	public static void exec(){
//		if(!isInit.get()){
//			String logbackConfPath = System.getProperty("user.dir") + "/conf/logback.xml";
//			LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//			JoranConfigurator configurator = new JoranConfigurator();
//			configurator.setContext(lc);
//			lc.reset();
//			try {
//				configurator.doConfigure(logbackConfPath);
//				InputStream is = new FileInputStream(new File(System.getProperty("user.dir") + "/conf/proxool.properties"));
//				Properties properties = new Properties();
//				properties.load(is);
//				PropertyConfigurator.configure(properties);
//				isInit.set(true);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//}
