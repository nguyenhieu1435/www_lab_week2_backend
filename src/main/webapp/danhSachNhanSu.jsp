<%@ page import="vn.edu.iuh.fit.backend.models.Employee" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 27/09/2023
  Time: 5:41 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>
<%
    List<Employee> employees = (List<Employee>) session.getAttribute("employees");
    int pageNumEmpl = (int) session.getAttribute("pageNumEmpl");
    int limitNumEmp = (int) session.getAttribute("limitNumEmp");
    String errMsgDelete = (String)session.getAttribute("errMsgDelete");
%>
<div>
    <%if (employees == null || employees.isEmpty()) {%>
        <h1 class="text-center">Chưa có nhân viên nào</h1>
    <%} else {%>
        <table class="table table-bordered table-hover">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Full Name</th>
                <th scope="col">Date Of Birth</th>
                <th scope="col">Email</th>
                <th scope="col">Phone</th>
                <th scope="col">Address</th>
                <th scope="col">Status</th>
                <th scope="col" class="text-center">
                    <a href="themNhanSu.jsp" class="btn btn-success px-5">Insert</a>
                </th>
            </tr>
            </thead>
            <tbody>
                <%for (int i = (pageNumEmpl - 1)*limitNumEmp; i < (Math.min(employees.size(), pageNumEmpl * limitNumEmp)); i++){%>
                    <tr>
                        <th scope="row"><%=employees.get(i).getId()%></th>
                        <td><%=employees.get(i).getFullname()%></td>
                        <td><%=employees.get(i).getDob()%></td>
                        <td><%=employees.get(i).getEmail()%></td>
                        <td><%=employees.get(i).getPhone()%></td>
                        <td><%=employees.get(i).getAddress()%></td>
                        <td><%=employees.get(i).getStatus().toString()%></td>
                        <td class="text-center">
                            <a class="btn btn-warning" href="control-servlet?action=handleOpenUpdateEmp&id=<%=employees.get(i).getId()%>">Update</a>
                            <a class="btn btn-danger" href="control-servlet?action=deleteEmpl&id=<%=employees.get(i).getId()%>">Delete</a>
                        </td>
                    </tr>
                <%}%>
            </tbody>
        </table>
        <div class="d-flex justify-content-center align-items-center">
            <%if (pageNumEmpl > 1){%>
                <a class="btn btn-primary" href="control-servlet?action=decreaseEmplPageNum">Prev</a>
            <%}%>
            <span class="mx-3"><%=pageNumEmpl%></span>
            <a class="btn btn-primary" href="control-servlet?action=increaseEmplPageNum">Next</a>
        </div>

        <%if (errMsgDelete != null && !errMsgDelete.isEmpty()){%>
            <div class="my-5">
                <h1 class="text-primary text-center"><%=errMsgDelete%></h1>
            </div>
        <%}
            session.removeAttribute("errMsgDelete");
        %>
    <%}%>
</div>
<%@ include file="./parts/footer.jsp"%>

