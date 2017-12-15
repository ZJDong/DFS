package com.skin.finder.client.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.skin.finder.client.util.IO;

/**
 * <p>Title: StreamPart</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class StreamPart implements Part {
    private String name;
    private String fileName;
    private String contentType;
    private InputStream inputStream;

    /**
     * @param name
     * @param fileName
     * @param contentType
     * @param inputStream
     */
    public StreamPart(String name, String fileName, String contentType, InputStream inputStream) {
        this.name = name;
        this.contentType = contentType;
        this.inputStream = inputStream;
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
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return this.inputStream;
    }

    /**
     * @param inputStream the inputStream to set
     */
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
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
        if(this.inputStream == null) {
            throw new NullPointerException("the file is null.");
        }

        try {
            return this.inputStream.available();
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
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
        IO.copy(this.inputStream, outputStream);
    }
}
