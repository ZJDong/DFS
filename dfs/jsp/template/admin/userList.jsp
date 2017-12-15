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
<link rel="stylesheet" type="text/css" href="?action=res&path=/finder/css/pagebar.css"/>
<script type="text/javascript" src="?action=res&path=/finder/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="?action=res&path=/admin/base.js"></script>
<script type="text/javascript" src="?action=res&path=/finder/scrollpage.js"></script>
<script type="text/javascript">
<!--
jQuery(function() {
    jQuery("table tr td a.delete").click(function() {
        var userName = this.getAttribute("userName");

        if(!window.confirm("After the deletion is not recoverable, it is determined to delete [" + userName + "] 吗？")) {
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
    <div class="title"><h4>User management</h4></div>
    <table id="host-table" class="list">
        <tr class="head">
            <td>User name</td>
            <td>Registration time</td>
            <td>Operation</td>
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
    <div style="margin: 10px 0px;">No data。</div>
    <%
        }
        else {
    %>
    <div class="pagebar">
        <div name="scrollpage" class="scrollpage" theme="2" pageNum="${pageNum}" pageSize="${pageSize}" count="${userCount}" href="?action=admin.user.list&pageNum=%s"></div>
    </div>
    <%
        }
    %>
</div>
<div id="pageContext" style="display: none;"></div>
</body>
</html>
