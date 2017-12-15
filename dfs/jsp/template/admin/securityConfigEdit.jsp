<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.skin.finder.config.ConfigFactory"%>
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
    jQuery("button.key-generate").click(function() {
        var name = jQuery(this).attr("for");

        if(name == null) {
            return;
        }

        jQuery.ajax({
            "type": "get",
            "url": "?action=admin.random.uuid",
            "dataType": "json",
            "error": function() {
                alert("系统错误，请稍后再试！");
            },
            "success": function(response) {
                if(response.status != 200) {
                    alert(response.message);
                    return;
                }
                jQuery("input[name=" + name + "]").val(response.value);
            }
        });
    });

    jQuery("#submit").click(function() {
        var params = {};
        params.adminName = jQuery.trim(jQuery("input[name=adminName]").val());
        params.securityKey = jQuery.trim(jQuery("input[name=securityKey]").val());
        params.sessionName = jQuery.trim(jQuery("input[name=sessionName]").val());
        params.sessionKey = jQuery.trim(jQuery("input[name=sessionKey]").val());

        jQuery.ajax({
            "type": "get",
            "url": "?action=admin.security.config.save",
            "dataType": "json",
            "data": jQuery.param(params, true),
            "error": function() {
                alert("系统错误，请稍后再试！");
            },
            "success": function(response) {
                if(response.status != 200) {
                    alert(response.message);
                }
                else {
                    alert("保存并同步成功！");
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
    <a class="button" href="javascript:void(0)" onclick="window.history.back();"><span class="back"></span>return</a>
    <a class="button" href="javascript:void(0)" onclick="window.location.reload();"><span class="refresh"></span>reflesh</a>
    <span class="seperator"></span>
</div>
<div class="form">
    <div class="title"><h4>System setup - security setting</h4></div>
    <div class="form-row">
        <div class="form-label">Superadministrator：</div>
        <div class="form-c400">
            <div class="form-field">
                <input name="adminName" type="text" class="text w300" placeholder="User Name" value="<%=ConfigFactory.getAdmin()%>"/>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">Super administrator account。</div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-label">security Key：</div>
        <div class="form-c400">
            <div class="form-field">
                <input name="securityKey" type="text" class="text w300" placeholder="Security Key" value="<%=ConfigFactory.getSecurityKey()%>"/>
                <button id="url-test" class="button key-generate" for="securityKey">Generate</button>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">Finder Strongly suggest regenerating。</div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-label">Session name：</div>
        <div class="form-c400">
            <div class="form-field">
                <input name="sessionName" type="text" class="text w300" placeholder="Session Name" value="<%=ConfigFactory.getSessionName()%>"/>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">Session storage cookie。</div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-label">Session signature Key：</div>
        <div class="form-c400">
            <div class="form-field">
                <input name="sessionKey" type="text" class="text w300" placeholder="Session Key" value="<%=ConfigFactory.getSessionKey()%>"/>
                <button id="url-test" class="button key-generate" for="sessionKey">generate</button>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">Strongly suggest regenerating。</div>
        </div>
    </div>
    <div class="button">
        <button id="submit" class="button ensure">Save and synchronize to the cluster</button>
    </div>
</div>
<div id="pageContext" style="display: none;"></div>
</body>
</html>
