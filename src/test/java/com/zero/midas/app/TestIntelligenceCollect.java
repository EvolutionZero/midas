//package com.zero.midas.app;
//
//import java.lang.management.ManagementFactory;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import com.zero.midas.Initialization;
//
//public class TestIntelligenceCollect {
//
//	@BeforeClass
//	public static void init(){
//		Initialization.exec();
//	}
//
//	@Test
//	public void testExec(){
//		new IntelligenceCollect().exec();
//		try {
//			Thread.sleep(60 * 1000 * 60);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		int threadCount = ManagementFactory.getThreadMXBean().getThreadCount();
//		System.out.println(threadCount);
//	}
//
//}
