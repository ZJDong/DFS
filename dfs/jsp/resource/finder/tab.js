var TabPanel = function(args) {
    var options = (args || {});

    if(typeof(options.container) == "string") {
        this.container = document.getElementById(options.container);
    }
    else {
        this.container = options.container;
    }
    this.create();
};

TabPanel.prototype.getContainer = function() {
    return this.container;
};

TabPanel.prototype.create = function() {
    var self = this;
    var container = this.getContainer();
    var parent = jQuery(container);
    var labelWrap = parent.children("div.tab-label-wrap");
    var panelWrap = parent.children("div.tab-panel-wrap");

    labelWrap.find("ul li.tab-label").unbind();
    labelWrap.find("ul li.tab-label").click(function(event) {
        var target = (event.target || event.srcElement);

        if(target.nodeName == "SPAN" && target.className == "close") {
            self.remove(this);
        }
        else {
            self.active(this);
        }
    });

    labelWrap.find("span.add").click(function() {
        if(self.add != null) {
            self.add();
        }
    });
    labelWrap.find("ul li.tab-label:eq(0)").click();
};

TabPanel.prototype.append = function(opts) {
    var self = this;
    var container = this.getContainer();
    var label = document.createElement("li");
    var panel = document.createElement("div");
    var span = document.createElement("span");

    label.className = "tab-label";
    panel.className = "tab-panel";
    span.className = "label";
    span.appendChild(document.createTextNode(opts.title));

    if(opts.tooltips != null) {
        span.setAttribute("title", opts.tooltips);
    }

    label.appendChild(span);

    if(opts.closeable == true) {
        var btn = document.createElement("span");
        btn.className = "close";
        label.appendChild(btn);
    }

    if(typeof(opts.content) == "string") {
        panel.innerHTML = opts.content;
    }
    else {
        panel.appendChild(opts.content);
    }

    if(opts.id != null) {
        label.setAttribute("tabId", opts.id);
        panel.setAttribute("tabId", opts.id);
    }

    jQuery(container).children("div.tab-label-wrap").children("ul").append(label);
    jQuery(container).children("div.tab-panel-wrap").append(panel);

    jQuery(container).children("div.tab-label-wrap").find("ul li.tab-label").click(function(event) {
        var target = (event.target || event.srcElement);

        if(target.nodeName == "SPAN" && target.className == "close") {
            self.remove(this);
        }
        else {
            self.active(this);
        }
    });

    if(opts.active != false) {
        jQuery(label).click();
    }
    return panel;
};

TabPanel.prototype.remove = function(ele) {
    var src = jQuery(ele);
    var index = src.index();
    var tabId = src.attr("tabId");
    var container = src.closest("div.tab-label-wrap").siblings("div.tab-panel-wrap");
    var active = src.hasClass("tab-active");

    if(active == true) {
        var size = src.parent().children("li").size();

        if((index + 1) < size) {
            src.parent().children("li:eq(" + (index + 1) + ")").click();
        }
        else if(index > 0) {
            src.parent().children("li:eq(" + (index - 1) + ")").click();
        }
        else if(size > 0) {
            src.parent().children("li:eq(" + (size - 1) + ")").click();
        }
    }

    if(tabId == null || tabId == undefined) {
        container.children("div.tab-panel:eq(" + index + ")").remove();
    }
    else {
        container.children("div.tab-panel[tabId=" + tabId + "]").remove();
    }
    src.remove();
};

TabPanel.prototype.active = function(ele) {
    if(typeof(ele) == "number") {
        var label = this.getLabel(ele);
        return this.active(label);
    }

    var src = jQuery(ele);
    var index = src.index();
    var tabId = src.attr("tabId");
    var container = src.closest("div.tab-label-wrap").siblings("div.tab-panel-wrap");

    src.closest("ul").find("li.tab-label").removeClass("tab-active");
    src.addClass("tab-active");

    if(tabId == null || tabId == undefined) {
        container.children("div.tab-panel").hide();
        container.children("div.tab-panel:eq(" + index + ")").show();
        container.children("div.tab-panel:eq(" + index + ")").change();
        container.children("div.tab-panel:eq(" + index + ")").trigger("active");
    }
    else {
        container.children("div.tab-panel").hide();
        container.children("div.tab-panel[tabId=" + tabId + "]").show();
        container.children("div.tab-panel[tabId=" + tabId + "]").change();
        container.children("div.tab-panel[tabId=" + tabId + "]").trigger("active");
    }
    jQuery(window).trigger("resize");
};

