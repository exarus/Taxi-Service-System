<%--
  Created by IntelliJ IDEA.
  User: Vadym Akymov
  Date: 15.04.15
  Time: 1:37
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@include file="../parts/meta.jsp"%>

    <!-- Bootstrap Core CSS -->
    <link href="<%=application.getContextPath()%>/resources/admin/bower_components/bootstrap/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="<%=application.getContextPath()%>/resources/admin/bower_components/metisMenu/dist/metisMenu.min.css"
          rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<%=application.getContextPath()%>/resources/admin/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="<%=application.getContextPath()%>/resources/admin/bower_components/font-awesome/css/font-awesome.min.css"
          rel="stylesheet" type="text/css">
    <link href="<%=application.getContextPath()%>/resources/admin/css/admin.css"
          rel="stylesheet" type="text/css">
</head>

<body>

<div id="wrapper">
    <jsp:include page="adminHeader.jsp"/>
    <div id="page-wrapper">
        <div class="jumbotron headerStyle">
            <h1>Hey!! You are in admin page!</h1>
        </div>
        <img class="adminPic" alt="admin picture" src="https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQbgjCP6ZaC-g-2huuBuZnupbyFY2xWNu_kFJCdtaorZWQ8nh1n">

    </div>
    <!-- /#page-wrapper -->
</div>
<!-- /#wrapper -->

<!-- jQuery -->
<script src="<%=application.getContextPath()%>/resources/admin/bower_components/jquery/dist/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="<%=application.getContextPath()%>/resources/admin/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="<%=application.getContextPath()%>/resources/admin/bower_components/metisMenu/dist/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="<%=application.getContextPath()%>/resources/admin/dist/js/sb-admin-2.js"></script>

</body>

</html>
