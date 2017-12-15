<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.skin.finder.util.SystemInfo"%>
<%@ page import="com.skin.finder.util.Version"%>
<%@ page import="com.skin.finder.util.ServletInfo"%>
<%
    Version version = (Version)(request.getAttribute("version"));
    SystemInfo systemInfo = (SystemInfo)(request.getAttribute("systemInfo"));
    ServletInfo servletInfo = (ServletInfo)(request.getAttribute("servletInfo"));
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
<link rel="stylesheet" type="text/css" href="?action=res&path=/admin/css/form.css"/>
<script type="text/javascript" src="?action=res&path=/finder/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="?action=res&path=/admin/base.js"></script>
</head>
<body>
<!--
${reqeustHeaders}
-->
<div class="menu-bar">
    <a class="button" href="javascript:void(0)" onclick="window.history.back();"><span class="back"></span>返回</a>
    <a class="button" href="javascript:void(0)" onclick="window.location.reload();"><span class="refresh"></span>刷新</a>
    <span class="seperator"></span>
</div>
<table class="list">
    <!-- Server Info -->
    <tr class="head">
        <td colspan="2">System Info</td>
    </tr>
    <tr>
        <td class="w200">Host</td>
        <td>${hostName}</td>
    </tr>
    <tr>
        <td class="w200">Version</td>
        <td><%=version.getVersionName()%></td>
    </tr>
    <tr>
        <td class="w200">Developer</td>
        <td><a href="<%=version.getDeveloper()%>" target="_blank"><%=version.getDeveloper()%></a></td>
    </tr>
    <tr>
        <td class="w200">Server</td>
        <td><%=servletInfo.getServerInfo()%></td>
    </tr>
    <tr>
        <td>ServletVersion</td>
        <td><%=servletInfo.getServletVersion()%></td>
    </tr>
    <!-- System Info -->
    <tr>
        <td>OS</td>
        <td><%=systemInfo.getOsName()%> (<%=systemInfo.getOsVersion()%>) (<%=systemInfo.getCpu()%>)</td>
    </tr>
    <tr>
        <td>Virtual Machine Name</td>
        <td><%=systemInfo.getVmName()%></td>
    </tr>
    <tr>
        <td>Virtual Machine Vendor</td>
        <td><%=systemInfo.getVmVendor()%></td>
    </tr>
    <tr>
        <td>Virtual Machine Version</td>
        <td><%=systemInfo.getVmVersion()%></td>
    </tr>
    <tr>
        <td>Runtime Name</td>
        <td><%=systemInfo.getRuntimeName()%></td>
    </tr>
    <tr>
        <td>Runtime Version</td>
        <td><%=systemInfo.getRuntimeVersion()%></td>
    </tr>
    <tr>
        <td>Max Memory</td>
        <td><%=systemInfo.getMaxMemory()%> (<%=systemInfo.getMaxMemory() / 1048576%>M)</td>
    </tr>
    <tr>
        <td>Total Memory</td>
        <td><%=systemInfo.getTotalMemory()%> (<%=systemInfo.getTotalMemory() / 1048576%>M)</td>
    </tr>
    <tr>
        <td>Free Memory</td>
        <td><%=systemInfo.getFreeMemory()%> (<%=systemInfo.getFreeMemory() / 1048576%>M)</td>
    </tr>
</table>
<div style="height: 200px;"></div>
</body>
</html>