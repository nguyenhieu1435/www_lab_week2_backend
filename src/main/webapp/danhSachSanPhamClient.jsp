<%@ page import="java.util.List" %>
<%@ page import="vn.edu.iuh.fit.backend.models.Product" %>
<%@ page import="vn.edu.iuh.fit.backend.models.ProductPrice" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 06/10/2023
  Time: 5:35 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>
<%
    List<Product> productList = (List<Product>)session.getAttribute("productListClient");
    List<ProductPrice> productPriceList = (List<ProductPrice>) session.getAttribute("productPriceListClient");
    int numPageProductListClient = (int)session.getAttribute("numPageProductListClient");
    int numLimitProductListClient = (int)session.getAttribute("numLimitProductListClient");
    String resultAddToCart = (String)session.getAttribute("resultAddToCart");
%>

<div>
    <%if (productList == null || productList.isEmpty()){%>
        <h1 class="text-center text-primary">Không có sản phẩm nafo để hiện thị</h1>
    <%} else {%>
        <div class="row g-0">
            <div class="col-1 py-3 border border-dark" style="font-weight: bold; text-align: center">ID</div>
            <div class="col-1 py-3 border border-dark font-weight-bold"  style="font-weight: bold; text-align: center">Image</div>
            <div class="col-2 py-3 border border-dark font-weight-bold"  style="font-weight: bold; text-align: center">Name</div>
            <div class="col-2 py-3 border border-dark font-weight-bold"  style="font-weight: bold; text-align: center">Descrption</div>
            <div class="col-2 py-3 border border-dark font-weight-bold"  style="font-weight: bold; text-align: center">Manufacturer</div>
            <div class="col-2 py-3 border border-dark font-weight-bold"  style="font-weight: bold; text-align: center">Price</div>
            <div class="col-2 py-3 border border-dark font-weight-bold"  style="font-weight: bold; text-align: center">Action</div>
        </div>
        <%for (int i = (numPageProductListClient-1)*numLimitProductListClient; i < Math.min(productList.size(), numLimitProductListClient*numPageProductListClient); i++){%>
            <form class="row g-0" action="control-servlet?action=addToCart" method="POST">
                <div class="col-1 text-center border border-dark"><%=productList.get(i).getProduct_id()%></div>
                <div class="col-1 text-center border border-dark">
                    <img style="width: 100px; height: 100px; object-fit: contain" src="
                    <%=productList.get(i).getProductImageList() != null && !productList.get(i).getProductImageList().isEmpty() ? productList.get(i).getProductImageList().get(0).getPath() : "https://static.vecteezy.com/system/resources/previews/007/451/786/original/an-outline-isometric-icon-of-unknown-product-vector.jpg"%>
                    " alt=""/>
                </div>
                <div class="col-2 text-center border border-dark"><%=productList.get(i).getName()%></div>
                <div class="col-2 text-center border border-dark"><%=productList.get(i).getDescription()%></div>
                <div class="col-2 text-center border border-dark"><%=productList.get(i).getManufacturer()%></div>
                <div class="col-2 text-center border border-dark"><%=productPriceList != null && !productPriceList.isEmpty() && productPriceList.get(i) != null ? productPriceList.get(i).getPrice() : "not for sell"%></div>
                <div class="col-2 d-flex align-items-center gap-4 justify-content-center border border-dark">
                    <input type="number" value="0" name="quantityOrder" style="width: 60px" class="mr-2" required/>
                    <input type="hidden" name="productId" value="<%=productList.get(i).getProduct_id()%>"/>
                    <button type="submit" class="btn btn-primary">Add to Cart</button>
                </div>
            </form>
        <%}%>
        <div class="d-flex justify-content-center align-items-center my-5">
            <%if (numPageProductListClient > 1){%>
            <a class="btn btn-primary" href="control-servlet?action=decreaseProductListClient">Prev</a>
            <%}%>
            <span class="mx-3"><%=numPageProductListClient%></span>
            <a class="btn btn-primary" href="control-servlet?action=increaseProductListClient">Next</a>
        </div>

        <h1 class="text-center text-primary"><%=resultAddToCart != null ? resultAddToCart : ""%></h1>
        <%if(resultAddToCart != null) { session.removeAttribute("resultAddToCart");}%>
    <%}%>
</div>
<%@ include file="./parts/footer.jsp"%>
