<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 29/09/2023
  Time: 4:16 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>

<%
    String errMsgAddOrder = (String)session.getAttribute("errMsgAddOrder");
%>

<div class="container">
    <form action="control-servlet?action=addOrder" method="POST">
        <div class="mb-3">
            <label class="form-label" for="inputCreationDate">Date</label>
            <div class="input-group date" id="datetimepicker1" data-target-input="nearest">
                <input type="text" class="form-control datetimepicker-input" id="inputCreationDate" data-target="#datetimepicker1"
                       name="createDate"
                />
                <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fas fa-calendar"></i></div>
                </div>
            </div>
        </div>
        <div class="mb-3">
            <label for="inputCustomer" class="form-label">Customer ID</label>
            <input type="number" class="form-control" id="inputCustomer"
                   name="customerID"
            >
        </div>

        <div class="mb-3">
            <label for="inputEmployee" class="form-label">Employee ID</label>
            <input type="number" class="form-control" id="inputEmployee"
                   name="employeeID"
            >
        </div>

        <button type="submit" class="btn btn-primary">Submit</button>
        <%if (errMsgAddOrder != null){%>
        <div class="my-5">
            <h1 class="text-center text-danger"><%=errMsgAddOrder%></h1>
        </div>
        <%}
            session.removeAttribute("errMsgAddOrder");
        %>

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