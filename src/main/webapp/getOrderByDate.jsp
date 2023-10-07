<%@ page import="java.util.Map" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 08/10/2023
  Time: 2:40 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>

<%
    Map<LocalDateTime, Integer> statisticsDate = (Map<LocalDateTime, Integer>) session.getAttribute("statisticsOrderByDate");
    System.out.println(statisticsDate);

%>
<%!
    public String handleLabel(ArrayList<LocalDateTime> times){
        StringBuilder initial = new StringBuilder();
        for (int i = 0; i < times.size(); i++){
            if (i != times.size() - 1){
                initial.append("\'").append(times.get(i).toString()).append("\', ");
            } else {
                initial.append("\'").append(times.get(i).toString()).append("\'");
            }
        }
        return initial.toString();
    }
    public String handleValue(ArrayList<Integer> orderNum){
        StringBuilder initial = new StringBuilder();
        for (int i = 0; i < orderNum.size(); i++){
            if (i != orderNum.size() - 1){
                initial.append(orderNum.get(i)).append(", ");
            } else {
                initial.append(orderNum.get(i));
            }
        }
        return initial.toString();
    }
%>

<div class="px-5">
    <%if (statisticsDate == null || statisticsDate.isEmpty()){%>
        <h1 class="text-center text-primary">Không có hóa đơn nào để thống kê!</h1>
    <%} else {%>
        <canvas id="myChart"></canvas>
    <%}%>

</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    const ctx = document.getElementById('myChart');

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels:
            <%if (statisticsDate == null || statisticsDate.isEmpty()){%>
                []
            <%} else {%>
                <%--[<%statisticsDate.forEach((k, v)->k.toString());%>]--%>
                    [<%=handleLabel(new ArrayList<>(statisticsDate.keySet()))%>]
            <%}%>
            ,
            datasets: [{
                label: 'Date',
                data:
                <%if (statisticsDate == null || statisticsDate.isEmpty()){%>
                    []
                        <%} else {%>
                        <%--[<%statisticsDate.forEach((k, v)->k.toString());%>]--%>
                        [<%=handleValue(new ArrayList<>(statisticsDate.values()))%>]
                <%}%>
                    ,
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Numer of Orders',
                    }
                }
            }
        }
    });
</script>

<%@ include file="./parts/footer.jsp"%>
