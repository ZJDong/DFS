/*
 * $RCSfile: WorkspacesTemplate.java,v $
 * $Revision: 1.1 $
 *
 * JSP generated by JspCompiler-1.0.0
 */
package com.skin.finder.admin.servlet.template;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import com.skin.finder.cluster.Host;
import com.skin.finder.cluster.Workspace;


/**
 * <p>Title: WorkspacesTemplate</p>
 * <p>Description: </p>
 * @author JspKit
 * @version 1.0
 */
public class WorkspacesTemplate extends com.skin.finder.web.servlet.JspServlet {
    private static final long serialVersionUID = 1L;
    private static final WorkspacesTemplate instance = new WorkspacesTemplate();


    /**
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public static void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        instance.service(request, response);
    }

    /**
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html; charset=utf-8");
        OutputStream out = response.getOutputStream();


    Host host = (Host)(request.getAttribute("host"));

    if(host == null) {
        host = new Host();
    }

    List<Workspace> workspaces = host.getWorkspaces();

        out.write(_jsp_string_5, 0, _jsp_string_5.length);
        out.write(_jsp_string_6, 0, _jsp_string_6.length);
        out.write(_jsp_string_7, 0, _jsp_string_7.length);
        out.write(_jsp_string_8, 0, _jsp_string_8.length);
        out.write(_jsp_string_9, 0, _jsp_string_9.length);
        out.write(_jsp_string_10, 0, _jsp_string_10.length);
        out.write(_jsp_string_11, 0, _jsp_string_11.length);
        out.write(_jsp_string_12, 0, _jsp_string_12.length);
        out.write(_jsp_string_13, 0, _jsp_string_13.length);
        out.write(_jsp_string_14, 0, _jsp_string_14.length);
        out.write(_jsp_string_15, 0, _jsp_string_15.length);
        out.write(_jsp_string_16, 0, _jsp_string_16.length);
        out.write(_jsp_string_17, 0, _jsp_string_17.length);
        out.write(_jsp_string_18, 0, _jsp_string_18.length);
        out.write(_jsp_string_19, 0, _jsp_string_19.length);
        out.write(_jsp_string_20, 0, _jsp_string_20.length);
        out.write(_jsp_string_21, 0, _jsp_string_21.length);
        out.write(_jsp_string_22, 0, _jsp_string_22.length);
        out.write(_jsp_string_23, 0, _jsp_string_23.length);
        out.write(_jsp_string_24, 0, _jsp_string_24.length);
        out.write(_jsp_string_25, 0, _jsp_string_25.length);
        out.write(_jsp_string_26, 0, _jsp_string_26.length);
        out.write(_jsp_string_27, 0, _jsp_string_27.length);
        out.write(_jsp_string_28, 0, _jsp_string_28.length);
        out.write(_jsp_string_29, 0, _jsp_string_29.length);
        this.write(out, request.getAttribute("contextPath"));
        out.write(_jsp_string_31, 0, _jsp_string_31.length);
        this.write(out, (host.getName()));
        out.write(_jsp_string_33, 0, _jsp_string_33.length);
        out.write(_jsp_string_34, 0, _jsp_string_34.length);
        out.write(_jsp_string_35, 0, _jsp_string_35.length);
        out.write(_jsp_string_36, 0, _jsp_string_36.length);
        out.write(_jsp_string_37, 0, _jsp_string_37.length);
        out.write(_jsp_string_38, 0, _jsp_string_38.length);
        out.write(_jsp_string_39, 0, _jsp_string_39.length);

        for(Workspace workspace : workspaces) {
    
        out.write(_jsp_string_41, 0, _jsp_string_41.length);
        this.write(out, (workspace.getName()));
        out.write(_jsp_string_43, 0, _jsp_string_43.length);
        this.write(out, (workspace.getOrderNum()));
        out.write(_jsp_string_45, 0, _jsp_string_45.length);
        this.write(out, (workspace.getName()));
        out.write(_jsp_string_47, 0, _jsp_string_47.length);
        this.write(out, (workspace.getDisplayName()));
        out.write(_jsp_string_49, 0, _jsp_string_49.length);
        this.write(out, (workspace.getWork()));
        out.write(_jsp_string_51, 0, _jsp_string_51.length);
        this.write(out, (workspace.getCharset()));
        out.write(_jsp_string_53, 0, _jsp_string_53.length);
        this.write(out, (workspace.getReadonly()));
        out.write(_jsp_string_55, 0, _jsp_string_55.length);
        this.write(out, (workspace.getReadonly()));
        out.write(_jsp_string_57, 0, _jsp_string_57.length);
        this.write(out, (workspace.getName()));
        out.write(_jsp_string_59, 0, _jsp_string_59.length);
        this.write(out, (workspace.getName()));
        out.write(_jsp_string_61, 0, _jsp_string_61.length);

        }
    
        out.write(_jsp_string_63, 0, _jsp_string_63.length);

        out.flush();
    }

    protected static final byte[] _jsp_string_5 = b("<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n");
    protected static final byte[] _jsp_string_6 = b("<meta http-equiv=\"Pragma\" content=\"no-cache\"/>\r\n<meta http-equiv=\"Cache-Control\" content=\"no-cache\"/>\r\n");
    protected static final byte[] _jsp_string_7 = b("<meta http-equiv=\"Expires\" content=\"0\"/>\r\n<title>Finder - Powered by Finder</title>\r\n");
    protected static final byte[] _jsp_string_8 = b("<link rel=\"shortcut icon\" href=\"?action=res&path=/finder/images/favicon.png\"/>\r\n");
    protected static final byte[] _jsp_string_9 = b("<link rel=\"stylesheet\" type=\"text/css\" href=\"?action=res&path=/admin/css/form.css\"/>\r\n");
    protected static final byte[] _jsp_string_10 = b("<script type=\"text/javascript\" src=\"?action=res&path=/finder/jquery-1.7.2.min.js\"></script>\r\n");
    protected static final byte[] _jsp_string_11 = b("<script type=\"text/javascript\" src=\"?action=res&path=/admin/base.js\"></script>\r\n");
    protected static final byte[] _jsp_string_12 = b("<script type=\"text/javascript\">\r\n<!--\r\njQuery(function() {\r\n    jQuery(\"#create\").click(function() {\r\n");
    protected static final byte[] _jsp_string_13 = b("        var hostName = document.body.getAttribute(\"hostName\");\r\n        window.location.href = \"?action=admin.workspace.edit&hostName=\" + encodeURIComponent(hostName);\r\n");
    protected static final byte[] _jsp_string_14 = b("    });\r\n\r\n    jQuery(\"table tr td a.update\").click(function() {\r\n        var hostName = document.body.getAttribute(\"hostName\");\r\n");
    protected static final byte[] _jsp_string_15 = b("        var workspaceName = this.getAttribute(\"workspaceName\");\r\n        window.location.href = \"?action=admin.workspace.edit&hostName=\" + encodeURIComponent(hostName) + \"&workspaceName=\" + encodeURIComponent(workspaceName);\r\n");
    protected static final byte[] _jsp_string_16 = b("    });\r\n\r\n    jQuery(\"table tr td a.delete\").click(function() {\r\n        var hostName = document.body.getAttribute(\"hostName\");\r\n");
    protected static final byte[] _jsp_string_17 = b("        var workspaceName = this.getAttribute(\"workspaceName\");\r\n\r\n        if(!window.confirm(\"确定要删除 [\" + workspaceName + \"] 吗？\")) {\r\n");
    protected static final byte[] _jsp_string_18 = b("            return;\r\n        }\r\n\r\n        jQuery.ajax({\r\n            \"type\": \"get\",\r\n");
    protected static final byte[] _jsp_string_19 = b("            \"url\": \"?action=admin.workspace.delete&hostName=\" + encodeURIComponent(hostName) + \"&workspaceName=\" + encodeURIComponent(workspaceName),\r\n");
    protected static final byte[] _jsp_string_20 = b("            \"dataType\": \"json\",\r\n            \"error\": function() {\r\n                alert(\"系统错误，请稍后再试！\");\r\n");
    protected static final byte[] _jsp_string_21 = b("            },\r\n            \"success\": function(response) {\r\n                if(response.status != 200) {\r\n");
    protected static final byte[] _jsp_string_22 = b("                    alert(response.message);\r\n                    return;\r\n                }\r\n");
    protected static final byte[] _jsp_string_23 = b("                window.location.reload();\r\n            }\r\n        });\r\n    });\r\n");
    protected static final byte[] _jsp_string_24 = b("\r\n    jQuery(\"table tr td input[name=orderNum]\").change(function() {\r\n        var hostName = document.body.getAttribute(\"hostName\");\r\n");
    protected static final byte[] _jsp_string_25 = b("        var workspaceName = this.getAttribute(\"workspaceName\");\r\n        var value = StringUtil.trim(this.value);\r\n");
    protected static final byte[] _jsp_string_26 = b("\r\n        if(value.length < 1) {\r\n            return;\r\n        }\r\n\r\n        var url = \"?action=admin.workspace.setValue&hostName=\" + encodeURIComponent(hostName)\r\n");
    protected static final byte[] _jsp_string_27 = b("            + \"&workspaceName=\" + encodeURIComponent(workspaceName)\r\n            + \"&orderNum=\" + encodeURIComponent(value)\r\n");
    protected static final byte[] _jsp_string_28 = b("\r\n        jQuery.ajax({\"type\": \"get\", \"url\": url, \"dataType\": \"json\"});\r\n    });\r\n");
    protected static final byte[] _jsp_string_29 = b("});\r\n//-->\r\n</script>\r\n</head>\r\n<body contextPath=\"");
    protected static final byte[] _jsp_string_31 = b("\" hostName=\"");
    protected static final byte[] _jsp_string_33 = b("\">\r\n<div class=\"menu-bar\">\r\n    <a class=\"button\" href=\"javascript:void(0)\" onclick=\"window.history.back();\"><span class=\"back\"></span>返回</a>\r\n");
    protected static final byte[] _jsp_string_34 = b("    <a class=\"button\" href=\"javascript:void(0)\" onclick=\"window.location.reload();\"><span class=\"refresh\"></span>刷新</a>\r\n");
    protected static final byte[] _jsp_string_35 = b("    <span class=\"seperator\"></span>\r\n    <a id=\"create\" class=\"button\" href=\"javascript:void(0)\"><span class=\"add\"></span>新建</a>\r\n");
    protected static final byte[] _jsp_string_36 = b("</div>\r\n<table id=\"workspace-table\" class=\"list\">\r\n    <tr class=\"head\">\r\n        <td class=\"w100\">Order</td>\r\n");
    protected static final byte[] _jsp_string_37 = b("        <td class=\"w200\">Name</td>\r\n        <td class=\"w200\">Display Name</td>\r\n");
    protected static final byte[] _jsp_string_38 = b("        <td class=\"w300\">Work</td>\r\n        <td class=\"w100\">Charset</td>\r\n        <td class=\"w100\">Readonly</td>\r\n");
    protected static final byte[] _jsp_string_39 = b("        <td>操作</td>\r\n    </tr>\r\n    ");
    protected static final byte[] _jsp_string_41 = b("    <tr>\r\n        <td><input name=\"orderNum\" type=\"text\" class=\"text w60\" workspaceName=\"");
    protected static final byte[] _jsp_string_43 = b("\" value=\"");
    protected static final byte[] _jsp_string_45 = b("\"/></td>\r\n        <td>");
    protected static final byte[] _jsp_string_47 = b("</td>\r\n        <td>");
    protected static final byte[] _jsp_string_49 = b("</td>\r\n        <td>");
    protected static final byte[] _jsp_string_51 = b("</td>\r\n        <td>");
    protected static final byte[] _jsp_string_53 = b("</td>\r\n        <td><span class=\"");
    protected static final byte[] _jsp_string_55 = b("\">");
    protected static final byte[] _jsp_string_57 = b("</span></td>\r\n        <td>\r\n            <a class=\"btn update\" href=\"javascript:void(0)\" workspaceName=\"");
    protected static final byte[] _jsp_string_59 = b("\">编 辑</a>\r\n            <a class=\"btn delete\" href=\"javascript:void(0)\" workspaceName=\"");
    protected static final byte[] _jsp_string_61 = b("\">刪 除</a>\r\n        </td>\r\n    </tr>\r\n    ");
    protected static final byte[] _jsp_string_63 = b("</table>\r\n</body>\r\n</html>\r\n");

}