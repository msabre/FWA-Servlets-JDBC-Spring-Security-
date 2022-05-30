<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="edu.school.cinema.models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.school.cinema.models.Image" %>
<%@ page import="edu.school.cinema.models.Visit" %>
<%@ page import="org.springframework.web.context.support.AnnotationConfigWebApplicationContext" %>
<%@ page import="edu.school.cinema.services.UserService" %>
<%@ page import="org.springframework.context.annotation.AnnotationConfigApplicationContext" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>

    <style>
        p, form, tr {
            text-align:center
        }
    </style>

</head>
<body>

<%
    AnnotationConfigApplicationContext applicationContext = (AnnotationConfigApplicationContext) session.getServletContext().getAttribute("springContext");
    UserService userService = applicationContext.getBean("userService", UserService.class);

    User user = (User) session.getAttribute("user");

    List<Image> images = userService.findImageByUserId(user.getId());
    request.setAttribute("images", images);

//        List<Visit> visits = userService.findVisitsById(user.getId());
//        request.setAttribute("visits", visits);
%>

<img alt="Photo not exist" src="images?name=<%=images.get(0).getFileName()%>" width="280" height="420"/>

<h2>It's Me</h2>
<br>
<h2><%=user.getEmail()%></h2>

<br>

<%--    <tr>--%>
<%--        <th>Date</th>--%>
<%--        <th>Time</th>--%>
<%--        <th>IP</th>--%>
<%--    </tr>--%>
<%--    <c:forEach items="${visits}" var="item">--%>
<%--        <tr>--%>
<%--            <td>${item.date}</td>--%>
<%--            <td>${item.time}</td>--%>
<%--            <td>${item.ip}</td>--%>
<%--        </tr>--%>
<%--    </c:forEach>--%>

<br>

<form enctype="multipart/form-data" method="post" action="images">
    <p>
        <input type="file" name="image">
        <input type="submit" value="Отправить">
    </p>
</form>

<br>

<tr>
    <th>File name</th>
    <th>Size</th>
    <th>MIME</th>
</tr>
<c:forEach items="${images}" var="item">
    <tr>
        <td>${item.fileName}</td>
        <td>${item.size}</td>
        <td>${item.mime}</td>
    </tr>
</c:forEach>

</body>
</html>
