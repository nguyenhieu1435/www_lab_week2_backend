<%@ page import="vn.edu.iuh.fit.backend.models.Order" %>
<%@ page import="java.util.Formatter" %>
<%@ page import="java.time.format.DateTimeFormatter" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 29/09/2023
  Time: 6:56 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>
<%
    String errMsgUpdOrder = (String)session.getAttribute("errMsgUpdOrder");
    Order order = (Order) session.getAttribute("order");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
%>
<div class="container">
    <%if (order == null){%>
        <h1>Cần truyền Order để cập nhật!</h1>
    <%} else {%>
        <form action="control-servlet?action=updateOrder" method="POST">
            <div class="mb-3">
                <label for="inputOrder" class="form-label">Order ID</label>
                <input type="text" class="form-control" id="inputOrder"
                       readonly
                       name="orderID"
                       value="<%=order.getOrder_id()%>"
                >
            </div>
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
                       value="<%=order.getCustomer().getId()%>"
                >
            </div>

            <div class="mb-3">
                <label for="inputEmployee" class="form-label">Employee ID</label>
                <input type="number" class="form-control" id="inputEmployee"
                       name="employeeID"
                       value="<%=order.getEmployee().getId()%>"
                >
            </div>

            <button type="submit" class="btn btn-primary">Submit</button>
            <%if (errMsgUpdOrder != null){%>
            <div class="my-5">
                <h1 class="text-center text-danger"><%=errMsgUpdOrder%></h1>
            </div>
            <%}
                session.removeAttribute("errMsgUpdOrder");
            %>
        </form>
    <%}%>
</div>

<script type="text/javascript">
    $("#datetimepicker1").datetimepicker({
        format: "MM/DD/YYYY HH:mm:ss",
        defaultDate: new Date("<%=order != null ? order.getOrderDate().format(formatter) : ""%>"),
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
</script>

<%@ include file="./parts/footer.jsp"%>