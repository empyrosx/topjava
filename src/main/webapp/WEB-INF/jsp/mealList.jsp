<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<link rel="stylesheet" href="webjars/datatables/1.10.9/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="webjars/datetimepicker/2.3.4/jquery.datetimepicker.css">

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3><a href="${pageContext.request.contextPath}">Home</a></h3>

            <div class="view-box">
                <h3><fmt:message key="meals.title"/></h3>

                <form id="filter" charset="utf-8" class="form-horizontal" accept-charset="UTF-8"
                      action="meals/filter" method="post">
                    <div class="form-group">

                        <label class="col-sm-2">From Date</label>

                        <div class="col-sm-2"><input id="startDate" name="startDate" placeholder="Start Date"
                                                     class="form-control date-picker" type="text" value=""/></div>


                        <label class="col-sm-2">To Date</label>

                        <div class="col-sm-2"><input id="endDate" name="endDate" placeholder="End Date"
                                                     class="form-control date-picker" type="text" value=""/></div>

                    </div>
                    <div class="form-group">

                        <label class="col-sm-2">From Time</label>

                        <div class="col-sm-2"><input id="startTime" name="startTime" placeholder="Start Time"
                                                     class="form-control time-picker" type="text" value=""/></div>


                        <label class="col-sm-2">To Time</label>

                        <div class="col-sm-2"><input id="endTime" name="endTime" placeholder="End Time"
                                                     class="form-control time-picker" type="text" value=""/></div>

                    </div>
                    <div class="form-group">
                        <div class="col-sm-8">
                            <button type="submit" class="btn btn-primary pull-right">Filter</button>
                        </div>
                    </div>
                    <div>
                        <input type="hidden" name="_csrf" value="dea92677-879b-4ef6-9a2a-dbcbe1cca75e"/>
                    </div>
                </form>

                <a class="btn btn-sm btn-info" id="add"><fmt:message key="meals.add"/></a>

                <table class="table table-striped display" id="datatable">
                    <thead>
                    <tr>
                        <th>Date</th>
                        <th>Description</th>
                        <th>Calories</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <c:forEach items="${mealList}" var="meal">
                        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.UserMealWithExceed"/>
                        <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                            <td>
                                <%=TimeUtil.toString(meal.getDateTime())%>
                            </td>
                            <td>${meal.description}</td>
                            <td>${meal.calories}</td>
                            <td><a class="btn btn-xs btn-primary edit" id="${meal.id}">Edit</a></td>
                            <td><a class="btn btn-xs btn-danger delete" id="${meal.id}">Delete</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><fmt:message key="meals.edit"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="detailsForm">
                    <input type="text" hidden="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="dateTime" class="control-label col-xs-3">Date</label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="dateTime" name="dateTime" placeholder="dateTime">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3">Description</label>

                        <div class="col-xs-9">
                            <input type="description" class="form-control" id="description" name="description" placeholder="description">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="calories" class="control-label col-xs-3">Calories</label>

                        <div class="col-xs-9">
                            <input type="calories" class="form-control" id="calories" name="calories" placeholder="calories">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>

</body>
<script type="text/javascript" src="webjars/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/datetimepicker/2.3.4/jquery.datetimepicker.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.9/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.2.4/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>
<script type="text/javascript">

    var ajaxUrl = 'ajax/profile/meals/';
    var oTable_datatable;
    var oTable_datatable_params;

    //            $(document).ready(function () {
    $(function () {
        oTable_datatable = $('#datatable');
        oTable_datatable_params = {
            "bPaginate": false,
            "bInfo": false,
            "aoColumns": [
                {
                    "mData": "dateTime"
                },
                {
                    "mData": "description"
                },
                {
                    "mData": "calories"
                },
                {
                    "sDefaultContent": "",
                    "bSortable": false
                },
                {
                    "sDefaultContent": "",
                    "bSortable": false
                }
            ],
            "aaSorting": [
                [
                    0,
                    "asc"
                ]
            ]
        };

        oTable_datatable.dataTable(oTable_datatable_params);
        makeEditable();
    });
</script>
</html>