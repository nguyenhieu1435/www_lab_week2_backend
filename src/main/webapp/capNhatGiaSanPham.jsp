<%@ page import="vn.edu.iuh.fit.backend.models.ProductPrice" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 05/10/2023
  Time: 3:42 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>
<%
    String errUpdProductPrice = (String)session.getAttribute("errUpdProductPrice");
    ProductPrice productPrice = (ProductPrice)session.getAttribute("productPrice");
%>
<div class="container">
    <%if (productPrice == null){%>
        <h1 class="text-center text-primary">Cần thông tin Product Price để cập nhật</h1>
    <%} else {%>
        <form action="control-servlet?action=updateProductPrice" method="POST">
            <div class="mb-3">
                <label for="inputProductId" class="form-label">ProductID</label>
                <input type="text" class="form-control" id="inputProductId"
                       name="productId"
                       readonly
                       value="<%=productPrice.getProduct().getProduct_id()%>"
                >
            </div>
            <div class="mb-3">
                <label for="inputDateTime" class="form-label">ProductID</label>
                <input type="text" class="form-control" id="inputDateTime"
                       name="dateTimeId"
                       readonly
                       value="<%=productPrice.getPrice_date_time()%>"
                >
            </div>
            <div class="mb-3">
                <label for="inputPrice" class="form-label">Price</label>
                <input type="number" class="form-control" id="inputPrice"
                       name="price"
                       required
                       value="<%=productPrice.getPrice()%>"
                >
            </div>

            <div class="mb-3">
                <label for="inputNote" class="form-label">Note</label>
                <input type="text" class="form-control" id="inputNote"
                       name="note"
                       value="<%=productPrice.getNote()%>"
                >
            </div>

            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
        <h1 class="text-center text-danger">
            <%=errUpdProductPrice != null ? errUpdProductPrice : ""%>
        </h1>
    <%}%>
</div>

<%@ include file="./parts/footer.jsp"%>

