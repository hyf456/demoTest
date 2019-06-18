package com.han.knowledge.PhoneCell;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * 调用命中规则详情查询 GET接口服务的Java示例
 *
 * @author hanyf
 */
public class RequestUtil {

    private final static String URL = "http://120.26.95.135:9500/api/api_interface.php";

    private final static String REQUEST_CHARSET = "UTF-8";
    private final static String RESPONSE_CHARSET = "UTF-8";
    private final static int CONNECT_TIMEOUT = 5000; // 调用连接超时时间
    private final static int REQUEST_TIMEOUT = 5000; // 调用获取数据超时时间

    /**
     * 拼接调用的URL
     *
     * @param url
     * @param parametersMap 业务数据，作为参数
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static URI buildUri(String url, Map parametersMap) {

        if (null == parametersMap || parametersMap.isEmpty()) {
            return URI.create(url);
        }
        ArrayList list = new ArrayList(parametersMap.size());
        Iterator iterator = parametersMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            list.add(entry.getKey().toString().trim() + "=" + entry.getValue().toString().trim());
        }
        return list.isEmpty() ? URI.create(url) : URI.create(url + "?" + StringUtils.join(list, "&"));
    }

    /**
     * 通过Https调用同盾基础数据服务，不建议长连接
     *
     * @param parameterMap
     * @return JSON格式的字符串
     * @throws Exception
     */
    @SuppressWarnings({"rawtypes"})
    public static String invoke(Map parameterMap) throws Exception {

        HttpGet httpGet = null;
        String result = "";
        try {

            // 拼接URL
            httpGet = new HttpGet(buildUri(URL, parameterMap));

            // 设置超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(CONNECT_TIMEOUT)
                    .setSocketTimeout(REQUEST_TIMEOUT)
                    .build();
            httpGet.setConfig(requestConfig);
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + REQUEST_CHARSET);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();

            // 返回值不是200，进行错误处理
            if (HttpStatus.SC_OK != httpResponse.getStatusLine().getStatusCode()) {
                // 相关处理
                if (null != httpGet) {
                    httpGet.abort();
                }
                return result;
            }

            // 获取结果
            result = EntityUtils.toString(httpEntity, RESPONSE_CHARSET);

        } catch (Exception e) {
            if (null != httpGet) {
                httpGet.abort();
            }
            throw e;
        }
        return result;
    }
}
