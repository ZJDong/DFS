/*
 * $RCSfile: LogChatServlet.java,v $
 * $Revision: 1.1 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.finder.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.finder.cluster.Agent;
import com.skin.finder.util.Ajax;
import com.skin.finder.util.StringUtil;
import com.skin.finder.web.UrlPattern;
import com.skin.finder.web.servlet.BaseServlet;
import com.skin.finder.web.util.CurrentUser;

/**
 * <p>Title: LogChatServlet</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class LogChatServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(LogChatServlet.class);

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @UrlPattern("finder.chat")
    public void chat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.forward(request, response, "/template/finder/logChat.jsp");
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @UrlPattern("finder.chat.send")
    public void send(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 每台机器一个房间
         */
        if(Agent.dispatch(request, response)) {
            return;
        }

        String userName = CurrentUser.getUserName(request);
        String nickName = request.getParameter("nickName");
        String roomName = request.getParameter("roomName");
        String message = request.getParameter("message");

        if(StringUtil.isBlank(nickName)) {
            nickName = userName;
        }

        if(StringUtil.isBlank(roomName)) {
            Ajax.error(request, response, "bad room.");
            return;
        }

        if(StringUtil.isBlank(message)) {
            Ajax.error(request, response, "bad message.");
            return;
        }

        if(message.length() > 200) {
            Ajax.error(request, response, "message is too large.");
            return;
        }

        String json = this.getMessage(message);
        logger.info(json);
        Ajax.success(request, response, "true");
    }

    /**
     * @param message
     * @return String
     */
    public String getMessage(String message) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("{\"status\":200,\"message\":\"");
        buffer.append(StringUtil.escape(message));
        buffer.append("\"}");
        return message;
    }
}
