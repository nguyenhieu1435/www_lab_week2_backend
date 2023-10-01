<%@ page import="vn.edu.iuh.fit.backend.models.Customer" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 28/09/2023
  Time: 3:27 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>

<%
    Customer customer = (Customer) session.getAttribute("customer");
    String errMsgUpdCust = (String)session.getAttribute("errMsgUpdCust");
%>

<div class="container">
    <%if (customer != null){%>
        <form action="control-servlet?action=updateCustomer" method="POST">
            <div class="mb-3">
                <label for="inputId" class="form-label">Id</label>
                <input type="text" class="form-control" id="inputId"
                       readonly
                       name="id"
                       value="<%=customer.getId()%>"
                >
            </div>
            <div class="mb-3">
                <label for="inputFullName" class="form-label">Name</label>
                <input type="text" class="form-control" id="inputFullName"
                       name="name"
                       value="<%=customer.getName()%>"
                >
            </div>
            <div class="mb-3">
                <label for="inputAddress" class="form-label">Address</label>
                <input type="text" class="form-control" id="inputAddress"
                       name="address"
                       value="<%=customer.getAddress()%>"
                >
            </div>
            <div class="mb-3">
                <label for="inputEmail" class="form-label">Email</label>
                <input type="email" class="form-control" id="inputEmail"
                       name="email"
                       value="<%=customer.getEmail()%>"
                >
            </div>
            <div class="mb-3">
                <label for="inputPhone" class="form-label">Phone</label>
                <input type="text" class="form-control" id="inputPhone"
                       name="phone"
                       value="<%=customer.getPhone()%>"
                >
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
            <%if (errMsgUpdCust != null){%>
                <div class="my-5">
                    <h1 class="text-center text-danger"><%=errMsgUpdCust%></h1>
                </div>
            <%}
                session.removeAttribute("errMsgUpdCust");
            %>

        </form>
    <%} else {%>
        <h1 class="text-center text-primary">Cần thông tin của Khách hàng để cập nhật</h1>
    <%}%>
</div>

<%@ include file="./parts/footer.jsp"%>