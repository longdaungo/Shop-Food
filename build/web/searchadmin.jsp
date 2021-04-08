<%-- 
    Document   : searchadmin
    Created on : Jan 7, 2021, 3:44:45 PM
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
        <div>
            <font color ="red">
            welcome ${sessionScope.NAME}
            </font>
        </div>
        <a href="DispatchServlet?btnAction=logout">Log Out</a>
        <form action="DispatchServlet" method="Get">
            Search By Name<input type="text" name="searchValue" id="searchValue" value="${param.searchValue}" /><br/>
            Money From <input type="text" name="moneyFrom" id="moneyFrom" value="${param.moneyFrom}" />
            Money To <input type="text" name="moneyTo" id="moneyTo" value="${param.moneyTo}" /><br/>
            <!--Category <input type="text" name="category" id="category" value="${param.category}" />-->
            Category<select name="category">
                <option></option>
                <c:if test="${not empty requestScope.LISTCATEGORY}">
                    <c:set var="count" value="${requestScope.PAGEINDEX*3-3}"/>
                    <c:forEach var="category" items="${requestScope.LISTCATEGORY}">
                        <c:set var="currentcategory" value="${category}"/>
                        <c:if test="${param.category == currentcategory}">
                            <option selected>
                                ${category}
                            </option>
                        </c:if>
                        <c:if test="${param.category != currentcategory}">
                            <option>
                                ${category}
                            </option>
                        </c:if>
                    </c:forEach>
                </c:if>c
            </select>
            <input type="submit" value="searchByAdmin" name="btnAction"/>
        </form>
        <c:set var ="list" value="${requestScope.LISTPRODUCTS}"/>
        <c:if test="${not empty list}">
            <table border="1">
                <thead>
                    <tr>
                        <th>NO.</th>
                        <th>Name</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Image</th>
                        <th>Create Date</th>
                        <th>Category</th>
                        <th>Delete</th>
                        <th>Update</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${list}" varStatus="couter">
                    <form action="DispatchServlet" method="get">
                        <tr>
                            <td>
                                <c:set var="count" value="${count+1}"/>
                                ${count}
                                <input type="hidden" name="id" value="${dto.id}" />
                            </td>
                            <td>
                                ${dto.name}
                                <input type="hidden" name="name" value="${dto.name}"/>
                            </td>
                            <td>
                                ${dto.quantity}
                                <input type="hidden" name="quantity" value="${dto.quantity}" />
                            </td>
                            <td>
                                ${dto.price}
                                <input type="hidden" name="price" value="${dto.price}" />
                            </td>
                            <td>
                                <img src="${dto.image}" width="100">
                                <input type="hidden" name="image" value="${dto.image}"/>
                            </td>
                            <td>${dto.createDate}</td>
                            <td>
                                ${dto.category}
                                <input type="hidden" name="category" value="${dto.category}" />
                            </td>
                            <td><a href="DispatchServlet?btnAction=delete&idproduct=${dto.id}" onclick="return alertDelete()">Delete</a></td>
                            <td><input type="submit" value="Update" name="btnAction"/></td>
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>
        <c:set var="count" value="${requestScope.NUMBERPAGE}"/>
        <c:forEach begin="1" end="${count}" step="1" var="dto">
            <a href="DispatchServlet?searchValue=${param.searchValue}&moneyFrom=${param.moneyFrom}&moneyTo=${param.moneyTo}&category=${param.category}&btnAction=searchByAdmin&pageIndex=${dto}">
                <c:set var="pagecurrent" value="${dto}"/>
                <c:if test="${requestScope.PAGEINDEX == pagecurrent}">
                    <font color = "red">
                    ${dto}
                    </font>
                </c:if>
                <c:if test="${requestScope.PAGEINDEX != pagecurrent}">
                    ${dto}       
                </c:if>
            </a>
        </c:forEach>
    </c:if>
    <c:if test="${empty list}">
        <div>
            There are no product
        </div>
    </c:if>
    <font color="red">
    ${NOTIFICATION}
    </font>
    <br/>
    <a href="DispatchServlet?btnAction=viewcreatefood">create a food</a>
</body>
</html>
