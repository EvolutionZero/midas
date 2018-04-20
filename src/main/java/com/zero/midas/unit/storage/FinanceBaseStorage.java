package com.zero.midas.unit.storage;

import java.sql.Connection;
import java.sql.DriverManager;

import com.zero.orm.core.BaseStorage;
import com.zero.orm.core.PojoBaseBean;

public class FinanceBaseStorage<T extends PojoBaseBean> extends BaseStorage<T> {

	@Override
	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");// 加载数据库连接池配备的驱动
			conn = DriverManager.getConnection("proxool.test");// proxool为配置文件名，test为连接池别名
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}
