<!DOCTYPE html>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>Login Form</title>
 
</head>

<body>
  <div class="login">
	<h1>Login</h1>
    <form method="post" action = "/Client/Login">
    	<input type="text" name="u" placeholder="Username" required="required" />
        <input type="password" name="p" placeholder="Password" required="required" />
        <button type="submit" class="btn btn-primary btn-block btn-large">LOGIN</button>
    </form>
</div>
  

</body>
</html>
