<%@ page import="vn.edu.iuh.fit.backend.models.OrderDetail" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 30/09/2023
  Time: 12:16 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>

<%
    OrderDetail orderDetail = (OrderDetail) session.getAttribute("orderdetail");
    String errMsgUpdOrderDetail = (String)session.getAttribute("errMsgUpdOrderDetail");
%>

<div class="container">
    <%if (orderDetail == null) {%>
        <h1 class="text-center text-primary">Không có chi tiết hóa đơn cần cập nhật</h1>
    <%} else {%>
        <form action="control-servlet?action=updateOrderDetail" method="POST">
            <div class="mb-3">
                <label for="inputOrder" class="form-label">OrderID</label>
                <input type="text" class="form-control" id="inputOrder"
                       name="orderID"
                       value="<%=orderDetail.getOrder().getOrder_id()%>"
                       readonly
                >
            </div>
            <div class="mb-3">
                <label for="inputProduct" class="form-label">Product Id</label>
                <input type="number" class="form-control" id="inputProduct"
                       name="productID"
                       readonly
                       value="<%=orderDetail.getProduct().getProduct_id()%>"
                >
            </div>
            <div class="mb-3">
                <label for="inputQuantity" class="form-label">Quantity</label>
                <input type="number" class="form-control" id="inputQuantity"
                       name="quantity"
                       value="<%=orderDetail.getQuantity()%>"
                >
            </div>
            <div class="mb-3">
                <label for="inputNote" class="form-label">Note</label>
                <input type="text" class="form-control" id="inputNote"
                       name="note"
                       value="<%=orderDetail.getNote()%>"
                >
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
            <%if (errMsgUpdOrderDetail != null){%>
            <div class="my-5">
                <h1 class="text-center text-danger"><%=errMsgUpdOrderDetail%></h1>
            </div>
            <%}
                session.removeAttribute("errMsgUpdOrderDetail");
            %>
        </form>
    <%}%>
</div>
<%@ include file="./parts/footer.jsp"%>