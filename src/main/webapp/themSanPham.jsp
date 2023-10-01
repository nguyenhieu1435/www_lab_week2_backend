<%@ page import="vn.edu.iuh.fit.backend.enums.ProductStatus" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 30/09/2023
  Time: 10:50 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>

<div class="container">
    <form action="control-servlet?action=addCustomer" method="POST">
        <div class="mb-3">
            <label for="inputName" class="form-label">Name</label>
            <input type="text" class="form-control" id="inputName"
                   name="name"
            >
        </div>
        <div class="mb-3">
            <label for="inputDesc" class="form-label">Description</label>
            <textarea class="form-control" id="inputDesc"
                   name="description"
            ></textarea>
        </div>
        <div class="mb-3">
            <label for="inputManufacturer" class="form-label">Manufacturer</label>
            <input type="text" class="form-control" id="inputManufacturer"
                   name="manufacturer"
            >
        </div>
        <div class="mb-3">
            <label for="inputUnit" class="form-label">Unit</label>
            <input type="text" class="form-control" id="inputUnit"
                   name="unit"
            >
        </div>
        <div class="mb-3">
            <label for="inputManufacturer" class="form-label">Status</label>
            <select name="status">
                <%for (int i = 0; i < ProductStatus.values().length; i++){%>
                    <%if (ProductStatus.values()[i].getStatusNumber() != -1){%>
                        <option <%=(i == 0 ? "selected" : "")%>><%=ProductStatus.values()[i].toString()%></option>
                    <%}%>
                <%}%>
            </select>
        </div>
        <div class="mb-3">
            <label for="inputPrice" class="form-label">Price</label>
            <input type="number" class="form-control" id="inputPrice"
                   name="price"
            >
        </div>
        <div class="mb-3">
            <label for="inputNote" class="form-label">Note Price</label>
            <input type="text" class="form-control" id="inputNote"
                   name="note"
            >
        </div>
        <div class="mb-3">
            <label for="inputImgPri" class="form-label">Image Primary</label>
            <input type="file" class="form-control" id="inputImgPri"
                   name="name"
            >
        </div>
        <div class="mb-3">
            <label for="inputImgSrd" class="form-label">Image Alternative</label>
            <input type="file" class="form-control" id="inputImgSrd"
                   name="name"
            >
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>

<%@ include file="./parts/footer.jsp"%>