/*
 * $RCSfile: upgrade.js,v $
 * $Revision: 1.1 $
 */
jQuery(function() {
    jQuery("div.menu-bar a.back").click(function() {
        window.history.back();
    });

    jQuery("div.menu-bar a.refresh").click(function() {
        window.location.reload();
    });

    jQuery("div.menu-bar a.add-host").click(function() {
        window.location.href = "?action=admin.host.edit";
    });
});

jQuery(function() {
    var master = PageContext.getAttribute("cluster-master-name");
    var table = document.getElementById("host-table");
    var rows = table.rows;

    for(var i = 0; i < rows.length; i++) {
        var tr = rows[i];
        var hostName = tr.getAttribute("hostName");

        if(hostName == null || hostName == undefined) {
            continue;
        }

        if(hostName == master) {
            jQuery(tr).find("td a.host-sync").attr("class", "btn reload").html("重新载入");
            jQuery(tr).find("td a.delete").remove();
        }
        else {
            jQuery(tr).find("td a.host-sync").addClass("disabled");
        }
    }
});

jQuery(function() {
    /**
     * 1. 遍历所有host, 发起ajax请求获取目标主机的host.xml的版本号
     */
    var handler = function(index) {
        if(index == null || index == undefined) {
            return;
        }

        var table = document.getElementById("host-table");
        var rows = table.rows;

        if(index >= rows.length) {
            return;
        }

        var tr = rows[index];
        var hostName = tr.getAttribute("hostName");
        var version = PageContext.getAttribute("cluster-master-version");

        if(hostName == null || hostName == undefined) {
            return;
        }

        jQuery.ajax({
            "type": "get",
            "url": "?action=agent.host.version&host=" + encodeURIComponent(hostName),
            "dataType": "json",
            "error": function() {
                jQuery(tr).find("td.version").html("连接失败");
                handler(index + 1);
            },
            "success": function(response) {
                if(response.status != 200) {
                    jQuery(tr).find("td.version").attr("title", response.message);
                    jQuery(tr).find("td.version").html("连接失败");
                }
                else {
                    jQuery(tr).removeClass("disabled");
                    jQuery(tr).find("a.btn").removeClass("disabled");

                    if(response.value != version) {
                        jQuery(tr).find("td.version").attr("title", "该节点host配置与master节点不一致，请从master同步数据。");
                        jQuery(tr).find("td.version").html("<span class=\"red\">" + response.value + "</span>");
                    }
                    else {
                        jQuery(tr).find("td.version").html("<span>" + response.value + "</span>");
                    }
                }
                handler(index + 1);
            }
        });
    };
    handler(1);
});

jQuery(function() {
    jQuery("table tr td input[name=orderNum]").change(function() {
        var hostName = this.getAttribute("hostName");
        var value = StringUtil.trim(this.value);

        if(value.length < 1) {
            return;
        }

        var url = "?action=admin.host.setValue&hostName=" + encodeURIComponent(hostName)
            + "&orderNum=" + encodeURIComponent(value);

        jQuery.ajax({"type": "get", "url": url, "dataType": "json"});
    });

    jQuery("table tr td a.host-edit").click(function() {
        var hostName = this.getAttribute("hostName");
        window.location.href = "?action=admin.host.edit&hostName=" + encodeURIComponent(hostName);
    });

    jQuery("table tr td a.reload").click(function() {
        var hostName = this.getAttribute("hostName");

        if(!window.confirm("确定要重新载入 [" + hostName + "] 吗？")) {
            return;
        }

        jQuery.ajax({
            "type": "get",
            "url": "?action=admin.host.reload",
            "dataType": "json",
            "error": function() {
                alert("系统错误，请稍后再试！");
            },
            "success": function(response) {
                if(response.status != 200) {
                    alert(response.message);
                    return;
                }
                window.location.reload();
            }
        });
    });

    jQuery("table tr td a.host-sync").click(function() {
        if(jQuery(this).hasClass("disabled")) {
            return;
        }

        var hostName = this.getAttribute("hostName");

        if(!window.confirm("确定要同步 [" + hostName + "] 吗？")) {
            return;
        }

        jQuery.ajax({
            "type": "get",
            "url": "?action=admin.host.push&hostName=" + encodeURIComponent(hostName),
            "dataType": "json",
            "error": function() {
                alert("系统错误，请稍后再试！");
            },
            "success": function(response) {
                if(response.status != 200) {
                    alert(response.message);
                    return;
                }
                window.location.reload();
            }
        });
    });

    jQuery("table tr td a.work-list").click(function() {
        var hostName = this.getAttribute("hostName");
        window.location.href = "?action=admin.workspace.list&hostName=" + encodeURIComponent(hostName);
    });

    jQuery("table tr td a.delete").click(function() {
        var hostName = this.getAttribute("hostName");

        if(!window.confirm("确定要删除 [" + hostName + "] 吗？")) {
            return;
        }

        jQuery.ajax({
            "type": "get",
            "url": "?action=admin.host.delete&hostName=" + encodeURIComponent(hostName),
            "dataType": "json",
            "error": function() {
                alert("系统错误，请稍后再试！");
            },
            "success": function(response) {
                if(response.status != 200) {
                    alert(response.message);
                    return;
                }
                window.location.reload();
            }
        });
    });
});

