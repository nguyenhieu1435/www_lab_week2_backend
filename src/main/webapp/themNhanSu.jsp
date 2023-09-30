<%@ page import="vn.edu.iuh.fit.backend.enums.EmployeeStatus" %>
<%@ page import="vn.edu.iuh.fit.backend.models.Employee" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 27/09/2023
  Time: 9:54 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>

<%
    String errMsgAddEmp = (String)session.getAttribute("errMsgAddEmp");
%>

<div class="container">
    <form action="control-servlet?action=insertEmployee" method="POST">
        <div class="mb-3">
            <label for="inputFullName" class="form-label">Fullname</label>
            <input type="text" class="form-control" id="inputFullName"
                name="fullname"
            >
        </div>
        <div class="mb-3">
            <label for="inputEmail" class="form-label">Email</label>
            <input type="email" class="form-control" id="inputEmail"
                name="email"
            >
        </div>
        <div class="mb-3">
            <label for="inputPhone" class="form-label">Phone</label>
            <input type="text" class="form-control" id="inputPhone"
                name="phone"
            >
        </div>
        <div class="mb-3">
            <label for="inputAddress" class="form-label">Address</label>
            <input type="text" class="form-control" id="inputAddress"
                name="address"
            >
        </div>
        <div class="mb-3">
            <label class="form-label" for="inputStatus">Status</label>
            <select class="form-select form-select-md" aria-label=".form-select-sm" id="inputStatus"
                name="status"
            >
                <%for (int i = 0; i < EmployeeStatus.values().length; i++){%>
                    <option <%=i == 0 ? "selected" : ""%> value="<%=EmployeeStatus.values()[i]%>"><%=EmployeeStatus.values()[i]%></option>
                <%}%>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label" for="inputDOB">Date Of Birth</label>
            <div class="input-group date" id="datetimepicker1" data-target-input="nearest">
                <input type="text" class="form-control datetimepicker-input" id="inputDOB" data-target="#datetimepicker1"
                    name="dateOfBirth"
                />
                <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fas fa-calendar"></i></div>
                </div>
            </div>
        </div>

        <button type="submit" class="btn btn-primary">Submit</button>

        <%if (errMsgAddEmp != null && !errMsgAddEmp.isEmpty()){%>
            <h1 class="text-center text-danger"><%=errMsgAddEmp%></h1>
        <%}%>
    </form>
</div>

<script type="text/javascript">
    $("#datetimepicker1").datetimepicker({
        format: "MM/DD/YYYY HH:mm:ss",
        defaultDate: new Date(),
        icons: {
            time: "fas fa-clock",
            date: "fas fa-calendar",
            up: "fas fa-arrow-up",
            down: "fas fa-arrow-down",
            previous: "fas fa-chevron-left",
            next: "fas fa-chevron-right",
            today: "fas fa-calendar-check-o",
            clear: "fas fa-trash",
            close: "fas fa-times"
        }
    });
    $("#datetimepicker1").on(
        "change.datetimepicker blur.datetimepicker keyup.datetimepicker keydown.datetimepicker focus.datetimepicker click.datetimepicker update.datetimepicker error.datetimepicker hide.datetimepicker show.datetimepicker",
        function(e) {
            console.log("event:", e);
        }
    );
</script>


<%@ include file="./parts/footer.jsp"%>