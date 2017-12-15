/*
 * $RCSfile: ServletInfo.java,v $
 * $Revision: 1.1 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.finder.util;

import javax.servlet.ServletContext;

/**
 * <p>Title: ServletInfo</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ServletInfo {
    private String serverInfo = null;
    private int majorVersion = 0;
    private int minorVersion = 0;
    private String servletVersion = null;

    /**
     * @param servletContext
     */
    public ServletInfo(ServletContext servletContext) {
        this.serverInfo = servletContext.getServerInfo();
        this.majorVersion = servletContext.getMajorVersion();
        this.minorVersion = servletContext.getMinorVersion();
        this.servletVersion = new StringBuffer().append(this.majorVersion).append(".").append(this.minorVersion).toString();
    }

    /**
     * @return the serverInfo
     */
    public String getServerInfo() {
        return this.serverInfo;
    }

    /**
     * @param serverInfo the serverInfo to set
     */
    public void setServerInfo(String serverInfo) {
        this.serverInfo = serverInfo;
    }

    /**
     * @return the majorVersion
     */
    public int getMajorVersion() {
        return this.majorVersion;
    }

    /**
     * @param majorVersion the majorVersion to set
     */
    public void setMajorVersion(int majorVersion) {
        this.majorVersion = majorVersion;
    }

    /**
     * @return the minorVersion
     */
    public int getMinorVersion() {
        return this.minorVersion;
    }

    /**
     * @param minorVersion the minorVersion to set
     */
    public void setMinorVersion(int minorVersion) {
        this.minorVersion = minorVersion;
    }

    /**
     * @return the servletVersion
     */
    public String getServletVersion() {
        return this.servletVersion;
    }

    /**
     * @param servletVersion the servletVersion to set
     */
    public void setServletVersion(String servletVersion) {
        this.servletVersion = servletVersion;
    }
}
