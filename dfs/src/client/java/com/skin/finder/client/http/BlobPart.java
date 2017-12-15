package com.skin.finder.client.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.skin.finder.client.util.IO;

/**
 * <p>Title: BlobPart</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class BlobPart extends FilePart {
    private long start;
    private long end;

    /**
     * @param name
     * @param fileName
     * @param contentType
     * @param body
     */
    public BlobPart(String name, String fileName, String contentType, File file, long start, long end) {
        super(name, fileName, contentType, file);
        this.start = start;
        this.end = end;
    }

    /**
     * @return int
     */
    @Override
    public int getDataSize() {
        if(this.getFile() == null) {
            throw new NullPointerException("the file is null.");
        }
        return (int)(this.end - this.start + 1);
    }

    /**
     * @param outputStream
     * @param charset
     */
    @Override
    public void write(OutputStream outputStream, Charset charset) throws IOException {
        String headers = this.getRequestHeader();
        outputStream.write(headers.getBytes(charset));
        InputStream inputStream = null;

        try {
            long length = (this.end - this.start + 1);
            inputStream = new FileInputStream(this.getFile());

            if(this.start > 0) {
                inputStream.skip(this.start);
            }
            IO.copy(inputStream, outputStream, 8192, length);
        }
        finally {
            IO.close(inputStream);
        }
    }
}
