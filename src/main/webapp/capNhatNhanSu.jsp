<%@ page import="vn.edu.iuh.fit.backend.enums.EmployeeStatus" %>
<%@ page import="vn.edu.iuh.fit.backend.models.Employee" %>
<%@ page import="java.time.format.DateTimeFormatter" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 28/09/2023
  Time: 12:10 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./parts/header.jsp"%>
<%@ include file="./parts/navbar.jsp"%>

<%
    Employee employee = (Employee) session.getAttribute("employee");
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    String errMsgUpdEmp = (String) session.getAttribute("errMsgUpdEmp");
%>

<div class="container">
    <%if (employee != null) {%>
        <form action="control-servlet?action=updateEmployee" method="POST">
            <div class="mb-3">
                <label for="inputId" class="form-label">Id</label>
                <input type="text" class="form-control" id="inputId"
                       readonly
                       name="id"
                       value="<%=employee.getId()%>"
                >
            </div>
            <div class="mb-3">
                <label for="inputFullName" class="form-label">Fullname</label>
                <input type="text" class="form-control" id="inputFullName"
                       name="fullname"
                       value="<%=employee.getFullname()%>"
                >
            </div>
            <div class="mb-3">
                <label for="inputEmail" class="form-label">Email</label>
                <input type="email" class="form-control" id="inputEmail"
                       name="email"
                       value="<%=employee.getEmail()%>"
                >
            </div>
            <div class="mb-3">
                <label for="inputPhone" class="form-label">Phone</label>
                <input type="text" class="form-control" id="inputPhone"
                       name="phone"
                       value="<%=employee.getPhone()%>"
                >
            </div>
            <div class="mb-3">
                <label for="inputAddress" class="form-label">Address</label>
                <input type="text" class="form-control" id="inputAddress"
                       name="address"
                       value="<%=employee.getAddress()%>"
                >
            </div>
            <div class="mb-3">
                <label class="form-label" for="inputStatus">Status</label>
                <select class="form-select form-select-md" aria-label=".form-select-sm" id="inputStatus"
                        name="status"
                >
                    <%for (int i = 0; i < EmployeeStatus.values().length; i++){%>
                    <option <%=i == 0 ? "selected" : ""%> value="<%=EmployeeStatus.values()[i]%>"
                        <%=EmployeeStatus.values()[i].getNumberStatus() == employee.getStatus().getNumberStatus() ? "selected" : ""%>
                    ><%=EmployeeStatus.values()[i]%></option>
                    <%}%>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label" for="inputDOB">Date Of Birth</label>
                <div class="input-group date" id="datetimepicker2" data-target-input="nearest">
                    <input type="text" class="form-control datetimepicker-input" id="inputDOB" data-target="#datetimepicker2"
                           name="dateOfBirth"
                    />
                    <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
                        <div class="input-group-text"><i class="fas fa-calendar"></i></div>
                    </div>
                </div>
            </div>

            <button type="submit" class="btn btn-primary">Submit</button>

            <%if (errMsgUpdEmp != null){%>
                <div>
                    <h1 class="text-center text-danger"><%=errMsgUpdEmp%></h1>
                </div>
            <%}
                session.removeAttribute("errMsgUpdEmp");
            %>
        </form>
    <%}%>
</div>

<script type="text/javascript">
    $("#datetimepicker2").datetimepicker({
        format: "MM/DD/YYYY HH:mm:ss",
        defaultDate: new Date("<%=employee != null ? employee.getDob().format(outputFormatter) : ""%>"),
        icons: {
            time: "fas fa-clock",
            date: "fas fa-calendar",
            up: "fas fa-arrow-up",
            down: "fas fa-arrow-down",
            previous: "fas fa-chevron-left",
            next: "fas fa-chevron-right",
            today: "fas fa-calendar-check-o",
            clear: "fas fa-trash",
            close: "fas fa-times"
        }
    });
    $("#datetimepicker2").on(
        "change.datetimepicker blur.datetimepicker keyup.datetimepicker keydown.datetimepicker focus.datetimepicker click.datetimepicker update.datetimepicker error.datetimepicker hide.datetimepicker show.datetimepicker",
        function(e) {
            console.log("event:", e);
        }
    );
</script>

<%@ include file="./parts/footer.jsp"%>