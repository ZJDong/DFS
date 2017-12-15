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
<script type="text/javascript">
<!--
jQuery(function() {
    var master = PageContext.getAttribute("master-name");
    var table = document.getElementById("host-table");
    var rows = table.rows;

    for(var i = 0; i < rows.length; i++) {
        var tr = rows[i];
        var hostName = tr.getAttribute("hostName");

        if(hostName == null || hostName == undefined) {
            continue;
        }

        if(hostName == master) {
            var src = jQuery(tr).find("td a.conf-sync");
            src.attr("class", "btn disabled");
        }
    }
});

jQuery(function() {
    /**
     * 1. 遍历所有host, 发起ajax请求获取目标主机的host.xml的版本号
     */
    var handler = function(index) {
        if(index == null || index == undefined) {
            return;
        }

        var table = document.getElementById("host-table");
        var rows = table.rows;

        if(index >= rows.length) {
            return;
        }

        var tr = rows[index];
        var hostName = tr.getAttribute("hostName");
        var version = PageContext.getAttribute("master-config-version");

        if(hostName == null || hostName == undefined) {
            return;
        }

        jQuery.ajax({
            "type": "get",
            "url": "?action=agent.conf.version&host=" + encodeURIComponent(hostName),
            "dataType": "json",
            "error": function() {
                jQuery(tr).find("td.version").html("系统错误");
                handler(index + 1);
            },
            "success": function(response) {
                if(response.status != 200) {
                    jQuery(tr).find("td.version").attr("title", response.message);
                    jQuery(tr).find("td.version").html("系统错误");
                }
                else {
                    if(response.value != version) {
                        jQuery(tr).find("td.version").attr("title", "该节点配置与master节点不一致，请从master同步数据。");
                        jQuery(tr).find("td.version").html("<span class=\"red\">" + response.value + "</span>");
                    }
                    else {
                        jQuery(tr).find("td.version").html("<span>" + response.value + "</span>");
                    }
                }
                handler(index + 1);
            }
        });
    };
    handler(1);
});
//-->
</script>
</head>
<body contextPath="${contextPath}">
<div class="menu-bar">
    <a class="button" href="javascript:void(0)" onclick="window.history.back();"><span class="back"></span>返回</a>
    <a class="button" href="javascript:void(0)" onclick="window.location.reload();"><span class="refresh"></span>刷新</a>
    <span class="seperator"></span>
    <a class="button" href="javascript:void(0)" onclick="window.location.href='?action=admin.config.edit';"><span class="refresh"></span>编辑</a>
</div>
<table id="host-table" class="list">
    <tr class="head">
        <td class="w200">Name</td>
        <td class="w200">Display Name</td>
        <td class="w300">URL</td>
        <td class="w100">Version</td>
        <td>operation</td>
    </tr>
    <%
        for(Host host : hosts) {
    %>
    <tr hostName="<%=host.getName()%>">
        <td><%=host.getName()%></td>
        <td><%=host.getDisplayName()%></td>
        <td><%=host.getUrl()%></td>
        <td class="version">...</td>
        <td>&nbsp;
            <!-- 配置文件不同步只能登录服务器手动修改 -->
            <!-- a class="btn disabled" href="javascript:void(0)" hostName="<%=host.getName()%>">从Master同步</a -->
        </td>
    </tr>
    <%
        }
    %>
</table>
<div id="pageContext" style="display: none;" master-name="${masterName}" master-config-version="${confVersion}"></div>
</body>
</html>
