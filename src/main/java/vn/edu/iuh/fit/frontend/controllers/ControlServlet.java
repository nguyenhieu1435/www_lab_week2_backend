package vn.edu.iuh.fit.frontend.controllers;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.iuh.fit.backend.models.*;
import vn.edu.iuh.fit.backend.services.EmployeeService;
import vn.edu.iuh.fit.frontend.model.*;

import java.io.IOException;

@WebServlet(name = "ControlServlet", value = "/control-servlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50)
public class ControlServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {

    }

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
            case "addNewProduct": {
                ProductModel productModel = new ProductModel();
                productModel.addNewProduct(req, resp);
                break;
            }
            case "updateOneProduct": {
                ProductModel productModel = new ProductModel();
                productModel.updateOneProduct(req, resp);
                break;
            }
            case "addProductPrice": {
                ProductPriceModel productPriceModel = new ProductPriceModel();
                productPriceModel.addProductPrice(req, resp);
                break;
            }
            case "updateProductPrice": {
                ProductPriceModel productPriceModel = new ProductPriceModel();
                productPriceModel.updateProductPrice(req, resp);
                break;
            }
            case "addNewProductImage": {
                ProductImageModel productImageModel = new ProductImageModel();
                productImageModel.addNewProductImage(req, resp);
                break;
            }
            case "updateProductImage": {
                ProductImageModel productImageModel =new ProductImageModel();
                productImageModel.updateProductImage(req, resp);
                break;
            }
            case "addToCart": {
                ProductModel productModel =new ProductModel();
                productModel.addToCart(req, resp);
                break;
            }
            case "getStatisticsOrderByDateRange": {
                OrderModel orderModel = new OrderModel();
                orderModel.getStatisticsOrderByDateRange(req, resp);
                break;
            }
            case "getStatisticsOrderByDateRangeAndEmpId": {
                OrderModel orderModel = new OrderModel();
                orderModel.getStatisticsOrderByDateRangeAndEmpId(req, resp);
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
            // CRUD Product
            case "product_list":{
                ProductModel productModel = new ProductModel();
                productModel.getProductList(req, resp);
                break;
            }
            case "decreaseProductMng": {
                ProductModel productModel = new ProductModel();
                productModel.decreaseProductMng(req, resp);
                break;
            }
            case "increaseProductMng": {
                ProductModel productModel = new ProductModel();
                productModel.increaseProductMng(req, resp);
                break;
            }
            case "updateProductManagement": {
                ProductModel productModel = new ProductModel();
                productModel.updateProductManagement(req, resp);
                break;
            }
            case "deleteProductManagement":{
                ProductModel productModel = new ProductModel();
                productModel.deleteProductManagement(req, resp);
                break;
            }
            case "getPriceListByProduct": {
                ProductPriceModel productPriceModel = new ProductPriceModel();
                productPriceModel.getPriceListByProduct(req, resp);
                break;
            }
            case "decreaseProductPrice": {
                ProductPriceModel productPriceModel = new ProductPriceModel();
                productPriceModel.decreaseProductPrice(req, resp);
                break;
            }
            case "increaseProductPrice": {
                ProductPriceModel productPriceModel = new ProductPriceModel();
                productPriceModel.increaseProductPrice(req, resp);
                break;
            }
            case "handleOpenUpdateProductPrice": {
                ProductPriceModel productPriceModel = new ProductPriceModel();
                productPriceModel.handleOpenUpdateProductPrice(req, resp);
                break;
            }
            case "deleteProductPrice": {
                ProductPriceModel productPriceModel = new ProductPriceModel();
                productPriceModel.deleteProductPrice(req, resp);
                break;
            }
            case "getImgListByProduct": {
                ProductImageModel productImageModel = new ProductImageModel();
                productImageModel.getImgListByProduct(req, resp);
                break;
            }
            case "decreaseProductImage": {
                ProductImageModel productImageModel =new ProductImageModel();
                productImageModel.decreaseProductImage(req, resp);
                break;
            }
            case "increaseProductImage": {
                ProductImageModel productImageModel = new ProductImageModel();
                productImageModel.increaseProductImage(req, resp);
                break;

            }
            case "handleOpenUpdProductImage": {
                ProductImageModel productImageModel = new ProductImageModel();
                productImageModel.handleOpenUpdProductImage(req, resp);
                break;
            }
            case "deleletProductImage": {
                ProductImageModel productImageModel = new ProductImageModel();
                productImageModel.deleletProductImage(req, resp);
                break;
            }
            case "handleOpenProductListClient": {
                ProductModel productModel = new ProductModel();
                productModel.handleOpenProductListClient(req, resp);
                break;

            }
            case "decreaseProductListClient": {
                ProductModel productModel = new ProductModel();
                productModel.decreaseProductListClient(req, resp);
                break;
            }
            case "increaseProductListClient": {
                ProductModel productModel = new ProductModel();
                productModel.increaseProductListClient(req, resp);
                break;
            }
            case "handleOpenCart": {
                ProductModel productModel = new ProductModel();
                productModel.handleOpenCart(req, resp);
                break;
            }
            case "deleteItemFromCart": {
                ProductModel productModel = new ProductModel();
                productModel.deleteItemFromCart(req, resp);
                break;
            }
            case "decreaseItemCart": {
                ProductModel productModel = new ProductModel();
                productModel.decreaseItemCart(req, resp);
                break;
            }
            case "increaseItemCart":{
                ProductModel productModel = new ProductModel();
                productModel.increaseItemCart(req, resp);
                break;
            }
            case "handleOpenGetOrderByDate": {
                OrderModel orderModel = new OrderModel();
                orderModel.handleOpenGetOrderByDate(req, resp);
                break;
            }
            default : {
                resp.sendError(400, "Get Action is invalid");
            }
        }
    }
}
