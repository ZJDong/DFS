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
    jQuery("button[name=submit]").click(function() {
        var userName = jQuery.trim(jQuery("input[name=userName]").val());
        var hostName = jQuery.trim(jQuery("select[name=hostName]").val());

        if(userName.length < 1) {
            alert("Enter one user name！");
            return;
        }

        if(hostName.length < 1) {
            alert("Please select host！");
            return;
        }

        var params = [];
        params[params.length] = "userName=" + encodeURIComponent(userName);
        params[params.length] = "hostName=" + encodeURIComponent(hostName);

        jQuery.ajax({
            "type": "get",
            "url": "?action=admin.policy.host.add&host=" + encodeURIComponent(hostName),
            "dataType": "json",
            "data": params.join("&"),
            "error": function() {
                alert("System error. Please try again later！");
            },
            "success": function(response) {
                if(response.status != 200) {
                    alert(response.message);
                    return;
                }
                alert("With the addition of success, you can continue to add other hosts or return to the previous page！");
            }
        });
    });
});
//-->
</script>
</head>
<body>
<div class="menu-bar">
    <a class="button" href="javascript:void(0)" onclick="window.history.back();"><span class="back"></span>return</a>
    <a class="button" href="javascript:void(0)" onclick="window.location.reload();"><span class="refresh"></span>reflesh</a>
    <span class="seperator"></span>
</div>
<%
    String[] hosts = (String[])(request.getAttribute("hosts"));

    if(hosts == null) {
        hosts = new String[0];
    }
%>
<div class="form">
    <div class="form-row">
        <div class="form-label">User Name：</div>
        <div class="form-c300">
            <div class="form-field">
                <input name="userName" type="text" class="text w200" placeholder="User Name" value=""/>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">User Name .</div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-label">Host Name</div>
        <div class="form-c300">
            <div class="form-field">
                <select name="hostName" type="text" class="select w200" title="主机">
                    <option value="">Host</option>
                <%
                    for(String hostName : hosts) {
                %>
                    <option value="<%=hostName%>"><%=hostName%></option>
                <%
                    }
                %>
                </select>
            </div>
        </div>
        <div class="form-m300">
            <div class="form-comment">Host Name .</div>
        </div>
    </div>

    <div class="button">
        <button name="submit" class="button ensure">Preservation</button>
    </div>
</div>
<div id="pageContext" style="display: none;" contextPath="${contextPath}"></div>
</body>
</html>
