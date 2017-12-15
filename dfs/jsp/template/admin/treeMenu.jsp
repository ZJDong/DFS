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
<link rel="stylesheet" type="text/css" href="?action=res&path=/admin/css/tree.css"/>
<script type="text/javascript" src="?action=res&path=/htree/htree.js"></script>
<script type="text/javascript" src="?action=res&path=/htree/htree.util.js"></script>
<script type="text/javascript" src="?action=res&path=/finder/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
//<![CDATA[
HTree.mode = "menu";
HTree.click = function(src) {
    var url = src.getAttribute("data");

    if(url == null || url.length < 1) {
        return;
    }

    if(url == "javascript:void(0);") {
        return;
    }

    try {
        var doc = window.parent.window.document;
        var iframe = doc.getElementById("mainFrame");

        if(iframe != null) {
            iframe.src = url;
        }
        else {
            alert("System error. Please try again later！");
        }
    }
    catch(e) {
        if(typeof(window.console) != "undefined") {
            window.console.error(e.name + ": " + e.message);
        }
        alert("System error. Please try again later！");
    }
};

function buildTree(id, xmlUrl, rootUrl){
    HTree.config.stylePath = window.location.pathname + "?action=res&path=/htree/menu/";

    var e = document.getElementById(id);

    if(e == null) {
        return;
    }

    var tree = new HTree.TreeNode({text: "Management console", href: rootUrl, xmlSrc: xmlUrl});

    tree.load(function() {
        this.render(document.getElementById(id));
    });
}

jQuery(function() {
    var resize = function() {
        var e = document.getElementById("htree");

        if(e != null) {
            var parent = e.parentNode;
            var offset = parseInt(parent.getAttribute("offset-top"));

            if(isNaN(offset)) {
                offset = 0;
            }

            var height = document.documentElement.clientHeight - offset;
            parent.style.height = height + "px";
        }
    };
    jQuery(window).load(resize);
    jQuery(window).resize(resize);
});

jQuery(function() {
    var requestURI = window.location.pathname;
    buildTree("htree", requestURI + "?action=admin.menu.xml&r=" + new Date().getTime(), requestURI + "?action=finder.blank");
});
//]]>
</script>
</head>
<body contextPath="${contextPath}">
<div class="left-nav">
    <div class="menu-body" style="padding-left: 8px; overflow-x: auto; overflow-y: scroll;">
        <div id="htree" class="htree" style="margin-top: 10px; white-space: nowrap;"></div>
    </div>
</div>
</body>
</html>
