package vn.edu.iuh.fit.frontend.model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.iuh.fit.backend.enums.ProductStatus;
import vn.edu.iuh.fit.backend.models.Order;
import vn.edu.iuh.fit.backend.models.OrderDetail;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductPrice;
import vn.edu.iuh.fit.backend.services.OrderDetailService;
import vn.edu.iuh.fit.backend.services.ProductPriceService;
import vn.edu.iuh.fit.backend.services.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class OrderDetailModel {
    private OrderDetailService orderDetailService;
    private ProductService productService;
    private ProductPriceService productPriceService;
    private final int LIMIT_NUM = 5;

    public OrderDetailModel(){
        this.orderDetailService = new OrderDetailService();
        this.productService = new ProductService();
        this.productPriceService = new ProductPriceService();
    }
    public void handleOpenOrderDetail(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailByNumPage(id, 1, LIMIT_NUM);

        req.getSession().setAttribute("orderIdOfOrderDetail", id);
        req.getSession().setAttribute("orderdetails", orderDetails);
        req.getSession().setAttribute("limitNumOrderDetail", LIMIT_NUM);
        req.getSession().setAttribute("pageNumOrderDetail", 1);
        resp.sendRedirect("danhSachChiTietHoaDon.jsp");
    }
    public void decreaseOrderDetailPageNum(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long numPage = (long) req.getSession().getAttribute("pageNumOrderDetail");
        if (numPage <= 1){
            resp.sendRedirect("danhSachChiTietHoaDon.jsp");
            return;
        }
        numPage -= 1;
        req.getSession().setAttribute("pageNumOrderDetail", numPage);
        resp.sendRedirect("danhSachChiTietHoaDon.jsp");
    }
    public void increaseOrderDetailPageNum(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int numPage = (int)req.getSession().getAttribute("pageNumOrderDetail");
        long orderID = (long) req.getSession().getAttribute("orderIdOfOrderDetail");
        List<OrderDetail> newNextOrderDetail = orderDetailService.getOrderDetailByNumPage(orderID, numPage+1, LIMIT_NUM);
        if (newNextOrderDetail == null || newNextOrderDetail.isEmpty()){
            resp.sendRedirect("danhSachChiTietHoaDon.jsp");
            return;
        }
        List<OrderDetail> orderDetails = (List<OrderDetail>) req.getSession().getAttribute("orderdetails");
        if (!orderDetails.contains(newNextOrderDetail.get(0))){
            orderDetails.addAll(newNextOrderDetail);
        }
        req.getSession().setAttribute("pageNumOrderDetail", numPage+1);
        req.getSession().setAttribute("orderdetails", orderDetails);
        resp.sendRedirect("danhSachChiTietHoaDon.jsp");
    }
    public void addOrderDetail(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String orderIdStr = (String) req.getParameter("orderID").trim();
        String productIdStr = (String) req.getParameter("productID").trim();
        String quantityStr = (String)req.getParameter("quantity").trim();
        String noteStr = (String) req.getParameter("note").trim();
        if (orderIdStr.isEmpty() || productIdStr.isEmpty() || quantityStr.isEmpty()){
            req.getSession().setAttribute("errMsgAddOrderDetail", "3 field đầu không được để trống!");
            resp.sendRedirect("themChiTietHoaDon.jsp");
            return;
        }
        long orderID, productId;
        double quantity;
        try {
            orderID = Long.parseLong(orderIdStr);
            productId = Long.parseLong(productIdStr);
            quantity = Integer.parseInt(quantityStr);
        } catch (Exception e){
            req.getSession().setAttribute("errMsgAddOrderDetail", "3 field đầu phải là số nguyên dương!");
            resp.sendRedirect("themChiTietHoaDon.jsp");
            return;
        }
        if (orderID <= 0 || productId <= 0 || quantity <= 0){
            req.getSession().setAttribute("errMsgAddOrderDetail", "3 field đầu phải là số nguyên dương!");
            resp.sendRedirect("themChiTietHoaDon.jsp");
            return;
        }
        Optional<Product> opPro = productService.findOne(productId);
        if (opPro.isEmpty() || opPro.get().getStatus() != ProductStatus.DoingBusiness){
            req.getSession().setAttribute("errMsgAddOrderDetail", "Không có Product Id này!");
            resp.sendRedirect("themChiTietHoaDon.jsp");
            return;
        }
        Optional<OrderDetail> opOD = orderDetailService.findOne(orderID, productId);
        if (opOD.isPresent()){
            req.getSession().setAttribute("errMsgAddOrderDetail", "Sản phẩm này đã có trong chi tiết hóa đơn!");
            resp.sendRedirect("themChiTietHoaDon.jsp");
            return;
        }
        Optional<ProductPrice> opPPrice = productPriceService.getNewestProductPriceByProductId(orderID);
        ProductPrice productPrice = null;
        if (opPPrice.isPresent()) {
            productPrice = opPPrice.get();
        } else {
            req.getSession().setAttribute("errMsgAddOrderDetail", "Thêm chi tiết hóa đơn thất bại!");
            resp.sendRedirect("themChiTietHoaDon.jsp");
            return;
        }
        OrderDetail orderDetail = new OrderDetail(new Product(productId)
                , new Order(orderID), productPrice.getPrice()*quantity, quantity, noteStr);

        boolean result = orderDetailService.add(orderDetail);
        if (result){
            req.getSession().removeAttribute("errMsgAddOrderDetail");
            List<OrderDetail> orderDetails = orderDetailService.getOrderDetailByNumPage(orderID, 1, LIMIT_NUM);
            req.getSession().setAttribute("orderdetails", orderDetails);
            req.getSession().setAttribute("limitNumOrderDetail", LIMIT_NUM);
            req.getSession().setAttribute("pageNumOrderDetail", 1);
            resp.sendRedirect("danhSachChiTietHoaDon.jsp");
            return;
        }
        req.getSession().setAttribute("errMsgAddOrderDetail", "Thêm chi tiết hóa đơn thất bại!");
        resp.sendRedirect("themChiTietHoaDon.jsp");
    }
    public void handleOpenUpdateDetailPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long orderId = Long.parseLong(req.getParameter("orderId"));
        long productId = Long.parseLong(req.getParameter("productId"));
        Optional<OrderDetail> opOD = orderDetailService.findOne(orderId,productId);

        OrderDetail orderDetail = opOD.orElse(null);
        req.getSession().setAttribute("orderdetail", orderDetail);
        resp.sendRedirect("capNhatChiTietHoaDon.jsp");
    }
    public void updateOrderDetail(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String orderIdStr = (String) req.getParameter("orderID").trim();
        String productIdStr = (String) req.getParameter("productID").trim();
        String quantityStr = (String)req.getParameter("quantity").trim();
        String noteStr = (String) req.getParameter("note").trim();
        if (orderIdStr.isEmpty() || productIdStr.isEmpty() || quantityStr.isEmpty()){
            req.getSession().setAttribute("errMsgUpdOrderDetail", "3 field đầu không được để trống!");
            resp.sendRedirect("capNhatChiTietHoaDon.jsp");
            return;
        }
        long orderID, productId;
        double quantity;
        try {
            orderID = Long.parseLong(orderIdStr);
            productId = Long.parseLong(productIdStr);
            quantity = Integer.parseInt(quantityStr);
        } catch (Exception e){
            req.getSession().setAttribute("errMsgUpdOrderDetail", "3 field đầu phải là số nguyên dương!");
            resp.sendRedirect("capNhatChiTietHoaDon.jsp");
            return;
        }
        if (orderID <= 0 || productId <= 0 || quantity <= 0){
            req.getSession().setAttribute("errMsgUpdOrderDetail", "3 field đầu phải là số nguyên dương!");
            resp.sendRedirect("capNhatChiTietHoaDon.jsp");
            return;
        }
        Optional<Product> opPro = productService.findOne(productId);
        if (opPro.isEmpty()){
            req.getSession().setAttribute("errMsgUpdOrderDetail", "Không có Product Id này!");
            resp.sendRedirect("capNhatChiTietHoaDon.jsp");
            return;
        }
        Optional<ProductPrice> opPPrice = productPriceService.getNewestProductPriceByProductId(orderID);
        ProductPrice productPrice = null;
        if (opPPrice.isPresent()) {
            productPrice = opPPrice.get();
        } else {
            req.getSession().setAttribute("errMsgUpdOrderDetail", "Cập nhật thất bại!");
            resp.sendRedirect("capNhatChiTietHoaDon.jsp");
            return;
        }
        OrderDetail orderDetail = new OrderDetail(new Product(productId)
                , new Order(orderID), productPrice.getPrice()*quantity, quantity, noteStr);

        boolean result = orderDetailService.update(orderDetail);
        if (result){
            req.getSession().removeAttribute("errMsgUpdOrderDetail");
            List<OrderDetail> orderDetails = orderDetailService.getOrderDetailByNumPage(orderID, 1, LIMIT_NUM);
            req.getSession().setAttribute("orderdetails", orderDetails);
            req.getSession().setAttribute("limitNumOrderDetail", LIMIT_NUM);
            req.getSession().setAttribute("pageNumOrderDetail", 1);
            resp.sendRedirect("danhSachChiTietHoaDon.jsp");
            return;
        }
        req.getSession().setAttribute("errMsgUpdOrderDetail", "Cập nhật thất bại!");
        resp.sendRedirect("capNhatChiTietHoaDon.jsp");
    }
    public void deleteOrderDetail(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long orderId = Long.parseLong(req.getParameter("orderId"));
        long productId = Long.parseLong(req.getParameter("productId"));

        boolean result = orderDetailService.delete(orderId, productId);
        if (result){
            List<OrderDetail> orderDetails = orderDetailService.getOrderDetailByNumPage(orderId, 1, LIMIT_NUM);
            req.getSession().setAttribute("orderdetails", orderDetails);
            req.getSession().setAttribute("limitNumOrderDetail", LIMIT_NUM);
            req.getSession().setAttribute("pageNumOrderDetail", 1);
            req.getSession().setAttribute("errMsgDelOrderDetail", "Xóa thành công!");
            resp.sendRedirect("danhSachChiTietHoaDon.jsp");
            return;
        }
        req.getSession().setAttribute("errMsgDelOrderDetail", "Xóa thất bại");
        resp.sendRedirect("danhSachChiTietHoaDon.jsp");
    }
}
