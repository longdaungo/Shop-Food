<%-- 
    Document   : index
    Created on : Jan 4, 2021, 9:45:33 PM
    Author     : IT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>index</title>
    </head>
    <body>
        <h1>Index</h1>
        <%
    String id = request.getAttribute("id").toString();
    String email = request.getAttribute("email").toString();
    out.print("Id: " + id);
    //out.print("<br/>Name: " + name);
    out.print("<br/>Email: " + email);
  %>
    </body>
</html>
