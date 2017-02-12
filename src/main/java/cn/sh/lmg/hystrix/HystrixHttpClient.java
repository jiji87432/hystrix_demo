package cn.sh.lmg.hystrix;

import cn.sh.lmg.exception.CommunicationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by liaomengge on 16/12/13.
 */
public final class HystrixHttpClient {

    private static int TIME_OUT = 2_000;//默认2秒超时

    public static String get(String url) {
        return get(url, "utf-8", 1);
    }

    public static String get(String url, int reTryTimes) {
        return get(url, "utf-8", reTryTimes);
    }

    public static String get(String url, String encoding, int reTryTimes) {
        return get(url, encoding, TIME_OUT, reTryTimes);
    }

    public static String get(String url, int timeoutMilliSeconds, int reTryTimes) {
        return get(url, "utf-8", timeoutMilliSeconds, reTryTimes);
    }

    public static String get(String url, String encoding, int timeoutMilliSeconds, int reTryTimes) {
        String result = StringUtils.EMPTY;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClients.custom().setRetryHandler(new HystrixRetryHandler(reTryTimes)).build();
            RequestConfig config = RequestConfig.custom().setConnectTimeout(timeoutMilliSeconds).setSocketTimeout(timeoutMilliSeconds).setConnectionRequestTimeout(timeoutMilliSeconds).build();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(config);
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

            try {
                result = EntityUtils.toString(httpResponse.getEntity(), encoding);
            } finally {
                httpResponse.close();
            }
        } catch (Exception e) {
            exceptionHandle(e, url);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (Exception e) {
                exceptionHandle(e, url);
            }
        }
        return result;
    }

    public static String post(String url) {
        return post(url, "", "application/json", 1);
    }

    public static String post(String url, int reTryTimes) {
        return post(url, "", "application/json", reTryTimes);
    }

    public static String post(String url, String postData, int timeoutMilliSeconds) {
        return post(url, postData, "application/json", "utf-8", null, timeoutMilliSeconds, 1);
    }

    public static String post(String url, String postData, int timeoutMilliSeconds, int reTryTimes) {
        return post(url, postData, "application/json", "utf-8", null, timeoutMilliSeconds, reTryTimes);
    }

    public static String post(String url, String postData, String mediaType, int reTryTimes) {
        return post(url, postData, mediaType, null, reTryTimes);
    }

    public static String post(String url, String postData, String mediaType, Header[] header, int reTryTimes) {
        return post(url, postData, mediaType, "utf-8", header, reTryTimes);
    }

    public static String post(String url, String postData, String mediaType, String encoding, Header[] headers, int reTryTimes) {
        return post(url, postData, mediaType, encoding, headers, TIME_OUT, reTryTimes);
    }

    public static String post(String url, String postData, String mediaType, String encoding, Header[] headers, int timeoutMilliSeconds, int reTryTimes) {
        String result = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClients.custom().setRetryHandler(new HystrixRetryHandler(reTryTimes)).build();
            RequestConfig config = RequestConfig.custom().setConnectTimeout(timeoutMilliSeconds).setSocketTimeout(timeoutMilliSeconds).setConnectionRequestTimeout(timeoutMilliSeconds).build();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(config);

            StringEntity entity = new StringEntity(postData, encoding);
            entity.setContentType(mediaType);
            httpPost.setEntity(entity);
            if (headers != null && headers.length > 0) {
                httpPost.setHeaders(headers);
            }
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            try {
                result = EntityUtils.toString(httpResponse.getEntity(), encoding);
            } finally {
                httpResponse.close();
            }
        } catch (Exception e) {
            exceptionHandle(e, url);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (Exception e) {
                exceptionHandle(e, url);
            }
        }
        return result;
    }

    private static void exceptionHandle(Exception e, String url) {
        throw new CommunicationException("调用服务失败，服务地址：" + url + "，异常类型："
                + e.getClass() + "，错误原因：" + e.getMessage());
    }

}
