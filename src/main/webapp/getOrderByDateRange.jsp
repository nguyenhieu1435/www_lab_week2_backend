<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 08/10/2023
  Time: 3:33 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>

<%
    String errGetStatisticOrderByDateRange = (String)session.getAttribute("errGetStatisticOrderByDateRange");
    Double totalPrice = (Double) session.getAttribute("statisticsPriceByDateRange");
%>

<div class="container">
    <form action="control-servlet?action=getStatisticsOrderByDateRange" method="POST">
        <div class="mb-3">
            <label class="form-label" for="inputBeginDate">Ngày bắt đầu</label>
            <div class="input-group date" id="datetimeBeginDate" data-target-input="nearest">
                <input type="text" class="form-control datetimepicker-input" id="inputBeginDate" data-target="#datetimeBeginDate"
                       name="beginDate"
                />
                <div class="input-group-append" data-target="#datetimeBeginDate" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fas fa-calendar"></i></div>
                </div>
            </div>
        </div>
        <div class="mb-3">
            <label class="form-label" for="inputEndDate">Ngày kết thúc</label>
            <div class="input-group date" id="datetimeEndDate" data-target-input="nearest">
                <input type="text" class="form-control datetimepicker-input" id="inputEndDate" data-target="#datetimeEndDate"
                       name="endDate"
                />
                <div class="input-group-append" data-target="#datetimeEndDate" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fas fa-calendar"></i></div>
                </div>
            </div>
        </div>
        <Button type="submit" class="btn btn-success">Thống kê</Button>
    </form>
    <%if (totalPrice != null){%>
        <h1 class="text-center text-primary">Tổng doanh thu của mốc thời gian trên là <%=String.format("%.2f", totalPrice)%></h1>
    <%}
        session.removeAttribute("statisticsPriceByDateRange");
    %>
    <%if (errGetStatisticOrderByDateRange != null && !errGetStatisticOrderByDateRange.isEmpty()){%>
        <h1 class="text-center text-danger"><%=errGetStatisticOrderByDateRange%></h1>
    <%
       session.removeAttribute("errGetStatisticOrderByDateRange");
    }%>
</div>

<script type="text/javascript">
    $("#datetimeBeginDate").datetimepicker({
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
    $("#datetimeEndDate").datetimepicker({
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
</script>

<%@ include file="./parts/footer.jsp"%>
