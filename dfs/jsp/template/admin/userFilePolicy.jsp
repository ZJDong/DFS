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
    var getOptions = function(name, list, value) {
        var b = [];
        b[b.length] = "<select name=\"" + name + "\" class=\"select\">";

        for(var i = 0; i < list.length; i++) {
            if(list[i] == value) {
                b[b.length] = "<option value=\"" + list[i] + "\" selected=\"true\">" + list[i] + "</option>";
            }
            else {
                b[b.length] = "<option value=\"" + list[i] + "\">" + list[i] + "</option>";
            }
        }
        b[b.length] = "</select>";
        return b.join("");
    };

    var showPermissionList = function(userName, hostName, bean) {
        var table = document.getElementById("permission-table");
        jQuery(table).find("tr.item").remove();

        var workspaces = bean.workspaces;
        var permissions = bean.permissions;

        table.setAttribute("hostName", hostName);
        table.setAttribute("workspaces", workspaces.join(","));

        if(permissions == null || permissions.length < 1) {
            return;
        }

        var compare = function(s1, s2) {
            if(s1 == s2) {
                return 0;
            }

            if(s1 > s2) {
                return 1;
            }
            else {
                return -1;
            }
        };

        var comparator = function(p1, p2) {
            var v = compare(p1.workspace, p2.workspace);

            if(v == 0) {
                return compare(p1.action, p2.action);
            }
            return v;
        };

        permissions.sort(comparator);

        var colors = {
            "index": 0,
            "values": ["#ffffff", "#f4f8c9", "#c2defb", "#fdc4c4"],
            "next": function() {
                if(this.index >= this.values.length) {
                    this.index = 0;
                }
                return this.values[this.index++];
            }
        };

        var current = null;
        var bgcolor = colors.next();
        var actions = ["read", "write", "delete", "download"];

        for(var i = 0; i < permissions.length; i++) {
            var permission = permissions[i];
            var tr = table.insertRow(-1);
            var td1 = tr.insertCell(-1);
            var td2 = tr.insertCell(-1);
            var td3 = tr.insertCell(-1);
            var td4 = tr.insertCell(-1);

            if(current != permission.workspace) {
                bgcolor = colors.next();
                current = permission.workspace;
            }

            tr.className = "item";
            td1.style.backgroundColor = bgcolor;
            td1.innerHTML = getOptions("workspace", workspaces, permission.workspace);
            td2.innerHTML = getOptions("action", actions, permission.action);
            td3.innerHTML = "<input name=\"pattern\" type=\"text\" class=\"text\" style=\"width: 80%; box-sizing: border-box;\" value=\"" + permission.pattern + "\"/>";
            td4.innerHTML = "<a class=\"btn delete\" href=\"javascript:void(0)\" userName=\"" + userName + "\" hostName=\"" + hostName + "\" onclick=\"jQuery(this).closest('tr').remove();\">删除</a>";
        }
    };

    jQuery("#add-btn").click(function() {
        var table = document.getElementById("permission-table");
        var tr = table.insertRow(-1);
        var td1 = tr.insertCell(-1);
        var td2 = tr.insertCell(-1);
        var td3 = tr.insertCell(-1);
        var td4 = tr.insertCell(-1);
        var workspaces = table.getAttribute("workspaces").split(",");
        var actions = ["read", "write", "delete", "download"];

        tr.className = "item";
        td1.innerHTML = getOptions("workspace", workspaces, "");
        td2.innerHTML = getOptions("action", actions, "read");
        td3.innerHTML = "<input name=\"pattern\" type=\"text\" class=\"text\" style=\"width: 80%; box-sizing: border-box;\" value=\"/**\"/>";
        td4.innerHTML = "<a class=\"btn delete\" href=\"javascript:void(0)\" onclick=\"jQuery(this).closest('tr').remove();\">delete</a>";
    });

    jQuery("input[name=query]").click(function() {
        var userName = jQuery.trim(jQuery("input[name=userName]").val());
        var hostName = jQuery.trim(jQuery("select[name=hostName]").val());

        if(userName.length < 1) {
            alert("userName Can't be empty！");
            return;
        }

        if(hostName.length < 1) {
            alert("hostName Can't be empty！");
            return;
        }

        var src = jQuery(this);
        src.prop("disabled", true);
        src.prop("class", "button disabled");

        var params = [];
        params[params.length] = "host=" + encodeURIComponent(hostName);
        params[params.length] = "userName=" + encodeURIComponent(userName);

        jQuery.ajax({
            "type": "get",
            "url": "?action=admin.policy.getFilePolicy&" + params.join("&"),
            "dataType": "json",
            "error": function() {
                src.prop("disabled", false);
                src.prop("class", "button");
                alert("Can't be empty！");
            },
            "success": function(response) {
                src.prop("disabled", false);
                src.prop("class", "button");

                if(response.status != 200) {
                    alert(response.message);
                    return;
                }
                jQuery("#permission-panel").show();
                jQuery("#advance-panel").hide();
                showPermissionList(userName, hostName, response.value);
            }
        });
    });

    jQuery("input[name=flush]").click(function() {
        var userName = jQuery.trim(jQuery("input[name=userName]").val());
        var hostName = jQuery.trim(jQuery("select[name=hostName]").val());

        if(userName.length < 1) {
            alert("userName Can't be empty！");
            return;
        }

        if(hostName.length < 1) {
            alert("hostName Can't be empty！");
            return;
        }

        var src = jQuery(this);
        src.prop("disabled", true);
        src.prop("class", "button disabled");

        var params = [];
        params[params.length] = "host=" + encodeURIComponent(hostName);
        params[params.length] = "userName=" + encodeURIComponent(userName);

        jQuery.ajax({
            "type": "get",
            "url": "?action=admin.policy.file.flush&" + params.join("&"),
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
                alert("System error. Please try again later！");
            }
        });
    });
});

