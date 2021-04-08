<%-- 
    Document   : createfood.html
    Created on : Jan 8, 2021, 9:17:38 AM
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
        <script type="text/javascript" src="javascript/notification.js"></script>
       <h1>Create food</h1>
       <form action="DispatchServlet" method="get" onsubmit="return validateQuantityOfInsertedProduct()"> 
            <b>Name Food</b><input type="text" name="Name" value="" required pattern="[a-zA-Z ]{1,15}"/>(contain a-z or A-z, length 15 and required)<br/>
            <b>Price</b> <input type="text" name="Price" value="" required pattern="[0-9]{1,6}"/> (contain 0-9, length 6 and required)<br/>
            <b>quantity</b><input type="number" name="Quantity" id="quantity" value="" required pattern="[0-9]{1,6}"/> (contain 0-9,length 6 and required)<br/>
            <b>link image</b> <input type="text" name="image" value="" pattern=".{0}|.{1,500}"/>(length is 500, not required)<br/>
            <b>category</b> <input type="text" name="category" value="" required pattern="[a-zA-Z ]{1,15}"/> (contain a-z or A-z, length 15 and required)<br/>
            <input type="submit" value="create new food" name="btnAction" /><br/>
        </form>
       <a href="DispatchServlet?btnAction=searchByAdmin">Back</a>
    </body>
</html>
