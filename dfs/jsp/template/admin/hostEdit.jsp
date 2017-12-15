<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.skin.finder.cluster.Host"%>
<%
    Host host = (Host)(request.getAttribute("host"));

    if(host == null) {
        host = new Host();
    }
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
    jQuery("#url-test").click(function() {
        var requestURI = window.location.pathname;
        var hostUrl = jQuery.trim(jQuery("input[name=hostUrl]").val());

        if(hostUrl.length < 1) {
            alert("Please enter Host URL!");
            return;
        }

        jQuery.ajax({
            type: "post",
            url: requestURI + "?action=admin.host.test",
            dataType: "json",
            data: "hostUrl=" + encodeURIComponent(hostUrl),
            error: function(req, status, error) {
                alert("System error. Please try again later！");
            },
            success: function(result) {
                if(result.status != 200) {
                    alert(result.message);
                    return;
                }
                alert("Test success！");
            }
        });
    });

    jQuery("#submit").click(function() {
        var oldName = jQuery.trim(jQuery("input[name=oldName]").val());
        var hostName = jQuery.trim(jQuery("input[name=hostName]").val());
        var displayName = jQuery.trim(jQuery("input[name=displayName]").val());
        var hostUrl = jQuery.trim(jQuery("input[name=hostUrl]").val());

        if(hostName.length < 1) {
            alert("The host name cannot be empty！");
            return;
        }

        if(hostUrl.length < 1) {
            alert("The host URL can not be empty！");
            return;
        }

        var params = [];
        var requestURI = window.location.pathname;
        params[params.length] = "oldName=" + encodeURIComponent(oldName);
        params[params.length] = "hostName=" + encodeURIComponent(hostName);
        params[params.length] = "displayName=" + encodeURIComponent(displayName);
        params[params.length] = "hostUrl=" + encodeURIComponent(hostUrl);

        jQuery.ajax({
            type: "post",
            url: requestURI + "?action=admin.host.save",
            dataType: "json",
            data: params.join("&"),
            error: function(req, status, error) {
                alert("System error. Please try again later！");
            },
            success: function(result) {
                if(result.status != 200) {
                    alert(result.message);
                    return;
                }
                alert("Successful operation！");
                window.location.href = "?action=admin.host.list";
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
    <div class="title"><h4>Host editor</h4></div>
    <ul class="tips">
        <li>
            <div style="color: #ff0000;">If you have modified the master, after adding all the hosts, go to the system settings - general settings, re - save and synchronize to the cluster;</div>
        </li>
    </ul>
    <div class="form-row">
        <div class="form-label">Host Name：</div>
        <div class="form-c300">
            <div class="form-field">
                <input name="oldName" type="hidden" value="${oldName}"/>
                <input name="hostName" type="text" class="text w200" placeholder="Host Name" value="<%=host.getName()%>"/>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">The letters and numbers, which allow the characters to be included: [a-z], [A-Z], [0-9], [-_.:@].</div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-label">Display Name：</div>
        <div class="form-c300">
            <div class="form-field">
                <input name="displayName" type="text" class="text w200" placeholder="Display Name" value="<%=host.getDisplayName()%>"/>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">Display name。</div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-label">Host URL:</div>
        <div class="form-comment">Finder的访问地址。一般为http://IP:PORT/finder，如果使用了contextPath，finder的访问地址为：http://IP:PORT/[CONTEXT_PATH]/finder</div>
        <div class="form-field">
            <input name="hostUrl" type="text" class="text w400" placeholder="Host URL" value="<%=host.getUrl()%>"/>
            <button id="url-test" class="button ensure">Test</button>
        </div>
    </div>

    <div class="button">
        <button id="submit" class="button ensure">Preservation</button>
    </div>
</div>
<div id="pageContext" style="display: none;" contextPath="${contextPath}"></div>
</body>
</html>