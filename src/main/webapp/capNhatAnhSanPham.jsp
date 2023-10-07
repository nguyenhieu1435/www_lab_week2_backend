<%@ page import="vn.edu.iuh.fit.backend.models.ProductImage" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 06/10/2023
  Time: 12:33 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>

<%
    ProductImage productImage = (ProductImage) session.getAttribute("selectedProductImage");
    String errUpdProductImage = (String)session.getAttribute("errUpdProductImage");
%>

<div class="container">
    <form action="control-servlet?action=updateProductImage" method="POST" enctype="multipart/form-data">
        <div class="mb-3">
            <label for="inputProductId" class="form-label">ProductID</label>
            <input type="text" class="form-control" id="inputProductId"
                   name="productId"
                   readonly
                   value="<%=productImage.getProduct().getProduct_id()%>"
            />
        </div>

        <div class="mb-3">
            <label for="inputProductImageId" class="form-label">ProductID</label>
            <input type="text" class="form-control" id="inputProductImageId"
                   name="productImgId"
                   readonly
                   value="<%=productImage.getImage_id()%>"
            />
        </div>
        <div class="mb-3">
            <label for="inputPathPrimary" class="form-label">Path</label>
            <input type="file" class="form-control" id="inputPathPrimary"
                   name="pathPrimary" required
            />
        </div>

        <div class="mb-3">
            <label for="inputAlterNative" class="form-label">Alternative</label>
            <input type="file" class="form-control" id="inputAlterNative"
                   name="pathAlternative" required

            />
        </div>

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
    <h1 class="text-center text-danger">
        <%=errUpdProductImage != null ? errUpdProductImage : ""%>
    </h1>
</div>
<%@ include file="./parts/footer.jsp"%>