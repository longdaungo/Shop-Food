<%-- 
    Document   : update
    Created on : Jan 9, 2021, 3:31:09 PM
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
            <h1>Update</h1>
            <input type="hidden" name="id" value="${param.id}" />
            <b>Name Food</b><input type="text" name="Name" value="${param.name}" required pattern="[a-zA-Z ]{1,15}"/>(contain a-z or A-z, length 15 and required)<br/>
            <b>Price</b> <input type="text" name="Price" value="${param.price}" required pattern="[0-9]{1,6}"/> (contain 0-9, length 6 and required)<br/>
            <b>quantity</b><input type="text" name="Quantity" value="${param.quantity}" required pattern="[0-9]{1,6}"/> (contain 0-9,length 6 and required)<br/>
            <b>link image</b> <input type="text" name="image" value="${param.image}" pattern=".{0}|.{1,500}"/>(length is 500, not required)<br/>
            <b>category</b> <input type="text" name="category" value="${param.category}" required pattern="[a-zA-Z ]{1,15}"/> (contain a-z or A-z, length 15 and required)<br/>
            <input type="submit" value="Updated" name="btnAction"/>
        </form>
        <a href="DispatchServlet?btnAction=searchByAdmin">Back</a>
    </body>
</html>
