<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 05/10/2023
  Time: 2:41 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>

<%
    long productID = (long)session.getAttribute("productIdAtProductPrice");
    String errAddProductPrice = (String) session.getAttribute("errAddProductPrice");
%>

<div class="container">
    <form action="control-servlet?action=addProductPrice" method="POST">
        <div class="mb-3">
            <label for="inputProductId" class="form-label">ProductID</label>
            <input type="text" class="form-control" id="inputProductId"
                   name="productId"
                   readonly
                   value="<%=productID%>"
            >
        </div>

        <div class="mb-3">
            <label for="inputPrice" class="form-label">Price</label>
            <input type="number" class="form-control" id="inputPrice"
                   name="price" required
            >
        </div>

        <div class="mb-3">
            <label for="inputNote" class="form-label">Note</label>
            <input type="text" class="form-control" id="inputNote"
                   name="note"
            >
        </div>

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
    <h1 class="text-center text-danger">
        <%=errAddProductPrice != null ? errAddProductPrice : ""%>
    </h1>
</div>
<%@ include file="./parts/footer.jsp"%>