jQuery(function() {
    var getUserPermission = function() {
        var context = {};

        jQuery("#permission-table tr").each(function() {
            var workspace = jQuery.trim(jQuery(this).find("select[name=workspace]").val());
            var pattern = jQuery.trim(jQuery(this).find("input[name=pattern]").val());
            var action = jQuery.trim(jQuery(this).find("select[name=action]").val());

            if(workspace.length < 1 || pattern.length < 1) {
                return;
            }

            if(pattern.charAt(0) == "!") {
                if(pattern.length < 2 || pattern.charAt(1) != "/") {
                    return;
                }
            }
            else if(pattern.charAt(0) != "/") {
                return;
            }

            var key = action + "@" + workspace;
            var permission = context[key];

            if(permission == null) {
                permission = context[key] = {"workspace": workspace, "action": action, "includes": []};
            }

            var includes = permission.includes;
            includes[includes.length] = pattern;
        });
        return context;
    };

    var getWorkspaceList = function(context) {
        var map = {};
        var list = [];

        for(var key in context) {
            var permission = context[key];
            var workspace = permission.workspace;
            map[workspace] = workspace;
        }

        for(var name in map) {
            list[list.length] = name;
        }
        return list;
    };

    var getContent = function() {
        var userPermission = getUserPermission();
        var workspaceList = getWorkspaceList(userPermission);

        var b = [];
        b[b.length] = "grant read on localhost@@ {\r\n";

        for(var i = 0; i < workspaceList.length; i++) {
            b[b.length] = "    include " + workspaceList[i] + "\r\n";
        }
        b[b.length] = "}\r\n\r\n";

        for(var key in userPermission) {
            var permission = userPermission[key];
            var workspace = permission.workspace;
            var action = permission.action;
            var includes = permission.includes;
            b[b.length] = "grant " + action + " on localhost@" + workspace + " {\r\n";

            for(var i = 0; i < includes.length; i++) {
                var pattern = jQuery.trim(includes[i]);

                if(pattern.length < 1) {
                    continue;
                }

                if(pattern.charAt(0) == "!") {
                    b[b.length] = "    exclude " + pattern.substring(1) + "\r\n";
                }
                else {
                    b[b.length] = "    include " + pattern + "\r\n";
                }
            }
            b[b.length] = "}\r\n\r\n";
        }
        return b.join("");
    };

    jQuery("input[name=submit]").click(function(event) {
        var userName = jQuery.trim(jQuery("input[name=userName]").val());
        var hostName = jQuery.trim(jQuery("#permission-table").attr("hostName"));
        var content = getContent();

        if(userName.length < 1) {
            alert("userName 不能为空！");
            return;
        }

        if(hostName.length < 1) {
            alert("hostName 不能为空！");
            return;
        }

        var params = [];
        params[params.length] = "userName=" + encodeURIComponent(userName);
        params[params.length] = "content=" + encodeURIComponent(content);

        jQuery.ajax({
            "type": "post",
            "url": "?action=admin.policy.file.save&host=" + encodeURIComponent(hostName),
            "dataType": "json",
            "data": params.join("&"),
            "error": function() {
                alert("系统错误，请稍后再试！");
            },
            "success": function(response) {
                if(response.status != 200) {
                    alert(response.message);
                    return;
                }
                alert("保存成功，请重新查询！");
            }
        });
    });

    jQuery("input[name=test]").click(function(event) {
        var userName = jQuery.trim(jQuery("input[name=userName]").val());
        var hostName = jQuery.trim(jQuery("select[name=hostName]").val());
        var path = jQuery.trim(jQuery("input[name=path]").val());

        if(userName.length < 1) {
            alert("userName 不能为空！");
            return;
        }

        if(hostName.length < 1) {
            alert("hostName 不能为空！");
            return;
        }

        if(path.length < 1) {
            alert("测试路径不能为空！");
            return;
        }

        var params = [];
        params[params.length] = "userName=" + encodeURIComponent(userName);
        params[params.length] = "path=" + encodeURIComponent(path);

        jQuery.ajax({
            "type": "post",
            "url": "?action=admin.policy.file.test&host=" + encodeURIComponent(hostName),
            "dataType": "json",
            "data": params.join("&"),
            "error": function() {
                alert("系统错误，请稍后再试！");
            },
            "success": function(response) {
                alert(response.value);
            }
        });
    });

    jQuery("input[name=edit-mode-btn]").click(function() {
        var src = jQuery(this);

        if(src.attr("edit-mode") == "1") {
            jQuery("#permission-panel").hide();
            jQuery("#advance-panel").show();
        }
        else {
            src.attr("edit-mode", "0");
            jQuery("#permission-panel").show();
            jQuery("#advance-panel").hide();
        }
    });

    jQuery("input[name=advance-submit]").click(function(event) {
        var userName = jQuery.trim(jQuery("input[name=userName]").val());
        var hostName = jQuery.trim(jQuery("select[name=hostName]").val());
        var content = jQuery("textarea[name=content]").val();

        if(userName.length < 1) {
            alert("userName 不能为空！");
            return;
        }

        if(hostName.length < 1) {
            alert("hostName 不能为空！");
            return;
        }

        var params = [];
        params[params.length] = "userName=" + encodeURIComponent(userName);
        params[params.length] = "content=" + encodeURIComponent(content);

        jQuery.ajax({
            "type": "post",
            "url": "?action=admin.policy.file.save&host=" + encodeURIComponent(hostName),
            "dataType": "json",
            "data": params.join("&"),
            "error": function() {
                alert("系统错误，请稍后再试！");
            },
            "success": function(response) {
                if(response.status != 200) {
                    alert(response.message);
                    return;
                }
                alert("保存成功，请重新查询！");
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
<%
    String[] hosts = (String[])(request.getAttribute("hosts"));

    if(hosts == null) {
        hosts = new String[0];
    }
%>
<div class="form">
    <div style="margin: 10px 0px; font-size: 13px;">请输入用户名查询该用户拥有的文件权限:</div>
    <div style="margin: 10px 0px;">
        <input name="userName" type="text" class="text w200" title="用户名" placeholder="user name" value=""/>
        <select name="hostName" type="text" class="select w200" title="主机">
        <%
            for(String hostName : hosts) {
        %>
            <option value="<%=hostName%>"><%=hostName%></option>
        <%
            }
        %>
        </select>
        <input name="query" type="button" class="button" value="查询"/>
        <input name="flush" type="button" class="button" value="刷新缓存"/>
    </div>
</div>
<div id="permission-panel" class="form" style="display: none;">
    <div class="title"><h4>操作说明</h4></div>
    <ul class="tips">
        <li>
            <div style="color: #ff0000;">路径匹配必须以/或者!/开头，支持通配符，遵循ANT路径匹配规则;</div>
<pre style="padding: 8px 0px; width: 80%; box-sizing: border-box; line-height: 20px; font-size: 14px;">
!    排除, 只能出现在开头
?    匹配任何单字符
*    匹配0或者任意数量的字符
**   匹配0或者更多的目录
</pre>
        </li>
    </ul>
    <div style="margin: 10px 0px;">
        <table id="permission-table" class="list">
            <tr class="head">
                <td class="w200">工作空间</td>
                <td class="w200">操作权限</td>
                <td class="w300">路径匹配</td>
                <td>操作</td>
            </tr>
        </table>
        <div style="margin: 10px 0px 10px 210px;">
            <a id="add-btn" class="add-row" href="javascript:void(0)">添加权限</a>
        </div>
    </div>
    <!-- div style="margin: 10px 0px;">
        测试路径：<input name="path" type="text" class="text w400" title="测试路径" placeholder="" value=""/>
        <input name="test" type="button" class="button" value="测试"/>
    </div -->
    <div style="margin: 10px 0px;">
        <input name="submit" type="button" class="button" value="保存"/>
        <input name="edit-mode-btn" type="button" class="button" edit-mode="1" value="高级权限设置"/>
    </div>
</div>
<div id="advance-panel" class="form" style="display: none;">
        <div class="title"><h4>操作说明</h4></div>
    <ul class="tips">
        <li>
            <div style="color: #ff0000;">路径匹配必须以/开头，支持通配符，遵循ANT路径匹配规则;</div>
<pre style="padding: 8px 0px; width: 80%; box-sizing: border-box; line-height: 20px; font-size: 14px;">
1. grant read on localhost, 此处只能且必须是localhost;
2. 两个@表示用户能看到的工作空间;
3. include表示包含某个目录或者文件;
4. exclude表示排除某个目录或者文件;
</pre>
        </li>
    </ul>
    <div style="margin: 10px 0px;">
        <textarea name="content" style="width: 800px; height: 200px; outline: none; resize: none; background-color: #000000; color: #999999;">
# 这只是一个示例, 请从外部编辑好之后粘贴到此处并保存
grant read on localhost@@ {
    include finder.webapp
    include server1.mp3
}

grant read on localhost@finder.webapp {
    include /**
}

grant read on localhost@server1.mp3 {
    include /**
    exclude /test
}
</textarea>
    </div>
    <div style="margin: 10px 0px;">
        <input name="advance-submit" type="button" class="button" value="保存"/>
        <input name="edit-mode-btn" type="button" class="button" edit-mode="0" value="一般权限设置"/>
    </div>
</div>
<div id="pageContext" style="display: none;" contextPath="${contextPath}"></div>
</body>
</html>
