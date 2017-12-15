<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>Finder - Powered by Finder</title>
<link rel="shortcut icon" href="?action=res&path=/finder/images/favicon.png"/>
<link rel="stylesheet" type="text/css" href="?action=res&path=/finder/css/user.css"/>
<script type="text/javascript" src="?action=res&path=/finder/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
<!--
jQuery(function() {
    jQuery("#submit").click(function() {
        var userName = jQuery.trim(jQuery("#s1").val());
        var oldPassword = jQuery.trim(jQuery("#s1").val());
        var newPassword = jQuery.trim(jQuery("#s2").val());
        var params = "userName=" + encodeURIComponent(userName) + "&oldPassword=" + encodeURIComponent(oldPassword) +  + "&newPassword=" + encodeURIComponent(newPassword);
        var requestURI = window.location.pathname;

        jQuery.ajax({
            type: "post",
            url: requestURI + "?action=user.password.update",
            dataType: "json",
            data: params,
            error: function(req, status, error) {
                alert("System error. Please try again later！");
            },
            success: function(result) {
                if(result.status == 200) {
                    alert("Add user success！");
                    window.location.reload();
                }
                else {
                    alert(result.message);
                }
            }
        });
    });
});
//-->
</script>
</head>
<body>
<div class="wrap">
    <div class="login-container">
        <h3>修改密码</h3>
        <div class="login-panel">
            <div class="row"><input id="s1" type="oldPassword" class="text" placeholder="Old Password" value=""/></div>
            <div class="row"><input id="s2" type="newPassword" class="text" placeholder="New Password" value=""/></div>
            <div class="row"><input id="submit" type="button" class="button" value="提交"/></div>
        </div>
    </div>
</div>
</body>
</html>