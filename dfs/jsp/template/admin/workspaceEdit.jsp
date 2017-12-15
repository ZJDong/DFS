<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.skin.finder.cluster.Workspace"%>
<%
    Workspace workspace = (Workspace)(request.getAttribute("workspace"));

    if(workspace == null) {
        workspace = new Workspace();
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
    jQuery("#submit").click(function() {
        var oldName = jQuery.trim(jQuery("input[name=oldName]").val());
        var hostName = jQuery.trim(jQuery("input[name=hostName]").val());
        var workspaceName = jQuery.trim(jQuery("input[name=workspaceName]").val());
        var displayName = jQuery.trim(jQuery("input[name=displayName]").val());
        var work = jQuery.trim(jQuery("input[name=work]").val());
        var charset = jQuery.trim(jQuery("input[name=charset]").val());
        var readonly = jQuery("input[name=readonly]").prop("checked");

        if(hostName.length < 1) {
            alert("The host name cannot be empty！");
            return;
        }

        if(workspaceName.length < 1) {
            alert("The namespace name of the workspace cannot be empty！");
            return;
        }

        if(work.length < 1) {
            alert("The working directory can not be empty！");
            return;
        }

        var params = [];
        var requestURI = window.location.pathname;
        params[params.length] = "oldName=" + encodeURIComponent(oldName);
        params[params.length] = "hostName=" + encodeURIComponent(hostName);
        params[params.length] = "workspaceName=" + encodeURIComponent(workspaceName);
        params[params.length] = "displayName=" + encodeURIComponent(displayName);
        params[params.length] = "work=" + encodeURIComponent(work);
        params[params.length] = "charset=" + encodeURIComponent(charset);
        params[params.length] = "readonly=" + encodeURIComponent(readonly);

        jQuery.ajax({
            type: "post",
            url: requestURI + "?action=admin.workspace.save",
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
                window.location.href = "?action=admin.workspace.list&hostName=" + encodeURIComponent(hostName);
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
    <div class="title"><h4>工作空间编辑(${hostName})</h4></div>
    <div class="form-row">
        <div class="form-label">Workspace Name：</div>
        <div class="form-c300">
            <div class="form-field">
                <input name="oldName" type="hidden" value="${oldName}"/>
                <input name="hostName" type="hidden" value="${hostName}"/>
                <input name="workspaceName" type="text" class="text w200" placeholder="Workspace Name" value="<%=workspace.getName()%>"/>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">工作空间名。字母和数字开头，允许包含的字符: [a-z], [A-Z], [0-9], [-_.:@].</div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-label">Display Name：</div>
        <div class="form-c300">
            <div class="form-field">
                <input name="displayName" type="text" class="text w200" placeholder="Display Name" value="<%=workspace.getDisplayName()%>"/>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">显示名。</div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-label">Work:</div>
        <div class="form-comment">当前主机的本地磁盘目录。contextPath:前缀表示finder自己的应用目录，无前缀表示磁盘的绝对地址。</div>
        <div class="form-field">
            <input name="work" type="text" class="text w400" placeholder="Work Directory" value="<%=workspace.getWork()%>"/>
        </div>
    </div>
    <div class="form-row">
        <div class="form-label">Charset：</div>
        <div class="form-c300">
            <div class="form-field">
                <input name="charset" type="text" class="text w200" placeholder="Charset" value="<%=workspace.getCharset()%>"/>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">工作空间文本文件的字符集。</div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-label">Readonly：</div>
        <div class="form-c300">
            <div class="form-field">
                <input name="readonly" type="checkbox" checked-value="<%=workspace.getReadonly()%>" value="true"/>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">是否只读。只读模式：包括管理员在内的所有用户都无写权限。</div>
        </div>
    </div>

    <div class="button">
        <button id="submit" class="button ensure">保 存</button>
    </div>
</div>
<div id="pageContext" style="display: none;" contextPath="${contextPath}"></div>
</body>
</html>