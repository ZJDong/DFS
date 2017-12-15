<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.skin.finder.cluster.Cluster"%>
<%@ page import="com.skin.finder.cluster.Host"%>
<%
    Cluster cluster = (Cluster)(request.getAttribute("cluster"));
    List<Host> hosts = cluster.getHosts();
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
<script type="text/javascript" src="?action=res&path=/finder/fileupload.js"></script>
<script type="text/javascript" src="?action=res&path=/admin/upgrade.js"></script>
</head>
<body contextPath="${contextPath}">
<div class="menu-bar">
    <a class="button back" href="javascript:void(0)"><span class="back"></span>return</a>
    <a class="button refresh" href="javascript:void(0)"><span class="refresh"></span>reflsh</a>
    <span class="seperator"></span>
    <a class="button upgrade" href="javascript:void(0)"><span class="add"></span>system</a>
</div>
<table id="host-table" class="list">
    <tr class="head">
        <td class="w100 center"><a id="checkall" href="javascript:void(0)">Select</a> / <a id="uncheck" href="javascript:void(0)">Cancel</a></td>
        <td class="w200">Name</td>
        <td class="w200">Display Name</td>
        <td class="w100">Version</td>
        <td class="w150">Start Time</td>
        <td>operation</td>
    </tr>
    <%
        for(Host host : hosts) {
    %>
    <tr class="disabled" hostName="<%=host.getName()%>">
        <td style="text-align: center;"><input name="hostName" type="checkbox" disabled="true" value="<%=host.getName()%>"/></td>
        <td><%=host.getName()%></td>
        <td><%=host.getDisplayName()%></td>
        <td class="version">...</td>
        <td class="startTime">...</td>
        <td>
            <a class="btn sys-inf disabled" href="javascript:void(0)" hostName="<%=host.getName()%>">system information</a>
            <a class="btn restart disabled" href="javascript:void(0)" hostName="<%=host.getName()%>">Reboot</a>
        </td>
    </tr>
    <%
        }
    %>
</table>
<div id="pageContext" style="display: none;" cluster-master-name="${masterName}" cluster-master-version="${masterVersion}"></div>
</body>
</html>
