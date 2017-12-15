/*
 * $RCSfile: Less.java,v $
 * $Revision: 1.1 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.finder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import javax.servlet.http.HttpServletResponse;

import com.skin.finder.util.IO;
import com.skin.finder.util.StringUtil;

/**
 * <p>Title: Less</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Less {
    public static final int MAX_ROWS = 1000;
    public static final int MAX_SIZE = 131072;
    private static final byte[] LF = new byte[]{0x0A};

    /**
     * less
     */
    private Less() {
    }

    /**
     * @param raf
     * @param position
     * @param rows
     * @return FileRange
     * @throws IOException
     */
    public static FileRange tail(RandomAccessFile raf, long position, int rows) throws IOException {
        if(position <= 0) {
            long length = raf.length();
            return prev(raf, length - 1, rows);
        }
        else {
            return read(raf, position, rows);
        }
    }

    /**
     * @param raf
     * @param position
     * @param rows
     * @return FileRange
     * @throws IOException
     */
    public static FileRange prev(RandomAccessFile raf, long position, int rows) throws IOException {
        long length = raf.length();

        if(position < 0 || position >= length) {
            FileRange range = new FileRange();
            range.setStart(position);
            range.setEnd(position);
            range.setCount(0L);
            range.setLength(length);
            range.setRows(0);
            return range;
        }

        int bufferSize = 4096;
        byte[] buffer = new byte[bufferSize];

        if(position > 0 && position < length) {
            raf.seek(position);
        }

        int count = 0;
        int readBytes = 0;
        long start = position;
        long end = position;

        while(true) {
            end = start;
            start = Math.max(start - bufferSize + 1, 0L);

            raf.seek(start);
            readBytes = raf.read(buffer, 0, (int)(end - start + 1));
            raf.seek(start);

            for(int i = readBytes - 1; i > -1; i--) {
                if(buffer[i] == '\n') {
                    count++;

                    if(count >= rows) {
                        start = (start + i + 1);
                        raf.seek(start);
                        break;
                    }
                }
            }

            if(start == 0L || count >= rows) {
                break;
            }
        }

        readBytes = (int)(position - start + 1);

        if(count < 1 && readBytes > 0) {
            count = 1;
        }

        FileRange range = new FileRange();
        range.setStart(start);
        range.setEnd(start + readBytes - 1);
        range.setCount(readBytes);
        range.setLength(length);
        range.setRows(count);
        return range;
    }

    /**
     * @param raf
     * @param position
     * @param rows
     * @return FileRange
     * @throws IOException
     */
    public static FileRange next(RandomAccessFile raf, long position, int rows) throws IOException {
        long start = position;
        long length = raf.length();

        if((start + 1) >= length) {
            start = Math.max(length - 1, 0);
            FileRange range = new FileRange();
            range.setStart(start);
            range.setEnd(start);
            range.setCount(0L);
            range.setLength(length);
            range.setRows(0);
            return range;
        }

        if(start < 0) {
            start = 0;
        }

        byte LF = 0x0A;
        int readBytes = 0;
        int bufferSize = IO.getBufferSize(length - start, 8192);
        byte[] buffer = new byte[bufferSize];
        raf.seek(start);

        if(start > 0) {
            boolean flag = false;

            while((readBytes = raf.read(buffer, 0, bufferSize)) > 0) {
                for(int i = 0; i < readBytes; i++) {
                    if(buffer[i] == LF) {
                        start = start + i + 1;
                        flag = true;
                        break;
                    }
                }

                if(flag) {
                    break;
                }
                else {
                    start += readBytes;
                }
            }

            if(flag) {
                raf.seek(start);
            }
            else {
                FileRange range = new FileRange();
                range.setStart(length - 1);
                range.setEnd(length - 1);
                range.setCount(0L);
                range.setLength(length);
                range.setRows(0);
                return range;
            }
        }

        int count = 0;
        int dataSize = 0;

        while((readBytes = raf.read(buffer, 0, bufferSize)) > 0) {
            for(int i = 0; i < readBytes; i++) {
                if(buffer[i] == LF) {
                    count++;

                    if(count >= rows || dataSize >= Less.MAX_SIZE) {
                        readBytes = i + 1;
                        break;
                    }
                }
            }

            if(readBytes > 0) {
                dataSize += readBytes;
            }

            if(count >= rows || dataSize >= Less.MAX_SIZE) {
                break;
            }
        }

        FileRange range = new FileRange();
        range.setStart(start);
        range.setEnd(start + dataSize - 1);
        range.setCount(dataSize);
        range.setLength(length);
        range.setRows(count);
        return range;
    }

    /**
     * @param raf
     * @param position
     * @param rows
     * @return FileRange
     * @throws IOException
     */
    public static FileRange read(RandomAccessFile raf, long position, int rows) throws IOException {
        long start = position;
        long length = raf.length();

        if((start + 1) >= length) {
            FileRange range = new FileRange();
            range.setStart(length);
            range.setEnd(length);
            range.setCount(0L);
            range.setLength(length);
            range.setRows(0);
            return range;
        }

        if(start < 0) {
            start = 0;
        }

        byte LF = 0x0A;
        int count = 0;
        int dataSize = 0;
        int readBytes = 0;
        int bufferSize = IO.getBufferSize(length - start, 8192);
        byte lastByte = -1;
        byte[] buffer = new byte[bufferSize];
        raf.seek(start);

        while((readBytes = raf.read(buffer, 0, bufferSize)) > 0) {
            for(int i = 0; i < readBytes; i++) {
                if(buffer[i] == LF) {
                    count++;

                    if(count >= rows || dataSize >= Less.MAX_SIZE) {
                        lastByte = LF;
                        readBytes = i + 1;
                        break;
                    }
                }
            }

            dataSize += readBytes;

            if(count >= rows || dataSize > Less.MAX_SIZE) {
                break;
            }
        }

        if(dataSize > 0 && lastByte != LF) {
            count++;
        }

        FileRange range = new FileRange();
        range.setStart(start);
        range.setEnd(start + dataSize);
        range.setCount(dataSize);
        range.setLength(length);
        range.setRows(count);
        return range;
    }

    /**
     * @param response
     * @param charset
     * @param range
     * @throws IOException
     */
    public static void success(HttpServletResponse response, String charset, FileRange range) throws IOException {
        response.setContentType("text/plain; charset=" + charset);
        String content = getReturnValue(200, null, range);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(content.getBytes(charset));

        if(range != null) {
            outputStream.write(LF, 0, 1);
            range.write(outputStream);
        }
        outputStream.flush();
    }

    /**
     * @param response
     * @param raf
     * @param charset
     * @param range
     * @throws IOException
     */
    public static void success(HttpServletResponse response, RandomAccessFile raf, String charset, FileRange range) throws IOException {
        response.setContentType("text/plain; charset=" + charset);
        write(raf, response.getOutputStream(), charset, range);
    }

    /**
     * @param raf
     * @param outputStream
     * @param charset
     * @param range
     * @throws IOException
     */
    public static void write(RandomAccessFile raf, OutputStream outputStream, String charset, FileRange range) throws IOException {
        String content = getReturnValue(200, null, range);
        outputStream.write(content.getBytes(charset));

        if(range != null) {
            outputStream.write(LF, 0, 1);

            if(range.getCount() > 0) {
                if(range.getStart() > -1) {
                    raf.seek(range.getStart());
                }
                IO.copy(raf, outputStream, range.getCount());
            }
        }
        outputStream.flush();
    }

    /**
     * @param response
     * @param charset
     * @param status
     * @param message
     * @throws IOException
     */
    public static void error(HttpServletResponse response, String charset, int status, String message) throws IOException {
        String content = getReturnValue(status, message, null);
        response.setContentType("text/plain; charset=" + charset);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(content.getBytes(charset));
        outputStream.flush();
    }

    /**
     * @param response
     * @param url
     * @throws IOException
     */
    public static void redirect(HttpServletResponse response, String url) throws IOException {
        String content = "(function(){window.location.href=\"\" + url + \"\";})()";
        byte[] buffer = content.getBytes("utf-8");
        response.setContentType("text/javascript; charset=utf-8");
        response.setContentLength(buffer.length);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(buffer);
        outputStream.flush();
    }

    /**
     * @param status
     * @param message
     * @param range
     * @return String
     */
    public static String getReturnValue(int status, String message, FileRange range) {
        StringBuilder buffer = new StringBuilder(128);
        buffer.append("{\"status\":");
        buffer.append(status);

        if(message != null) {
            buffer.append(",\"message\":\"");
            buffer.append(StringUtil.escape(message));
            buffer.append("\"");
        }

        if(range != null) {
            buffer.append(",\"value\":{\"start\":");
            buffer.append(range.getStart());
            buffer.append(",\"end\":");
            buffer.append(range.getEnd());
            buffer.append(",\"size\":");
            buffer.append((range.getEnd() - range.getStart()));
            buffer.append(",\"length\":");
            buffer.append(range.getLength());

            if(range.getTimestamp() > 0L) {
                buffer.append(",\"lastModified\":");
                buffer.append(range.getTimestamp());
            }

            buffer.append(",\"rows\":");
            buffer.append(range.getRows());
            buffer.append("}");
        }
        buffer.append("}");
        return buffer.toString();
    }
}
