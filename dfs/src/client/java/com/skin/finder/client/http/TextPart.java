package com.skin.finder.client.http;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * <p>Title: TextPart</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author Admin
 * @version 1.0
 */
public class TextPart implements Part {
    private String name;
    private String contentType;
    private String charset;
    private String value;

    /**
     * @param name
     * @param contentType
     * @param charset
     * @param value
     */
    public TextPart(String name, String contentType, String charset, String value) {
        this.name = name;
        this.contentType = contentType;
        this.charset = charset;
        this.value = value;
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the contentType
     */
    @Override
    public String getContentType() {
        return this.contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return the charset
     */
    @Override
    public String getCharset() {
        return this.charset;
    }

    /**
     * @param charset the charset to set
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return int
     */
    @Override
    public int length() {
        return this.getBodySize();
    }

    /**
     * @return int
     */
    public int getBodySize() {
        int size = getRequestHeader().getBytes().length;
        return (size + this.getDataSize());
    }

    /**
     * @return int
     */
    public int getDataSize() {
        if(this.name != null && this.value != null) {
            if(this.charset == null) {
                throw new NullPointerException("the charset is null.");
            }

            Charset charset = Charset.forName(this.charset);
            return this.value.getBytes(charset).length;
        }
        return 0;
    }

    /**
     * @param charset
     * @return String
     */
    @Override
    public String getRequestHeader() {
        if(this.name == null) {
            throw new NullPointerException("the name is null.");
        }

        StringBuilder buffer = new StringBuilder();
        buffer.append("Content-Disposition: form-data; name=\"");
        buffer.append(this.name);
        buffer.append("\"\r\n");
        buffer.append("Content-Type: ");
        buffer.append(this.contentType);

        if(this.charset != null) {
            buffer.append("; charset=");
            buffer.append(this.charset);
        }
        buffer.append("\r\n");
        buffer.append("Content-Transfer-Encoding: 8bit\r\n\r\n");
        return buffer.toString();
    }

    /**
     * @param outputStream
     * @throws IOException
     */
    @Override
    public void write(OutputStream outputStream, Charset charset) throws IOException {
        String headers = this.getRequestHeader();
        outputStream.write(headers.getBytes(charset));
        outputStream.write(this.getValue().getBytes(charset));
    }
}
