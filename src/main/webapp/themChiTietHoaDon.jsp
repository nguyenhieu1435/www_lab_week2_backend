<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 29/09/2023
  Time: 10:48 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>

<%
    String errMsgAddOrderDetail = (String)session.getAttribute("errMsgAddOrderDetail");
    long orderID = (long)session.getAttribute("orderIdOfOrderDetail");
%>

<div class="container">
    <form action="control-servlet?action=addOrderDetail" method="POST">
        <div class="mb-3">
            <label for="inputOrder" class="form-label">OrderID</label>
            <input type="text" class="form-control" id="inputOrder"
                   name="orderID"
                   value="<%=orderID%>"
                   readonly
            >
        </div>
        <div class="mb-3">
            <label for="inputProduct" class="form-label">Product Id</label>
            <input type="number" class="form-control" id="inputProduct"
                   name="productID"
            >
        </div>
        <div class="mb-3">
            <label for="inputQuantity" class="form-label">Quantity</label>
            <input type="number" class="form-control" id="inputQuantity"
                   name="quantity"
            >
        </div>
        <div class="mb-3">
            <label for="inputNote" class="form-label">Note</label>
            <input type="text" class="form-control" id="inputNote"
                   name="note"
            >
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
        <%if (errMsgAddOrderDetail != null){%>
        <div class="my-5">
            <h1 class="text-center text-danger"><%=errMsgAddOrderDetail%></h1>
        </div>
        <%}
            session.removeAttribute("errMsgAddOrderDetail");
        %>
    </form>
</div>
<%@ include file="./parts/footer.jsp"%>