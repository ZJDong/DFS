<%@ page pageEncoding="utf-8" isThreadSafe="false" session="false"%>
<%!
    String loginURL;
    com.skin.finder.web.ActionDispatcher dispatcher;

    /**
     * @return boolean
     */
    protected static boolean getTrue() {
        return true;
    }
%>
<%
    response.resetBuffer();

    if(this.dispatcher == null) {
        this.loginURL = com.skin.finder.filter.SessionFilter.getLoginURL(request);
        this.dispatcher = new com.skin.finder.web.ActionDispatcher();
        this.dispatcher.setPackages(new String[]{"com.skin.finder.servlet", "com.skin.finder.admin.servlet"});
        this.dispatcher.init(application);
    }

    if(com.skin.finder.filter.SessionFilter.check(request, response, this.loginURL)) {
        this.dispatcher.service(request, response);
    }

    response.flushBuffer();
    out.clear();
    out = pageContext.pushBody(); 

    if(getTrue()) {
        return;
    }
%>
