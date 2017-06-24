<%-- 
    Document   : eBooksStoreManager
    Created on : Jun 16, 2017, 9:05:15 PM
    Author     : MariusNeagu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Electronic Books Store Manager</title>
        <link rel="stylesheet" type="text/css" href="./css/eBooksStoreCSS.css">
    </head>
    <body>
        <%-- test if actual user is authenticated and authorized --%>
        <c:choose>
            <c:when test="${validUser == true}">   
                <%-- include menu --%>
                <%@ include file="./utils/eBooksStoreMenu.jsp" %>
                <%-- Master view --%>
                <form action="${pageContext.request.contextPath}/eBooksStoreManagerServlet" method="POST">
                    <sql:setDataSource 
                        var="snapshot" 
                        driver="org.apache.derby.jdbc.ClientDriver"
                        url="jdbc:derby://localhost:1527/eBooksDatabase ;create=true;"
                        user="test"  
                        password="test"/>
                    <sql:query dataSource="${snapshot}" var="result">
                        SELECT EBOOKS_AUTHORS.ID, EBOOKS_AUTHORS.ID_AUTHORS, AUTHORS.NAME, EBOOKS.ISBN, EBOOKS.TITLE, EBOOKS.PRICE
                        FROM AUTHORS, EBOOKS, EBOOKS_AUTHORS
                        WHERE AUTHORS.ID = EBOOKS_AUTHORS.ID_AUTHORS
                        AND EBOOKS_AUTHORS.ISBN = EBOOKS.ISBN
                    </sql:query>

                    <table border="1" width="100%">
                        <tr>
                            <td class="thc"> select </td>
                            <td class="thc">ID</td>
                            <td class="thc">ID_AUTHORS</td>
                            <td class="thc">NAME</td>
                            <td class="thc">ISBN</td>
                            <td class="thc">TITLE</td>
                            <td class="thc">PRICE</td>
                        </tr>
                        <c:forEach var="row" varStatus="loop" items="${result.rows}">
                            <tr>
                                <td><input type="checkbox" name="admin_isbn_values_checkbox" value="${row.ISBN}"></td>
                                <td><c:out value="${row.ID}"/></td>
                                <td><c:out value="${row.ID_AUTHORS}"/></td>
                                <td><c:out value="${row.NAME}"/></td>
                                <td><c:out value="${row.ISBN}"/></td>
                                <td><c:out value="${row.TITLE}"/></td>
                                <td><c:out value="${row.PRICE}"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <%-- Details --%>
                    <table class="tablecenterdwithborder">
                        <tr><td>
                                <table>
                                    <tr>
                                        <td> ID </td><td> <input type="text" name="admin_id_values"></input></td>
                                    </tr>
                                    <tr>
                                        <td> ID_AUTHORS </td><td> <input type="text" name="admin_authorid_values"></input></td>
                                    </tr>
                                    <tr>
                                        <td> NAME </td><td> <input type="text" name="admin_name_values"></input></td>
                                    </tr>
                                    <tr>
                                        <td> ISBN </td><td> <input type="text" name="admin_isbn_values"></input></td>
                                    </tr>
                                    <tr>
                                        <td> TITLE </td><td> <input type="text" name="admin_title_values"></input></td>
                                    </tr>
                                    <tr>
                                        <td> PRICE </td><td> <input type="text" name="admin_price_values"></input></td>
                                    </tr>
                                </table>
                                <%-- buttons --%>
                                <table>
                                    <tr><td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_isbn_values_insert" value="Insert"></td> 
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_isbn_values_update" value="Update"></td>
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_isbn_values_delete" value="Delete"></td> 
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_isbn_values_cancel" value="Cancel"></td>
                                    </tr>     
                                </table>
                            </td></tr>
                    </table>    
                </form>
            </c:when>
            <c:otherwise>
                <c:redirect url="./index.jsp"></c:redirect>
            </c:otherwise>
        </c:choose>     
    </body>
</html>