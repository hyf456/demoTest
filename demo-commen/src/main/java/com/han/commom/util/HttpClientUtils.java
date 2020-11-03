// package com.han.commom.util;
//
// import org.apache.http.Consts;
// import org.apache.http.HttpEntity;
// import org.apache.http.HttpResponse;
// import org.apache.http.NameValuePair;
// import org.apache.http.client.HttpClient;
// import org.apache.http.client.ResponseHandler;
// import org.apache.http.client.config.RequestConfig;
// import org.apache.http.client.entity.UrlEncodedFormEntity;
// import org.apache.http.client.methods.HttpGet;
// import org.apache.http.client.methods.HttpPost;
// import org.apache.http.conn.ConnectTimeoutException;
// import org.apache.http.entity.ContentType;
// import org.apache.http.entity.StringEntity;
// import org.apache.http.entity.mime.HttpMultipartMode;
// import org.apache.http.entity.mime.MultipartEntityBuilder;
// import org.apache.http.entity.mime.content.StringBody;
// import org.apache.http.impl.client.BasicResponseHandler;
// import org.apache.http.impl.client.CloseableHttpClient;
// import org.apache.http.impl.client.HttpClients;
// import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
// import org.apache.http.message.BasicNameValuePair;
//
// import java.io.File;
// import java.net.SocketTimeoutException;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;
// import java.util.Set;
//
// /**
//  * @Description HttpClient工具类
//  * @Date 2019/7/26 17:28
//  * @Author hanyf
//  */
// public class HttpClientUtils {
//
// 	public static final int connTimeout=10000;
// 	public static final int readTimeout=10000;
// 	public static final String charset="UTF-8";
// 	private static HttpClient client = null;
//
// 	static {
// 		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
// 		cm.setMaxTotal(128);
// 		cm.setDefaultMaxPerRoute(128);
// 		client= HttpClients.custom().setConnectionManager(cm).setConnectionManagerShared(true).build();
// 	}
//
// 	public static String postParameters(String url, String parameterStr) throws ConnectTimeoutException, SocketTimeoutException, Exception{
// 		return post(url,parameterStr,"application/x-www-form-urlencoded",charset,connTimeout,readTimeout);
// 	}
//
// 	public static String postParameters(String url, String parameterStr,String charset, Integer connTimeout, Integer readTimeout) throws ConnectTimeoutException, SocketTimeoutException, Exception{
// 		return post(url,parameterStr,"application/x-www-form-urlencoded",charset,connTimeout,readTimeout);
// 	}
//
// 	public static String postParameters(String url, Map<String, String> params) throws ConnectTimeoutException,
// 			SocketTimeoutException, Exception {
// 		return postForm(url, params, null, connTimeout, readTimeout);
// 	}
//
// 	public static String postParameters(String url, Map<String, String> params, Integer connTimeout,Integer readTimeout) throws ConnectTimeoutException,
// 			SocketTimeoutException, Exception {
// 		return postForm(url, params, null, connTimeout, readTimeout);
// 	}
//
// 	public static String get(String url) throws Exception {
// 		return get(url, charset, null, null);
// 	}
//
// 	public static String get(String url, String charset) throws Exception {
// 		return get(url, charset, connTimeout, readTimeout);
// 	}
//
// 	/**
// 	 * 发送一个 Post 请求, 使用指定的字符集编码.
// 	 *
// 	 * @param url
// 	 * @param body RequestBody
// 	 * @param mimeType 例如 application/xml "application/x-www-form-urlencoded" a=1&b=2&c=3
// 	 * @param charset 编码
// 	 * @param connTimeout 建立链接超时时间,毫秒.
// 	 * @param readTimeout 响应超时时间,毫秒.
// 	 * @return ResponseBody, 使用指定的字符集编码.
// 	 * @throws ConnectTimeoutException 建立链接超时异常
// 	 * @throws SocketTimeoutException  响应超时
// 	 * @throws Exception
// 	 */
// 	public static String post(String url, String body, String mimeType,String charset, Integer connTimeout, Integer readTimeout)
// 			throws ConnectTimeoutException, SocketTimeoutException, Exception {
// 		HttpClient client = null;
// 		HttpPost post = new HttpPost(url.trim());
// 		String result = "";
// 		try {
// 			if (StringUtils.isNotBlank(body)) {
// 				HttpEntity entity = new StringEntity(body, ContentType.create(mimeType, charset));
// 				post.setEntity(entity);
// 			}
// 			// 设置参数
// 			RequestConfig.Builder customReqConf = RequestConfig.custom();
// 			if (connTimeout != null) {
// 				customReqConf.setConnectTimeout(connTimeout);
// 			}
// 			if (readTimeout != null) {
// 				customReqConf.setSocketTimeout(readTimeout);
// 			}
// 			post.setConfig(customReqConf.build());
// 			client = HttpClientUtils.client;
// 			HttpResponse res = client.execute(post);
// 			result = IOUtils.toString(res.getEntity().getContent(), charset);
// 		} finally {
// 			post.releaseConnection();
// 			if (client != null&& client instanceof CloseableHttpClient) {
// 				((CloseableHttpClient) client).close();
// 			}
// 		}
// 		return result;
// 	}
//
//
// 	/**
// 	 * 提交form表单
// 	 *
// 	 * @param url
// 	 * @param params
// 	 * @param connTimeout
// 	 * @param readTimeout
// 	 * @return
// 	 * @throws ConnectTimeoutException
// 	 * @throws SocketTimeoutException
// 	 * @throws Exception
// 	 */
// 	public static String postForm(String url, Map<String, String> params, Map<String, String> headers, Integer connTimeout,Integer readTimeout) throws ConnectTimeoutException,
// 			SocketTimeoutException, Exception {
//
// 		HttpClient client = null;
// 		HttpPost post = new HttpPost(url.trim());
// 		try {
// 			if (params != null && !params.isEmpty()) {
// 				List<NameValuePair> formParams = new ArrayList<NameValuePair>();
// 				Set<Map.Entry<String, String>> entrySet = params.entrySet();
// 				for (Map.Entry<String, String> entry : entrySet) {
// 					formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
// 				}
// 				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
// 				post.setEntity(entity);
// 			}
// 			if (headers != null && !headers.isEmpty()) {
// 				for (Map.Entry<String, String> entry : headers.entrySet()) {
// 					post.addHeader(entry.getKey(), entry.getValue());
// 				}
// 			}
// 			// 设置参数
// 			RequestConfig.Builder customReqConf = RequestConfig.custom();
// 			if (connTimeout != null) {
// 				customReqConf.setConnectTimeout(connTimeout);
// 			}
// 			if (readTimeout != null) {
// 				customReqConf.setSocketTimeout(readTimeout);
// 			}
// 			post.setConfig(customReqConf.build());
// 			client = HttpClientUtils.client;
// 			HttpResponse res =client.execute(post);
// 			return IOUtils.toString(res.getEntity().getContent(), "UTF-8");
// 		} finally {
// 			post.releaseConnection();
// 			if (client != null && client instanceof CloseableHttpClient) {
// 				((CloseableHttpClient) client).close();
// 			}
// 		}
// 	}
//
//
// 	/**
// 	 * 发送一个 GET 请求
// 	 *
// 	 * @param url
// 	 * @param charset
// 	 * @param connTimeout  建立链接超时时间,毫秒.
// 	 * @param readTimeout  响应超时时间,毫秒.
// 	 * @return
// 	 * @throws ConnectTimeoutException   建立链接超时
// 	 * @throws SocketTimeoutException   响应超时
// 	 * @throws Exception
// 	 */
// 	public static String get(String url, String charset, Integer connTimeout,Integer readTimeout)
// 			throws ConnectTimeoutException,SocketTimeoutException, Exception {
//
// 		HttpClient client = null;
// 		HttpGet get = new HttpGet(url.trim());
// 		get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
// 		String result = "";
// 		try {
// 			// 设置参数
// 			RequestConfig.Builder customReqConf = RequestConfig.custom();
// 			if (connTimeout != null) {
// 				customReqConf.setConnectTimeout(connTimeout);
// 			}
// 			if (readTimeout != null) {
// 				customReqConf.setSocketTimeout(readTimeout);
// 			}
// 			get.setConfig(customReqConf.build());
// 			client = HttpClientUtils.client;
// 			HttpResponse res = client.execute(get);
// 			result = IOUtils.toString(res.getEntity().getContent(), charset);
// 		} finally {
// 			get.releaseConnection();
// 			if (client != null && client instanceof CloseableHttpClient) {
// 				((CloseableHttpClient) client).close();
// 			}
// 		}
// 		return result;
// 	}
//
//
// 	/**
// 	 * 从 response 里获取 charset
// 	 *
// 	 * @param ressponse
// 	 * @return
// 	 */
// 	@SuppressWarnings("unused")
// 	private static String getCharsetFromResponse(HttpResponse ressponse) {
// 		// Content-Type:text/html; charset=GBK
// 		if (ressponse.getEntity() != null  && ressponse.getEntity().getContentType() != null && ressponse.getEntity().getContentType().getValue() != null) {
// 			String contentType = ressponse.getEntity().getContentType().getValue();
// 			if (contentType.contains("charset=")) {
// 				return contentType.substring(contentType.indexOf("charset=") + 8);
// 			}
// 		}
// 		return null;
// 	}
//
//
//
// 	/**
// 	 * 多文件上传文件
// 	 *
// 	 * @param url
// 	 * @param params
// 	 * @param connTimeout
// 	 * @param readTimeout
// 	 * @return
// 	 * @throws ConnectTimeoutException
// 	 * @throws SocketTimeoutException
// 	 * @throws Exception
// 	 */
// 	public static String postFiles(String url, Map<String, String> params, Map<String, String> headers, Integer connTimeout, Integer readTimeout, String name, File[] files) throws ConnectTimeoutException,
// 			SocketTimeoutException, Exception {
//
// 		HttpClient client = null;
// 		HttpPost post = new HttpPost(url.trim());
// 		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
// 		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
// 		try {
// 			if (headers != null && !headers.isEmpty()) {
// 				for (Map.Entry<String, String> entry : headers.entrySet()) {
// 					post.addHeader(entry.getKey(), entry.getValue());
// 				}
// 			}
// 			if (params != null && !params.isEmpty()) {
// 				Set<Map.Entry<String, String>> entrySet = params.entrySet();
// 				for (Map.Entry<String, String> entry : entrySet) {
// 					System.out.println("entry.getKey:"+entry.getKey());
// 					System.out.println("entry.getValue:"+entry.getValue());
// 					//builder.addTextBody(entry.getKey(), entry.getValue());
// 					//builder.addTextBody(entry.getKey(), entry.getValue(), ContentType.TEXT_PLAIN.withCharset("UTF-8"));
// 					builder.addPart(entry.getKey(), new StringBody(entry.getValue(),ContentType.create("text/plain", Consts.UTF_8)));
// 				}
// 			}
// 			if(files!=null&&files.length>0){
// 				for (File file : files) {
// 					builder.addBinaryBody(name,file);
// 				}
// 			}
// 			// 设置参数
// 			RequestConfig.Builder customReqConf = RequestConfig.custom();
// 			if (connTimeout != null) {
// 				customReqConf.setConnectTimeout(connTimeout);
// 			}
// 			if (readTimeout != null) {
// 				customReqConf.setSocketTimeout(readTimeout);
// 			}
// 			HttpEntity entity = builder.build();
// 			post.setEntity(entity);
// 			client = HttpClientUtils.client;
// 			HttpResponse res =client.execute(post);
// 			return IOUtils.toString(res.getEntity().getContent(), "UTF-8");
// 		} finally {
// 			post.releaseConnection();
// 			if (client != null && client instanceof CloseableHttpClient) {
// 				((CloseableHttpClient) client).close();
// 			}
// 		}
// 	}
//
// 	public static String postWithJson(String url, String json,Integer connTimeout,Integer readTimeout) throws Exception{
// 		HttpClient client = null;
// 		HttpPost post = new HttpPost(url);
// 		String result = "";
// 		try{
// 			CloseableHttpClient httpClient = HttpClients.createDefault();
// 			ResponseHandler<String> responseHandler = new BasicResponseHandler();
// 			StringEntity requestEntity = new StringEntity(json,"utf-8");
// 			requestEntity.setContentEncoding("UTF-8");
// 			post.setHeader("Content-type", "application/json");
// 			// 设置参数
// 			RequestConfig.Builder customReqConf = RequestConfig.custom();
// 			if (connTimeout != null) {
// 				customReqConf.setConnectTimeout(connTimeout);
// 			}
// 			if (readTimeout != null) {
// 				customReqConf.setSocketTimeout(readTimeout);
// 			}
// 			post.setConfig(customReqConf.build());
// 			post.setEntity(requestEntity);
// 			client = HttpClientUtils.client;
// 			post.setEntity(requestEntity);
// 			result = httpClient.execute(post,responseHandler);
// 		} catch(Exception e) {
// 			e.printStackTrace();
// 		} finally {
// 			post.releaseConnection();
// 			if (client != null && client instanceof CloseableHttpClient) {
// 				((CloseableHttpClient) client).close();
// 			}
// 		}
// 		return result;
// 	}
// }
