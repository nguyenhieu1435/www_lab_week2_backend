package vn.edu.iuh.fit.frontend.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.iuh.fit.backend.models.Customer;
import vn.edu.iuh.fit.backend.models.Order;
import vn.edu.iuh.fit.backend.models.OrderDetail;
import vn.edu.iuh.fit.backend.services.EmployeeService;
import vn.edu.iuh.fit.frontend.model.CustomerModel;
import vn.edu.iuh.fit.frontend.model.EmployeeModel;
import vn.edu.iuh.fit.frontend.model.OrderDetailModel;
import vn.edu.iuh.fit.frontend.model.OrderModel;

import java.io.IOException;

@WebServlet(name = "ControlServlet", value = "/control-servlet")
public class ControlServlet extends HttpServlet {
    public ControlServlet(){
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        System.out.println(action);

        switch (action){
            case "insertEmployee": {
                EmployeeModel employeeModel = new EmployeeModel();
                employeeModel.insertEmployee(req, resp);
                break;
            }
            case "updateEmployee": {
                EmployeeModel employeeModel = new EmployeeModel();
                employeeModel.updateEmployee(req, resp);
                break;
            }
            case "addCustomer": {
                CustomerModel customerModel = new CustomerModel();
                customerModel.addCustomer(req, resp);
                break;
            }
            case "updateCustomer":{
                CustomerModel customerModel = new CustomerModel();
                customerModel.updateCustomer(req, resp);
                break;
            }
            // CRUD Order
            case "addOrder": {
                OrderModel orderModel = new OrderModel();
                orderModel.addOrder(req, resp);
                break;
            }
            case "updateOrder": {
                OrderModel orderModel = new OrderModel();
                orderModel.updateOrder(req, resp);
                break;
            }
            case "addOrderDetail": {
                OrderDetailModel orderDetailModel = new OrderDetailModel();
                orderDetailModel.addOrderDetail(req, resp);
                break;
            }
            case "updateOrderDetail": {
                OrderDetailModel orderDetailModel = new OrderDetailModel();
                orderDetailModel.updateOrderDetail(req, resp);
                break;
            }
            default: {
                resp.sendError(400, "Post action is invalid");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        System.out.println(action);
        switch (action){
            // CRUD Employee
            case "emp_list": {
                EmployeeModel employeeModel = new EmployeeModel();
                employeeModel.getEmployeeList(req, resp);
                break;
            }
            case "decreaseEmplPageNum":{
                EmployeeModel employeeModel = new EmployeeModel();
                employeeModel.decreaseEmplPageNum(req, resp);
                break;
            }
            case "increaseEmplPageNum":{
                EmployeeModel employeeModel = new EmployeeModel();
                employeeModel.increaseEmplPageNum(req, resp);
                break;
            }
            case "handleOpenUpdateEmp": {
                EmployeeModel employeeModel = new EmployeeModel();
                employeeModel.handleOpenUpdateEmp(req, resp);
                break;
            }
            case "deleteEmpl":{
                EmployeeModel employeeModel = new EmployeeModel();
                employeeModel.deleteEmpl(req, resp);
                break;
            }
            // Customer CRUD
            case "cust_list": {
                CustomerModel customerModel = new CustomerModel();
                customerModel.getCustomerList(req, resp);
                break;
            }
            case "decreaseCustPageNum":{
                CustomerModel customerModel = new CustomerModel();
                customerModel.decreaseCustPageNum(req, resp);
                break;
            }
            case "increaseCustPageNum": {
                CustomerModel customerModel = new CustomerModel();
                customerModel.increaseCustPageNum(req, resp);
                break;
            }
            case "handleOpenUpdCust": {
                CustomerModel customerModel = new CustomerModel();
                customerModel.handleOpenUpdCust(req, resp);
                break;
            }
            case "deleteCustomer": {
                CustomerModel customerModel = new CustomerModel();
                customerModel.deleteCustomer(req, resp);
                break;
            }
            // Order CRUD
            case "order_list": {
                OrderModel orderModel = new OrderModel();
                orderModel.getOrderList(req, resp);
                break;
            }
            case "decreaseOrderPageNum": {
                OrderModel orderModel = new OrderModel();
                orderModel.decreaseOrderPageNum(req, resp);
                break;
            }
            case "increaseOrderPageNum": {
                OrderModel orderModel = new OrderModel();
                orderModel.increaseOrderPageNum(req, resp);
                break;
            }
            case "handleOpenOrderUpdate": {
                OrderModel orderModel = new OrderModel();
                orderModel.handleOpenOrderUpdate(req, resp);
                break;
            }
            case "deleteOrder": {
                OrderModel orderModel = new OrderModel();
                orderModel.deleteOrder(req, resp);
                break;
            }
            case "handleOpenOrderDetail": {
                OrderDetailModel orderDetailModel = new OrderDetailModel();
                orderDetailModel.handleOpenOrderDetail(req, resp);
                break;
            }
            case "decreaseOrderDetailPageNum": {
                OrderDetailModel orderDetailModel = new OrderDetailModel();
                orderDetailModel.decreaseOrderDetailPageNum(req, resp);
                break;
            }
            case "increaseOrderDetailPageNum": {
                OrderDetailModel orderDetailModel = new OrderDetailModel();
                orderDetailModel.increaseOrderDetailPageNum(req, resp);
                break;
            }
            case "handleOpenUpdateDetailPage": {
                OrderDetailModel orderDetailModel = new OrderDetailModel();
                orderDetailModel.handleOpenUpdateDetailPage(req, resp);
                break;
            }
            case "deleteOrderDetail": {
                OrderDetailModel orderDetailModel = new OrderDetailModel();
                orderDetailModel.deleteOrderDetail(req, resp);
                break;
            }
            default : {
                resp.sendError(400, "Get Action is invalid");
            }
        }
    }
}
