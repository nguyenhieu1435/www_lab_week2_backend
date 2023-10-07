<%@ page import="vn.edu.iuh.fit.backend.models.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.iuh.fit.backend.models.ProductPrice" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 30/09/2023
  Time: 8:49 SA
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>

<%
    List<Product> productList = (List<Product>) session.getAttribute("productsOfManagement");
    List<ProductPrice> productPriceList = (List<ProductPrice>) session.getAttribute("productPricesOfManagement");
    int numPageProductManagement = (int)session.getAttribute("numPageProductManagement");
    int numLimitProductManagement = (int)session.getAttribute("numLimitProductManagement");
    String msgResultDeleteProduct = (String)session.getAttribute("msgResultDeleteProduct");

%>

<%if (productList == null || productList.isEmpty()){%>
    <h1 class="text-center text-primary">Không có sản phẩm nào trong kho!</h1>
<%} else {%>
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Image</th>
            <th scope="col">Name</th>
            <th scope="col">Descrption</th>
            <th scope="col">Unit</th>
            <th scope="col">Manufacturer</th>
            <th scope="col">Status</th>
            <th scope="col">Newest Price</th>
            <th scope="col" class="text-center">
                <a href="themSanPham.jsp" class="btn btn-success px-5">Insert</a>
            </th>
        </tr>
        </thead>
        <tbody>
            <%for (int i = (numPageProductManagement-1)*numLimitProductManagement; i < Math.min(productList.size(), numPageProductManagement * numLimitProductManagement); i++){%>
                <tr>
                    <th scope="row"><%=productList.get(i).getProduct_id()%></th>
                    <td class="d-flex justify-content-center">
                        <%System.out.println(productList.get(i).getProductImageList());%>
                        <img style="width: 50px; height: 50px" src="<%=(productList.get(i).getProductImageList() != null && !productList.get(i).getProductImageList().isEmpty()) ?  productList.get(i).getProductImageList().get(0).getPath() : "https://static.vecteezy.com/system/resources/previews/007/451/786/original/an-outline-isometric-icon-of-unknown-product-vector.jpg"%>">
                    </td>
                    <td><%=productList.get(i).getName()%></td>
                    <td><%=productList.get(i).getDescription()%></td>
                    <td><%=productList.get(i).getUnit()%></td>
                    <td><%=productList.get(i).getManufacturer()%></td>
                    <td><%=productList.get(i).getStatus()%></td>
                    <td><%=productPriceList.get(i) != null ? productPriceList.get(i).getPrice() : 0%></td>
                    <td class="text-center">
                        <a class="btn btn-primary" href="control-servlet?action=getPriceListByProduct&id=<%=productList.get(i).getProduct_id()%>">
                            Price List
                        </a>
                        <a class="btn btn-primary" href="control-servlet?action=getImgListByProduct&id=<%=productList.get(i).getProduct_id()%>">
                            Image List
                        </a>
                        <a class="btn btn-warning"
                           href="control-servlet?action=updateProductManagement&id=<%=productList.get(i).getProduct_id()%>">
                            Update
                        </a>
                        <a class="btn btn-danger"
                           href="control-servlet?action=deleteProductManagement&id=<%=productList.get(i).getProduct_id()%>"
                        >Delete</a>
                    </td>
                </tr>
            <%}%>
        </tbody>
    </table>
    <div class="d-flex justify-content-center align-items-center">
        <%if (numPageProductManagement > 1){%>
        <a class="btn btn-primary" href="control-servlet?action=decreaseProductMng">Prev</a>
        <%}%>
        <span class="mx-3"><%=numPageProductManagement%></span>
        <a class="btn btn-primary" href="control-servlet?action=increaseProductMng">Next</a>
    </div>
    <%if (msgResultDeleteProduct != null && !msgResultDeleteProduct.isEmpty()){%>
            <h1 class="text-center text-primary"><%=msgResultDeleteProduct%></h1>
    <%}%>
    <%if (msgResultDeleteProduct != null) {session.removeAttribute("msgResultDeleteProduct");}%>
<%}%>

<%@ include file="./parts/footer.jsp"%>

