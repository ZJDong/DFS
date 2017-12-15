/*
 * $RCSfile: tree.js,v $
 * $Revision: 1.1 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
var treeId = "htree";

HTree.click = function(src) {
    var node = HTree.getNode(src);
    var url = src.getAttribute("data");
    HTree.setActive(node);

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
            openURL(iframe, url);
            return;
        }
    }
    catch(e) {
        if(typeof(window.console) != "undefined") {
            window.console.error(e.name + ": " + e.message);
        }
        alert("系统错误，请稍后再试！");
    }
};

function openURL(iframe, url) {
    var win = iframe.contentWindow;

    if(win.display == null || win.display == undefined) {
        iframe.src = url;
        return;
    }

    var params = ParameterParser.parse(url);
    var host = params.getParameter("host");
    var workspace = params.getParameter("workspace");
    var path = (params.getParameter("path") || "");
    var title = getFileName(path);
    var tooltips = host + "@" + workspace;

    if(title == "" || title == "/") {
        title = workspace;
    }
    else {
         tooltips = tooltips + "/" + path
    }
    win.display(title, tooltips, url);
};

function getFileName(path) {
    if(path != null) {
        var k = path.lastIndexOf("/");

        if(k > -1) {
            return path.substring(k + 1);
        }
        else {
            return path;
        }
    }
    else {
        return "";
    }
};

/**
 * API
 */
function reload(path) {
    var root = HTree.Util.getRootNode(document.getElementById(treeId));

    if(root == null) {
        return;
    }

    var n = root;
    var a = getNodePaths(path);

    for(var i = 0; i < a.length; i++) {
        n = getTreeNodeByValue(n, a[i]);

        if(n == null) {
            break;
        }
    }

    if(n != null) {
        HTree.reload(n);
    }
};

function expand(path) {
    var root = HTree.Util.getRootNode(document.getElementById(treeId));

    if(root == null) {
        return;
    }

    var a = getNodePaths(path);
    var handler = function(node, i) {
        if(i >= a.length) {
            HTree.setActive(node);
            return;
        }

        var e = getTreeNodeByValue(node, a[i]);

        if(e != null) {
            var height = document.documentElement.clientHeight;
            var scrollTop = document.body.scrollTop;
            var offsetTop = e.offsetTop;

            if(scrollTop > offsetTop) {
                document.body.scrollTop = offsetTop - Math.floor(height / 2);
                document.documentElement.scrollTop = offsetTop - Math.floor(height / 2);
            }

            if(offsetTop > (height + scrollTop)) {
                document.body.scrollTop = offsetTop - Math.floor(height / 2);
                document.documentElement.scrollTop = offsetTop - Math.floor(height / 2);
            }

            HTree.expand(e, {"expand": true, "callback": function(e) {
                handler(e, i + 1);
            }});
        }
        else {
            console.log("node [" + a[i] + "] not found, reload...");
            HTree.reload(node);
        }
    };
    handler(root, 0);
};

function getNodePaths(path) {
    var a = [];
    var s = path.split("/");

    for(var i = 0; i < s.length; i++) {
        s[i] = HTree.trim(s[i]);

        if(s[i].length > 0) {
            a[a.length] = s[i];
        }
    }
    return a;
};

function getTreeNodeByValue(node, value) {
    if(node == null) {
        return null;
    }

    var list = getChildTreeNodes(node);
    var length = list.length;

    for(var i = 0; i < length; i++) {
        var a = HTree.Util.getChildNode(list[i], "//a");

        if(a != null && a.getAttribute("value") == value) {
            return list[i];
        }
    }
    return null;
};

function getChildTreeNodes(node) {
    var c = null;
    var n = node.nextSibling;

    while(n != null) {
        if(n.nodeType == 1) {
            c = n;
            break;
        }
        else {
            n = n.nextSibling;
        }
    }

    var temp = [];

    if(c != null) {
        var list = c.childNodes;
        var length = list.length;

        for(var i = 0; i < length; i++) {
            n = list[i];

            if(n.nodeType == 1 && n.className != null && n.className.indexOf("node") > -1) {
                temp[temp.length] = n;
            }
        }
    }
    return temp;
};

///////////////////
function buildTree(id, xmlUrl, rootUrl) {
    var e = document.getElementById(id);

    if(e == null) {
        return;
    }

    var tree = new HTree.TreeNode({text: "Finder", href: rootUrl, xmlSrc: xmlUrl});

    tree.load(function() {
        HTree.setActive(null);
        this.render(document.getElementById(id));
    });
};

jQuery(function() {
    var resize = function() {
        var e = document.getElementById(treeId);

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
    HTree.config.stylePath = requestURI + "?action=res&path=/htree/images/";
    buildTree(treeId, requestURI + "?action=finder.getHostXml", "");
});
