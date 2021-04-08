<%-- 
    Document   : login
    Created on : Jan 4, 2021, 9:48:27 PM
    Author     : IT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>login</title>
    </head>
    <body>
        <h1>Login</h1>
        <form action="DispatchServlet" method="GET">
            Username<input type="text" name=txtUsername value="" required/><br/>
            Password<input type="password" name=txtPassword value="" required/><br/>
            <input type="submit" value="Login" name="btnAction" />
        </form>
        <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/J3.L.P0013/LoginGoogleController
&response_type=code
           &client_id=276667571710-ablkpm0hl51a8s4noj8r6jrbgdba5f81.apps.googleusercontent.com&approval_prompt=force">Login With Google</a><br/>
           <a href="/J3.L.P0013">Back</a>
    </body>
</html>
