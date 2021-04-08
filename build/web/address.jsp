<%-- 
    Document   : address
    Created on : Jan 18, 2021, 10:03:01 PM
    Author     : IT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="DispatchServlet">
            address <input type="text" name="address" value="" required/>
            Phone <input type="text" name="phone" value="" required pattern="(\+84|0)\d{9,10}"/><br/>
            <input type="hidden" name="total" value="${param.total}" />
            <input type="submit" value="AddAdress" name="btnAction" />
        </form>
            <a href="DispatchServlet?btnAction=viewCart">Back</a>
    </body>
</html>
