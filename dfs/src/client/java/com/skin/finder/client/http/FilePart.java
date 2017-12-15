package com.skin.finder.client.http;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.skin.finder.client.util.IO;

/**
 * <p>Title: FilePart</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class FilePart implements Part {
    private String name;
    private String fileName;
    private String contentType;
    private File file;

    /**
     * @param name
     * @param fileName
     * @param contentType
     * @param body
     */
    public FilePart(String name, String fileName, String contentType, File file) {
        this.name = name;
        this.fileName = file.getName();
        this.contentType = contentType;
        this.file = file;
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
     * @return the file
     */
    public File getFile() {
        return this.file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * @param start
     * @param end
     * @return BlobPart
     */
    public BlobPart slice(long start, long end) {
        return new BlobPart(this.name, this.fileName, this.contentType, this.file, start, end);
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
        return (size + this.getDataSize());
    }

    /**
     * @return int
     */
    public int getDataSize() {
        if(this.file == null) {
            throw new NullPointerException("the file is null.");
        }
        return (int)(this.file.length());
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
        IO.copy(this.file, outputStream);
    }
}
