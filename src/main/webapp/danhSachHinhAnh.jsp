<%@ page import="java.util.List" %>
<%@ page import="vn.edu.iuh.fit.backend.models.ProductImage" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 05/10/2023
  Time: 5:06 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>

<%
    List<ProductImage> productImageList = (List<ProductImage>)session.getAttribute("productImageList");
    long productIdAtProductImage  = (long)session.getAttribute("productIdAtProductImage");
    int numPageProductImage  = (int)session.getAttribute("numPageProductImage");
    int limitNumProductImage = (int)session.getAttribute("limitNumProductImage");
    String msgDeleteActionProductImage = (String)session.getAttribute("msgDeleteActionProductImage");
%>

<div>
    <%if (productImageList == null || productImageList.isEmpty()){%>
        <h1 class="text-center text-primary">Không có hình ảnh nào của sản phẩm</h1>
    <%} else {%>
        <table class="table table-bordered table-hover" style="max-width: 100vw">
            <thead>
            <tr>
                <th scope="col">Image Id</th>
                <th scope="col">Path </th>
                <th scope="col">Image of Path </th>
                <th scope="col">Alternative</th>
                <th scope="col">Image of Alternative </th>
                <th scope="col" class="text-center">
                    <a href="themHinhAnhSanPham.jsp" class="btn btn-success px-5">Insert</a>
                </th>
            </tr>
            </thead>
            <tbody>
            <%for (int i = (numPageProductImage-1)*limitNumProductImage; i < Math.min(productImageList.size(), limitNumProductImage*numPageProductImage); i++){%>
                <tr>
                    <th scope="row"><%=productImageList.get(i).getImage_id()%></th>
                    <td style="width: 10%"><%=productImageList.get(i).getPath()%></td>
                    <td><img src="<%=productImageList.get(i).getPath()%>" alt="" style="width: 100px; height: 100px; object-fit: contain"/> </td>
                    <td style="width: 10%"><%=productImageList.get(i).getAlternative()%></td>
                    <td><img src="<%=productImageList.get(i).getAlternative()%>" alt="" style="width: 100px; height: 100px; object-fit: contain"/> </td>
                    <td class="text-center">
                        <a class="btn btn-warning my-2"
                           href="control-servlet?action=handleOpenUpdProductImage&productImageId=<%=productImageList.get(i).getImage_id()%>">
                            Update
                        </a>
                        <a class="btn btn-danger my-2"
                           href="control-servlet?action=deleletProductImage&productImageId=<%=productImageList.get(i).getImage_id()%>"
                        >Delete</a>
                    </td>
                </tr>
            <%}%>
            </tbody>
        </table>
    <%}%>
    <div class="d-flex align-items-center justify-content-center">
        <%if (numPageProductImage > 1){%>
        <a class="btn btn-primary"
           href="control-servlet?action=decreaseProductImage"
        >Prev</a>
        <%}%>
        <span class="mx-3"><%=numPageProductImage%></span>
        <a class="btn btn-primary"
           href="control-servlet?action=increaseProductImage"
        >Next</a>
    </div>

    <%if (msgDeleteActionProductImage != null && !msgDeleteActionProductImage.isEmpty()){%>
    <div>
        <h1 class="text-center text-primary"><%=msgDeleteActionProductImage%></h1>
    </div>
    <%
            session.removeAttribute("msgDeleteActionProductImage");
        }
    %>
</div>

<%@ include file="./parts/footer.jsp"%>

