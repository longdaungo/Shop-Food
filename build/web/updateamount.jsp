<%-- 
    Document   : updateamount
    Created on : Jan 10, 2021, 3:43:49 PM
    Author     : IT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <script type="text/javascript" src="javascript/notification.js"></script>
    <body>
        <h1>Update Amount</h1>
        <h2>${param.name}</h2>
        <form action="DispatchServlet" onsubmit="return validateAmountNumber()">
            quantity<input type="number" name="quantity" value="${param.quantity}" min="1" max="2000" step="1" pattern="[0-9]{1,4}" required id="quantity"/>
            <input type="hidden" name="id" value="${param.id}" />
            <input type="submit" value="updatedamount" name="btnAction" />
        </form>
    </body>
</html>
