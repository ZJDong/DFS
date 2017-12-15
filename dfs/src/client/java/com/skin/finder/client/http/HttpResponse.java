package com.skin.finder.client.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: HttpResponse</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author Admin
 * @version 1.0
 */
public class HttpResponse {
    private HttpURLConnection connection;

    /**
     * @param connection
     */
    protected HttpResponse(HttpURLConnection connection) {
        this.connection = connection;
    }

    /**
     * @return int
     * @throws IOException
     */
    public int getStatus() throws IOException {
        return this.connection.getResponseCode();
    }

    /**
     * @param name
     * @return String
     */
    public String getHeader(String name) {
        return this.connection.getHeaderField(name);
    }

    /**
     * @return Map<String, List<String>>
     */
    public Map<String, List<String>> getHeaders() {
        return this.connection.getHeaderFields();
    }

    /**
     * @return String
     * @throws IOException
     */
    public String getBody() throws IOException {
        String charset = this.connection.getContentEncoding();
        return this.getBody((charset != null ? charset : "utf-8"));
    }

    /**
     * @return String
     * @throws IOException
     */
    public String getBody(String charset) throws IOException {
        return toString(this.connection.getInputStream(), charset, 8192);
    }

    /**
     * @return String
     */
    public String getContentEncoding() {
        return this.connection.getContentEncoding();
    }

    public void close() {
        if(this.connection != null) {
            this.connection.disconnect();
        }
    }

    /**
     * @param reader
     * @param bufferSize
     * @return String
     * @throws IOException
     */
    private static String toString(InputStream inputStream, String charset, int bufferSize) throws IOException {
        return toString(new InputStreamReader(inputStream, charset), 4096);
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
