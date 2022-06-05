<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="edu.school.cinema.models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.school.cinema.models.Image" %>
<%@ page import="edu.school.cinema.models.Visit" %>
<%@ page import="org.springframework.web.context.support.AnnotationConfigWebApplicationContext" %>
<%@ page import="edu.school.cinema.services.UserService" %>
<%@ page import="org.springframework.context.annotation.AnnotationConfigApplicationContext" %>
<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>

    <style>

        /*thead, tbody { */
        /*    display: block; */
        /*}*/

        table thead {
            flex: 0 0 auto;
            width: calc(100% - 0.9em);
        }

        table tbody {
            /* body takes all the remaining available space */
            flex: 1 1 auto;
            display: block;
            overflow-y: scroll;
        }

        table tbody tr {
            width: 100%;
        }


        table thead, table tbody tr {
            display: table;
            table-layout: fixed;
        }
        /*tbody {*/
        /*    height: 200px;       !* Just for the demo          *!*/
        /*    overflow-y: auto;    !* Trigger vertical scroll    *!*/
        /*    overflow-x: auto;  !* Hide the horizontal scroll *!*/
        /*}*/

        table {
            border: 4px double #333;
            border-collapse: collapse;
            /*width: 50%;*/
            /*height: 50%;*/
            border-spacing: 0;
            table-layout: fixed;

            display: flex;
            flex-flow: column;
            height: 200px;
            width: 60%;
        }
        td, th {
            padding: 5px; /* Поля вокруг текста */
            border: 1px solid #a52a2a; /* Граница вокруг ячеек */
            text-align: center;
            vertical-align: middle;
            width: 100%;
        }


        #uploadButton {
            flex-direction: row;
            display:flex;
            justify-content: center;
            align-items: start;
        }
        #image {
            display:flex;
            justify-content: center;
            align-items: start;
            /*position: relative;*/
        }
        #visits {
            margin-left : 20px
        }
        #filesTab {
            display:flex;
            justify-content: center;
            align-items: start;
            position: relative;
        }
        h2 {
            line-height: 10px;
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

    List<Visit> visits = userService.findVisitsById(user.getId());
    request.setAttribute("visits", visits);
%>


<div id="image">

    <div>
        <img alt="Photo not exist" src="images?name=<%=!images.isEmpty() ? images.get(images.size() - 1).getFileName() : "empty"%>&type=avatar" width="280px" height="420px"/>
    </div>

    <div id="visits">
        <h2>It's Me</h2>
        <br>
        <h2><%=user.getEmail()%></h2>
        <br>

        <table>
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Time</th>
                    <th>IP</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${visits}" var="item">
                    <tr>
                        <td>${item.date}</td>
                        <td>${item.time}</td>
                        <td>${item.ip}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </div>

</div>

<div id="uploadButton">
    <form enctype="multipart/form-data" method="post" action="images">
        <p>
            <input type="file" name="image">
            <input type="submit" value="Отправить">
        </p>
    </form>
</div>

<div id="filesTab">

    <table>
        <thead>
            <tr>
                <th>File name</th>
                <th>Size</th>
                <th>MIME</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${images}" var="item">
                <tr>
                    <td><a href="images?name=${item.fileName}" target="_blank">${item.fileName}</a></td>
                    <td>${item.size}</td>
                    <td>${item.mime}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</div>

</body>
</html>
