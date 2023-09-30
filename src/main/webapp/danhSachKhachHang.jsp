<%@ page import="java.util.List" %>
<%@ page import="vn.edu.iuh.fit.backend.models.Customer" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 28/09/2023
  Time: 1:51 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>

<%
    List<Customer> customers = (List<Customer>) session.getAttribute("customers");
    int limitNumCust = (int)session.getAttribute("limitNumCust");
    int pageNumCust = (int)session.getAttribute("pageNumCust");
    String errMsgDelCust = (String)session.getAttribute("errMsgDelCust");
%>

<div>
    <%if (customers == null || customers.isEmpty()){%>
        <h1 class="text-center text-danger">Không có khách hàng nào!</h1>
    <%} else {%>
        <table class="table table-bordered table-hover">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Full name</th>
                <th scope="col">Address</th>
                <th scope="col">Email</th>
                <th scope="col">Phone</th>
                <th scope="col" class="text-center">
                    <a href="themKhachHang.jsp" class="btn btn-success px-5">Insert</a>
                </th>
            </tr>
            </thead>
            <tbody>
                <%for (int i = (pageNumCust - 1)*limitNumCust; i < Math.min(customers.size(), limitNumCust*pageNumCust); i++){%>
                    <tr>
                        <th scope="row"><%=customers.get(i).getId()%></th>
                        <td><%=customers.get(i).getName()%></td>
                        <td><%=customers.get(i).getAddress()%></td>
                        <td><%=customers.get(i).getEmail()%></td>
                        <td><%=customers.get(i).getPhone()%></td>
                        <td class="text-center">
                            <a class="btn btn-warning" href="control-servlet?action=handleOpenUpdCust&id=<%=customers.get(i).getId()%>">Update</a>
                            <a class="btn btn-danger" href="control-servlet?action=deleteCustomer&id=<%=customers.get(i).getId()%>">Delete</a>
                        </td>
                    </tr>
                <%}%>
            </tbody>
        </table>
        <div class="d-flex align-items-center justify-content-center">
            <%if (pageNumCust > 1){%>
                <a class="btn btn-primary"
                    href="control-servlet?action=decreaseCustPageNum"
                >Prev</a>
            <%}%>
            <span class="mx-3"><%=pageNumCust%></span>
            <a class="btn btn-primary"
                href="control-servlet?action=increaseCustPageNum"
            >Next</a>
        </div>

        <%if (errMsgDelCust != null){%>
            <div>
                <h1 class="text-center text-primary"><%=errMsgDelCust%></h1>
            </div>
        <%}
            session.removeAttribute("errMsgDelCust");
        %>

    <%}%>
</div>

<%@ include file="./parts/footer.jsp"%>