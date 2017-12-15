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
<script type="text/javascript" src="?action=res&path=/admin/host-list.js"></script>
</head>
<body contextPath="${contextPath}">
<div class="menu-bar">
    <a class="button back" href="javascript:void(0)"><span class="back"></span>Return</a>
    <a class="button refresh" href="javascript:void(0)"><span class="refresh"></span>Refresh</a>
    <span class="seperator"></span>
    <a class="button add-host" href="javascript:void(0)"><span class="add"></span>Add host</a>
</div>
<table id="host-table" class="list">
    <tr class="head">
        <td class="w100">Order</td>
        <td class="w200">Name</td>
        <td class="w200">Display Name</td>
        <td class="w300">URL</td>
        <td class="w100">Version</td>
        <td>operation</td>
    </tr>
    <%
        for(Host host : hosts) {
    %>
    <tr class="disabled" hostName="<%=host.getName()%>">
        <td><input name="orderNum" type="text" class="text w60" hostName="<%=host.getName()%>" value="<%=host.getOrderNum()%>"/></td>
        <td><%=host.getName()%></td>
        <td><%=host.getDisplayName()%></td>
        <td><%=host.getUrl()%></td>
        <td class="version">...</td>
        <td>
            <a class="btn host-edit" href="javascript:void(0)" hostName="<%=host.getName()%>">edit</a>
            <a class="btn host-sync" href="javascript:void(0)" hostName="<%=host.getName()%>">Master synchronization</a>
            <a class="btn work-list" href="javascript:void(0)" hostName="<%=host.getName()%>">Workspace management</a>
            <a class="btn delete" href="javascript:void(0)" hostName="<%=host.getName()%>">Deleting</a>
        </td>
    </tr>
    <%
        }
    %>
</table>
<div id="pageContext" style="display: none;" cluster-master-name="${masterName}" cluster-master-version="<%=cluster.getVersion()%>"></div>
</body>
</html>
