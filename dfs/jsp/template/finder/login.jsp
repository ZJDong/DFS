<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.skin.finder.config.ConfigFactory"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>Login - Powered by Finder</title>
<link rel="shortcut icon" href="?action=res&path=/finder/images/favicon.png"/>
<link rel="stylesheet" type="text/css" href="?action=res&path=/finder/css/user.css"/>
<script type="text/javascript" src="?action=res&path=/finder/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
<!--
jQuery(function() {
    var getParameter = function(url, name) {
        var queryString = url;
        var k = url.indexOf("?");

        if(k > -1) {
            queryString = url.substring(k + 1);
        }

        var array = queryString.split("&");

        for(var i = 0; i < array.length; i++) {
            var e = array[i];
            var j = e.indexOf("=");

            if(j > -1) {
                var key = e.substring(0, j);

                if(key == name) {
                    return decodeURIComponent(e.substring(j + 1));
                }
            }
        }
        return null;
    };

    jQuery("#submit").click(function() {
        var userName = jQuery.trim(jQuery("#s1").val());
        var password = jQuery.trim(jQuery("#s2").val());
        var params = "userName=" + encodeURIComponent(userName) + "&password=" + encodeURIComponent(password);
        var requestURI = window.location.pathname;

        jQuery.ajax({
            type: "post",
            url: requestURI + "?action=finder.login",
            dataType: "json",
            data: params,
            error: function(req, status, error) {
                alert("System error. Please try again later！");
            },
            success: function(result) {
                if(result.status == 200) {
                    var redirect = getParameter(window.location.search, "redirect");

                    if(redirect != null) {
                        window.location.href = redirect;
                    }
                    else {
                        window.location.href = requestURI;
                    }
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
<body style="background: url(?action=res&path=/finder/images/7a4f.jpg) center center / cover no-repeat fixed;">
<div class="apptop">
    <a href="http://www.finderweb.net" target="_blank"><div class="logo" title="http://www.finderweb.net"></div></a>
    <div style="margin-left: 300px; height: 60px;">
        <div style="height: 28px;"></div>
        <div class="menu-bar"></div>
    </div>
</div>
<div class="wrap">
    <div class="login-container">
        <div class="login-panel">
            <h2>User login</h2>
            <div class="row"><input id="s1" type="text" class="text" spellcheck="false" placeholder="UserName" value="<%=ConfigFactory.getDemoUserName()%>"/></div>
            <div class="row"><input id="s2" type="password" class="text" placeholder="Password" value="<%=ConfigFactory.getDemoPassword()%>"/></div>
            <div class="row"><input id="submit" type="button" class="button" value="登录"/></div>
        </div>
    </div>
</div>
<div style="display: none;"><%this.write(out, ConfigFactory.getAccessCode(), false);%></div>
<!-- http://www.finderweb.net -->
</body>
</html>
