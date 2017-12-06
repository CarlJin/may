package wss.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author shiyongjun
 * @ClassName HttpUtil.java
 * @Description
 * @date 2017年3月10日 下午2:16:12
 */
public abstract class HttpUtils {

    public final static String UTF8Encode = "UTF-8";
    public final static String POST = "POST";
    public final static String GET = "GET";
    public final static String PUT = "PUT";
    public final static String CONTENT_TYPE_URLENCODED = "application/x-www-form-urlencoded";
    public final static String CONTENT_TYPE_APPLICATION_JSON = "application/json";

    public static String doHttpRequest(String data, String requestUrl, String requestMethod, int timeout)
            throws IOException {
        return HttpUtils.doHttpRequest(data, requestUrl, requestMethod, CONTENT_TYPE_URLENCODED, timeout);
    }

    public static String doHttpRequest(String data, String requestUrl, String requestMethod, String contentType,
                                       int timeout) throws IOException {
        URL url = null;
        HttpURLConnection httpurlconnection = null;
        try {
            url = new URL(requestUrl);
            httpurlconnection = (HttpURLConnection) url.openConnection();
            httpurlconnection.setRequestMethod(requestMethod.toUpperCase());
            httpurlconnection.setRequestProperty("Content-Type", contentType);
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setConnectTimeout(timeout);
            httpurlconnection.setReadTimeout(timeout);
            if (requestMethod.equalsIgnoreCase("POST")) {
                httpurlconnection.getOutputStream().write(data.getBytes(UTF8Encode));
                httpurlconnection.getOutputStream().flush();
                httpurlconnection.getOutputStream().close();
            }

            int code = httpurlconnection.getResponseCode();
            if (code == 200) {
                try (InputStream is = httpurlconnection.getInputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                    } catch (IOException e) {
                        throw e;
                    }
                    return sb.toString();
                }
            }

        } finally {
            if (httpurlconnection != null) {
                httpurlconnection.disconnect();
            }
        }
        return null;
    }
}
