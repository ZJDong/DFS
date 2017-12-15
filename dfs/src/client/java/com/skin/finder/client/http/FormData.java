package com.skin.finder.client.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.skin.finder.client.util.IO;

/**
 * <p>Title: FormData</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class FormData {
    private String boundry;
    private List<Part> items;
    public static final byte[] CRLF = new byte[]{0x0D, 0x0A};

    /**
     * @param args
     */
    public static void main(String[] args) {
        Charset UTF8 = Charset.forName("utf-8");
        String boundry = "----finder-client-000000000";

        FormData formData = new FormData(boundry);
        File file = new File("D:\\中文gbk.txt");
        formData.append("userName", "中文");
        formData.append("userName", "中文");
        formData.append("uploadFile", file.getName(), "text/plain", file);

        try {
            long contentLength = formData.getContentLength();
            File formDataFile = new File("D:\\form-data.txt");

            formData.write(formDataFile, UTF8);
            System.out.println("contentLength: " + contentLength);
            System.out.println(contentLength == formDataFile.length());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     */
    public FormData(String boundry) {
        this.boundry = boundry;
        this.items = new ArrayList<Part>();
    }

    /**
     * @param name
     * @param value
     */
    public void append(String name, String value) {
        this.items.add(new TextPart(name, "text/plain", "utf-8", value));
    }

    /**
     * @param name
     * @param fileName
     * @param contenType
     * @param file
     */
    public void append(String name, String fileName, String contenType, File file) {
        if(file != null) {
            this.items.add(new FilePart(name, file.getName(), contenType, file));
        }
    }

    /**
     * @param name
     * @param fileName
     * @param contenType
     * @param bytes
     */
    public void append(String name, String fileName, String contenType, byte[] bytes) {
        if(bytes != null) {
            this.items.add(new BytePart(name, fileName, contenType, bytes));
        }
    }

    /**
     * @param name
     * @param fileName
     * @param contenType
     * @param inputStream
     */
    public void append(String name, String fileName, String contenType, InputStream inputStream) {
        if(inputStream != null) {
            this.items.add(new StreamPart(name, fileName, contenType, inputStream));
        }
    }

    /**
     * @param name
     * @param part
     */
    public void append(String name, Part part) {
        this.items.add(part);
    }

    /**
     * @return int
     */
    public int getContentLength() {
        if(this.items == null || this.items.size() < 1) {
            return 0;
        }

        int length = 0;

        for(Part part : this.items) {
            length += this.boundry.length();
            length += 2;
            length += part.length();
            length += 2;
        }
        return length;
    }

    /**
     * @param file
     * @param charset
     * @throws IOException
     */
    public void write(File file, Charset charset) throws IOException {
        if(this.items == null || this.items.size() < 1) {
            return;
        }

        OutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(file);
            write(outputStream, charset);
        }
        finally {
            IO.close(outputStream);
        }
    }

    /**
     * @param outputStream
     * @param charset
     * @throws IOException
     */
    public void write(OutputStream outputStream, Charset charset) throws IOException {
        if(this.items == null || this.items.size() < 1) {
            return;
        }

        byte[] bytes = this.boundry.getBytes();

        for(Part part : this.items) {
            outputStream.write(bytes, 0, bytes.length);
            outputStream.write(CRLF, 0, 2);
            part.write(outputStream, charset);
            outputStream.write(CRLF, 0, 2);
        }
        outputStream.flush();
    }

    /**
     * @param connection
     * @param charset
     * @throws IOException
     */
    public void write(HttpURLConnection connection, Charset charset) throws IOException {
        this.write(connection.getOutputStream(), charset);
    }
}

