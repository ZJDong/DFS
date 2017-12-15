<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.skin.finder.cluster.Host"%>
<%@ page import="com.skin.finder.cluster.Workspace"%>
<%
    Host host = (Host)(request.getAttribute("host"));

    if(host == null) {
        host = new Host();
    }

    List<Workspace> workspaces = host.getWorkspaces();
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
<script type="text/javascript">
<!--
jQuery(function() {
    jQuery("#create").click(function() {
        var hostName = document.body.getAttribute("hostName");
        window.location.href = "?action=admin.workspace.edit&hostName=" + encodeURIComponent(hostName);
    });

    jQuery("table tr td a.update").click(function() {
        var hostName = document.body.getAttribute("hostName");
        var workspaceName = this.getAttribute("workspaceName");
        window.location.href = "?action=admin.workspace.edit&hostName=" + encodeURIComponent(hostName) + "&workspaceName=" + encodeURIComponent(workspaceName);
    });

    jQuery("table tr td a.delete").click(function() {
        var hostName = document.body.getAttribute("hostName");
        var workspaceName = this.getAttribute("workspaceName");

        if(!window.confirm("Be sure to delete [" + workspaceName + "] 吗？")) {
            return;
        }

        jQuery.ajax({
            "type": "get",
            "url": "?action=admin.workspace.delete&hostName=" + encodeURIComponent(hostName) + "&workspaceName=" + encodeURIComponent(workspaceName),
            "dataType": "json",
            "error": function() {
                alert("System error. Please try again later！");
            },
            "success": function(response) {
                if(response.status != 200) {
                    alert(response.message);
                    return;
                }
                window.location.reload();
            }
        });
    });

    jQuery("table tr td input[name=orderNum]").change(function() {
        var hostName = document.body.getAttribute("hostName");
        var workspaceName = this.getAttribute("workspaceName");
        var value = StringUtil.trim(this.value);

        if(value.length < 1) {
            return;
        }

        var url = "?action=admin.workspace.setValue&hostName=" + encodeURIComponent(hostName)
            + "&workspaceName=" + encodeURIComponent(workspaceName)
            + "&orderNum=" + encodeURIComponent(value)

        jQuery.ajax({"type": "get", "url": url, "dataType": "json"});
    });
});
//-->
</script>
</head>
<body contextPath="${contextPath}" hostName="<%=host.getName()%>">
<div class="menu-bar">
    <a class="button" href="javascript:void(0)" onclick="window.history.back();"><span class="back"></span>返回</a>
    <a class="button" href="javascript:void(0)" onclick="window.location.reload();"><span class="refresh"></span>刷新</a>
    <span class="seperator"></span>
    <a id="create" class="button" href="javascript:void(0)"><span class="add"></span>新建</a>
</div>
<table id="workspace-table" class="list">
    <tr class="head">
        <td class="w100">Order</td>
        <td class="w200">Name</td>
        <td class="w200">Display Name</td>
        <td class="w300">Work</td>
        <td class="w100">Charset</td>
        <td class="w100">Readonly</td>
        <td>operation</td>
    </tr>
    <%
        for(Workspace workspace : workspaces) {
    %>
    <tr>
        <td><input name="orderNum" type="text" class="text w60" workspaceName="<%=workspace.getName()%>" value="<%=workspace.getOrderNum()%>"/></td>
        <td><%=workspace.getName()%></td>
        <td><%=workspace.getDisplayName()%></td>
        <td><%=workspace.getWork()%></td>
        <td><%=workspace.getCharset()%></td>
        <td><span class="<%=workspace.getReadonly()%>"><%=workspace.getReadonly()%></span></td>
        <td>
            <a class="btn update" href="javascript:void(0)" workspaceName="<%=workspace.getName()%>">Editors</a>
            <a class="btn delete" href="javascript:void(0)" workspaceName="<%=workspace.getName()%>">Deleting</a>
        </td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
