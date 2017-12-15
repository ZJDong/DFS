package com.skin.finder.client.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: HttpClient</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author Admin
 * @version 1.0
 */
public class HttpClient {
    private static final String USER_AGENT = getUserAgent();
    private static final Charset UTF8 = Charset.forName("utf-8");
    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);

    /**
     * @param url
     * @param headers
     * @return String
     * @throws IOException
     */
    public static HttpResponse get(String url, Map<String, List<String>> headers) throws IOException {
        return request("get", url, headers, null);
    }

    /**
     * @param url
     * @param headers
     * @param data
     * @return HttpResponse
     * @throws IOException
     */
    public static HttpResponse post(String url, Map<String, List<String>> headers, String data) throws IOException {
        return request("post", url, headers, data);
    }

    /**
     * @param method
     * @param url
     * @param headers
     * @param postBody
     * @return HttpResponse
     * @throws IOException
     */
    public static HttpResponse request(String method, String url, Map<String, List<String>> headers, String postBody) throws IOException {
        HttpURLConnection connection = null;
        String requestURL = url;
        byte[] bytes = null;
        int contentLength = 0;

        if(method.equalsIgnoreCase("post")) {
            if(postBody != null) {
                bytes = postBody.getBytes(UTF8);
                contentLength = bytes.length;
            }
        }
        else if(method.equalsIgnoreCase("get")) {
            if(postBody != null) {
                if(requestURL.indexOf('?') < 0) {
                    requestURL = requestURL + "?" + postBody;
                }
                else {
                    requestURL = requestURL + "&" + postBody;
                }
            }
        }
        else {
            throw new UnsupportedOperationException("unsupported operation.");
        }

        try {
            logger.debug("==================== request ====================");
            logger.debug("request - {}", url);
            URL realUrl = new URL(url);

            /**
             * 使用代理的方式
             * connection = (HttpURLConnection)(realUrl.openConnection(getProxy("127.0.0.1", 8888)));
             */
            connection = (HttpURLConnection)(realUrl.openConnection());
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.setRequestMethod(method.toUpperCase());
            connection.setUseCaches(false);

            if(headers != null && headers.size() > 0) {
                for(Map.Entry<String, List<String>> entry : headers.entrySet()) {
                    String name = entry.getKey();
                    List<String> values = entry.getValue();

                    if(values != null && values.size() > 0) {
                        for(String value : values) {
                            logger.debug("request - {}: {}", name, value);
                            connection.addRequestProperty(name, value);
                        }
                    }
                }
            }

            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Connection", "close");

            if(method.equalsIgnoreCase("post")) {
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                connection.setRequestProperty("Content-Length", Integer.toString(contentLength));
            }
            else if(method.equalsIgnoreCase("get")) {
                connection.setRequestProperty("Content-Length", "0");
            }
            else {
                throw new UnsupportedOperationException("unsupported operation.");
            }

            if(bytes != null) {
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
            }

            if(logger.isDebugEnabled()) {
                print(connection);
            }
            return new HttpResponse(connection);
        }
        catch(IOException e) {
            if(connection != null) {
                connection.disconnect();
            }
            throw e;
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return String
     */
    public static String getUserAgent() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("FinderClient-Java/");
        buffer.append(System.getProperty("java.runtime.version"));
        buffer.append(" (");
        buffer.append(System.getProperty("os.name"));
        buffer.append("; ");
        buffer.append(System.getProperty("os.version"));
        buffer.append(")");
        return buffer.toString();
    }

    /**
     * @param connection
     */
    public static void print(HttpURLConnection connection) {
        logger.debug("==================== response ====================");
        Map<String, List<String>> map = connection.getHeaderFields();

        for(Map.Entry<String, List<String>> entry : map.entrySet()) {
            String name = entry.getKey();
            List<String> values = entry.getValue();

            if(values != null && values.size() > 0) {
                for(String value : values) {
                    if(name == null) {
                        logger.debug("response - {}", value);
                    }
                    else {
                        logger.debug("response - {}: {}", name, value);
                    }
                }
            }
        }
    }

    /**
     * @param connection
     */
    public static void print(Map<String, List<String>> map) {
        for(Map.Entry<String, List<String>> entry : map.entrySet()) {
            String name = entry.getKey();
            List<String> values = entry.getValue();

            if(values != null && values.size() > 0) {
                for(String value : values) {
                    if(name == null) {
                        logger.debug("header - {}", value);
                    }
                    else {
                        logger.debug("header - {}: {}", name, value);
                    }
                }
            }
        }
    }

    /**
     * @param reader
     * @param bufferSize
     * @return String
     * @throws IOException
     */
    public static String toString(Reader reader, int bufferSize) throws IOException {
        int length = 0;
        char[] buffer = new char[Math.max(bufferSize, 4096)];
        StringBuilder out = new StringBuilder();

        while((length = reader.read(buffer)) > -1) {
            out.append(buffer, 0, length);
        }
        return out.toString();
    }

    /**
     * @param closeable
     */
    public static void close(java.io.Closeable closeable) {
        if(closeable != null) {
            try {
                closeable.close();
            }
            catch(IOException e) {
            }
        }
    }
}
