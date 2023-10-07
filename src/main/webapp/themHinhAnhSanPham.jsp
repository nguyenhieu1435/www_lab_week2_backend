<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 05/10/2023
  Time: 5:56 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>

<%
    long productIdAtProductImage = (long)session.getAttribute("productIdAtProductImage");
    String msgErrAddProductImage = (String)session.getAttribute("msgErrAddProductImage");
%>

<div class="container">
    <form action="control-servlet?action=addNewProductImage" method="POST" enctype="multipart/form-data">
        <div class="mb-3">
            <label for="inputProductId" class="form-label">ProductID</label>
            <input type="text" class="form-control" id="inputProductId"
                   name="productId"
                   readonly
                   value="<%=productIdAtProductImage%>"
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
        <%=msgErrAddProductImage != null ? msgErrAddProductImage : ""%>
    </h1>
</div>
<%@ include file="./parts/footer.jsp"%>