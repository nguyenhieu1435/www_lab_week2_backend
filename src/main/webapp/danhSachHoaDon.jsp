<%@ page import="java.util.List" %>
<%@ page import="vn.edu.iuh.fit.backend.models.Order" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 29/09/2023
  Time: 11:35 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>
<%
    List<Order> orders = (List<Order>) session.getAttribute("orders");
    int limitNumOrder = (int)session.getAttribute("limitNumOrder");
    int pageNumOrder = (int)session.getAttribute("pageNumOrder");
    String errMsgDelOrder = (String)session.getAttribute("errMsgDelOrder");
%>

<div>
    <%if (orders == null || orders.isEmpty()){%>
        <h1 class="text-center text-primary">Không có Hóa đơn nào!</h1>
    <%} else {%>
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Order Date</th>
            <th scope="col">Customer</th>
            <th scope="col">Employee</th>
            <th scope="col" class="text-center">
                <a href="themHoaDon.jsp" class="btn btn-success px-5">Insert Order</a>
            </th>
        </tr>
        </thead>
        <tbody>
            <%for (int i = (pageNumOrder-1)*limitNumOrder; i < Math.min(orders.size(), limitNumOrder*pageNumOrder); i++){%>
                <tr>
                    <th scope="row"><%=orders.get(i).getOrder_id()%></th>
                    <td><%=orders.get(i).getOrderDate()%></td>
                    <td><strong>ID:</strong> <%=orders.get(i).getCustomer().getId()%>, <strong>Name:</strong> <%=orders.get(i).getCustomer().getName()%></td>
                    <td><strong>ID:</strong> <%=orders.get(i).getEmployee().getId()%>, <strong>Name:</strong> <%=orders.get(i).getEmployee().getFullname()%></td>
                    <td class="text-center">
                        <a class="btn btn-primary" href="control-servlet?action=handleOpenOrderDetail&id=<%=orders.get(i).getOrder_id()%>">Order Detail</a>
                        <a class="btn btn-warning" href="control-servlet?action=handleOpenOrderUpdate&id=<%=orders.get(i).getOrder_id()%>">Update</a>
                        <a class="btn btn-danger" href="control-servlet?action=deleteOrder&id=<%=orders.get(i).getOrder_id()%>">Delete</a>
                    </td>
                </tr>
            <%}%>
        </tbody>
    </table>
    <div class="d-flex align-items-center justify-content-center">
        <%if (pageNumOrder > 1){%>
            <a class="btn btn-primary"
               href="control-servlet?action=decreaseOrderPageNum"
            >Prev</a>
        <%}%>
        <span class="mx-3"><%=pageNumOrder%></span>
        <a class="btn btn-primary"
           href="control-servlet?action=increaseOrderPageNum"
        >Next</a>
    </div>
        <%if (errMsgDelOrder != null && !errMsgDelOrder.isEmpty()){%>
            <div>
                <h1 class="text-center text-primary"><%=errMsgDelOrder%></h1>
            </div>
        <%
            session.removeAttribute("errMsgDelOrder");
        }
        %>
    <%}%>
</div>
<%@ include file="./parts/footer.jsp"%>