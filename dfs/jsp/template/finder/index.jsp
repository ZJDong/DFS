<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.skin.finder.config.ConfigFactory"%>
<%@ page import="com.skin.finder.i18n.I18N"%>
<%@ page import="com.skin.finder.i18n.LocalizationContext"%>
<%
    LocalizationContext i18n = I18N.getBundle(request);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Finder - Powered by Finder</title>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<link rel="shortcut icon" href="?action=res&path=/finder/images/favicon.png"/>
<link rel="stylesheet" type="text/css" href="?action=res&path=/finder/css/frame.css"/>
<script type="text/javascript">
<!--
(function() {
    if(window.parent != window) {
        window.top.location.href = window.location.href;
        return;
    }
})();
//-->
</script>
<script type="text/javascript" src="?action=res&path=/finder/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="?action=res&path=/finder/widget.js"></script>
<script type="text/javascript" src="?action=res&path=/finder/fileupload.js"></script>
<script type="text/javascript" src="?action=res&path=/finder/index.js"></script>
</head>
<body localIp="${localIp}" userName="${userName}">
<div class="apptop">
    <a href="http://www.finderweb.net" target="_blank"><div class="logo" title="http://www.finderweb.net"></div></a>
    <div style="margin-left: 300px; height: 60px;">
        <div style="height: 28px;">
            <div id="tools-menu" class="tools-menu" style="display: none;">
                <ul>
                    <li id="tools-home" class="icon-01" onclick="App.home();"><%=i18n.format("finder.index.home")%></li>
                    <li id="tools-setting" class="icon-07" onclick="App.setting();"><%=i18n.format("finder.index.setting")%></li>
                    <li id="tools-help" class="icon-08" onclick="App.help();"><%=i18n.format("finder.index.help")%></li>
                    <li id="tools-logout" class="icon-09" onclick="App.logout();"><%=i18n.format("finder.index.logout")%></li>
                </ul>
            </div>
        </div>
        <div class="menu-bar">
            <ul>
                <!-- li class="active" onclick="window.location.href='${requestURI}';"><%=i18n.format("finder.index.home")%></li -->
            </ul>
        </div>
    </div>
</div>
<div id="viewPanel">
    <div id="leftPanel" class="left-panel"><iframe id="leftFrame" name="leftFrame" class="left-frame"
        src="${requestURI}?action=finder.tree" frameborder="0" scrolling="no" marginwidth="0" marginheight="0"></iframe></div>

    <div id="mainPanel" class="main-panel"><iframe id="mainFrame" name="mainFrame" class="main-frame"
        src="${requestURI}?action=finder.tabpanel" frameborder="0" scrolling="auto" marginwidth="0" marginheight="0"></iframe></div>
</div>
<div id="statusBar" class="status-bar hide">
    <div id="_task_bar" class="widget-task-bar"></div>
    <div class="footer">
        <span class="copyright">Powered by Finder v${version} | Copyright © <a href="http://www.finderweb.net/" draggable="false" target="_blank">www.finderweb.net</a> All rights reserved.</span>
        <img style="margin-top: -2px; margin-right: 4px;" src="?action=res&path=/finder/images/sound.gif" title="${remoteIp}: ${userName}"/><%=i18n.format("finder.index.welcome")%>
    </div>
</div>
<%
    if(Boolean.TRUE.equals(request.getAttribute("hasNewVersion"))) {
%>
<div id="upgrade-tips" class="upgrade-tips">New Upgrade ${newVersion}！<a href="${downloadUrl}" title="${newVersion}" target="_blank">Click for download.</a>
</div>
<%
    }
%>
<div id="pageContext" style="display: none;" contextPath="${contextPath}" admin="${admin}" nav-bar="<%=ConfigFactory.getNavBar()%>"></div>
<div style="display: none;"><%this.write(out, ConfigFactory.getAccessCode(), false);%></div>
</body>
</html>
