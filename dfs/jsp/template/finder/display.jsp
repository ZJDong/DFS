<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.skin.finder.config.ConfigFactory"%>
<%
    String path = (String)(request.getAttribute("path"));
    String theme = (String)(request.getAttribute("theme"));
    String type = (String)(request.getAttribute("type"));
    String encoding = (String)(request.getAttribute("encoding"));
    Long start = (Long)(request.getAttribute("start"));

    if(path == null || (path = path.trim()).length() <= 1) {
        path = "/";
    }

    if(theme == null || (theme = theme.trim()).length() < 1) {
        theme = "Default";
    }

    if(type == null || (type = type.trim()).length() < 1) {
        type = "";
    }

    if(encoding == null || (encoding = encoding.trim()).length() < 1) {
        encoding = "utf-8";
    }

    if(start == null) {
        start = Long.valueOf(0);
    }
    request.setAttribute("path", path);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>Finder - Powered by Finder</title>
<link rel="shortcut icon" href="?action=res&path=/finder/images/favicon.png"/>
<link rel="stylesheet" type="text/css" href="?action=res&path=/finder/css/finder.css"/>
<link rel="stylesheet" type="text/css" href="?action=res&path=/sh/style/shCore${theme}.css"/>
<link rel="stylesheet" type="text/css" href="?action=res&path=/sh/style/shTheme${theme}.css"/>
<script type="text/javascript" src="?action=res&path=/finder/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="?action=res&path=/sh/shCore.js"></script>
<script type="text/javascript" src="?action=res&path=/sh/shAutoloader.js"></script>
<script type="text/javascript" src="?action=res&path=/finder/widget.js"></script>
<script type="text/javascript" src="?action=res&path=/finder/finder.js"></script>
<script type="text/javascript" src="?action=res&path=/finder/display.js"></script>
</head>
<body style="overflow: hidden;" page="display" contextPath="${contextPath}" host="${host}" workspace="${workspace}" path="${path}">
<div class="finder" style="min-width: 988px;">
    <div class="menu-bar">
        <div style="float: left; width: 80px;">
            <a class="button" href="javascript:void(0)" title="return"><span class="back"></span></a>
            <a class="button" href="javascript:void(0)" title="reflesh"><span class="refresh"></span></a>
        </div>
        <div style="float: left; height: 28px; position: relative;">
            <div style="float: left;"><input id="address" type="text" class="address" autocomplete="off" file="true" value="${path}"/></div>
            <div id="finder-suggest" class="list suggest"></div>
            <span class="label">theme:</span>
            <select id="uiThemeOption" selected-value="<%=theme%>"></select>

            <span class="label">type:</span>
            <select id="uiTypeOption" selected-value="<%=type%>"></select>

            <span class="label">encoding:</span>
            <select id="uiEncodingOption" selected-value="<%=encoding%>"></select>
        </div>
        <div style="float: right; width: 40px;">
            <a class="button" href="${requestURI}?action=finder.help" title="help" target="_blank"><span class="help"></span></a>
        </div>
    </div>
</div>
<%
    if(start > 0L) {
%>
<div style="padding-left: 4px; height: 28px; line-height: 28px; background-color: #efefef; font-size: 12px;">
    The file is large and only a part of the data is displayed. To see all the data, use it <a href="${requestURI}?action=finder.less&workspace=${workspace}&path=${path}" style="color: #ff0000;">less</a> open。
    [${start} - ${end}/${length}]
</div>
<%
    }
%>
<div id="content" file-type="${type}" style="display: none;"><pre class="brush: bash;">${content}</pre></div>
<div id="loading" class="widget-mask" style="display: block;" contextmenu="false"><div class="loading"><img src="?action=res&path=/finder/images/loading.gif"/></div></div>
<div style="display: none;"><%this.write(out, ConfigFactory.getAccessCode(), false);%></div>
</body>
</html>