package com.zero.midas.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpsUtils {
	
	private static final Logger LOG = LoggerFactory.getLogger(HttpsUtils.class);

	
	public static String get(String httpsUrl, int connectTimeout, int readTimeout){
		LOG.info("请求URL:{}", httpsUrl);
		String response = "";
		HttpsURLConnection httpsConn = null;
        try {
        	TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
       		 
        		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        			return new java.security.cert.X509Certificate[] {};
        		}
         
        		public void checkClientTrusted(X509Certificate[] chain, String authType)  {
        			
        		}
         
        		public void checkServerTrusted(X509Certificate[] chain, String authType) {
        			
        		}
        	} };
			SSLContext ssl = SSLContext.getInstance("TLS");
			ssl.init(null, trustAllCerts, new java.security.SecureRandom());
			httpsConn = (HttpsURLConnection) new URL(httpsUrl).openConnection();
			httpsConn.setSSLSocketFactory(ssl.getSocketFactory());
			httpsConn.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			
			httpsConn.setConnectTimeout(connectTimeout);
			httpsConn.setReadTimeout(readTimeout);
			httpsConn.setDoInput(true);  
			httpsConn.setDoOutput(true);  
			httpsConn.setRequestMethod("GET");  
//			httpsConn.setRequestProperty("Content-Length",  
//			        String.valueOf(requestXml.getBytes().length));  
			httpsConn.setUseCaches(false);  
//			httpsConn.getOutputStream().write(requestXml.getBytes("UTF-8"));  
			httpsConn.getOutputStream().flush();  
			httpsConn.getOutputStream().close();  
			BufferedReader in = new BufferedReader(new InputStreamReader(  
					httpsConn.getInputStream()));  
			StringBuilder result = new StringBuilder();
			String line = "";  
			while ((line = in.readLine()) != null) {  
				result.append(line);
			}  
			response = result.toString();
		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			if(httpsConn != null){
				httpsConn.disconnect();
			}
		}
        return response;
	}
	
	
	public static String get(String httpsUrl, String keyStorePath, String keyStorePassword, String requestXml, int connectTimeout, int readTimeout){
		LOG.info("请求URL:{}", httpsUrl);
		LOG.info("使用证书:{}", keyStorePath);
		LOG.info("证书秘钥:{}", keyStorePassword);
		String response = "";
		HttpsURLConnection httpsConn = null;
        try {
        	
			SSLContext ssl = SSLContext.getInstance("TLS");
			ssl.init(getKeyManagers(keyStorePath, keyStorePassword), getTrustManagers(keyStorePath), new SecureRandom());
			httpsConn = (HttpsURLConnection) new URL(httpsUrl).openConnection();
			httpsConn.setSSLSocketFactory(ssl.getSocketFactory());
			httpsConn.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			
			httpsConn.setConnectTimeout(connectTimeout);
			httpsConn.setReadTimeout(readTimeout);
			httpsConn.setDoInput(true);  
			httpsConn.setDoOutput(true);  
			httpsConn.setRequestMethod("GET");  
			httpsConn.setRequestProperty("Content-Length",  
			        String.valueOf(requestXml.getBytes().length));  
			httpsConn.setUseCaches(false);  
			httpsConn.getOutputStream().write(requestXml.getBytes("UTF-8"));  
			httpsConn.getOutputStream().flush();  
			httpsConn.getOutputStream().close();  
			BufferedReader in = new BufferedReader(new InputStreamReader(  
					httpsConn.getInputStream()));  
			StringBuilder result = new StringBuilder();
			String line = "";  
			while ((line = in.readLine()) != null) {  
				result.append(line);
			}  
			response = result.toString();
		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			if(httpsConn != null){
				httpsConn.disconnect();
			}
		}
        return response;
	}
	
	public static TrustManager[] getTrustManagers(String trustStore)
			throws java.security.NoSuchAlgorithmException,
			java.security.KeyStoreException, java.io.IOException,
			java.security.GeneralSecurityException {
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(new FileInputStream(trustStore), null);
		TrustManagerFactory tmf = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(ks);

		return tmf.getTrustManagers();
	}

	public static KeyManager[] getKeyManagers(String keyStore,
			String keyStorePassword)
			throws java.security.NoSuchAlgorithmException,
			java.security.KeyStoreException,
			java.security.GeneralSecurityException,
			java.security.cert.CertificateException, java.io.IOException,
			java.security.UnrecoverableKeyException {
		String keyStoreKey = keyStore.endsWith(".jks") ? "JKS" : (keyStore.endsWith(".p12") ? "PKCS12" : "");
		KeyStore ks = KeyStore.getInstance(keyStoreKey);
		ks.load(new FileInputStream(keyStore), keyStorePassword.toCharArray());
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory
				.getDefaultAlgorithm());
		kmf.init(ks, keyStorePassword.toCharArray());
		return kmf.getKeyManagers();
	}
}
