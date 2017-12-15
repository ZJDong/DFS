package com.skin.finder.client.http;

/**
 * <p>Title: CookieManager</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author Admin
 * @version 1.0
 */
public class CookieManager {
    // private static final Logger logger = LoggerFactory.getLogger(CookieManager.class);

    public static void main(String[] args) {
        String text = "passport=MA%3D%3D%7CMQ%3D%3D%7CMQ%3D%3D%7CYWRtaW4%3D%7CYWRtaW4%3D%7CMTUwNjA2MTg2ODMzMA%3D%3D%7CYjMwZTJhMGE1NjYzOGNlNWRjNDEzMDRlZDE0ZWQ4MTE%3D;Max-Age=604800;path=/";
        Cookie cookie = CookieManager.parse(text);
        System.out.println(cookie);
    }

    /**
     * @param value
     * @return Cookie
     */
    public static Cookie parse(String text) {
        if(text == null) {
            return null;
        }

        int start = 0;
        int end = 0;
        String name = null;
        String value = null;
        Cookie cookie = new Cookie();

        while(start < text.length()) {
            end = text.indexOf('=', start);

            if(end > -1) {
                name = text.substring(start, end).trim();

                start = end + 1;
                end = text.indexOf(';', start);

                if(end > -1) {
                    value = text.substring(start, end);
                    start = end + 1;
                }
                else {
                    value = text.substring(start);
                    start = text.length();
                }

                // logger.debug("cookie: {}={}", name, value);

                if(name.equalsIgnoreCase("Max-Age")) {
                    cookie.setMaxAge(Integer.parseInt(value));
                }
                else if(name.equalsIgnoreCase("path")) {
                    cookie.setPath(value);
                }
                else if(name.equalsIgnoreCase("secure")) {
                    // ignore
                }
                else if(name.equalsIgnoreCase("domain")) {
                    // ignore
                }
                else if(name.equalsIgnoreCase("comment")) {
                    // ignore
                }
                else if(name.equalsIgnoreCase("version")) {
                    // ignore
                }
                else if(name.equalsIgnoreCase("Expires")) {
                    // set max-age
                }
                else {
                    cookie.setName(name);
                    cookie.setValue(value);
                }
            }
            else {
                break;
            }
        }

        if(cookie.getName() != null) {
            return cookie;
        }
        return null;
    }
}
