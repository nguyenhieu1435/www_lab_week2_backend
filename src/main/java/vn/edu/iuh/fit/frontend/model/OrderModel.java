package vn.edu.iuh.fit.frontend.model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.iuh.fit.backend.models.Customer;
import vn.edu.iuh.fit.backend.models.Employee;
import vn.edu.iuh.fit.backend.models.Order;
import vn.edu.iuh.fit.backend.models.OrderDetail;
import vn.edu.iuh.fit.backend.services.CustomerService;
import vn.edu.iuh.fit.backend.services.EmployeeService;
import vn.edu.iuh.fit.backend.services.OrderDetailService;
import vn.edu.iuh.fit.backend.services.OrderService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class OrderModel {
    private OrderService orderService;
    private CustomerService customerService;
    private EmployeeService employeeService;
    private final int LIMIT_NUM = 5;

    public OrderModel(){
        this.orderService = new OrderService();
        this.customerService = new CustomerService();
        this.employeeService = new EmployeeService();
    }

    public void getOrderList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Order> orders = orderService.getOrderByPageNum(1, LIMIT_NUM);
        req.getSession().setAttribute("orders", orders);
        req.getSession().setAttribute("limitNumOrder", LIMIT_NUM);
        req.getSession().setAttribute("pageNumOrder", 1);

        resp.sendRedirect("danhSachHoaDon.jsp");
    }
    public void decreaseOrderPageNum(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int numPage = (int)req.getSession().getAttribute("pageNumOrder");
        if (numPage <= 1){
            resp.sendRedirect("danhSachHoaDon.jsp");
            return;
        }
        numPage -= 1;
        req.getSession().setAttribute("pageNumOrder", numPage);
        resp.sendRedirect("danhSachHoaDon.jsp");
    }
    public void increaseOrderPageNum(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        int numPage = (int)req.getSession().getAttribute("pageNumOrder");
        List<Order> newOrderNextPage = orderService.getOrderByPageNum(numPage+1, LIMIT_NUM);
        if (newOrderNextPage == null || newOrderNextPage.isEmpty()){
            resp.sendRedirect("danhSachHoaDon.jsp");
            return;
        }
        List<Order> orders = (List<Order>) req.getSession().getAttribute("orders");
        if (!orders.contains(newOrderNextPage.get(0))){
            orders.addAll(newOrderNextPage);
        }
        req.getSession().setAttribute("orders", orders);
        req.getSession().setAttribute("pageNumOrder", numPage+1);
        resp.sendRedirect("danhSachHoaDon.jsp");

    }
    public void addOrder(HttpServletRequest req, HttpServletResponse resp)throws IOException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        String createDateStr = req.getParameter("createDate").trim();
        String customerIdStr = req.getParameter("customerID").trim();
        String employeeIdStr = req.getParameter("employeeID").trim();
        if (createDateStr.isEmpty() || customerIdStr.isEmpty() || employeeIdStr.isEmpty()){
            req.getSession().setAttribute("errMsgAddOrder", "Các field không được để trống");
            resp.sendRedirect("themHoaDon.jsp");
            return;
        }
        Long employeeId = null, customerId = null;
        try {
            employeeId = Long.parseLong(employeeIdStr);
            customerId = Long.parseLong(customerIdStr);

        } catch (Exception e){
            req.getSession().setAttribute("errMsgAddOrder", "CustomerID và EmployeeID phải là số nguyên!");
            resp.sendRedirect("themHoaDon.jsp");
            return;
        }
        Optional<Customer>  op = customerService.findOne(customerId);
        if (!op.isPresent()){
            req.getSession().setAttribute("errMsgAddOrder", "Customer ID không tồn tại!");
            resp.sendRedirect("themHoaDon.jsp");
            return;
        }
        Optional<Employee> op2 = employeeService.findOne(employeeId);
        if (! op2.isPresent()){
            req.getSession().setAttribute("errMsgAddOrder", "EmployeeID không tồn tại!!");
            resp.sendRedirect("themHoaDon.jsp");
            return;
        }
        LocalDateTime creationDate = LocalDateTime.parse(createDateStr, formatter);
        Order order = new Order(creationDate, new Customer(customerId), new Employee(employeeId));
        boolean result = orderService.add(order);
        if (result){
            req.getSession().setAttribute("errMsgAddOrder", "");
            getOrderList(req, resp);
            return;
        }
        req.getSession().setAttribute("errMsgAddOrder", "Thêm thất bại!");
        resp.sendRedirect("themHoaDon.jsp");
    }
    public void handleOpenOrderUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long idOrder = Long.parseLong(req.getParameter("id"));
        Optional<Order> op = orderService.findOne(idOrder);
        Order order = op.orElse(null);
        req.getSession().setAttribute("order", order);
        resp.sendRedirect("capNhatHoaDon.jsp");

    }
    public void updateOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        String orderIdStr = req.getParameter("orderID").trim();
        String createDateStr = req.getParameter("createDate").trim();
        String customerIdStr = req.getParameter("customerID").trim();
        String employeeIdStr = req.getParameter("employeeID").trim();
        if (orderIdStr.isEmpty() || createDateStr.isEmpty() || customerIdStr.isEmpty() || employeeIdStr.isEmpty()){
            req.getSession().setAttribute("errMsgUpdOrder", "Các field không được để trống!");
            resp.sendRedirect("capNhatHoaDon.jsp");
            return;
        }
        Long employeeId = null, customerId = null, orderId = null;
        try {
            employeeId = Long.parseLong(employeeIdStr);
            customerId = Long.parseLong(customerIdStr);
            orderId = Long.parseLong(orderIdStr);
        } catch (Exception e){
            req.getSession().setAttribute("errMsgUpdOrder", "OrderID, CustomerID và EmployeeID phải là số nguyên!");
            resp.sendRedirect("capNhatHoaDon.jsp");
            return;
        }
        Optional<Customer> opCus= customerService.findOne(customerId);
        if (opCus.isEmpty()){
            req.getSession().setAttribute("errMsgUpdOrder", "Customer Id này không tồn tại!");
            resp.sendRedirect("capNhatHoaDon.jsp");
            return;
        }
        Optional<Employee> opEmp = employeeService.findOne(employeeId);
        if (opEmp.isEmpty()){
            req.getSession().setAttribute("errMsgUpdOrder", "Employee Id này không tồn tại!");
            resp.sendRedirect("capNhatHoaDon.jsp");
            return;
        }
        LocalDateTime creationDate = LocalDateTime.parse(createDateStr, formatter);

        Order order = new Order(orderId, creationDate, new Customer(customerId), new Employee(employeeId));
        boolean resullt = orderService.update(order);
        if (resullt){
            req.getSession().removeAttribute("errMsgUpdOrder");
            getOrderList(req, resp);
            return;
        }
        req.getSession().setAttribute("errMsgUpdOrder", "Thêm thất bại!");
        resp.sendRedirect("capNhatHoaDon.jsp");
        return;
    }
    public void deleteOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        boolean result = orderService.delete(id);
        if (result){
            req.getSession().setAttribute("errMsgDelOrder", "Xóa thành công!");
            getOrderList(req, resp);
            return;
        }
        req.getSession().setAttribute("errMsgDelOrder", "Xóa thành công!");
        resp.sendRedirect("danhSachHoaDon.jsp");
    }
}
