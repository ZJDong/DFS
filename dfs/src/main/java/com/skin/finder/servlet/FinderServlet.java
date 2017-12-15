/*
 * $RCSfile: FinderServlet.java,v $
 * $Revision: 1.1 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.finder.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.finder.FileItem;
import com.skin.finder.FileItemList;
import com.skin.finder.FileRange;
import com.skin.finder.FileType;
import com.skin.finder.Finder;
import com.skin.finder.FinderManager;
import com.skin.finder.cluster.Agent;
import com.skin.finder.cluster.WorkspaceManager;
import com.skin.finder.config.ConfigFactory;
import com.skin.finder.security.AccessController;
import com.skin.finder.servlet.page.Display;
import com.skin.finder.servlet.template.DisplayTemplate;
import com.skin.finder.servlet.template.FinderTemplate;
import com.skin.finder.servlet.template.PlayTemplate;
import com.skin.finder.util.Ajax;
import com.skin.finder.util.IO;
import com.skin.finder.util.IP;
import com.skin.finder.util.Path;
import com.skin.finder.util.StringUtil;
import com.skin.finder.util.UpdateChecker;
import com.skin.finder.web.Startup;
import com.skin.finder.web.UrlPattern;
import com.skin.finder.web.servlet.FileServlet;
import com.skin.finder.web.upload.MultipartHttpRequest;
import com.skin.finder.web.upload.Part;
import com.skin.finder.web.util.CurrentUser;

/**
 * <p>Title: FinderServlet</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
@Startup(value = 0)
public class FinderServlet extends FileServlet {
    private static final long serialVersionUID = 1L;
    private static final Map<String, String> map = new HashMap<String, String>();
    private static final Logger logger = LoggerFactory.getLogger(FinderServlet.class);
    private static final Logger accessLogger = LoggerFactory.getLogger("accessLogger");

    static{
        map.put("exe",    "exe");
        map.put("bin",    "bin");
        map.put("class",  "class");
        map.put("swf",    "swf");
        map.put("ico",    "ico");
        map.put("jpg",    "jpg");
        map.put("jpeg",   "jpeg");
        map.put("gif",    "gif");
        map.put("bmp",    "bmp");
        map.put("png",    "png");
        map.put("pdf",    "pdf");
        map.put("doc",    "doc");
        map.put("zip",    "zip");
        map.put("rar",    "rar");
        map.put("jar",    "jar");
        map.put("ear",    "ear");
        map.put("war",    "war");
    }

    /**
     * default
     */
    public FinderServlet() {
        logger.info("init");

        /**
         * 日志级别不能低于info
         */
        if(!logger.isInfoEnabled()) {
            throw new RuntimeException("logger init failed.");
        }

        if(!accessLogger.isInfoEnabled()) {
            throw new RuntimeException("logger init failed.");
        }

        if(ConfigFactory.getUpdateCheck()) {
            UpdateChecker.start();
        }
    }

    /**
     *
     */
    @Override
    public void init() {
    }

    /**
     * @param servletConfig
     */
    @Override
    public void init(ServletConfig servletConfig) {
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Display.error(request, response, 404, "Not Found !");
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @UrlPattern("finder.getFile")
    public void getFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Agent.dispatch(request, response)) {
            return;
        }

        String userName = CurrentUser.getUserName(request);
        String workspace = request.getParameter("workspace");
        String path = request.getParameter("path");
        String work = Finder.getWork(request, workspace);

        if(work == null) {
            Ajax.error(request, response, "workspace not exists !");
            return;
        }

        String realPath = Finder.getRealPath(work, path);

        if(realPath == null) {
            Ajax.error(request, response, path + " not exists !");
            return;
        }

        if(!AccessController.getRead(userName, workspace, path)) {
            Ajax.denied(request, response);
            return;
        }

        File file = new File(realPath);

        if(!file.exists() || !file.isFile()) {
            Ajax.error(request, response, "file not exists.");
            return;
        }

        FileItem fileItem = FinderManager.getFileItem(file);
        Ajax.success(request, response, FileItemList.getJSONString(fileItem));
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @UrlPattern("finder.getFileList")
    public void getFileList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Agent.dispatch(request, response)) {
            return;
        }

        String userName = CurrentUser.getUserName(request);
        String workspace = request.getParameter("workspace");
        String path = request.getParameter("path");
        String work = Finder.getWork(request, workspace);

        if(work == null) {
            Ajax.error(request, response, "workspace not exists !");
            return;
        }

        String realPath = Finder.getRealPath(work, path);

        if(realPath == null) {
            Ajax.error(request, response, path + " not exists !");
            return;
        }

        File file = new File(realPath);

        if(!file.exists() || !file.isDirectory()) {
            Ajax.error(request, response, path + " is not directory !");
            return;
        }

        long t1 = System.currentTimeMillis();
        FinderManager finderManager = new FinderManager(work);
        FileItemList fileItemList = finderManager.list(userName, workspace, path);
        Ajax.success(request, response, fileItemList.getJSONString());
        long t2 = System.currentTimeMillis();

        if(logger.isDebugEnabled()) {
            logger.debug("time: {}", (t2 - t1));
        }
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @UrlPattern("finder.display")
    public void display(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Agent.dispatch(request, response)) {
            return;
        }

        String userName = CurrentUser.getUserName(request);
        String workspace = request.getParameter("workspace");
        String path = request.getParameter("path");
        String home = Finder.getWork(request, workspace);

        if(home == null) {
            Display.error(request, response, 404, "The workspace \"" + workspace + "\" does not exist or is not a directory!");
            return;
        }

        String realPath = Finder.getRealPath(home, path);

        if(realPath == null) {
            Display.error(request, response, 404, path + " not exists !");
            return;
        }

        File file = new File(realPath);

        if(!file.exists()) {
            Display.error(request, response, 404, path + " not exists !");
            return;
        }

        String relativePath = Path.getRelativePath(home, realPath);

        if(StringUtil.isBlank(relativePath)) {
            relativePath = "/";
        }

        if(!AccessController.getRead(userName, workspace, relativePath)) {
            Display.denied(request, response);
            return;
        }

        if(file.isDirectory()) {
            int mode = AccessController.getMode(userName, workspace, relativePath);
            request.setAttribute("localIp", IP.LOCAL);
            request.setAttribute("host", ConfigFactory.getHostName());
            request.setAttribute("workspace", workspace);
            request.setAttribute("path", relativePath);
            request.setAttribute("mode", mode);
            FinderTemplate.execute(request, response);
            return;
        }

        String type = request.getParameter("type");
        String encoding = request.getParameter("encoding");
        String theme = request.getParameter("theme");

        if(type == null || type.length() < 1) {
            type = FileType.getExtension(path).toLowerCase();
        }
        else {
            type = type.toLowerCase();
        }

        if(theme == null || theme.length() < 1) {
            theme = "Default";
        }

        if(map.get(type) != null) {
            this.execute(request, response, false);
            return;
        }

        long start = 0L;
        long end = 0L;
        long length = file.length();
        long maxSize = 256L * 1024L;
        FileRange range = null;
        String charset = encoding;

        if(charset == null || charset.trim().length() < 1) {
            charset = "utf-8";
        }

        if(length > maxSize) {
            long offset = length - maxSize;
            range = this.getRange(file, offset, charset);
        }
        else if(length > 0L) {
            range = this.getRange(file, 0L, charset);
        }

        String content = "";

        if(range != null) {
            start = range.getStart();
            end = range.getEnd();
            content = getString(file, start, (int)(range.getCount()), charset);
        }

        request.setAttribute("localIp", IP.LOCAL);
        request.setAttribute("host", ConfigFactory.getHostName());
        request.setAttribute("workspace", workspace);
        request.setAttribute("path", relativePath);
        request.setAttribute("content", content);
        request.setAttribute("encoding", encoding);
        request.setAttribute("type", type);
        request.setAttribute("theme", theme);
        request.setAttribute("start", start);
        request.setAttribute("end", end);
        request.setAttribute("length", length);
        DisplayTemplate.execute(request, response);
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @UrlPattern("finder.play")
    public void play(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Agent.dispatch(request, response)) {
            return;
        }

        String userName = CurrentUser.getUserName(request);
        String workspace = request.getParameter("workspace");
        String path = request.getParameter("path");
        String home = Finder.getWork(request, workspace);

        if(home == null) {
            Display.error(request, response, 404, "The workspace \"" + workspace + "\" does not exist or is not a directory!");
            return;
        }

        String realPath = Finder.getRealPath(home, path);

        if(realPath == null) {
            Display.denied(request, response);
            return;
        }

        if(!AccessController.getRead(userName, workspace, path)) {
            Display.denied(request, response);
            return;
        }

        File file = new File(realPath);

        if(!file.exists() || file.isDirectory()) {
            Display.error(request, response, 404, path + " not exists !");
            return;
        }

        String relativePath = Path.getRelativePath(home, realPath);
        request.setAttribute("localIp", IP.LOCAL);
        request.setAttribute("host", ConfigFactory.getHostName());
        request.setAttribute("workspace", workspace);
        request.setAttribute("work", home);
        request.setAttribute("path", relativePath);
        PlayTemplate.execute(request, response);
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @UrlPattern("finder.download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Agent.dispatch(request, response)) {
            return;
        }
        this.execute(request, response, true);
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @UrlPattern("finder.suggest")
    public void suggest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Agent.dispatch(request, response)) {
            return;
        }

        String userName = CurrentUser.getUserName(request);
        String path = request.getParameter("path");
        String workspace = request.getParameter("workspace");
        String home = Finder.getWork(request, workspace);

        if(home == null) {
            Ajax.error(request, response, 404, "workspace not exists.");
            return;
        }

        FinderManager finderManager = new FinderManager(home);
        List<FileItem> fileItemList = finderManager.suggest(userName, workspace, path);
        String json = FileItemList.getJSONString(fileItemList);
        Ajax.success(request, response, json);
        return;
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @UrlPattern("finder.rename")
    public void rename(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Agent.dispatch(request, response)) {
            return;
        }

        String userName = CurrentUser.getUserName(request);
        String workspace = request.getParameter("workspace");
        String path = request.getParameter("path");
        String newName = request.getParameter("newName");
        String home = Finder.getWork(request, workspace);

        if(home == null) {
            Ajax.error(request, response, 403, "Workspace not exists.");
            return;
        }

        if(WorkspaceManager.getReadonly(workspace)) {
            Ajax.error(request, response, 403, "Workspace is readonly.");
            return;
        }

        String parent = Path.getParent(path);

        if(!AccessController.getWrite(userName, workspace, parent)) {
            Ajax.denied(request, response);
            return;
        }

        FinderManager finderManager = new FinderManager(home);
        int count = finderManager.rename(path, newName);
        Ajax.success(request, response, Boolean.toString(count > 0));
        return;
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @UrlPattern("finder.mkdir")
    public void mkdir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Agent.dispatch(request, response)) {
            return;
        }

        String userName = CurrentUser.getUserName(request);
        String workspace = request.getParameter("workspace");
        String work = Finder.getWork(request, workspace);

        if(work == null) {
            Ajax.error(request, response, 403, "Workspace not exists.");
            return;
        }

        String path = request.getParameter("path");
        String name = request.getParameter("name");

        if(WorkspaceManager.getReadonly(workspace)) {
            Ajax.error(request, response, 403, "Workspace is readonly.");
            return;
        }

        if(!AccessController.getWrite(userName, workspace, path)) {
            Ajax.denied(request, response);
            return;
        }

        FinderManager finderManager = new FinderManager(work);
        File dir = finderManager.mkdir(path, name);
        Ajax.success(request, response, Boolean.toString(dir != null));
        return;
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @UrlPattern("finder.upload")
    public void upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Agent.dispatch(request, response)) {
            return;
        }

        String userName = CurrentUser.getUserName(request);
        String workspace = request.getParameter("workspace");
        String path = request.getParameter("path");
        String work = Finder.getWork(request, workspace);

        if(work == null) {
            Ajax.error(request, response, 403, "Workspace not exists.");
            return;
        }

        if(WorkspaceManager.getReadonly(workspace)) {
            Ajax.error(request, response, 403, "Workspace is readonly.");
            return;
        }

        if(!AccessController.getWrite(userName, workspace, path)) {
            Ajax.denied(request, response);
            return;
        }

        int maxFileSize = 20 * 1024 * 1024;
        int maxBodySize = 24 * 1024 * 1024;
        String repository = System.getProperty("java.io.tmpdir");
        MultipartHttpRequest multipartRequest = null;
        Part uploadFile = null;

        try {
            multipartRequest = MultipartHttpRequest.parse(request, maxFileSize, maxBodySize, repository);
            uploadFile = multipartRequest.getFileItem("uploadFile");

            if(uploadFile == null || !uploadFile.isFileField()) {
                Ajax.error(request, response, 404, "Bad Request.");
                return;
            }

            String fileName = uploadFile.getFileName();
            String realPath = Finder.getRealPath(work, path);
            logger.info("fileName: {}", fileName);

            if(fileName.endsWith(".link.tail")) {
                Ajax.denied(request, response);
                return;
            }

            if(realPath == null) {
                Ajax.denied(request, response);
                return;
            }

            File dir = new File(realPath);

            if(!dir.exists() || !dir.isDirectory()) {
                Ajax.error(request, response, 404, "File not exists.");
                return;
            }

            boolean exists = true;
            File target = new File(dir, fileName);
            long offset = multipartRequest.getLong("offset", 0L);
            long lastModified = multipartRequest.getLong("lastModified", 0L);

            if(!target.exists()) {
                try {
                    exists = target.createNewFile();
                }
                catch(IOException e) {
                    exists = false;
                    logger.error(e.getMessage(), e);
                }
            }

            if(!exists) {
                Ajax.error(request, response, 500, "Create file failed.");
                return;
            }

            IO.write(uploadFile.getFile(), target, offset, lastModified);
            Ajax.success(request, response, Boolean.toString(true));
            return;
        }
        catch(Exception e) {
            Ajax.error(request, response, 500, "finder.system.error");
            return;
        }
        finally {
            if(uploadFile != null) {
                uploadFile.delete();
            }
            if(multipartRequest != null) {
                multipartRequest.destroy();
            }
        }
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @UrlPattern("finder.cut")
    public void cut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Agent.dispatch(request, response)) {
            return;
        }

        String workspace = request.getParameter("workspace");

        if(WorkspaceManager.getReadonly(workspace)) {
            Ajax.error(request, response, 501, "Workspace is readonly.");
            return;
        }
        this.move(request, response, true);
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @UrlPattern("finder.copy")
    public void copy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Agent.dispatch(request, response)) {
            return;
        }

        String workspace = request.getParameter("workspace");

        if(WorkspaceManager.getReadonly(workspace)) {
            Ajax.error(request, response, 501, "Workspace is readonly.");
            return;
        }
        this.move(request, response, false);
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @UrlPattern("finder.delete")
    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Agent.dispatch(request, response)) {
            return;
        }

        String userName = CurrentUser.getUserName(request);
        String workspace = request.getParameter("workspace");
        String[] path = request.getParameterValues("path");

        if(WorkspaceManager.getReadonly(workspace)) {
            Ajax.error(request, response, 501, "Workspace is readonly.");
            return;
        }

        if(path == null || path.length < 1) {
            Ajax.error(request, response, 501, "Bad Request.");
            return;
        }

        if(!AccessController.getDelete(userName, workspace, path)) {
            Ajax.denied(request, response);
            return;
        }

        int count = 0;
        String home = Finder.getWork(request, workspace);

        if(home == null) {
            Ajax.error(request, response, 403, "Workspace not exists.");
            return;
        }

        FinderManager finderManager = new FinderManager(home);

        for(int i = 0; i < path.length; i++) {
            count += finderManager.delete(path[i]);
        }
        Ajax.success(request, response, Integer.toString(count));
        return;
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @UrlPattern("finder.save")
    public void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Agent.dispatch(request, response)) {
            return;
        }

        String userName = CurrentUser.getUserName(request);
        String workspace = request.getParameter("workspace");
        String path = request.getParameter("path");
        String content = request.getParameter("content");
        String home = Finder.getWork(request, workspace);

        if(home == null) {
            Ajax.error(request, response, 403, "Workspace not exists.");
            return;
        }

        if(WorkspaceManager.getReadonly(workspace)) {
            Ajax.error(request, response, 403, "Workspace is readonly.");
            return;
        }

        String realPath = Finder.getRealPath(home, path);

        if(realPath == null) {
            Ajax.error(request, response, 404, path + " not exists !");
            return;
        }

        if(!AccessController.getWrite(userName, workspace, path)) {
            Ajax.denied(request, response);
            return;
        }

        String parent = Path.getParent(path);

        if(!AccessController.getWrite(userName, workspace, parent)) {
            Ajax.denied(request, response);
            return;
        }

        File file = new File(realPath);

        if(content != null) {
            IO.write(file, content.getBytes("utf-8"));
        }
        else {
            IO.touch(file);
        }
        Ajax.success(request, response, "true");
        return;
    }

    /**
     * @param request
     * @param response
     * @param download
     * @throws ServletException
     * @throws IOException
     */
    private void execute(HttpServletRequest request, HttpServletResponse response, boolean download) throws ServletException, IOException {
        String userName = CurrentUser.getUserName(request);
        String workspace = request.getParameter("workspace");
        String path = request.getParameter("path");
        String home = Finder.getWork(request, workspace);

        if(home == null) {
            Ajax.error(request, response, 403, "Workspace not exists.");
            return;
        }

        String realPath = Finder.getRealPath(home, path);

        if(realPath == null) {
            logger.debug("can't access - {}: {}: {}", workspace, home, path);
            response.setStatus(404);
            return;
        }

        File file = new File(realPath);

        if(file.isDirectory()) {
            this.display(request, response);
            return;
        }

        if(!AccessController.getRead(userName, workspace, path)) {
            Display.denied(request, response);
            return;
        }

        if(file.exists() == false) {
            logger.debug("file not exists: {}", file.getAbsolutePath());
            response.setStatus(404);
            return;
        }
        this.service(request, response, file, download);
    }

    /**
     * @param request
     * @param response
     * @param delete
     * @throws ServletException
     * @throws IOException
     */
    private void move(HttpServletRequest request, HttpServletResponse response, boolean delete) throws ServletException, IOException {
        String userName = CurrentUser.getUserName(request);
        String sourceWorkspace = request.getParameter("sourceWorkspace");
        String sourcePath = request.getParameter("sourcePath");
        String targetWorkspace = request.getParameter("workspace");
        String targetPath = request.getParameter("path");
        String[] fileList = request.getParameterValues("file");
        String sourceHome = Finder.getWork(request, sourceWorkspace);
        String targetHome = Finder.getWork(request, targetWorkspace);

        if(sourceHome == null) {
            Ajax.error(request, response, 403, "sourceHome not exists.");
            return;
        }

        if(targetHome == null) {
            Ajax.error(request, response, 403, "targetHome not exists.");
            return;
        }

        if(fileList == null || fileList.length < 1) {
            Ajax.error(request, response, 404, "Bad Request.");
            return;
        }

        if(!AccessController.getRead(userName, sourceWorkspace, sourcePath)) {
            Ajax.denied(request, response);
            return;
        }

        if(!AccessController.getWrite(userName, targetWorkspace, targetPath)) {
            Ajax.denied(request, response);
            return;
        }

        if(delete) {
            for(String file : fileList) {
                if(!AccessController.getDelete(userName, sourceWorkspace, sourcePath + "/" + file)) {
                    Ajax.denied(request, response);
                    return;
                }
            }
        }

        for(String file : fileList) {
            String sourceFile = Finder.getRealPath(sourceHome, sourcePath + "/" + file);
            String targetFile = Finder.getRealPath(targetHome, targetPath + "/" + file);

            File f1 = new File(sourceFile);
            File f2 = new File(targetFile);

            if(f1.equals(f2)) {
                if(delete) {
                    continue;
                }
                else {
                    f2 = Finder.getFile(f2.getParentFile(), f2.getName());
                }
            }

            IO.copy(f1, f2, true);

            if(delete) {
                IO.delete(f1);
            }
        }
        Ajax.success(request, response, "true");
        return;
    }

    /**
     * @param file
     * @param offset
     * @param charset
     * @return String
     */
    private FileRange getRange(File file, long offset, String charset) {
        RandomAccessFile raf = null;

        try {
            raf = new RandomAccessFile(file, "r");

            byte LF = 0x0A;
            int readBytes = 0;
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];

            long start = offset;
            long length = raf.length();
            raf.seek(start);

            if(offset > 0) {
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
                    range.setLength(length);
                    range.setRows(-1);
                    return range;
                }
            }

            readBytes = Math.max((int)(length - start), 0);

            FileRange range = new FileRange();
            range.setStart(start);
            range.setEnd(start + readBytes - 1);
            range.setCount(readBytes);
            range.setLength(length);
            range.setRows(-1);
            return range;
        }
        catch(IOException e) {
        }
        finally {
            IO.close(raf);
        }
        return null;
    }

    /**
     * @param file
     * @param start
     * @param length
     * @param charset
     * @return String
     * @throws IOException
     */
    private String getString(File file, long start, int length, String charset) throws IOException {
        InputStream inputStream = null;
        byte[] bytes = new byte[length];

        try {
            inputStream = new FileInputStream(file);

            if(start > 0) {
                inputStream.skip(start);
            }

            int readBytes = inputStream.read(bytes, 0, length);
            return new String(bytes, 0, readBytes, charset);
        }
        finally {
            IO.close(inputStream);
        }
    }

    /**
     * destory
     */
    @Override
    public void destroy() {
        UpdateChecker.shutdown();
    }
}
