<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>grep - ${fileName} - Powered by Finder</title>
<link rel="shortcut icon" href="?action=res&path=/finder/images/favicon.png"/>
<link rel="stylesheet" type="text/css" href="?action=res&path=/finder/css/less.css"/>
<script type="text/javascript" src="?action=res&path=/finder/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="?action=res&path=/finder/config.js"></script>
<script type="text/javascript" src="?action=res&path=/finder/charset.js"></script>
<script type="text/javascript" src="?action=res&path=/finder/grep.js"></script>
</head>
<body contextPath="${contextPath}" host="${host}" workspace="${workspace}" parent="${parent}" path="${path}" charset="${charset}">
<div id="less-container" class="less-container" contenteditable="true" spellcheck="false"></div>
<div id="less-progress-bar" class="less-progress-bar">
    <div class="progress">
        <div class="slider">
            <div class="pace"></div>
            <a class="dot" href="#"></a>
            <div class="mask"></div>
        </div>
    </div>
</div>
<div id="less-status-bar" class="less-status-bar">
    <div style="height: 18px; background-color: #333333;">
        <span class="file"><input id="less-file" type="text" class="text w240" readonly="true" title="${host}@${workspace}/${path}" value="${fileName}"/></span>
        <span class="charset"><select name="charset" selected-value="${charset}"></select></span>
        <span class="info"><input id="less-info" type="text" class="text w160" value="0 B"/></span>
        <span class="status"><input id="less-status" type="text" class="text w160" readonly="true" value="READY"/></span>
    </div>
</div>
<div id="less-tooltip" class="less-tooltip">50%</div>
<div id="find-panel" class=" dialog" style="top: 100px;">
    <div class="find-panel">
        <div>
            查找内容: <input id="grep-keyword" type="text" class="grep-keyword" value="" placeholder="正则示例: /finder/.*\.html"/>
            <input id="grep-ensure" type="button" class="grep-search" value="查找"/>
        </div>
        <div style="clear: both; padding-top: 12px; height: 20px;">
            <span style="float: left; width: 10px; display: inline-block;"><input id="grep-regexp" type="checkbox" title="正则表达式"/></span>
            <span style="float: left; margin-left: 6px; margin-top: -1px; width: 100px; display: inline-block;">正则表达式</span>
        </div>
        <div style="clear: both; margin-top: 10px;">
            <p><span style="color: #ff0000;">提示：</span>快捷键(Ctrl + B)，再次按下可关闭。</p>
            <p><span style="color: #ff0000;">参考：</span><a href="http://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html#sum" target="_blank">正则表达式参考</a></p>
        </div>
        <div style="margin-top: 20px; text-align: center;">
            <input id="grep-close" type="button" class="grep-button" value="关闭"/>
        </div>
    </div>
</div>
<!-- http://www.finderweb.net -->
</body>
</html>
