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
    var getOperateButton = function() {
        var b = [];

        jQuery("input[name=operateButton]:checked").each(function() {
            b[b.length] = this.value;
        });
        return b.join(", ");
    };

    jQuery("#submit").click(function() {
        var params = {};
        params.sessionTimeout = jQuery.trim(jQuery("input[name=sessionTimeout]").val());
        params.topNavBar = jQuery("input[name=topNavBar]").prop("checked");
        params.operateButton = getOperateButton();
        params.textType = jQuery.trim(jQuery("input[name=textType]").val());
        params.uploadPartSize = jQuery.trim(jQuery("input[name=uploadPartSize]").val());
        params.demoUserName = jQuery.trim(jQuery("input[name=demoUserName]").val());
        params.demoPassword = jQuery.trim(jQuery("input[name=demoPassword]").val());
        params.updateCheck = jQuery("input[name=updateCheck]").prop("checked");

        jQuery.ajax({
            "type": "get",
            "url": "?action=admin.config.save",
            "dataType": "json",
            "data": jQuery.param(params, true),
            "error": function() {
                alert("System error. Please try again later！");
            },
            "success": function(response) {
                if(response.status != 200) {
                    alert(response.message);
                }
                else {
                    alert("Save and synchronize success！");
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
    <div class="title"><h4>系统设置 - 一般设置</h4></div>
    <div class="form-row">
        <div class="form-label">会话有效期：</div>
        <div class="form-c400">
            <div class="form-field">
                <input name="sessionTimeout" type="text" class="text w300" placeholder="0s" value="<%=ConfigFactory.getSessionTimeout()%>"/>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">用户登录的有效期。可选单位: [d: 天, h: 时, m: 分, s: 秒]；单位不区分大小写, 无单位或者其他都按照秒处理。支持小数形式。</div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-label">显示顶部导航：</div>
        <div class="form-c400">
            <div class="form-field">
                <input name="topNavBar" type="checkbox" checked-value="<%=ConfigFactory.getNavBar()%>" value="true"/>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">是否显示顶部导航条。</div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-label">允许打开的文本类型：</div>
        <div class="form-c400">
            <div class="form-field">
                <input name="textType" type="text" class="text w300" placeholder="5M" value="<%=ConfigFactory.getTextType()%>"/>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">允许使用tail，less，grep打开的文本文件类型，逗号分隔。"*"表示全部。</div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-label">显示操作按钮：</div>
        <div class="form-c400">
            <div class="form-field">
                <p><input name="operateButton" type="checkbox" checked-value="<%=ConfigFactory.getOperateButton()%>" value="tail"/> tail</p>
                <p><input name="operateButton" type="checkbox" checked-value="<%=ConfigFactory.getOperateButton()%>" value="less"/> less</p>
                <p><input name="operateButton" type="checkbox" checked-value="<%=ConfigFactory.getOperateButton()%>" value="grep"/> grep</p>
                <p><input name="operateButton" type="checkbox" checked-value="<%=ConfigFactory.getOperateButton()%>" value="open"/> open</p>
                <p><input name="operateButton" type="checkbox" checked-value="<%=ConfigFactory.getOperateButton()%>" value="download"/> download</p>
                <p><input name="operateButton" type="checkbox" checked-value="<%=ConfigFactory.getOperateButton()%>" value="delete"/> delete</p>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">显示的操作按钮, 文件列表项后面的操作按钮。</div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-label">文件上传分片大小：</div>
        <div class="form-c400">
            <div class="form-field">
                <input name="uploadPartSize" type="text" class="text w300" placeholder="5M" value="<%=ConfigFactory.getUploadPartSize()%>"/>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">可选单位: [b: 字节, k: 千字节, m: 兆字节, g: 千兆字节]，单位不区分大小写, 无单位或者其他都按照字节处理。支持小数形式。</div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-label">演示账号：</div>
        <div class="form-c400">
            <div class="form-field">
                <input name="demoUserName" type="text" class="text w300" placeholder="User Name" value="<%=ConfigFactory.getDemoUserName()%>"/>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">显示在登录页的用户名密码。一般用于演示系统或者公共用户。置空则不显示账号密码。</div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-label">演示账号密码：</div>
        <div class="form-c400">
            <div class="form-field">
                <input name="demoPassword" type="text" class="text w300" placeholder="Password" value="<%=ConfigFactory.getDemoPassword()%>"/>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">显示在登录页的用户名密码。一般用于演示系统或者公共用户。置空则不显示账号密码。</div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-label">自动更新检查：</div>
        <div class="form-c400">
            <div class="form-field">
                <input name="updateCheck" type="checkbox" checked-value="<%=ConfigFactory.getUpdateCheck()%>" value="true"/>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">是否开启自动更新检查。</div>
        </div>
    </div>
    <div class="button">
        <button id="submit" class="button ensure">保存并同步到集群</button>
    </div>
</div>
<div id="pageContext" style="display: none;"></div>
</body>
</html>
