package vn.edu.iuh.fit.frontend.model;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import org.apache.commons.io.FileUtils;
import vn.edu.iuh.fit.backend.enums.ProductStatus;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductImage;
import vn.edu.iuh.fit.backend.models.ProductPrice;
import vn.edu.iuh.fit.backend.services.ProductPriceService;
import vn.edu.iuh.fit.backend.services.ProductService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;


public class ProductModel {
    private ProductService productService;
    private ProductPriceService productPriceService;

    private Dotenv dotenv;

    private final int LIMIT_NUM = 5;

    public ProductModel(){
        this.productService = new ProductService();
        this.productPriceService = new ProductPriceService();
    }
    public void getProductList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Product> productsOfManagement = productService.getProductByPageNum(1,LIMIT_NUM);
        List<ProductPrice> productPricesOfManagement = new ArrayList<>();

        for(Product product : productsOfManagement){
            product.getProductImageList();
            Optional<ProductPrice> opPP = productPriceService.getNewestProductPriceByProductId(product.getProduct_id());
            productPricesOfManagement.add(opPP.orElse(null));
        }
        req.getSession().setAttribute("productsOfManagement", productsOfManagement);
        req.getSession().setAttribute("productPricesOfManagement", productPricesOfManagement);
        req.getSession().setAttribute("numPageProductManagement", 1);
        req.getSession().setAttribute("numLimitProductManagement", LIMIT_NUM);
        resp.sendRedirect("danhSachSanPham.jsp");
    }
    public void decreaseProductMng(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int numPageProductManagement = (int)req.getSession().getAttribute("numPageProductManagement");
        if (numPageProductManagement <= 1){
            resp.sendRedirect("danhSachSanPham.jsp");
            return;
        }
        numPageProductManagement -= 1;
        req.getSession().setAttribute("numPageProductManagement", numPageProductManagement);
        resp.sendRedirect("danhSachSanPham.jsp");
    }
    public void increaseProductMng(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int numPageProductManagement = (int)req.getSession().getAttribute("numPageProductManagement");
        List<Product> newPageProducts = productService.getProductByPageNum(numPageProductManagement+1, LIMIT_NUM);
        if (newPageProducts == null || newPageProducts.isEmpty()){
            resp.sendRedirect("danhSachSanPham.jsp");
            return;
        }
        List<Product> productsOfManagement = (List<Product>) req.getSession().getAttribute("productsOfManagement");
        List<ProductPrice> productPricesOfManagement = (List<ProductPrice>) req.getSession().getAttribute("productPricesOfManagement");

        if (!productsOfManagement.contains(newPageProducts.get(0))){
            for (Product product : newPageProducts){
                product.getProductImageList();
                Optional<ProductPrice> opPP= productPriceService.getNewestProductPriceByProductId(product.getProduct_id());
                productPricesOfManagement.add(opPP.orElse(null));
            }
            productsOfManagement.addAll(newPageProducts);
        }
        req.getSession().setAttribute("productsOfManagement", productsOfManagement);
        req.getSession().setAttribute("productPricesOfManagement", productPricesOfManagement);
        req.getSession().setAttribute("numPageProductManagement", numPageProductManagement+1);
        resp.sendRedirect("danhSachSanPham.jsp");
    }
    public void addNewProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name").trim();
        String description  = req.getParameter("description").trim();
        String manufacturer = req.getParameter("manufacturer").trim();
        String unit = req.getParameter("unit").trim();
        String status = req.getParameter("status").trim();
        String priceStr = req.getParameter("price").trim();
        String note = req.getParameter("note").trim();
        Part imgPrimary = req.getPart("imagePrimary");
        Part imgAlter = req.getPart("imageAlternative");

        if (name.isEmpty() || description.isEmpty() || manufacturer.isEmpty() || unit.isEmpty() || status.isEmpty() || priceStr.isEmpty() || note.isEmpty() || imgPrimary.getSize() == 0 || imgAlter.getSize() == 0){
            req.getSession().setAttribute("msgErrAddProductManagement", "Tất cả các ô input đều không được bỏ trống!");
            resp.sendRedirect("themSanPham.jsp");
            return;
        }
        double price = 0;
        try {
            price = Double.parseDouble(priceStr);

        } catch (Exception e){
            req.getSession().setAttribute("msgErrAddProductManagement", "Gía phải là số");
            resp.sendRedirect("themSanPham.jsp");
            return;
        }
        if (price <= 0){
            req.getSession().setAttribute("msgErrAddProductManagement", "Gía phải > 0");
            resp.sendRedirect("themSanPham.jsp");
            return;
        }
        ProductStatus productStatus = ProductStatus.valueOf(status);

        InputStream isImgPrimary = imgPrimary.getInputStream();
        InputStream isImgAlter = imgAlter.getInputStream();

        File imagePrimaryFile = File.createTempFile("imagePrimaryFile", ".png");
        File imageAlterFile = File.createTempFile("imageAlterFile", ".png");

        FileUtils.copyToFile(isImgPrimary, imagePrimaryFile);
        FileUtils.copyToFile(isImgAlter, imageAlterFile);


        Cloudinary cloudinary = new Cloudinary("cloudinary://781433791738536:_dgJmfsVpCO0RQBkvE1wpsQSnRU@dmma7axts");
        cloudinary.config.secure = true;

        Map uploadResultImgPrimary = cloudinary.uploader().upload(imagePrimaryFile, ObjectUtils.emptyMap());
        Map uploadResultImgAlter = cloudinary.uploader().upload(imageAlterFile, ObjectUtils.emptyMap());

        ProductImage productImage = new ProductImage((String)uploadResultImgPrimary.get("url"), (String)uploadResultImgAlter.get("url"));

        ProductPrice productPrice = new ProductPrice(note, LocalDateTime.now(), price);

        Product product = new Product(name, manufacturer, description, unit, productStatus);

        productImage.setProduct(product);
        productPrice.setProduct(product);
        product.setProductImageList(List.of(productImage));
        product.setProductPrices(List.of(productPrice));

        boolean result = productService.add(product);
        if (result){
            req.getSession().removeAttribute("msgErrAddProductManagement");
            getProductList(req, resp);
            return;
        }
        req.getSession().setAttribute("msgErrAddProductManagement", "Thêm thất bại!");
        resp.sendRedirect("themSanPham.jsp");

    }
    public void updateProductManagement(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        Optional<Product> op = productService.findOne(productId);
        if (op.isPresent()){
            Product product = op.get();
            req.getSession().setAttribute("selectedProductManagement", product);
            resp.sendRedirect("capNhatSanPham.jsp");
            return;
        }
        resp.sendRedirect("danhSachSanPham.jsp");

    }
    public void updateOneProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long productID = 0;
        try {
            productID = Long.parseLong(req.getParameter("productId"));
        } catch (NumberFormatException e){
            req.getSession().setAttribute("msgErrUpdateProduct", "Id truyền vào không hợp lệ");
            resp.sendRedirect("capNhatSanPham.jsp");
            return;
        }
        String name = req.getParameter("name").trim();
        String description = req.getParameter("description").trim();
        String manufacturer = req.getParameter("manufacturer").trim();
        String unit = req.getParameter("unit").trim();
        String statusStr = req.getParameter("status").trim();

        if (name.isEmpty() || description.isEmpty() || manufacturer.isEmpty() || unit.isEmpty() || statusStr.isEmpty()){
            req.getSession().setAttribute("msgErrUpdateProduct", "Các field không được để trông!");
            resp.sendRedirect("capNhatSanPham.jsp");
            return;
        }
        ProductStatus productStatus = ProductStatus.valueOf(statusStr);
        Product product = new Product(productID, name, manufacturer, description, unit, productStatus);

        Optional<Product> op = productService.findOne(productID);

        if (op.isPresent()){
            product.setProductImageList(op.get().getProductImageList());
            product.setProductPrices(op.get().getProductPrices());
        }

        boolean result = productService.update(product);
        if(result){
            req.getSession().removeAttribute("msgErrUpdateProduct");
            getProductList(req, resp);
            return;
        }
        req.getSession().setAttribute("msgErrUpdateProduct", "Thêm thất bại");
        resp.sendRedirect("capNhatSanPham.jsp");
    }
    public void deleteProductManagement(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        boolean result = productService.delete(productId);
        if (result){
            req.getSession().setAttribute("msgResultDeleteProduct", "Xóa thành công");
            getProductList(req, resp);
            return;
        }
        req.getSession().setAttribute("msgResultDeleteProduct", "Xóa thất bại");
        resp.sendRedirect("danhSachSanPham.jsp");
    }

    public void handleOpenProductListClient(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Product> products = productService.getProductAvailableByPageNum(1, LIMIT_NUM);
        List<ProductPrice> productPrices = new ArrayList<>();
        for (Product product : products){
            productPrices.add(productPriceService.getNewestProductPriceByProductId(product.getProduct_id()).orElse(null));
        }
        req.getSession().setAttribute("productListClient", products);
        req.getSession().setAttribute("productPriceListClient", productPrices);
        req.getSession().setAttribute("numPageProductListClient", 1);
        req.getSession().setAttribute("numLimitProductListClient", LIMIT_NUM);
        resp.sendRedirect("danhSachSanPhamClient.jsp");
    }
    public void decreaseProductListClient(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int numPageProductListClient = (int)req.getSession().getAttribute("numPageProductListClient");
        if (numPageProductListClient <= 1){
            resp.sendRedirect("danhSachSanPhamClient.jsp");
            return;
        }
        numPageProductListClient -= 1;
        resp.sendRedirect("danhSachSanPhamClient.jsp");
    }
    public void increaseProductListClient(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int numPageProductListClient = (int)req.getSession().getAttribute("numPageProductListClient");
        List<Product> productsNextPage = productService.getProductAvailableByPageNum(numPageProductListClient+1, LIMIT_NUM);
        if (productsNextPage == null || productsNextPage.isEmpty()){
            resp.sendRedirect("danhSachSanPhamClient.jsp");
            return;
        }
        List<Product> productsCurrent = (List<Product>) req.getSession().getAttribute("productListClient");
        if (productsCurrent != null && !productsCurrent.contains(productsNextPage.get(0))){
            productsCurrent.addAll(productsNextPage);
        }
        req.getSession().setAttribute("productListClient", productsCurrent);
        req.getSession().setAttribute("numPageProductListClient", numPageProductListClient+1);
        resp.sendRedirect("danhSachSanPhamClient.jsp");
    }
    public void addToCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long productId=  Long.parseLong(req.getParameter("productId"));
        int quantity = Integer.parseInt(req.getParameter("quantityOrder"));
        if (quantity <= 0){
            req.getSession().setAttribute("resultAddToCart", "Số lượng phải > 0");
            resp.sendRedirect("danhSachSanPhamClient.jsp");
            return;
        }
        Map<Product, Integer> cart = new HashMap<>();
        Product product = productService.findOne(productId).orElse(null);
        if (req.getSession().getAttribute("carts") == null){
            req.getSession().setAttribute("carts", cart);
        } else {
            cart = (Map<Product, Integer>) req.getSession().getAttribute("carts");
        }
        boolean isContain = cart.containsKey(product);
        if (isContain){
            cart.put(product, cart.get(product)+quantity);
        } else {
            cart.put(product, quantity);
        }
        req.getSession().setAttribute("resultAddToCart", "Thêm thành công " + quantity + " sản phẩm " + product.getName() + " vào giỏ hàng!");
        resp.sendRedirect("danhSachSanPhamClient.jsp");
    }
    public void handleOpenCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<Product, Integer> carts = new HashMap<>();
        Map<Long, Double> priceOfProducts = new HashMap<>();
        if (req.getSession().getAttribute("carts") != null){
            carts = (Map<Product, Integer>) req.getSession().getAttribute("carts");

            for(Map.Entry<Product, Integer> entry : carts.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();

                ProductPrice productPrice = productPriceService.getNewestProductPriceByProductId(product.getProduct_id()).orElse(null);
                double price = productPrice != null ? productPrice.getPrice() : 0;
                priceOfProducts.put(product.getProduct_id(), price);
            }
        }
        req.getSession().setAttribute("carts", carts);
        req.getSession().setAttribute("priceOfProducts", priceOfProducts);
        resp.sendRedirect("gioHangPage.jsp");
    }
    public void deleteItemFromCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long productId = Long.parseLong(req.getParameter("productId"));
        Product product = productService.findOne(productId).orElse(null);
        Map<Product, Integer> carts = (Map<Product, Integer>) req.getSession().getAttribute("carts");
        if (carts != null){
            carts.remove(product);
        }
        req.getSession().setAttribute("carts", carts);
        resp.sendRedirect("gioHangPage.jsp");
    }
    public void decreaseItemCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long productId = Long.parseLong(req.getParameter("productId"));
        Product product = productService.findOne(productId).orElse(null);
        Map<Product, Integer> carts = (Map<Product, Integer>) req.getSession().getAttribute("carts");
        if (carts != null){
            carts.put(product, carts.get(product)-1);
        }
        req.getSession().setAttribute("carts", carts);
        resp.sendRedirect("gioHangPage.jsp");
    }
    public void increaseItemCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long productId = Long.parseLong(req.getParameter("productId"));
        Product product = productService.findOne(productId).orElse(null);
        Map<Product, Integer> carts = (Map<Product, Integer>) req.getSession().getAttribute("carts");
        if (carts != null){
            carts.put(product, carts.get(product)+1);
        }
        req.getSession().setAttribute("carts", carts);
        resp.sendRedirect("gioHangPage.jsp");
    }
}
