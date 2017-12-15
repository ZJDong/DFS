package com.skin.finder.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.finder.client.http.Cookie;
import com.skin.finder.client.http.CookieManager;
import com.skin.finder.client.http.HttpClient;
import com.skin.finder.client.http.HttpResponse;
import com.skin.finder.client.http.URLUtil;
import com.skin.finder.client.util.JSONParser;

/**
 * <p>Title: DefaultFinderClient</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author Admin
 * @version 1.0
 */
public class DefaultFinderClient implements FinderClient {
    private String url;
    private String userName;
    private String password;
    private Map<String, Cookie> cookies;
    private static final String UTF8 = "utf-8";
    private static final Logger logger = LoggerFactory.getLogger(DefaultFinderClient.class);

    public DefaultFinderClient() {
        this.cookies = new HashMap<String, Cookie>();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        DefaultFinderClient client = new DefaultFinderClient();
        client.setUrl("http://www.finderweb.net/finder");
        client.setUserName("test1");
        client.setPassword("1234");

        String content = client.getFileList("www.finderweb.net", "server1.mp3", "");
        System.out.println(content);
        // client.signin();
        // HttpClient.print(client.getRequestHeaders());
    }

    /**
     * @return boolean
     */
    @Override
    public boolean signin() {
        int count = 0;
        StringBuilder requestURL = new StringBuilder();
        requestURL.append(this.getUrl());
        requestURL.append("?action=finder.login");

        StringBuilder postBody = new StringBuilder();
        postBody.append("userName=");
        postBody.append(URLUtil.encode(this.userName, UTF8));
        postBody.append("&password=");
        postBody.append(URLUtil.encode(this.password, UTF8));
        Map<String, List<String>> headers = new HashMap<String, List<String>>();

        while(count++ < 3) {
            HttpResponse response = null;
            logger.info("login - userName: {}", this.userName);

            try {
                response = HttpClient.request("post", requestURL.toString(), headers, postBody.toString());
                this.setCookies(response);
                String content = response.getBody();

                @SuppressWarnings("unchecked")
                Map<String, Object> result = (Map<String, Object>)(new JSONParser().parse(content));
                Object status = this.getInteger(result, "status");

                if(status != null && status.equals(200)) {
                    if(!this.isSignin()) {
                        logger.info("Signin Failed: {}", this.userName);
                        break;
                    }
                    return true;
                }
            }
            catch(IOException e) {
                throw new RuntimeException(e);
            }
            finally {
                if(response != null) {
                    response.close();
                }
            }
        }
        throw new RuntimeException("Signin Failed.");
    }

    /**
     * @return boolean
     */
    @Override
    public boolean signout() {
        this.cookies.remove("passport");
        return true;
    }

    /**
     * @return boolean
     */
    @Override
    public boolean isSignin() {
        return (this.getPassport() != null);
    }

