<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 28/09/2023
  Time: 4:02 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>

<%
    String errMsgAddCust = (String)session.getAttribute("errMsgAddCust");
%>

<div class="container">
    <form action="control-servlet?action=addCustomer" method="POST">
        <div class="mb-3">
            <label for="inputFullName" class="form-label">Name</label>
            <input type="text" class="form-control" id="inputFullName"
                   name="name"
            >
        </div>
        <div class="mb-3">
            <label for="inputAddress" class="form-label">Address</label>
            <input type="text" class="form-control" id="inputAddress"
                   name="address"
            >
        </div>
        <div class="mb-3">
            <label for="inputEmail" class="form-label">Email</label>
            <input type="email" class="form-control" id="inputEmail"
                   name="email"
            >
        </div>
        <div class="mb-3">
            <label for="inputPhone" class="form-label">Phone</label>
            <input type="text" class="form-control" id="inputPhone"
                   name="phone"
            >
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
        <%if (errMsgAddCust != null){%>
        <div class="my-5">
            <h1 class="text-center text-danger"><%=errMsgAddCust%></h1>
        </div>
        <%}
            session.removeAttribute("errMsgAddCust");
        %>

    </form>
</div>