<%@ page import="vn.edu.iuh.fit.backend.models.ProductPrice" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 05/10/2023
  Time: 1:50 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>

<%
    String titleProductPriceList = (String)session.getAttribute("titleProductPriceList");
    List<ProductPrice> productPriceList = (List<ProductPrice>) session.getAttribute("productPriceList");
    int numPageProductPrice = (int)session.getAttribute("numPageProductPrice");
    int limitNumProductPrice = (int)session.getAttribute("limitNumProductPrice");
    String errDeleteProductPrice = (String)session.getAttribute("errDeleteProductPrice");
%>

<div>
    <%if (titleProductPriceList != null){%>
        <h1 class="text-center text-primary"><%=titleProductPriceList%></h1>
    <%}%>
    <%if (productPriceList == null || productPriceList.isEmpty()){%>
        <h1 class="text-center text-primary">Không có giá sản phẩm nào để hiện thị</h1>
    <%} else {%>
        <table class="table table-bordered table-hover">
            <thead>
            <tr>
                <th scope="col">Time</th>
                <th scope="col">Price</th>
                <th scope="col">Note</th>
                <th scope="col" class="text-center">
                    <a href="themGiaSanPham.jsp" class="btn btn-success px-5">Insert</a>
                </th>
            </tr>
            </thead>
            <tbody>
            <%for (int i = (numPageProductPrice - 1) * limitNumProductPrice; i < Math.min(productPriceList.size(), limitNumProductPrice*numPageProductPrice); i++){%>
                <tr>
                    <th scope="row"><%=productPriceList.get(i).getPrice_date_time()%></th>
                    <td><%=productPriceList.get(i).getPrice()%></td>
                    <td><%=productPriceList.get(i).getNote()%></td>
                    <td class="text-center">
                        <a class="btn btn-warning"
                           href="control-servlet?action=handleOpenUpdateProductPrice&id=<%=productPriceList.get(i).getProduct().getProduct_id()%>&date=<%=productPriceList.get(i).getPrice_date_time()%>">
                            Update
                        </a>
                        <a class="btn btn-danger"
                           href="control-servlet?action=deleteProductPrice&id=<%=productPriceList.get(i).getProduct().getProduct_id()%>&date=<%=productPriceList.get(i).getPrice_date_time()%>"
                        >Delete</a>
                    </td>
                </tr>
            <%}%>
            </tbody>
        </table>

        <div class="d-flex justify-content-center align-items-center">
            <%if (numPageProductPrice > 1){%>
                <a class="btn btn-primary" href="control-servlet?action=decreaseProductPrice">Prev</a>
            <%}%>
            <span class="mx-3"><%=numPageProductPrice%></span>
            <a class="btn btn-primary" href="control-servlet?action=increaseProductPrice">Next</a>
        </div

        <%if (errDeleteProductPrice != null){%>
            <h1 class="text-center text-primary"><%=errDeleteProductPrice%></h1>
        <%}%>

        <%if (errDeleteProductPrice != null) {session.removeAttribute("errDeleteProductPrice");}%>
    <%}%>
</div>
<%@ include file="./parts/footer.jsp"%>

