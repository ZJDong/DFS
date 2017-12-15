package com.skin.finder.client;

import java.io.InputStream;

/**
 * <p>Title: FinderClient</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author Admin
 * @version 1.0
 */
public interface FinderClient {
    /**
     * @return boolean
     */
    boolean signin();

    /**
     * @return boolean
     */
    boolean signout();

    /**
     * @return boolean
     */
    boolean isSignin();

    /**
     * @param host
     * @param workspace
     * @param path
     * @return String
     */
    String getFileList(String host, String workspace, String path);

    /**
     * @param host
     * @param workspace
     * @param path
     * @param oldName
     * @param newName
     * @return rename
     */
    String rename(String host, String workspace, String path, String oldName, String newName);

    /**
     * @param host
     * @param workspace
     * @param path
     * @param name
     * @return String
     */
    String mkdir(String host, String workspace, String path, String name);

    /**
     * @param host
     * @param workspace
     * @param path
     * @param names
     * @return String
     */
    String delete(String host, String workspace, String path, String[] names);

    /**
     * 接口需要重新定义
     * @param host
     * @param workspace
     * @param path
     * @param names
     * @return String
     */
    String copy(String host, String workspace, String path);

    /**
     * 接口需要重新定义
     * @param host
     * @param workspace
     * @param path
     * @param names
     * @return String
     */
    String cut(String host, String workspace, String path);

    /**
     * @param host
     * @param workspace
     * @param path
     * @param inputStream
     * @return String
     */
    String upload(String host, String workspace, String path, InputStream inputStream);

    /**
     * @return the url
     */
    String getUrl();

    /**
     * @return the userName
     */
    String getUserName();

    /**
     * @return the password
     */
    String getPassword();
}
