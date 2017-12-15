package com.skin.finder.client.http;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * <p>Title: BytePart</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class BytePart implements Part {
    private String name;
    private String fileName;
    private String contentType;
    private byte[] bytes;

    /**
     * @param name
     * @param fileName
     * @param contentType
     * @param bytes
     */
    public BytePart(String name, String fileName, String contentType, byte[] bytes) {
        this.name = name;
        this.contentType = contentType;
        this.bytes = bytes;
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
     * @return the fileName
     */
    public String getFileName() {
        return this.fileName;
    }

    
    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
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
        return null;
    }

    /**
     * @return the bytes
     */
    public byte[] getBytes() {
        return this.bytes;
    }

    /**
     * @param bytes the bytes to set
     */
    public void setInputStream(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public int length() {
        return this.getBodySize();
    }

    /**
     * @return int
     */
    public int getBodySize() {
        int size = getRequestHeader().getBytes().length;
        return (size + this.getDataSize() + 2);
    }

    /**
     * @return int
     */
    public int getDataSize() {
        if(this.bytes == null) {
            throw new NullPointerException("the file is null.");
        }
        return this.bytes.length;
    }

    /**
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
        buffer.append("\"; filename=\"");
        buffer.append(this.fileName);
        buffer.append("\"\r\n");
        buffer.append("Content-Type: ");
        buffer.append(this.contentType);
        buffer.append("\r\n");
        buffer.append("Content-Transfer-Encoding: binary\r\n\r\n");
        return buffer.toString();
    }

    /**
     * @param outputStream
     * @param charset
     */
    @Override
    public void write(OutputStream outputStream, Charset charset) throws IOException {
        String headers = this.getRequestHeader();
        outputStream.write(headers.getBytes(charset));
        outputStream.write(this.bytes, 0, this.bytes.length);
    }
}
