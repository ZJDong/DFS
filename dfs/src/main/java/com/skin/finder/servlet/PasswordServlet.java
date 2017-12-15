/*
 * $RCSfile: UserServlet.java,v $
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

import com.skin.finder.cluster.Agent;
import com.skin.finder.config.ConfigFactory;
import com.skin.finder.security.SimpleUserManager;
import com.skin.finder.security.User;
import com.skin.finder.servlet.template.PasswordTemplate;
import com.skin.finder.util.Ajax;
import com.skin.finder.util.Password;
import com.skin.finder.util.RandomUtil;
import com.skin.finder.util.StringUtil;
import com.skin.finder.web.UrlPattern;
import com.skin.finder.web.servlet.BaseServlet;
import com.skin.finder.web.util.CurrentUser;

/**
 * <p>Title: UserServlet</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class PasswordServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @UrlPattern("user.password")
    public void password(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PasswordTemplate.execute(request, response);
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @UrlPattern("user.password.update")
    public void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String master = ConfigFactory.getMaster();

        if(Agent.dispatch(request, response, master, true)) {
            return;
        }

        String userName = CurrentUser.getUserName(request);
        String oldPassword = this.getTrimString(request, "oldPassword");
        String newPassword = this.getTrimString(request, "newPassword");

        if(StringUtil.isBlank(oldPassword)) {
            Ajax.error(request, response, 504, "finder.system.password.oldpassword.empty.");
            return;
        }

        if(StringUtil.isBlank(oldPassword)) {
            Ajax.error(request, response, 504, "finder.system.password.newPassword.empty.");
            return;
        }

        SimpleUserManager userManager = SimpleUserManager.getInstance();
        User user = userManager.getByName(userName);

        if(user == null) {
            Ajax.error(request, response, 504, "finder.system.user.not.exists.");
            return;
        }

        String oldSalt = user.getUserSalt();
        String oldPass = user.getPassword();
        String oldText = Password.encode(oldPassword, oldSalt);

        if(!oldPass.equals(oldText)) {
            Ajax.error(request, response, 504, "finder.system.user.oldpassword.wrong.");
            return;
        }

        String newSalt = RandomUtil.getRandString(8, 8);
        String newPass = Password.encode(newPassword, newSalt);

        user.setUserName(userName);
        user.setUserSalt(newSalt);
        user.setPassword(newPass);
        userManager.update(user);
        Ajax.success(request, response, "true");
        return;
    }
}
