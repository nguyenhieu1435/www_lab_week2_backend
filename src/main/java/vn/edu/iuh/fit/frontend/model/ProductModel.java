package vn.edu.iuh.fit.frontend.model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductPrice;
import vn.edu.iuh.fit.backend.services.ProductPriceService;
import vn.edu.iuh.fit.backend.services.ProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductModel {
    private ProductService productService;
    private ProductPriceService productPriceService;

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

}
