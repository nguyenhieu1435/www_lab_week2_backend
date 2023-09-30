package vn.edu.iuh.fit.frontend.model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.iuh.fit.backend.models.Customer;
import vn.edu.iuh.fit.backend.services.CustomerService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CustomerModel {
    private final int LIMIT_NUM = 5;
    private CustomerService customerService;

    public CustomerModel(){
        this.customerService = new CustomerService();
    }
    public void getCustomerList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Customer> customers = customerService.getCustByNumPage(1, LIMIT_NUM);
        req.getSession().setAttribute("customers", customers);
        req.getSession().setAttribute("limitNumCust", LIMIT_NUM);
        req.getSession().setAttribute("pageNumCust", 1);
        resp.sendRedirect("danhSachKhachHang.jsp");
    }
    public void decreaseCustPageNum(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        int pageNumCust = (int)req.getSession().getAttribute("pageNumCust");
        if (pageNumCust <= 1){
            resp.sendRedirect("danhSachKhachHang.jsp");
            return;
        }
        req.getSession().setAttribute("pageNumCust", pageNumCust-1);
        resp.sendRedirect("danhSachKhachHang.jsp");
    }
    public void increaseCustPageNum(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int pageNumCust = (int)req.getSession().getAttribute("pageNumCust");
        List<Customer> newCustomers = customerService.getCustByNumPage(pageNumCust+1, LIMIT_NUM);
        if (newCustomers == null || newCustomers.isEmpty()){
            resp.sendRedirect("danhSachKhachHang.jsp");
            return;
        }
        req.getSession().setAttribute("pageNumCust", pageNumCust+1);
        List<Customer> customers = (List<Customer>) req.getSession().getAttribute("customers");
        if (!customers.contains(newCustomers.get(0))){
            customers.addAll(newCustomers);
        }
        req.getSession().setAttribute("customers", customers);
        resp.sendRedirect("danhSachKhachHang.jsp");
    }
    public void handleOpenUpdCust(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        Optional<Customer> op = customerService.findOne(id);
        Customer customer = op.orElse(null);

        req.getSession().setAttribute("customer", customer);
        resp.sendRedirect("capNhatKhachHang.jsp");
    }
    public void addCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name").trim();
        String address = req.getParameter("address").trim();
        String email = req.getParameter("email").trim();
        String phone = req.getParameter("phone").trim();

        if (name.isEmpty() || address.isEmpty() || email.isEmpty() || phone.isEmpty()){
            req.getSession().setAttribute("errMsgAddCust", "Các field không được để trống!");
            resp.sendRedirect("themKhachHang.jsp");
            return;
        }
        Customer customer = new Customer(name, address, phone, email);
        boolean result = customerService.add(customer);
        if (result){
            req.getSession().removeAttribute("errMsgAddCust");
            getCustomerList(req, resp);
            return;
        }
        req.getSession().setAttribute("errMsgAddCust", "Thêm thất bại");
        resp.sendRedirect("themKhachHang.jsp");
    }
    public void updateCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name").trim();
        String address = req.getParameter("address").trim();
        String email = req.getParameter("email").trim();
        String phone = req.getParameter("phone").trim();

        if (name.isEmpty() || address.isEmpty() || email.isEmpty() || phone.isEmpty()){
            req.getSession().setAttribute("errMsgUpdCust", "Các trường không được để trống!");
            resp.sendRedirect("capNhatKhachHang.jsp");
            return;
        }

        Customer customer = new Customer(id, name, address, phone, email);
        boolean result = customerService.update(customer);
        if (result){
            req.getSession().removeAttribute("errMsgUpdCust");
            getCustomerList(req, resp);
            return;
        }
        req.getSession().setAttribute("errMsgUpdCust", "Cập nhật thất bại!");
        resp.sendRedirect("capNhatKhachHang.jsp");
    }
    public void deleteCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        boolean result = customerService.delete(id);
        if (result){
            req.getSession().setAttribute("errMsgDelCust", "Xóa thành công!");
            getCustomerList(req, resp);
            return;
        }
        req.getSession().setAttribute("errMsgDelCust", "Xóa thất bại!");
        resp.sendRedirect("danhSachKhachHang.jsp");
    }
}
