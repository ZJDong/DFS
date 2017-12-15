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
    var showHostList = function(userName, list) {
        var table = document.getElementById("host-table");
        jQuery(table).find("tr.item").remove();

        if(list == null || list.length < 1) {
            return;
        }

        for(var i = 0; i < list.length; i++) {
            var hostName = list[i];
            var tr = table.insertRow(-1);
            var td1 = tr.insertCell(-1);
            var td2 = tr.insertCell(-1);

            tr.className = "item";
            td1.innerHTML = hostName;
            td2.innerHTML = "<a class=\"btn delete\" href=\"javascript:void(0)\" userName=\"" + userName + "\" hostName=\"" + hostName + "\">删除</a>";
        }
        jQuery("#host-list").show();
    };

    jQuery("input[name=query]").click(function() {
        var userName = jQuery.trim(jQuery("input[name=userName]").val());

        if(userName.length < 1) {
            alert("Enter one user name！");
            return;
        }

        var src = jQuery(this);
        src.prop("disabled", true);
        src.prop("class", "button disabled");
        jQuery.ajax({
            "type": "get",
            "url": "?action=admin.policy.getHostPolicy&userName=" + encodeURIComponent(userName),
            "dataType": "json",
            "error": function() {
                src.prop("disabled", false);
                src.prop("class", "button");
                alert("System error. Please try again later！");
            },
            "success": function(response) {
                src.prop("disabled", false);
                src.prop("class", "button");
                if(response.status != 200) {
                    alert(response.message);
                    return;
                }

                jQuery("#host-list").show();
                showHostList(userName, response.value);
            }
        });
    });

    jQuery("input[name=flush]").click(function() {
        var userName = jQuery.trim(jQuery("input[name=userName]").val());

        if(userName.length < 1) {
            return;
        }

        jQuery.ajax({
            "type": "get",
            "url": "?action=admin.policy.host.flush&userName=" + encodeURIComponent(userName),
            "dataType": "json",
            "error": function() {
                alert("System error. Please try again later！");
            },
            "success": function(response) {
                if(response.status != 200) {
                    alert(response.message);
                    return;
                }
                alert("System error. Please try again later！");
            }
        });
    });
});

jQuery(function() {
    var remove = function(src) {
        var userName = src.getAttribute("userName");
        var hostName = src.getAttribute("hostName");

        if(hostName == null || hostName == undefined) {
            return;
        }

        jQuery.ajax({
            "type": "get",
            "url": "?action=admin.policy.host.remove&userName=" + encodeURIComponent(userName) + "&hostName=" + encodeURIComponent(hostName),
            "dataType": "json",
            "error": function() {
                alert("System error. Please try again later！");
            },
            "success": function(response) {
                if(response.status != 200) {
                    alert(response.message);
                    return;
                }
                jQuery(src).closest("tr").remove();
            }
        });
    };

    jQuery("#host-table").click(function(event) {
        var src = (event.target || event.srcElement);
        var className = src.className;

        if(className == null) {
            return;
        }

        if(className.indexOf("delete") > -1) {
            remove(src);
        }
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
    <a class="button add" href="javascript:void(0)" onclick="window.location.href='?action=admin.user.host.edit';"><span class="add"></span>添加</a>
</div>
<div class="form">
    <div style="margin: 10px 0px; font-size: 13px;">请输入用户名查询该用户拥有的主机权限:</div>
    <div style="margin: 10px 0px;">
        <input name="userName" type="text" class="text w200" placeholder="User Name" value=""/>
        <input name="query" type="button" class="button" value="查询"/>
        <input name="flush" type="button" class="button" value="刷新缓存"/>
    </div>
</div>
<div id="host-list" class="form" style="display: none;">
    <table id="host-table" class="list">
        <tr class="head">
            <td>Host</td>
            <td>操作</td>
        </tr>
    </table>
</div>
<div id="pageContext" style="display: none;" contextPath="${contextPath}"></div>
</body>
</html>
