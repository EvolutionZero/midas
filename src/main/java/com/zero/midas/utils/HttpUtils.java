package com.zero.midas.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {

	private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);
	
	public static String getText(String urlStr, String charset) {
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(urlStr);
		String response = "";
		try {
			client.executeMethod(method);
			byte[] responseBody = null;
			responseBody = method.getResponseBody();
			response = new String(responseBody, charset);
		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);

		}
		return response;
	}

}
