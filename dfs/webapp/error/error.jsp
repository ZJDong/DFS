<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" isErrorPage="true"%>
<%!
    private static org.slf4j.Logger logger = null;

    public void jspInit(){
        logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
    }
%>
<%
    int status = response.getStatus();
    String requestURI = request.getRequestURI();
    request.setAttribute("exception", exception);
    logger.error("{} - {}", status, requestURI);

    if(exception != null) {
        logger.error("requestURI: {}", requestURI);
        logger.error(exception.getMessage(), exception);
    }
    else {
        logger.error("{} - {} exception: null", status, requestURI);
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title><%=status%></title>
<link rel="shortcut icon" href="?action=res&path=/finder/images/favicon.png"/>
</head>
<body>
<!-- /error/error -->
<div style="margin: 0px auto; width: 800px;">
    <h1><%=status%></h1>
    <p>System error. Please try again later!</p>
</div>
</body>
</html>
