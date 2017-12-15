<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>Finder - Powered by Finder</title>
<link rel="stylesheet" type="text/css" href="?action=res&path=/finder/css/form.css"/>
<script type="text/javascript" src="?action=res&path=/finder/jquery-1.7.2.min.js"></script>
</head>
<body contextPath="${contextPath}" workspace="${workspace}">
<div class="box-wrap">
    <div id="finder-panel" class="form">
        <div class="menu-panel"><h4>Hello</h4></div>
        <div class="form-row">
            <div class="form-label">Nickname：</div>
            <div class="form-c300">
                <div class="form-field">
                    
                </div>
            </div>
        </div>
        <div class="form-row">
            <div class="form-label">Content：</div>
            <div class="form-c300">
                <div class="form-field">
                    <textarea name="message"></textarea>
                </div>
            </div>
        </div>
        <div class="button">
            <button id="ensure-btn" class="button ensure">Send out</button>
        </div>
    </div>
</div>
</body>
</html>
