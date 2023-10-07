<%@ page import="java.util.HashMap" %>
<%@ page import="vn.edu.iuh.fit.backend.models.Product" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.DecimalFormat" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 06/10/2023
  Time: 9:27 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="./parts/header.jsp" %>
<%@ include file="./parts/navbar.jsp" %>

<%
    HashMap<Product, Integer> carts = (HashMap<Product, Integer>) session.getAttribute("carts");
    HashMap<Long, Double> priceOfProducts = (HashMap<Long, Double>) session.getAttribute("priceOfProducts");
    double totalPrice = 0;
    if (carts != null && priceOfProducts != null){
        for(Map.Entry<Product, Integer> entry : carts.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            totalPrice += quantity * priceOfProducts.get(product.getProduct_id());
        }
    }
    NumberFormat formatter = new DecimalFormat("#0.00");

%>

<div class="row g-0 container" style="margin: 0 auto">
    <%if (carts == null || carts.isEmpty()) {%>
    <h1 class="text-center text-primary">Không có sản phẩm nào</h1>
    <%} else {%>
    <div class="col-9" style="padding-right: 20px; border-right: 1px solid #000">
        <div class="d-flex justify-content-between">
            <h3>Shopping Cart</h3>
            <h3><%=carts.size()%> Items</h3>
        </div>
        <div class="row g-0 border p-3">
            <p class="col-5" style="font-weight: bold">PRODUCT DETAILS</p>
            <p class="col-3" style="font-weight: bold">QUANTITY</p>
            <p class="col-2" style="font-weight: bold">PRICE</p>
            <p class="col-2" style="font-weight: bold">TOTAL</p>
        </div>
        <%for(Map.Entry<Product, Integer> entry : carts.entrySet()) {
            assert priceOfProducts != null;
        %>
            <div class="row g-0 border border-bottom p-3">
                <div class="col-5 d-flex align-items-center">
                    <img src="
                        <%=entry.getKey().getProductImageList() != null && !entry.getKey().getProductImageList().isEmpty() ? entry.getKey().getProductImageList().get(0).getPath() : "https://static.vecteezy.com/system/resources/previews/007/451/786/original/an-outline-isometric-icon-of-unknown-product-vector.jpg"%>
                    " alt="" style="width: 100px; height: 100px; object-fit: contain"/>
                    <div>
                        <p><%=entry.getKey().getName()%></p>
                        <p><%=entry.getKey().getManufacturer()%></p>
                        <a href="control-servlet?action=deleteItemFromCart&productId=<%=entry.getKey().getProduct_id()%>" class="text-danger">Remove</a>
                    </div>
                </div>
                <div class="col-3" style="display: flex; align-items: center">
                    <a href="control-servlet?action=decreaseItemCart&productId=<%=entry.getKey().getProduct_id()%>" class="btn btn-warning">-</a>
                    <input type="number" readonly value="<%=entry.getValue()%>" style="width: 60px;"
                    />
                    <a href="control-servlet?action=increaseItemCart&productId=<%=entry.getKey().getProduct_id()%>" class="btn btn-primary">+</a>
                </div>
                <div class="col-2">
                    <p style="font-weight: bold">$<%=formatter.format(priceOfProducts.get(entry.getKey().getProduct_id()))%></p>
                </div>
                <div class="col-2">
                    <p style="font-weight: bold">$<%=formatter.format(priceOfProducts.get(entry.getKey().getProduct_id()) * entry.getValue())%></p>
                </div>
            </div>
        <%}%>

    </div>
    <div class="col-3" style="padding-left: 20px">
        <h1>Order Summary</h1>
        <div style="display: flex; justify-content: space-between; align-items: center">
            <p style="font-weight: bold; font-size: 25px">Total cost</p>
            <p class="text-danger" style="font-weight: bold; font-size: 25px">$<%=formatter.format(totalPrice)%></p>
        </div>
        <a href="#" class="btn btn-success">CHECKOUT</a>
    </div>
    <%}%>
</div>

<%@ include file="./parts/footer.jsp" %>