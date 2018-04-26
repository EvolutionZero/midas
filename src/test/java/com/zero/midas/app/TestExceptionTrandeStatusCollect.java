package com.zero.midas.app;

import java.lang.management.ManagementFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import com.zero.midas.Initialization;

public class TestExceptionTrandeStatusCollect {

	@BeforeClass
	public static void init(){
		Initialization.exec();
	}
	
	@Test
	public void testExec(){
		new ExceptionTrandeStatusCollect().exec();
		try {
			Thread.sleep(60 * 1000 * 60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int threadCount = ManagementFactory.getThreadMXBean().getThreadCount();
		System.out.println(threadCount);
	}
	
}
