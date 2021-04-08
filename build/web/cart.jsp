<%-- 
    Document   : cart
    Created on : Jan 9, 2021, 8:06:26 PM
    Author     : IT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <script type="text/javascript" src="javascript/notification.js"></script>
        <h1>Your cart</h1>
        <c:if test="${not empty sessionScope.CART.map}">
            <c:set var="cart" value="${sessionScope.CART}"/>
            <table border="1">
                <thead>
                    <tr>
                        <th>NO.</th>
                        <th>Name</th>
                        <th>Amount</th>
                        <th>Price</th>
                        <th>total</th>
                        <th>delete</th>
                        <th>Update Amount</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="total" value="0" />
                    <c:forEach var="dto" items="${cart.map}" varStatus="count">
                        <tr>
                            <td>${count.count}</td>
                            <td>${dto.value.name}</td>
                            <td>
                                ${dto.value.quantity}
                            </td>
                            <td>
                                ${dto.value.price}

                            </td>
                            <td>
                                ${dto.value.total}
                                <c:set var="total" value="${dto.value.total+total}"/>
                            </td>
                            <td>
                                <a href="DispatchServlet?btnAction=removeCart&id=${dto.value.idproduct}" onclick="return alertDelete()">Delete</a>
                            </td>
                            <td>
                                <form action="DispatchServlet">
                                    <input type="hidden" name="name" value="${dto.value.name}" />
                                    <input type="hidden" name="id" value="${dto.value.idproduct}" />
                                    <input type="hidden" name="quantity" value="${dto.value.quantity}" />
                                    <input type="submit" value="updateamount" name="btnAction"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="4">TOTAL</td>
                        <td colspan="3">
                            ${total}
                        </td>
                    </tr>
                </tbody>
            </table>
            <form action="DispatchServlet">
                <input type="hidden" name="total" value="${total}" />
                <input type="submit" value="traditional payment" name="btnAction"/>
                <input type="submit" value="paypal" name="btnAction"/>
            </form>
            <div>You can update your amount</div>
        </c:if>
        <c:if test="${empty sessionScope.CART.map}">
            there is no item in your cart
        </c:if>
            <font color="red">
                ${requestScope.Exception}
            </font>
        <a href="DispatchServlet?btnAction=search">Back</a>
        
    </body>
</html>
