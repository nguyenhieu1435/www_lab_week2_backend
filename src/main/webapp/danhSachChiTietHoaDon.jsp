<%@ page import="vn.edu.iuh.fit.backend.models.OrderDetail" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 29/09/2023
  Time: 9:21 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>

<%
  List<OrderDetail> orderDetailList = (List<OrderDetail>) session.getAttribute("orderdetails");
  long orderID = (long)session.getAttribute("orderIdOfOrderDetail");
  int limitNumOrderDetail = (int)session.getAttribute("limitNumOrderDetail");
  int pageNumOrderDetail = (int)session.getAttribute("pageNumOrderDetail");
  String errMsgDelOrderDetail = (String) session.getAttribute("errMsgDelOrderDetail");
%>

<div>
  <%if (orderDetailList == null || orderDetailList.isEmpty()){%>
    <h1 class="text-center text-danger">Không có chi tiết hóa đơn nào</h1>
    <table class="table table-bordered table-hover">
      <thead>
      <tr>
        <th scope="col">Order</th>
        <th scope="col">Product</th>
        <th scope="col">Price</th>
        <th scope="col">Quantity</th>
        <th scope="col">Note</th>
        <th scope="col" class="text-center">
          <a href="themChiTietHoaDon.jsp" class="btn btn-success px-5">Insert Order</a>
        </th>
      </tr>
      </thead>
    </table>
  <%} else {%>
    <h3 class="text-center text-primary">Chi tiết hóa đơn của Hóa đơn ID: <%=orderID%></h3>
    <table class="table table-bordered table-hover">
      <thead>
      <tr>
        <th scope="col">OrderID</th>
        <th scope="col">Product</th>
        <th scope="col">Price</th>
        <th scope="col">Quantity</th>
        <th scope="col">Note</th>
        <th scope="col" class="text-center">
          <a href="themChiTietHoaDon.jsp" class="btn btn-success px-5">Insert Order</a>
        </th>
      </tr>
      </thead>
      <tbody>
      <%for (int i = (pageNumOrderDetail-1)*limitNumOrderDetail; i < Math.min(orderDetailList.size(), pageNumOrderDetail*limitNumOrderDetail); i++){%>
      <tr>
        <th scope="row"><%=orderDetailList.get(i).getOrder().getOrder_id()%></th>
        <td><strong>ID: </strong><%=orderDetailList.get(i).getProduct().getProduct_id()%> <strong>Name: </strong><%=orderDetailList.get(i).getProduct().getName()%></td>
        <td><%=orderDetailList.get(i).getPrice()%></td>
        <td><%=orderDetailList.get(i).getQuantity()%></td>
        <td><%=orderDetailList.get(i).getNote()%></td>
        <td class="text-center">
          <a class="btn btn-warning" href="control-servlet?action=handleOpenUpdateDetailPage&orderId=<%=orderDetailList.get(i).getOrder().getOrder_id()%>&productId=<%=orderDetailList.get(i).getProduct().getProduct_id()%>"

          >Update</a>
          <a class="btn btn-danger" href="control-servlet?action=deleteOrderDetail&orderId=<%=orderDetailList.get(i).getOrder().getOrder_id()%>&productId=<%=orderDetailList.get(i).getProduct().getProduct_id()%>">Delete</a>
        </td>
      </tr>
      <%}%>
      </tbody>
    </table>

      <div class="d-flex align-items-center justify-content-center">
        <%if (pageNumOrderDetail > 1){%>
        <a class="btn btn-primary"
           href="control-servlet?action=decreaseOrderDetailPageNum"
        >Prev</a>
        <%}%>
        <span class="mx-3"><%=pageNumOrderDetail%></span>
        <a class="btn btn-primary"
           href="control-servlet?action=increaseOrderDetailPageNum"
        >Next</a>
      </div>
      <%if (errMsgDelOrderDetail != null && !errMsgDelOrderDetail.isEmpty()){%>
        <div>
          <h1 class="text-center text-primary"><%=errMsgDelOrderDetail%></h1>
        </div>
      <%
            session.removeAttribute("errMsgDelOrderDetail");
        }
      %>
  <%}%>
</div>
<%@ include file="./parts/footer.jsp"%>