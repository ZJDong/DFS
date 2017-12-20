<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Welcome</title>

</head>
<body>
	<div class="login">
		<%
			String status = (String) session.getAttribute("status");
		%>
		Welcome
		<%
			String username = (String) session.getAttribute("fname");
		%>
		<%=username%>
		<br>
		<br>
		<%
			if (status.equals("1")) {
		%>
		File content is visible below. (Editable) <br>Filename:<%
			String filename = (String) session.getAttribute("filename");
		%>
		<%=filename%>
		<%
			String filecontent = (String) session.getAttribute("filecontent");
		%>
		<br>
		<form method="post" id="usrform" action="/Client/SendChangesForWrite">
			<textarea style="width: 100%; height: 100%; border: solif, medium;"
				name='writebox' id='writebox' form="usrform"><%=filecontent%></textarea>
			<button type="submit" class="btn btn-primary btn-block btn-large">Submit
				Changes</button>
		</form>
		<br> <br>
		<%
			} else {
		%>
		<%
			String message = (String) session.getAttribute("message");
		%>
		<%
			if (!message.isEmpty()) {
		%>
		<%=message%>
		<%
			} else {
		%>
		Session expired because of invalid login token or using browser back
		button
		<%
			}
		%>
		<%
			}
		%>


	</div>
	<script src="js/index.js"></script>
</body>
</html>