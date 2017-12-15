package com.skin.finder.client.http;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * <p>Title: Part</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface Part {
    /**
     * @return String
     */
    String getName();

    /**
     * @return String
     */
    String getContentType();

    /**
     * @return String
     */
    String getCharset();

    /**
     * @return int
     */
    int length();

    /**
     * @return String
     */
    String getRequestHeader();

    /**
     * @param outputStream
     * @param charset
     * @throws IOException
     */
    void write(OutputStream outputStream, Charset charset) throws IOException;
}
