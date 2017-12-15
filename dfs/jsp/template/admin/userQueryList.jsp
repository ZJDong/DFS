<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.skin.finder.security.User"%>
<%
    @SuppressWarnings("unchecked")
    List<User> userList = (List<User>)(request.getAttribute("userList"));
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
    jQuery("input[name=query]").click(function() {
        var userName = jQuery.trim(jQuery("input[name=userName]").val());

        if(userName.length < 1) {
            alert("The username can not be empty！");
            return;
        }
        window.location.href = "?action=admin.user.query&userName=" + encodeURIComponent(userName);
    });

    jQuery("table tr td a.delete").click(function() {
        var userName = this.getAttribute("userName");

        if(!window.confirm("After the deletion is not recoverable, it is determined to delete[" + userName + "] 吗？")) {
            return;
        }

        jQuery.ajax({
            "type": "get",
            "url": "?action=admin.user.delete&userName=" + encodeURIComponent(userName),
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

    jQuery("table tr td a.as-admin").click(function() {
        var userName = this.getAttribute("userName");

        if(!window.confirm("The administrator can only have one, and the administrator before the administrator will drop it to the ordinary user, do you decide to continue？")) {
            return;
        }

        jQuery.ajax({
            "type": "get",
            "url": "?action=admin.user.asadmin&userName=" + encodeURIComponent(userName),
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
});
//-->
</script>
</head>
<body contextPath="${contextPath}">
<div class="menu-bar">
    <a class="button" href="javascript:void(0)" onclick="window.history.back();"><span class="back"></span>返回</a>
    <a class="button" href="javascript:void(0)" onclick="window.location.reload();"><span class="refresh"></span>刷新</a>
    <span class="seperator"></span>
</div>
<div class="form">
    <div class="title"><h4>查询用户</h4></div>
    <div style="margin: 10px 0px; font-size: 13px;">请先输入用户名查询用户:</div>
    <div style="margin: 10px 0px;">
        <input name="userName" type="text" class="text w200" placeholder="User Name" value="${userName}"/>
        <input name="query" type="button" class="button" value="查询"/>
    </div>
    <table id="host-table" class="list">
        <tr class="head">
            <td>用户名</td>
            <td>注册时间</td>
            <td>操作</td>
        </tr>
        <%
            if(userList != null && userList.size() > 0) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                for(User user : userList) {
        %>
        <tr userName="<%=user.getUserName()%>">
            <td class="w200"><%=user.getUserName()%></td>
            <td class="w400"><%=dateFormat.format(user.getCreateTime())%></td>
            <td>
                <a class="btn" href="?action=admin.user.edit&userName=<%=user.getUserName()%>">修改</a>
                <a class="btn delete" href="javascript:void(0)" userName="<%=user.getUserName()%>">刪除</a>
                <a class="btn as-admin" href="javascript:void(0)" userName="<%=user.getUserName()%>">设置为管理员</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
    <%
        if(userList == null || userList.size() < 1) {
    %>
    <div style="margin: 10px 0px;">无查询条件或者无数据。</div>
    <%
        }
    %>
</div>
<div id="pageContext" style="display: none;"></div>
</body>
</html>
