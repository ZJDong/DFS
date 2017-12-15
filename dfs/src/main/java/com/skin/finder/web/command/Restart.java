/*
 * $RCSfile: Restart.java,v $
 * $Revision: 1.1 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.finder.web.command;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.skin.finder.util.DateUtil;
import com.skin.finder.util.IO;
import com.skin.finder.util.StringUtil;

/**
 * <p>Title: Restart</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Restart {
    /**
     * @param args
     */
    public static void main(String[] args) {
        if(args == null || args.length < 2) {
            System.out.println("Usage:");
            System.out.println("com.skin.finder.web.command.Restart [PID] [WORK]");
            System.exit(1);
            return;
        }
        execute(args[0], args[1]);
    }

    /**
     * @param delay
     * @return boolean
     */
    public static boolean execute(long delay) {
        new RestartThread(delay).start();
        return true;
    }

    /**
     * @param processId
     * @param work
     * @return boolean
     */
    public static boolean execute(String processId, String work) {
        String cmd = getRestartCommand(work);
        String currentId = Self.getProcessId();
        String message = "restart - currentId: " + currentId + ", processId: " + processId + ", work: " + work;
        System.out.println(message);
        info(message);

        if(cmd == null) {
            System.out.println("restart failed, cmd is null.");
            return false;
        }

        Self.kill(90000L);

        try {
            System.out.println("execute: " + cmd);
            ProcessBuilder processBuilder = new ProcessBuilder(cmd, processId);
            processBuilder.directory(new File(work));
            Process process = processBuilder.start();

            new ReadThread("errout", process.getErrorStream()).start();
            new ReadThread("stdout", process.getInputStream()).start();

            int exitCode = process.waitFor();
            System.out.println("exitCode: " + exitCode);
            System.out.println("wait start tomcat");
            Thread.sleep(2000);
            process.destroy();
            return true;
        }
        catch(Exception e) {
            e.printStackTrace(System.out);
        }
        info(currentId + " exit.");
        return false;
    }

    /**
     * @param work
     * @return String
     */
    private static String getRestartCommand(String work) {
        File file = null;

        if(OS.WINDOWS) {
            file = new File(work, "/finder_restart.bat");
        }
        else {
            file = new File(work, "/finder_restart.sh");
        }

        if(file.exists() && file.isFile()) {
            return file.getAbsolutePath();
        }

        OutputStream outputStream = null;
        Map<String, String> params = new HashMap<String, String>();
        params.put("work.directory", work);

        try {
            String cmd = build(params);
            outputStream = new FileOutputStream(file);
            outputStream.write(cmd.toString().getBytes("utf-8"));
            outputStream.flush();

            file.setReadable(true, true);
            file.setWritable(true, true);
            file.setExecutable(true, true);
            return file.getAbsolutePath();
        }
        catch(Exception e) {
            e.printStackTrace(System.out);
        }
        finally {
            IO.close(outputStream);
        }
        return null;
    }

    /**
     * @param params
     * @return String
     * @throws IOException
     */
    public static String build(Map<String, String> params) throws IOException {
        if(OS.WINDOWS) {
            return build("restart.windows.bat", params);
        }
        else {
            String cmd = build("restart.linux.sh", params);
            return StringUtil.remove(cmd, '\r');
        }
    }

    /**
     * @param name
     * @param params
     * @return String
     * @throws IOException
     */
    public static String build(String name, Map<String, String> params) throws IOException {
        InputStream inputStream = Restart.class.getResourceAsStream(name);

        if(inputStream != null) {
            String content = IO.toString(inputStream, "utf-8");
            return StringUtil.replace(content, params, '#');
        }
        else {
            throw new IOException(name + " not exists.");
        }
    }

    /**
     * @param message
     */
    private static void info(String message) {
        FileWriter fileWriter = null;
        File logFile = new File("finder_restart.log");

        try {
            if(!logFile.exists()) {
                logFile.createNewFile();
                logFile.setReadable(true, true);
                logFile.setWritable(true, true);
                logFile.setExecutable(true, true);
            }

            fileWriter = new FileWriter(logFile, true);
            fileWriter.write(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss SSS"));
            fileWriter.write(" [INFO] ");
            fileWriter.write(message);
            fileWriter.write("\r\n");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            IO.close(fileWriter);
        }
    }
}