    @Override
    public String getFileList(String host, String workspace, String path) {
        /**
         * request.url
         */
        StringBuilder requestURL = new StringBuilder();
        requestURL.append(this.getUrl());
        requestURL.append("?action=finder.getFileList");
        requestURL.append("&host=");
        requestURL.append(URLUtil.encode(host, UTF8));
        requestURL.append("&workspace=");
        requestURL.append(URLUtil.encode(workspace, UTF8));
        requestURL.append("&path=");
        requestURL.append(URLUtil.encode(path, UTF8));
        logger.debug("Finder.getFileList: {}", requestURL);

        try {
            return this.request("get", requestURL.toString(), null);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String rename(String host, String workspace, String path, String oldName, String newName) {
        /**
         * request.url
         */
        StringBuilder requestURL = new StringBuilder();
        requestURL.append(this.getUrl());
        requestURL.append("?action=finder.rename");
        requestURL.append("&host=");
        requestURL.append(URLUtil.encode(host, UTF8));
        requestURL.append("&workspace=");
        requestURL.append(URLUtil.encode(workspace, UTF8));
        requestURL.append("&path=");
        requestURL.append(URLUtil.encode(path, UTF8));
        requestURL.append("&oldName=");
        requestURL.append(URLUtil.encode(oldName, UTF8));
        requestURL.append("&newName=");
        requestURL.append(URLUtil.encode(newName, UTF8));

        try {
            return this.request("get", requestURL.toString(), null);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String mkdir(String host, String workspace, String path, String name) {
        /**
         * request.url
         */
        StringBuilder requestURL = new StringBuilder();
        requestURL.append(this.getUrl());
        requestURL.append("?action=finder.mkdir");
        requestURL.append("&host=");
        requestURL.append(URLUtil.encode(host, UTF8));
        requestURL.append("&workspace=");
        requestURL.append(URLUtil.encode(workspace, UTF8));
        requestURL.append("&path=");
        requestURL.append(URLUtil.encode(path, UTF8));
        requestURL.append("&name=");
        requestURL.append(URLUtil.encode(name, UTF8));

        try {
            return this.request("get", name, null);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String delete(String host, String workspace, String path, String[] names) {
        return null;
    }

    @Override
    public String copy(String host, String workspace, String path) {
        return null;
    }

    @Override
    public String cut(String host, String workspace, String path) {
        return null;
    }

    @Override
    public String upload(String host, String workspace, String path, InputStream inputStream) {
        return null;
    }

    /**
     * @param url
     * @param headers
     * @return String
     * @throws IOException
     */
    private String request(String method, String url, String postBody) throws IOException {
        if(!this.isSignin()) {
            this.signin();
        }

        while(true) {
            HttpResponse response = null;
            Map<String, List<String>> headers = this.getRequestHeaders();

            try {
                response = HttpClient.request(method, url, headers, postBody);
                this.setCookies(response);
                String signin = response.getHeader("Finder-Signin");

                if(signin == null) {
                    return response.getBody();
                }

                if(signin.equals("false")) {
                    logger.debug("need signin: true");
                    this.signin();
                }

                if(!this.isSignin()) {
                    throw new IOException("Signin Finder Failed.");
                }
            }
            catch(IOException e) {
                throw new RuntimeException(e);
            }
            finally {
                if(response != null) {
                    response.close();
                }
            }
        }
    }

    /**
     * @return Map<String, List<String>>
     */
    private Map<String, List<String>> getRequestHeaders() {
        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Finder-Client", Arrays.asList("1.0.0"));

        if(this.cookies.size() > 0) {
            StringBuilder buffer = new StringBuilder();

            for(Map.Entry<String, Cookie> entry : this.cookies.entrySet()) {
                String name = entry.getKey();
                Cookie cookie = entry.getValue();

                if(buffer.length() > 0) {
                    buffer.append("; ");
                }

                buffer.append(name);
                buffer.append("=");
                buffer.append(cookie.getValue());
            }
            headers.put("Cookie", Arrays.asList(buffer.toString()));
        }
        return headers;
    }

    /**
     * @param response
     */
    private void setCookies(HttpResponse response) {
        Map<String, List<String>> headers = response.getHeaders();
        List<String> setCookies = headers.get("Set-Cookie");

        if(setCookies == null || setCookies.isEmpty()) {
            return;
        }

        for(String value : setCookies) {
            Cookie cookie = CookieManager.parse(value);

            if(cookie != null) {
                this.cookies.put(cookie.getName(), cookie);
            }
        }
    }

    /**
     * @param response
     * @return String
     */
    private String getPassport() {
        Cookie cookie = this.cookies.get("passport");

        if(cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    /**
     * @param context
     * @param name
     * @return Long
     */
    protected Long getLong(Map<String, Object> context, String name) {
        Object value = context.get(name);

        if(value == null) {
            return null;
        }

        if(value instanceof Number) {
            return ((Number)value).longValue();
        }

        try {
            return Long.parseLong(value.toString());
        }
        catch(NumberFormatException e) {
        }
        return null;
    }

    /**
     * @param context
     * @param name
     * @return Integer
     */
    protected Integer getInteger(Map<String, Object> context, String name) {
        Object value = context.get(name);

        if(value == null) {
            return null;
        }

        if(value instanceof Number) {
            return ((Number)value).intValue();
        }

        try {
            return Integer.parseInt(value.toString());
        }
        catch(NumberFormatException e) {
        }
        return null;
    }

    /**
     * @return the userName
     */
    @Override
    public String getUserName() {
        return this.userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the url
     */
    @Override
    public String getUrl() {
        return this.url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
