<%@ page import="vn.edu.iuh.fit.backend.enums.ProductStatus" %>
<%@ page import="vn.edu.iuh.fit.backend.models.Product" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 05/10/2023
  Time: 12:12 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>

<%
    Product product = (Product) session.getAttribute("selectedProductManagement");
    String msgErrUpdateProduct  = (String) session.getAttribute("msgErrUpdateProduct");

%>

<div class="container">
   <%if (product == null){%>
        <h1 class="text-center text-danger">Không có product để chỉnh sửa</h1>
    <%} else {%>
        <form action="control-servlet?action=updateOneProduct" method="POST">
            <div class="mb-3">
                <label for="inputIdProduct" class="form-label">Name</label>
                <input type="text" class="form-control" id="inputIdProduct"
                       readonly
                       name="productId"
                       value="<%=product.getProduct_id()%>"
                >
            </div>
            <div class="mb-3">
                <label for="inputName" class="form-label">Name</label>
                <input type="text" class="form-control" id="inputName"
                       name="name"
                       value="<%=product.getName()%>"
                >
            </div>
            <div class="mb-3">
                <label for="inputDesc" class="form-label">Description</label>
                <textarea class="form-control" id="inputDesc"
                          name="description"

                ><%=product.getDescription()%></textarea>
            </div>
            <div class="mb-3">
                <label for="inputManufacturer" class="form-label">Manufacturer</label>
                <input type="text" class="form-control" id="inputManufacturer"
                       name="manufacturer"
                       value="<%=product.getManufacturer()%>"
                >
            </div>
            <div class="mb-3">
                <label for="inputUnit" class="form-label">Unit</label>
                <input type="text" class="form-control" id="inputUnit"
                       name="unit"
                       value="<%=product.getUnit()%>"
                >
            </div>
            <div class="mb-3">
                <label for="inputManufacturer" class="form-label">Status</label>
                <select name="status">
                    <%for (int i = 0; i < ProductStatus.values().length; i++){%>
                    <%if (ProductStatus.values()[i].getStatusNumber() != -1){%>
                        <option <%=(product.getStatus().getStatusNumber() == ProductStatus.values()[i].getStatusNumber() ? "selected" : "")%>><%=ProductStatus.values()[i].toString()%></option>
                    <%}%>
                    <%}%>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
        <%if (msgErrUpdateProduct != null && !msgErrUpdateProduct.isEmpty()){%>
            <h1 class="text-center text-danger"><%=msgErrUpdateProduct%></h1>
        <%}%>
    <%}%>

</div>
<%@ include file="./parts/footer.jsp"%>