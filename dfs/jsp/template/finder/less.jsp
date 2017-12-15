<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>less - ${fileName} - Powered by Finder</title>
<link rel="shortcut icon" href="?action=res&path=/finder/images/favicon.png"/>
<link rel="stylesheet" type="text/css" href="?action=res&path=/finder/css/less.css"/>
<script type="text/javascript" src="?action=res&path=/finder/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="?action=res&path=/finder/config.js"></script>
<script type="text/javascript" src="?action=res&path=/finder/charset.js"></script>
<script type="text/javascript" src="?action=res&path=/finder/less.js"></script>
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
<!-- http://www.finderweb.net -->
</body>
</html>
