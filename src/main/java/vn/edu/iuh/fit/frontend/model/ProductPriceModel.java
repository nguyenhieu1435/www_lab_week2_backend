package vn.edu.iuh.fit.frontend.model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductPrice;
import vn.edu.iuh.fit.backend.services.ProductPriceService;
import vn.edu.iuh.fit.backend.services.ProductService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProductPriceModel {
    private ProductPriceService productPriceService;
    private ProductService productService;
    private final int LIMIT_NUM = 5;
    public ProductPriceModel(){
        this.productPriceService = new ProductPriceService();
        this.productService = new ProductService();
    }
    public void getPriceListByProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long productId = 0;
        try {
            productId = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e){
            resp.sendRedirect("danhSachSanPham.jsp");
            return;
        }
        if (productId < 0){
            resp.sendRedirect("danhSachSanPham.jsp");
            return;
        }
        Product product = productService.findOne(productId).orElse(null);
        req.getSession().setAttribute("titleProductPriceList", "Danh sách giá của sản phẩm: "
                + product.getName() + " ,ID: " + product.getProduct_id());
        List<ProductPrice> productPriceList =
                productPriceService.getProductsByNumPage(productId, 1, LIMIT_NUM) ;
        req.getSession().setAttribute("productPriceList", productPriceList);
        req.getSession().setAttribute("productIdAtProductPrice", productId);
        req.getSession().setAttribute("numPageProductPrice", 1);
        req.getSession().setAttribute("limitNumProductPrice", LIMIT_NUM);
        resp.sendRedirect("danhSachGiaSanPham.jsp");
    }
    public void decreaseProductPrice(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int numPageProductPrice = (int)req.getSession().getAttribute("numPageProductPrice");
        if (numPageProductPrice <= 1){
            resp.sendRedirect("danhSachGiaSanPham.jsp");
            return;
        }
        numPageProductPrice -= 1;
        req.getSession().setAttribute("numPageProductPrice", numPageProductPrice);
        resp.sendRedirect("danhSachGiaSanPham.jsp");
    }
    public void increaseProductPrice(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int numPageProductPrice = (int)req.getSession().getAttribute("numPageProductPrice");
        long productIdAtProductPrice  = (long)req.getSession().getAttribute("productIdAtProductPrice");
        List<ProductPrice> productPriceNextPage = productPriceService.getProductsByNumPage(productIdAtProductPrice, numPageProductPrice+1, LIMIT_NUM);
        if (productPriceNextPage == null || productPriceNextPage.isEmpty()){
            resp.sendRedirect("danhSachGiaSanPham.jsp");
            return;
        }
        List<ProductPrice> productPriceCurrent = (List<ProductPrice>) req.getSession().getAttribute("productPriceList");
        if (productPriceCurrent != null && !productPriceCurrent.contains(productPriceNextPage.get(0))){
            productPriceCurrent.addAll(productPriceNextPage);
        }
        req.getSession().setAttribute("numPageProductPrice", numPageProductPrice+1);
        req.getSession().setAttribute("productPriceList", productPriceCurrent);
        resp.sendRedirect("danhSachGiaSanPham.jsp");
    }
    public void addProductPrice(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long productId = 0;
        String priceStr  = req.getParameter("price").trim();
        double price = 0;
        try {
            price = Double.parseDouble(priceStr);
            productId = Long.parseLong(req.getParameter("productId"));
        } catch (NumberFormatException e){
            req.getSession().setAttribute("errAddProductPrice", "ProductID và Price phải là số!");
            resp.sendRedirect("themGiaSanPham.jsp");
            return;
        }
        if (productId < 0 || price < 0){
            req.getSession().setAttribute("errAddProductPrice", "Product Id và Price phải > 0!");
            resp.sendRedirect("themGiaSanPham.jsp");
            return;
        }
        String note = req.getParameter("note").trim();
        ProductPrice productPrice = new ProductPrice(new Product(productId), note, LocalDateTime.now(), price);

        boolean result = productPriceService.add(productPrice);
        if (result){
            req.getSession().removeAttribute("errAddProductPrice");
            List<ProductPrice> productPriceList =
                    productPriceService.getProductsByNumPage(productId, 1, LIMIT_NUM) ;
            req.getSession().setAttribute("productPriceList", productPriceList);
            req.getSession().setAttribute("numPageProductPrice", 1);
            resp.sendRedirect("danhSachGiaSanPham.jsp");
            return;
        }
        req.getSession().setAttribute("errAddProductPrice", "Thêm thất bại!");
        resp.sendRedirect("themGiaSanPham.jsp");
    }
    public void handleOpenUpdateProductPrice(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        String date = req.getParameter("date");
        LocalDateTime dateTime = LocalDateTime.parse(date);
        ProductPrice productPrice = productPriceService.findOne(id, dateTime).orElse(null);

        req.getSession().setAttribute("productPrice", productPrice);
        resp.sendRedirect("capNhatGiaSanPham.jsp");
    }
    public void updateProductPrice(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long productId = Long.parseLong(req.getParameter("productId").trim());
        LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("dateTimeId").trim());
        double price = Double.parseDouble(req.getParameter("price").trim());
        if (price < 0){
            req.getSession().setAttribute("errUpdProductPrice", "Gía phải >= 0");
            resp.sendRedirect("capNhatGiaSanPham.jsp");
            return;
        }
        String note = (String)req.getParameter("note");
        ProductPrice productPrice = new ProductPrice(new Product(productId), note, localDateTime, price);

        boolean result = productPriceService.update(productPrice);
        if (result){
            req.getSession().removeAttribute("errUpdProductPrice");
            List<ProductPrice> productPriceList =
                    productPriceService.getProductsByNumPage(productId, 1, LIMIT_NUM) ;
            req.getSession().setAttribute("productPriceList", productPriceList);
            req.getSession().setAttribute("numPageProductPrice", 1);
            resp.sendRedirect("danhSachGiaSanPham.jsp");
            return;
        }
        req.getSession().setAttribute("errUpdProductPrice", "Cập nhật thất bại!");
    }
    public void deleteProductPrice(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long productId = Long.parseLong(req.getParameter("id").trim());
        LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("date").trim());

        boolean result = productPriceService.delete(productId, localDateTime);
        if (result){
            req.getSession().setAttribute("errDeleteProductPrice", "Xóa thành công");
            List<ProductPrice> productPriceList =
                    productPriceService.getProductsByNumPage(productId, 1, LIMIT_NUM) ;
            req.getSession().setAttribute("productPriceList", productPriceList);
            req.getSession().setAttribute("numPageProductPrice", 1);
            resp.sendRedirect("danhSachGiaSanPham.jsp");
            return;
        }
        req.getSession().setAttribute("errDeleteProductPrice", "Xóa thất bại!");
    }
}
