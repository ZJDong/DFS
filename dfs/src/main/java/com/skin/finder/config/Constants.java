/*
 * $RCSfile: Constants.java,v $
 * $Revision: 1.1 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.finder.config;

/**
 * <p>Title: Constants</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Constants {
    /**
     * 应用ID
     */
    public static final long APP_ID = 1L;

    /**
     * 集群中当前机器的名称, 全局唯一
     */
    public static final String CLUSTER_NODE_NAME = "finder.cluster.node.name";

    /**
     * 集群中master机器的名称
     */
    public static final String CLUSTER_MASTER_NAME = "finder.cluster.master.name";

    /**
     * 集群间通信安全KEY
     */
    public static final String CLUSTER_SECURITY_KEY = "finder.cluster.security.key";

    /**
     * 系统超级管理员帐号
     */
    public static final String CLUSTER_SECURITY_ROOT = "finder.security.root";

    /**
     * 用户会话md5 key
     */
    public static final String SESSION_KEY = "finder.session.key";

    /**
     * 用户会话有效期
     */
    public static final String SESSION_NAME = "finder.session.name";

    /**
     * 用户会话有效期
     */
    public static final String SESSION_TIMEOUT = "finder.session.timeout";

    /**
     * 文本文件类型
     */
    public static final String TEXT_TYPE = "finder.text.type";

    /**
     * 首页是否显示导航条
     */
    public static final String DISPLAY_NAV_BAR = "finder.display.nav-bar";

    /**
     * 文件列表页面显示的按钮
     */
    public static final String DISPLAY_OPERATE_BUTTON = "finder.display.operate-button";

    /**
     * 客户端上传大文件的分片大小
     */
    public static final String UPLOAD_PART_SIZE = "finder.upload.part-size";

    /**
     * 版本检查
     */
    public static final String UPDATE_CHECK = "finder.update.check";

    /**
     * 演示账号用户
     */
    public static final String DEMO_USERNAME = "finder.demo.userName";

    /**
     * 演示账号密码
     */
    public static final String DEMO_PASSWORD = "finder.demo.password";

    /**
     * 统计代码
     */
    public static final String ACCESS_CODE = "finder.access.code";

    /**
     * 配置文件版本号
     */
    public static final String CONF_VERSION = "finder.conf.version";
}
