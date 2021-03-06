<%--
  Created by IntelliJ IDEA.
  User: maria
  Date: 30.04.2015
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
  <%@include file="../parts/meta.jsp"%>

  <!-- Bootstrap Core CSS -->
  <link href="<%=application.getContextPath()%>/resources/admin/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

  <!-- MetisMenu CSS -->
  <link href="<%=application.getContextPath()%>/resources/admin/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

  <!-- Custom CSS -->
  <link href="<%=application.getContextPath()%>/resources/admin/dist/css/sb-admin-2.css" rel="stylesheet">

  <!-- Custom Fonts -->
  <link href="<%=application.getContextPath()%>/resources/admin/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

  <!-- Bootstrap editable-->
  <link href="<%=application.getContextPath()%>/resources/css/bootstrap-editable.css" rel="stylesheet">
  
  <script>
    window.onbeforeunload = close;
    function close(){
      $.ajax({
        type: 'POST',
        async: false,
        url: '../admin/userdash',
        success: function() {

        }
      });
    }
  </script>
  
</head>
<body>
<div id="wrapper">

  <c:set var="ID_USER_VALUE" value="${sessionScope.get('isAdmin')}" />
  <c:if test="${ID_USER_VALUE == null}" >
    <jsp:include page="driverHeader.jsp"/>
  </c:if>

  <div id="page-wrapper">

    <div class="row">
      <div class="col-lg-12">
        <h1 class="page-header">Assigned orders</h1>
      </div>
      <!-- /.col-lg-12 -->
    </div>


                        <!-- Plans -->
              <c:forEach items="${requestScope.orders}" var="order">
                <section id="plans">
                  <div class="container">
                    <div class="row">

                      <!-- item -->
                      <div class="col-md-9 text-center">
                        <div class="panel panel-success panel-pricing">
                          <div class="panel-heading">
                            <c:set var="startPoint" value="${order.itemList[0].path.getStartPoint()}"/>
                            <c:set var="endPoint" value="${order.itemList[0].path.getEndPoint()}"/>
                            <div class="map-canvas">
                              <iframe frameborder="0" width="100%" height="250"
                                      src="https://www.google.com/maps/embed/v1/directions?key=AIzaSyAtwMePDVDymtf-yC-qk1hdmUMnDtGYbb8&mode=driving&origin=${pageScope.startPoint.getX()},${pageScope.startPoint.getY()}&destination=${pageScope.endPoint.getX()},${pageScope.endPoint.getY()}">
                              </iframe>
                            </div>
                          </div>

                          <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                            <thead>
                            <tr>
                              <th>#</th>
                              <th>Service</th>
                              <c:set var="hide2" scope="session" value="hidden=\"hidden\""/>
                              <c:choose>
                                <c:when test="${order.service == 'CELEBRATION_TAXI'}">
                                  <th>Duration</th>
                                </c:when>
                                <c:when test="${order.service == 'TAXI_FOR_LONG_TERM'}">
                                  <th>Duration</th>
                                </c:when>
                              </c:choose>
                              <th>Order time</th>
                              <th>Car arrive time</th>
                              <th>Price</th>
                              <th>Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr class="odd gradeX">
                              <td>${order.trackingNumber}</td>
                              <td>
                                <c:set var="string7" value="${order.service}"/>
                                <c:set var="string8" value="${fn:toLowerCase(string7)}" />
                                <c:set var="string9" value="${fn:replace(string8,
                                '_', ' ')}" />
                                  ${string9}
                              </td>
                              <c:set var="hide2" scope="session" value="hidden=\"hidden\""/>
                              <c:choose>
                                <c:when test="${order.service == 'CELEBRATION_TAXI'}">
                                  <td>${order.amountOfHours} : ${order.amountOfMinutes}</td>
                                </c:when>
                                <c:when test="${order.service == 'TAXI_FOR_LONG_TERM'}">
                                  <td>${order.amountOfHours} : ${order.amountOfMinutes}</td>
                                </c:when>
                              </c:choose>
                              <td>
                                <fmt:formatDate value="${order.orderedDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                              </td>
                              <td>
                                <fmt:formatDate value="${order.arriveDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                              </td>
                              <td>${order.price}</td>
                              <td>
                                <c:set var="string4" value="${order.status}"/>
                                <c:set var="string5" value="${fn:toLowerCase(string4)}" />
                                <c:set var="string6" value="${fn:replace(string5,
                                '_', ' ')}" />
                                  ${string6}
                              </td>
                            </tr>
                            </tbody>
                          </table>

                          <div class="panel-footer">
                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                              <tbody>
                              <tr class="odd gradeX">
                                <td> <form action="assigned-order" method="post">
                                  <a href="javascript:;" onclick="parentNode.submit();">
                                    <button type="button" class="btn btn-danger">Refuse</button></a>
                                  <input type="hidden" name="trackingNumber" value=${order.trackingNumber}>
                                  <input type="hidden" name="orderStatus" value="Toqueue">
                                </form></td>
                                <td>
                                  <form action="assigned-order" method="post">
                                    <a href="javascript:;" onclick="parentNode.submit();">
                                      <button type="button" class="btn btn-info">Customer not arrived</button></a>
                                    <input type="hidden" name="trackingNumber" value=${order.trackingNumber}>
                                    <input type="hidden" name="orderStatus" value="Refused">
                                  </form>
                                </td>
                                <td>
                                  <c:set var="inputDisplay" value="IN_PROGRESS" />
                                  <c:choose>
                                    <c:when test="${order.status == 'IN_PROGRESS'}">
                                      <button type="button" class="btn btn-warning">In progress</button>
                                    </c:when>
                                    <c:otherwise>
                                      <form action="assigned-order" method="post">
                                        <a href="javascript:;" onclick="parentNode.submit();">
                                          <button type="button" class="btn btn-primary">In progress</button></a>
                                        <input type="hidden" name="trackingNumber" value=${order.trackingNumber}>
                                        <input type="hidden" name="orderStatus" value="In progress">
                                      </form>
                                    </c:otherwise>
                                  </c:choose>
                                </td>
                                <td>
                                  <form action="assigned-order" method="post">
                                    <a href="javascript:;" onclick="parentNode.submit();">
                                      <button type="button" class="btn btn-success">Complete</button></a>
                                    <input type="hidden" name="trackingNumber" value=${order.trackingNumber}>
                                    <input type="hidden" name="orderStatus" value="Completed">
                                  </form>
                                </td>
                                </tr>
                              </tbody>
                              </table>
                          </div>
                        </div>
                      </div>
                      <!-- /item -->

                    </div>
                  </div>
                </section>
              </c:forEach>
              <!-- /Plans -->
			  <c:if test="${ID_USER_VALUE != null}" >
				<button type="button" class="btn btn-default" onclick="closeAndGo();">Back</button>
			  </c:if>
			  
    <%--Pagination--%>
    <div class="text-center">
      <ul class="pagination">
        <c:if test="${requestScope.pagenumber != 1}">
          <li class="dropdown pull-left">
            <form action="assigned-order" method="post">
              <a href="javascript:;" onclick="parentNode.submit();">
                <button type="button" class="btn btn-default" aria-label="Previous">
                  Previous</button></a>
              <input type="hidden" name="pagenumber" value=${requestScope.pagenumber - 1}>
            </form>
          </li>
        </c:if>
        <c:forEach begin="1" end="${requestScope.pagesCount}" var="i">
          <c:choose>
            <c:when test="${requestScope.pagenumber eq i}">
              <li class="dropdown pull-left"><button type="button" class="btn btn-default" aria-label="Next">
                  ${i}</button></li>
            </c:when>
            <c:otherwise>
              <li class="dropdown pull-left">
                <form action="assigned-order" method="post">
                  <a href="javascript:;" onclick="parentNode.submit();">
                    <button type="button" class="btn btn-default" aria-label="Next">
                        ${i}</button></a>
                  <input type="hidden" name="pagenumber" value=${i}>
                </form>
              </li>
            </c:otherwise>
          </c:choose>
        </c:forEach>
        <c:if test="${requestScope.pagesCount != 0}">
        <c:if test="${requestScope.pagenumber != requestScope.pagesCount}">
          <li class="dropdown pull-left">
            <form action="assigned-order" method="post">
              <a href="javascript:;" onclick="parentNode.submit();">
                <button type="button" class="btn btn-default" aria-label="Next">
                  Next</button></a>
              <input type="hidden" name="pagenumber" value=${requestScope.pagenumber + 1}>
            </form>
          </li>
        </c:if>
          </c:if>
      </ul>
    </div>

    <%--<div class="text-center">--%>
      <%--<ul class="pagination">--%>
        <%--<c:forEach var="i" begin="1" end="${requestScope.pagesCount}">--%>
          <%--<li class="pageLi${i}"><a class="pageButton" href="#">${i}</a></li>--%>
        <%--</c:forEach>--%>
      <%--</ul>--%>
    <%--</div>--%>

          <!-- Pop up-->

      <c:choose>
        <c:when test="${status == 'true'}">
          <script src="<%=application.getContextPath()%>/resources/driver/js/jquery.min.js"></script>
          <script src="<%=application.getContextPath()%>/resources/driver/js/modalOrderInProgress.js"></script>

          </head>
          <body>
          <div id="myModal" class="modal fade">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                  <h4 class="modal-title">Warning</h4>
                </div>
                <div class="modal-body">
                  <p>Please be careful, you can not have two orders in "in progress" state!</p>
                </div>
                <div class="modal-footer">
                  <form action="change-satus" method="post">
                  <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <input type="hidden" name="status" value="false">
                    </form>
                </div>
              </div>
            </div>
          </div>
        </c:when>
          </c:choose>
          <!-- end pop up -->

              <div class="text-center">
                <ul class="pagination">
                  <%--not ready
                  <c:forEach var="i" begin="1" end="${requestScope.pagesCount}">
                    <li class="pageLi${i}"><a class="pageButton" href="#">${i}</a></li>
                  </c:forEach>
                  --%>
                </ul>
              </div>
            </div>
            <!-- /.table-responsive -->

          </div>
          <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
      </div>
      <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->


  </div>
  <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<script src="<%=application.getContextPath()%>/resources/driver/js/jquery.min.js"></script>