TabPanel.prototype.show = function() {
    var container = this.getContainer();
    jQuery(container).children("div.tab-label-wrap").find("ul li.tab-label:eq(0)").click();
};

TabPanel.prototype.size = function() {
    var container = this.getContainer();
    return jQuery(container).children("div.tab-label-wrap").find("ul li.tab-label").size();
};

TabPanel.prototype.getLabel = function(index) {
    var container = this.getContainer();
    var eles = jQuery(container).children("div.tab-label-wrap").find("ul li.tab-label:eq(" + index + ")");

    if(eles.size() > 0) {
        return eles.get(0);
    }
    return null;
};

TabPanel.prototype.getPanel = function(index) {
    var container = this.getContainer();
    var eles = jQuery(container).children("div.tab-panel-wrap").find("div.tab-panel:eq(" + index + ")");

    if(eles.size() > 0) {
        return eles.get(0);
    }
    return null;
};

TabPanel.prototype.getLabelById = function(id) {
    var container = this.getContainer();
    var eles = jQuery(container).children("div.tab-label-wrap").find("ul li.tab-label[tabId=" + id + "]");

    if(eles.size() > 0) {
        return eles.get(0);
    }
    return null;
};

TabPanel.prototype.getActive = function() {
    var container = this.getContainer();
    var index = jQuery(container).children("div.tab-label-wrap").find("ul li.tab-active").index();
    return jQuery(container).children("div.tab-panel-wrap").children("div.tab-panel:eq(" + index + ")").get(0);
};

jQuery(function() {
    window.tabPanel = new TabPanel({"container": "tab-panel-container"});
});

jQuery(function() {
    jQuery(window).resize(function() {
        jQuery(".resize-d").each(function() {
            var offset = jQuery(this).offset();
            var clientHeight = document.documentElement.clientHeight;
            var offsetBottom = this.getAttribute("offset-bottom");

            if(offsetBottom != null) {
                offsetBottom = parseInt(offsetBottom);
            }

            if(isNaN(offsetBottom)) {
                offsetBottom = 0;
            }

            var height = clientHeight - offset.top - offsetBottom;
            jQuery(this).css("height", height + "px");
        });
    });
});

/**
 * 对外API
 */
function display(title, tooltips, url) {
    jQuery("#welcome-panel").remove();
    jQuery("#tab-panel-container").show();

    var iframe = document.getElementById("finderFrame");

    if(iframe == null) {
        iframe = document.createElement("iframe");
        iframe.id = "finderFrame"
        iframe.frameborder = "0";
        iframe.scrolling = "auto";
        iframe.style.cssText = "position: relative; top: 0px; left: 0px; width: 100%; height: 100%; border: 0px solid #ffffff; background-color: transparent; overflow: hidden;";
        iframe.src = "about:blank";

        var opts = {"id": "finder-panel", "active": true, "title": title, "tooltips": tooltips, "content": iframe};
        window.tabPanel.append(opts);
    }
    else {
        var label = window.tabPanel.getLabelById("finder-panel");
        var span = jQuery(label).find("span.label");

        span.html(title);
        span.attr("title", tooltips);
        window.tabPanel.active(label);
    }
    iframe.src = url;
};

function addTabPanel(title, tooltips, url) {
    var iframe = document.createElement("iframe");
    iframe.frameborder = "0";
    iframe.scrolling = "auto";
    iframe.style.cssText = "position: relative; top: 0px; left: 0px; width: 100%; height: 100%; border: 0px solid #ffffff; background-color: transparent; overflow: hidden;";
    iframe.src = url;

    var opts = {"title": title, "tooltips": tooltips, "content": iframe, "active": true, "closeable": true};
    window.tabPanel.append(opts);
};

function setLabel(label) {
    var e = window.tabPanel.getLabelById(label.id);

    if(e == null) {
        return;
    }

    var span = jQuery(e).find("span.label");

    if(label.title != null) {
        span.html(label.title);
    }

    if(label.tooltips != null) {
        span.attr("title", label.tooltips);
    }
};
