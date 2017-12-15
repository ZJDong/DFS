<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>tail - ${fileName} - Powered by Finder</title>
<link rel="shortcut icon" href="?action=res&path=/finder/images/favicon.png"/>
<link rel="stylesheet" type="text/css" href="?action=res&path=/finder/css/less.css"/>
<script type="text/javascript" src="?action=res&path=/finder/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="?action=res&path=/finder/config.js"></script>
<script type="text/javascript" src="?action=res&path=/finder/charset.js"></script>
<script type="text/javascript" src="?action=res&path=/finder/tail.js"></script>
</head>
<body contextPath="${contextPath}" host="${host}" workspace="${workspace}" parent="${parent}" path="${path}" charset="${charset}">
<div id="tail-container" class="less-container" contenteditable="true" spellcheck="false"></div>
<div class="less-status-bar">
    <div style="height: 18px; background-color: #333333;">
        <span class="file"><input id="less-file" type="text" class="text w240" readonly="true" title="${host}@${workspace}/${path}" value="${fileName}"/></span>
        <span class="charset"><select name="charset" selected-value="${charset}"></select></span>
        <span class="ctrl">
            <input id="tail-reload-btn" type="button" class="button" value="刷 新"/>
            <input id="tail-clear-btn" type="button" class="button" value="清 空"/>
            <input id="tail-stop-btn" type="button" class="button" value="停 止"/>
            <input id="tail-select-btn" type="button" class="button" value="全 选"/>
            <input id="tail-find-btn" type="button" class="button" value="过 滤"/>
        </span>
        <span class="pad4"><input id="tail-reload-interval" type="hidden" class="text w30" value="1"/></span>
        <span class="pad4"><input id="tail-auto-scroll" type="checkbox" class="checkbox" checked="true"/>自动滚动</span>
    </div>
</div>
<div id="find-panel" class=" dialog" style="top: 100px; display: none;">
    <div class="find-panel">
        <div>
            查找内容: <input id="grep-keyword" type="text" class="grep-keyword" value="" placeholder="正则示例: /finder/.*\.html"/>
            <input id="grep-ensure" type="button" class="grep-search" value="确定"/>
        </div>
        <div style="clear: both; padding-top: 12px; height: 20px;">
            <span style="float: left; width: 10px; display: inline-block;"><input id="grep-regexp" type="checkbox" title="正则表达式"/></span>
            <span style="float: left; margin-left: 6px; margin-top: -1px; width: 100px; display: inline-block;">正则表达式</span>
        </div>
        <div style="clear: both; margin-top: 10px;">
            <p><span style="color: #ff0000;">提示：</span>快捷键(Ctrl + B)，再次按下可关闭。</p>
            <p><span style="color: #ff0000;">说明：</span>正则相关文档请参考JavaScript正则表达式 。</p>
        </div>
        <div style="margin-top: 20px; text-align: center;">
            <input id="grep-close" type="button" class="grep-button" value="取消"/>
        </div>
    </div>
</div>
<!-- http://www.finderweb.net -->
</body>
</html>
