package com.skin.finder.client.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * <p>Title: URLUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author Admin
 * @version 1.0
 */
public class URLUtil {
    /**
     * @param value
     * @param encoding
     * @return String
     */
    public static String encode(String value, String encoding) {
        try {
            return URLEncoder.encode(value, encoding);
        }
        catch (UnsupportedEncodingException e) {
        }
        return "";
    }
}