<c:if test="${ID_USER_VALUE != null}" >
  <script>
    $("form").hide();
  </script>
</c:if>

<script>  
  function closeAndGo() {
    close();
    window.location.replace('../admin/searchdriver');
  }
</script>

<!-- jQuery Version 1.11.2 -->
<script src="<%=application.getContextPath()%>/resources/js/jquery-1.11.2.min.js"></script>

<!--
<script src="../../../resources/js/jquery-1.11.2.js"></script>
<script src="../../../resources/js/jquery.min.js"></script>
<script type="text/javascript" language="javascript" src="../../../resources/js/jquery.js"></script>
-->

<!-- Bootstrap Core JavaScript -->
<script src="<%=application.getContextPath()%>/resources/js/bootstrap3/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="<%=application.getContextPath()%>/resources/js/metisMenu.min.js"></script>

<!-- Morris Charts JavaScript -->
<script src="<%=application.getContextPath()%>/resources/js/raphael-min.js"></script>
<script src="<%=application.getContextPath()%>/resources/js/morris.min.js"></script>
<script src="<%=application.getContextPath()%>/resources/js/morris-data.js"></script>

<!-- Custom Theme JavaScript -->
<script src="<%=application.getContextPath()%>/resources/js/sb-admin-2.js"></script>

<!-- Bootstrap editable JavaScript-->
<script src="<%=application.getContextPath()%>/resources/js/bootstrap-editable.js"></script>

<%--for pagination--%>
<script src="<%=application.getContextPath()%>/resources/driver/js/paginator-orders.js"></script>

</body>

</html>
