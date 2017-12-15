<%@ page contentType="text/xml; charset=utf-8"%>
<?xml version="1.0" encoding="utf-8"?>
<tree>
<!-- Finder - Powered by Finder -->
    <treeNode title="Host management" href="?action=admin.host.list"/>
    <%
        if(Boolean.TRUE.equals(request.getAttribute("userSupport"))) {
    %>
    <treeNode title="user management" href="?action=admin.user.query" expand="true">
        <treeNode title="All users" href="?action=admin.user.list"/>
        <treeNode title="Add users" href="?action=admin.user.edit"/>
        <treeNode title="Change users" href="?action=admin.user.query"/>
        <treeNode title="Delete users" href="?action=admin.user.query"/>
    </treeNode>
    <%
        }
    %>
    <treeNode title="Authority management" href="?action=admin.user.host.policy" expand="true">
        <treeNode title="Host permissions" href="?action=admin.user.host.policy"/>
        <treeNode title="File right" href="?action=admin.user.file.policy"/>
    </treeNode>
    <treeNode title="System setup" href="?action=admin.config.list" expand="true">
        <treeNode title="General setting" href="?action=admin.config.edit"/>
        <treeNode title="Security setting" href="?action=admin.security.config.edit"/>
    </treeNode>
    <treeNode title="System update" href="?action=admin.upgrade.index"/>
    <treeNode title="system information" href="?action=admin.system.info"/>
</tree>
