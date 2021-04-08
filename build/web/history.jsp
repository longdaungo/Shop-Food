<%-- 
    Document   : history
    Created on : Jan 13, 2021, 10:03:07 AM
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
        <h1>Your History</h1>
        <form action="DispatchServlet">
            <input type="submit" value="Reset" name="btnAction" />
        </form>
        <c:if test="${not empty requestScope.MAPHISTORY}">
            <form action="DispatchServlet">
                Date <input type="date" name="searchDate" value="${param.searchDate}" /><br/>
                Name Product <input type="text" name="searchNameProduct" value="${param.searchNameProduct}" /><br/>
                <input type="submit" value="getHistory" name="btnAction"/>
            </form>
            <br/>

            <c:forEach var="order" items="${requestScope.MAPHISTORY}">
                <b>Id Bill:</b>${order.key.id}
                &nbsp;&nbsp;&nbsp;&nbsp;
                <b>Date:</b>${order.key.date}
                <table border="1">
                    <thead>
                        <tr>
                            <th>NO.</th>
                            <th>Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="tblorderdetail" items="${order.value}" varStatus="couter">
                            <tr>
                                <td>${couter.count}</td>
                                <td>${tblorderdetail.productid}</td>
                                <td>${tblorderdetail.quantity}</td>
                                <td>${tblorderdetail.price}</td>
                            </tr>   
                        </c:forEach>
                        <tr>
                            <td colspan="3">
                                <b>Total:</b>
                            </td>
                            <td>
                                ${order.key.total}
                            </td>
                        </tr>
                    </tbody>
                </table>
                <br/>
            </c:forEach>
        </c:if>
        <c:if test="${empty requestScope.MAPHISTORY}">
            <div>There is no history</div>
        </c:if>
        <a href="DispatchServlet?btnAction=search">Back</a>
    </body>
</html>
