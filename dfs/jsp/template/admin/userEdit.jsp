<%@ page contentType="text/html; charset=utf-8"%>
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
    jQuery("#submit").click(function() {
        var userName = jQuery.trim(jQuery("#s1").val());
        var password = jQuery.trim(jQuery("#s2").val());

        if(userName.length < 1) {
            alert("The username can not be empty！");
            return;
        }

        if(password.length < 1) {
            alert("The password can not be empty！");
            return;
        }

        var requestURI = window.location.pathname;
        var params = "userName=" + encodeURIComponent(userName) + "&password=" + encodeURIComponent(password);

        jQuery.ajax({
            type: "post",
            url: requestURI + "?action=admin.user.save",
            dataType: "json",
            data: params,
            error: function(req, status, error) {
                alert("System error. Please try again later！");
            },
            success: function(result) {
                if(result.status != 200) {
                    alert(result.message);
                    return;
                }
                alert("Successful operation！");
                window.location.href = "?action=admin.user.query";
            }
        });
    });
});
//-->
</script>
</head>
<body>
<div class="menu-bar">
    <a class="button" href="javascript:void(0)" onclick="window.history.back();"><span class="back"></span>返回</a>
    <a class="button" href="javascript:void(0)" onclick="window.location.reload();"><span class="refresh"></span>刷新</a>
    <span class="seperator"></span>
</div>
<div class="form">
    <div class="title"><h4>用户编辑</h4></div>
    <div class="form-row">
        <div class="form-label">User Name：</div>
        <div class="form-c300">
            <div class="form-field">
                <input id="s1" type="text" class="text w200" placeholder="User Name" value="${userName}"/>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">User Name .</div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-label">Password:</div>
        <div class="form-c300">
            <div class="form-field">
                <input id="s2" type="text" class="text w200" placeholder="password" value=""/>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">Password .</div>
        </div>
    </div>

    <div class="button">
        <button id="submit" class="button ensure">保 存</button>
    </div>
</div>
<div id="pageContext" style="display: none;" contextPath="${contextPath}"></div>
</body>
</html>