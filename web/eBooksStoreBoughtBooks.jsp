<%-- 
    Document   : eBooksStoreBoughtBooks
    Created on : Jun 25, 2017, 3:21:15 PM
    Author     : MariusNeagu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Electronic Sold Books</title>
        <link rel="stylesheet" type="text/css" href="./css/eBooksStoreCSS.css">
    </head>
    <body>
        <%-- test if actual user is authenticated and authorized --%>
        <c:choose>
            <c:when test="${validUser == true}">   
                <h3>This is your Orders Page</h3>  
                <h3>Welcome Anna!</h3> 
                <!-- include menu -->
                <%@ include file="./utils/eBooksStoreMenu.jsp" %>
                <%-- Master view --%>
                <form action="${pageContext.request.contextPath}/eBooksStoreSoldBooksServlet" method="POST">
                    <sql:setDataSource 
                        var="snapshot" 
                        driver="org.apache.derby.jdbc.ClientDriver"
                        url="jdbc:derby://localhost:1527/eBooksDatabase;create=true;"
                        user="test"  
                        password="test"/>
                    <sql:query dataSource="${snapshot}" var="result">
                        SELECT SOLD_EBOOKS.USER_ID, SOLD_EBOOKS.USERNAME, SOLD_EBOOKS.ISBN,  SOLD_EBOOKS.TITLE, SOLD_EBOOKS.AUTHOR, SOLD_EBOOKS.BOUGHT_DATE FROM SOLD_EBOOKS, EBOOKS, USERS WHERE USERS.NAME='Anna' AND SOLD_EBOOKS.USER_ID = USERS.ID AND SOLD_EBOOKS.ISBN = EBOOKS.ISBN ORDER BY USER_ID ASC
                    </sql:query>
                    <table border="1" width="100%">
                        <tr>
                            <td width="12%" class="thc">USER_ID</td>   
                            <td width="12%" class="thc">USERNAME</td>
                            <td width="12%" class="thc">ISBN</td> 
                            <td width="13%" class="thc">TITLE</td>
                            <td width="13%" class="thc">AUTHOR</td>
                            <td width="24%" class="thc">BOUGHT_DATE</td> 
                        </tr>
                    </table>    
                    <table border="1" width="100%">    
                        </tr>
                        <c:forEach var="row" varStatus="loop" items="${result.rows}">
                            <tr>
                                <td width="12%" class="tdc"><c:out value="${row.user_id}"/></td>
                                <td width="12%" class="tdc"><c:out value="${row.username}"/></td>
                                <td width="12%" class="tdc"><c:out value="${row.isbn}"/></td>
                                <td width="13%" class="tdc"><c:out value="${row.title}"/></td>
                                <td width="13%" class="tdc"><c:out value="${row.author}"/></td>
                                <td width="24%" class="tdc"><c:out value="${row.bought_date}"/></td> 
                                
                            </tr>
                        </c:forEach>
                    </table>
                </form>
            </c:when>
            <c:otherwise>
                <c:redirect url="./index.jsp"></c:redirect>
            </c:otherwise>
        </c:choose>
    </body>    
</html>
