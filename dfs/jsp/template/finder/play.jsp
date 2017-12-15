<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>${path} - Powered by Finder</title>
<link rel="shortcut icon" href="?action=res&path=/finder/images/favicon.png"/>
<link rel="stylesheet" type="text/css" href="?action=res&path=/finder/media/css/media.css"/>
<script type="text/javascript" src="?action=res&path=/finder/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="?action=res&path=/finder/widget.js"></script>
<script type="text/javascript" src="?action=res&path=/finder/media/index.js"></script>
<script type="text/javascript">
<!--
jQuery(function() {
    jQuery(document.body).css("margin", "0px");
    jQuery(document.body).css("padding", "0px");
    jQuery(document.body).css("backgroundColor", "#333333");
    jQuery(document.body).css("overflow", "hidden");
});

jQuery(function() {
    var playList = new PlayList();
    var host = document.body.getAttribute("host");
    var workspace = document.body.getAttribute("workspace");
    var path = document.body.getAttribute("path");
    var requestURI = window.location.pathname;

    var prefix = requestURI + "?action=finder.download&host=" + encodeURIComponent(host) + "&workspace=" + encodeURIComponent(workspace);
    playList.add({"title": "test", "url": prefix + "&path=" + encodeURIComponent(path)});

    var videoPlayer = new VideoPlayer({"container": "finder-videodialog"});
    var container = videoPlayer.getContainer();
    var parent = jQuery(container);
    parent.css("top", "0px");
    parent.css("left", "0px");
    parent.css("width", "100%");
    parent.css("height", "auto");
    parent.css("position", "static");
    parent.css("border", "none");
    parent.find("div.title").remove();

    jQuery(window).resize(function() {
        var height = (document.documentElement.clientHeight - 100);
        parent.find("video").attr("width", "100%");
        parent.find("video").attr("height", height + "px;");
    });
    jQuery(window).resize();

    videoPlayer.setPlayList(playList);
    videoPlayer.play(0);
    parent.show();
});

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
//-->
</script>
</head>
<body loacalIp="${loacalIp}" contextPath="${contextPath}" host="${host}" workspace="${workspace}" work="${work}" path="${path}">
<div id="finder-videodialog" class="media-dialog" contextmenu="false" style="z-index: 1010; margin-top: 0px; margin-left: 0px; display: block;"></div>
</body>
</html>
