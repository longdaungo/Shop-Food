<%-- 
    Document   : search
    Created on : Jan 5, 2021, 7:19:23 PM
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
        <div>
            <font color ="red">
            welcome ${sessionScope.NAME}
            </font>
        </div>
        <a href="DispatchServlet?btnAction=logout">Log Out</a>
        <form action="DispatchServlet" method="Get">
            Search By Name<input type="text" name="searchValue" value="${param.searchValue}" /><br/>
            Money From <input type="text" name="moneyFrom" value="${param.moneyFrom}" />
            Money To <input type="text" name="moneyTo" value="${param.moneyTo}" /><br/>
            Category<select name="category">
                <option></option>
                <c:if test="${not empty requestScope.LISTCATEGORY}">
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
            <input type="submit" value="search" name="btnAction" />
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
                        <th>Shopping</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="count" value="${requestScope.PAGEINDEX*3-3}"/>
                    <c:forEach var="dto" items="${list}" varStatus="couter">
                    <form action="DispatchServlet">
                        <tr>
                            <td>
                                <c:set var="count" value="${count+1}"/>
                                ${count}
                            </td>
                            <td>
                                ${dto.name}
                                <input type="hidden" name="id" value="${dto.id}" />
                                <input type="hidden" name="name" value="${dto.name}" />
                            </td>
                            <td>
                                ${dto.quantity}
                                <input type="hidden" name="quantity" value="1"/>
                            </td>
                            <td>
                                ${dto.price}
                                <input type="hidden" name="price" value="${dto.price}" />
                            </td>
                            <td><img src="${dto.image}" width="100"></td>
                            <td>${dto.createDate}</td>
                            <td>${dto.category}</td>
                            <td>
                                <input type="hidden" name="pageIndex" value="${requestScope.PAGEINDEX}"/>
                                <input type="hidden" name="searchValue" value="${param.searchValue}" />
                                <input type="hidden" name="moneyFrom" value="${param.moneyFrom}" />
                                <input type="hidden" name="moneyTo" value="${param.moneyTo}" />
                                <input type="hidden" name="category" value="${param.category}" />
                                <input type="submit" value="buy" name="btnAction"/>
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>
        <c:set var="count" value="${requestScope.NUMBERPAGE}"/>
        <c:forEach begin="1" end="${count}" step="1" var="dto">
            <a href="DispatchServlet?searchValue=${param.searchValue}&moneyFrom=${param.moneyFrom}&moneyTo=${param.moneyTo}&category=${param.category}&btnAction=search&pageIndex=${dto}">
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
        </c:forEach><br/>
        <a href="DispatchServlet?btnAction=viewCart">See your cart</a><br/>
    </c:if>
    <c:if test="${empty list}">
        <div>
            There are no product
        </div>
    </c:if>     
    <font color="red">
    ${requestScope.NOTIFICATION}
    </font>
    <a href="DispatchServlet?btnAction=getHistory">Shopping History</a>
    <h2>Best Seller</h2>
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
                <th>Shopping</th>
            </tr>
        </thead>
        <tbody>
            <c:set var="count" value="${requestScope.PAGEINDEXBESTSELLER*3-3}"/>
            <c:forEach var="dto" items="${requestScope.LISTPRODUCTSBESTSELLER}" varStatus="counter">
            <form action="DispatchServlet">
                <tr>
                    <td>
                        <c:set var="count" value="${count+1}"/>
                        ${count}
                    </td>
                    <td>
                        ${dto.name}
                        <input type="hidden" name="id" value="${dto.id}" />
                        <input type="hidden" name="name" value="${dto.name}" />
                    </td>
                    <td>
                        ${dto.quantity}
                        <input type="hidden" name="quantity" value="1"/>
                    </td>
                    <td>
                        ${dto.price}
                        <input type="hidden" name="price" value="${dto.price}" />
                    </td>
                    <td>
                        <img src="${dto.image}" width="100">
                    </td>
                    <td>${dto.createDate}</td>
                    <td>${dto.category}</td>
                    <td>
                        <input type="hidden" name="pageIndexBestSeller" value="${requestScope.PAGEINDEXBESTSELLER}" />
                        <input type="submit" value="buy" name="btnAction"/>
                    </td>
                </tr>
            </form>
        </c:forEach>
    </tbody>
</table>
<a href="DispatchServlet?btnAction=search&pageIndexBestSeller=1">1</a>
<a href="DispatchServlet?btnAction=search&pageIndexBestSeller=2">2</a>

<c:if test="${not empty requestScope.LISTPRODUCTRECOMMENDATION}">
    <h2>Recomend for you</h2>
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
                <th>Shopping</th>
            </tr>
        </thead>
        <tbody>
            <c:set var="count" value="${requestScope.PAGEINDEXRECOMMENDATION*3-3}"/>
            <c:forEach var="dto" items="${requestScope.LISTPRODUCTRECOMMENDATION}" varStatus="counter">
            <form action="DispatchServlet">
                <tr>
                    <td>
                        <c:set var="count" value="${count+1}"/>
                        ${count}
                    </td>
                    <td>
                        ${dto.name}
                        <input type="hidden" name="id" value="${dto.id}" />
                        <input type="hidden" name="name" value="${dto.name}" />
                    </td>
                    <td>
                        ${dto.quantity}
                        <input type="hidden" name="quantity" value="1"/>
                    </td>
                    <td>
                        ${dto.price}
                        <input type="hidden" name="price" value="${dto.price}" />
                    </td>
                    <td>
                        <img src="${dto.image}" width="100">
                    </td>
                    <td>${dto.createDate}</td>
                    <td>${dto.category}</td>
                    <td>
                        <input type="hidden" name="pageIndexRecomendation" value="${requestScope.PAGEINDEXRECOMMENDATION}" />
                        <input type="submit" value="buy" name="btnAction"/>
                    </td>
                </tr>
            </form>
        </c:forEach>
            <c:forEach begin="1" end="${requestScope.NUMBERPAGERECOMMENDATION}" step="1" var="dto">
            <a href="DispatchServlet?btnAction=search&pageIndexRecomendation=${dto}">
                <c:set var="pagecurrent" value="${dto}"/>
                <c:if test="${requestScope.PAGEINDEXRECOMMENDATION == pagecurrent}">
                    <font color = "red">
                    ${dto}
                    </font>
                </c:if>
                <c:if test="${requestScope.PAGEINDEXRECOMMENDATION != pagecurrent}">
                    ${dto}       
                </c:if>
            </a>
        </c:forEach><br/>
    </c:if>

</body>
</html>
