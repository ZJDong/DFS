var PageContext = {};

PageContext.setAttribute = function(name, value) {
    var e = document.getElementById("pageContext");

    if(e != null) {
        if(value == null || value == undefined) {
            e.removeAttribute(name);
        }
        else {
            e.setAttribute(name, value);
        }
    }
};

PageContext.getAttribute = function(name, defaultValue) {
    var value = null;
    var e = document.getElementById("pageContext");

    if(e != null) {
        value = e.getAttribute(name);
    }

    if(value == null || value == undefined) {
        value = defaultValue;
    }

    if(value != null && value != undefined) {
        return value;
    }
    return null;
};

PageContext.getInteger = function(name, defaultValue) {
    var value = this.getAttribute(name);

    if(value == null || isNaN(value)) {
        return defaultValue;
    }
    return parseInt(value);
};

PageContext.getContextPath = function() {
    if(this.contextPath == null || this.contextPath == undefined) {
        var contextPath = document.body.getAttribute("contextPath");

        if(contextPath == null || contextPath == "/") {
            contextPath = "";
        }
        this.contextPath = contextPath;
    }
    return this.contextPath;
};

var App = {};

App.show = function(leftUrl, mainUrl, viewType) {
    try {
        var doc = window.document;
        var leftFrame = doc.getElementById("leftFrame");
        var mainFrame = doc.getElementById("mainFrame");

        if(leftFrame != null && leftUrl != null) {
            leftFrame.src = leftUrl;
        }

        if(mainFrame != null && mainUrl != null) {
            mainFrame.src = mainUrl;
        }
    }
    catch(e) {
        if(typeof(window.console) != "undefined") {
            window.console.error(e.name + ": " + e.message);
        }
    }
};

App.home = function() {
    App.show("?action=finder.tree", "?action=finder.tabpanel");
};

App.refresh = function() {
    window.location.reload();
};

App.setting = function() {
    App.show("?action=admin.menu", "?action=admin.system.info");
};

App.help = function() {
    window.open("?action=finder.help", "_blank");
};

App.logout = function() {
    window.location.href = "?action=finder.logout";
};

jQuery(function() {
    var admin = PageContext.getAttribute("admin", "false");

    if(admin == "true") {
        jQuery("#tools-menu").show();
    }
    else {
        jQuery("#tools-setting").remove();
        jQuery("#tools-menu").show();
    }
});

jQuery(function() {
    var navBar = PageContext.getAttribute("nav-bar", "true");

    if(navBar == "true") {
        jQuery("#menu-bar").show();
    }
    else {
        jQuery("#menu-bar").hide();
    }
    jQuery("#statusBar").show();
});

jQuery(function() {
    jQuery(window).resize(function(){
        var offset = document.getElementById("viewPanel").offsetTop + 24;
        var clientHeight = document.documentElement.clientHeight;
        document.getElementById("leftFrame").style.height = (clientHeight - offset) + "px";
        document.getElementById("mainFrame").style.height = (clientHeight - offset) + "px";
    });
    jQuery(window).resize();
});

/**
 * 事件入口
 * 拦截当前页面的全部事件, 并将控制权转交到widget
 * widget负责管理所有的窗口并负责将页面的事件转发给当前活动窗口
 * widget完成的功能有：
 * 1. 窗口管理, zIndex分配, 活动窗口管理
 * 2. 事件转发, 将事件转发给当前活动的窗口
 */
jQuery(function() {
    /**
     * jQuery-1.7.2版本的paste事件获取不到clipboardData
     * 该事件尽可能放到jQuery事件之前
     * bug: IE11不触发paste事件
     */
    EventUtil.addEventListener(document, "paste", function(event) {
        return DialogManager.dispatch("paste", event);
    });

    jQuery(document).click(function(event) {
        return DialogManager.dispatch("click", event);
    });

    jQuery(document).dblclick(function(event) {
        return DialogManager.dispatch("dblclick", event);
    });

    /**
     * keydown事件先于paste触发
     * 因此要保证paste被触发必须使Ctrl + V操作返回true
     * 如果Ctrl + V事件存在弹框, 那么root将无法捕获到paste事件
     * 因为当弹框出现的时候, 弹框是活动窗口, 因此paste事件不会被传递到root
     */
    jQuery(document).keydown(function(event) {
        var flag = DialogManager.dispatch("keydown", event);
        return flag;
    });

    jQuery(document).bind("contextmenu", function(event) {
        var e = (event || window.event);
        var src = (e.srcElement || e.target);
        var nodeName = src.nodeName.toLowerCase();

        if(nodeName == "input" || nodeName == "textarea") {
            return true;
        }
        else {
            return DialogManager.dispatch("contextmenu", e);
        }
    });
});

jQuery(function() {
    setTimeout(function() {
        jQuery("#upgrade-tips").fadeOut();
    }, 10000);
});
