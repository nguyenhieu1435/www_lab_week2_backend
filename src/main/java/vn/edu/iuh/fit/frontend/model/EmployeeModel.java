package vn.edu.iuh.fit.frontend.model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.iuh.fit.backend.enums.EmployeeStatus;
import vn.edu.iuh.fit.backend.models.Employee;
import vn.edu.iuh.fit.backend.services.EmployeeService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class EmployeeModel {
    private EmployeeService employeeService;
    private final int LIMIT_NUM = 5;
    public EmployeeModel(){
        this.employeeService = new EmployeeService();
    }

    public void getEmployeeList(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        List<Employee> employees = employeeService.getEmplByPageNum(1, LIMIT_NUM);
        req.getSession().setAttribute("employees", employees);
        req.getSession().setAttribute("limitNumEmp", LIMIT_NUM);
        req.getSession().setAttribute("pageNumEmpl", 1);
        resp.sendRedirect("danhSachNhanSu.jsp");
    }
    public void decreaseEmplPageNum(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        int numPage = (int) req.getSession().getAttribute("pageNumEmpl");
        if (numPage == 1){
            resp.sendRedirect("danhSachNhanSu.jsp");
            return;
        }
        numPage -= 1;
        req.getSession().setAttribute("pageNumEmpl", numPage);
        resp.sendRedirect("danhSachNhanSu.jsp");
    }
    public void increaseEmplPageNum(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        int numPage = (int) req.getSession().getAttribute("pageNumEmpl");
        List<Employee> employeesNextPage = employeeService.getEmplByPageNum(numPage+1, LIMIT_NUM);
        if (employeesNextPage == null || employeesNextPage.isEmpty()){
            resp.sendRedirect("danhSachNhanSu.jsp");
            return;
        }
        List<Employee> employees = (List<Employee>) req.getSession().getAttribute("employees");
        if(!employees.contains(employeesNextPage.get(0))){
            employees.addAll(employeesNextPage);
        }
        req.getSession().setAttribute("pageNumEmpl", numPage+1);
        req.getSession().setAttribute("employees", employees);
        resp.sendRedirect("danhSachNhanSu.jsp");
    }
    public void insertEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fullname = req.getParameter("fullname").trim();
        String email = req.getParameter("email").trim();
        String phone = req.getParameter("phone").trim();
        String address = req.getParameter("address").trim();
        String statusStr = req.getParameter("status").trim();
        String dateOfBirth = req.getParameter("dateOfBirth").trim();

        if (fullname.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || statusStr.isEmpty()
            || dateOfBirth.isEmpty()
        ){
            req.getSession().setAttribute("errMsgAddEmp", "Các field không được để trống!");
            resp.sendRedirect("themNhanSu.jsp");
            return;
        }

        EmployeeStatus employeeStatus = EmployeeStatus.valueOf(statusStr);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateOfBirth, formatter);

        Employee employee = new Employee(fullname, email, phone, address, localDateTime, employeeStatus);
        boolean result = employeeService.add(employee);
        if (result){
            req.getSession().setAttribute("errMsgAddEmp", "");
            getEmployeeList(req, resp);
            return;
        }
        req.getSession().setAttribute("errMsgAddEmp", "Thêm thất bại");
        resp.sendRedirect("themNhanSu.jsp");
    }
    public void handleOpenUpdateEmp(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        Optional<Employee> op = employeeService.findOne(id);
        Employee employee = op.orElse(null);

        req.getSession().setAttribute("employee", employee);
        resp.sendRedirect("capNhatNhanSu.jsp");
    }
    public void updateEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        String fullname = req.getParameter("fullname").trim();
        String email = req.getParameter("email").trim();
        String phone = req.getParameter("phone").trim();
        String address = req.getParameter("address").trim();
        String statusStr = req.getParameter("status").trim();
        String dateOfBirth = req.getParameter("dateOfBirth").trim();

        if (fullname.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || statusStr.isEmpty() || dateOfBirth.isEmpty()){
            req.getSession().setAttribute("errMsgUpdEmp", "Các field không được để trống!");
            resp.sendRedirect("capNhatNhanSu.jsp");
            return;
        }

        EmployeeStatus employeeStatus = EmployeeStatus.valueOf(statusStr);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateOfBirth, formatter);

        Employee employee = new Employee(id, fullname, email, phone, address, localDateTime, employeeStatus);

        boolean result = employeeService.update(employee);
        if (result){
            req.getSession().setAttribute("errMsgUpdEmp", "");
            getEmployeeList(req, resp);
            return;
        }
        req.getSession().setAttribute("errMsgUpdEmp", "Cập nhật thất bại!");
        resp.sendRedirect("capNhatNhanSu.jsp");
    }
    public void deleteEmpl(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));

        boolean result = employeeService.delete(id);
        if (result){
            req.getSession().setAttribute("errMsgDelete", "Xóa thành công!");
            getEmployeeList(req,resp);
            return;
        }
        req.getSession().setAttribute("errMsgDelete","Xóa thất bại");
        resp.sendRedirect("danhSachNhanSu.jsp.jsp");
    }
}
