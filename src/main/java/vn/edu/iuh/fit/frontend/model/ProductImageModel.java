package vn.edu.iuh.fit.frontend.model;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.commons.io.FileUtils;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductImage;
import vn.edu.iuh.fit.backend.services.ProductImageService;
import vn.edu.iuh.fit.backend.services.ProductService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ProductImageModel {
    private ProductImageService productImageService;
    private ProductService productService;
    private final int LIMIT_NUM = 5;
    public ProductImageModel(){
        this.productImageService = new ProductImageService();
        this.productService = new ProductService();
    }
    public void getImgListByProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long productId = 0;
        try {
            productId = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e){
            resp.sendRedirect("danhSachSanPham.jsp.jsp");
            return;
        }
        if (productId < 0){
            resp.sendRedirect("danhSachSanPham.jsp");
            return;
        }
        Product product = productService.findOne(productId).orElse(null);
        req.getSession().setAttribute("titleProductProductImage", "Danh sách ảnh của sản phẩm: "
                + product.getName() + " ,ID: " + product.getProduct_id());
        List<ProductImage> productImageList = productImageService.getImageByProductIdWithPagination(productId, 1, LIMIT_NUM);

        req.getSession().setAttribute("productImageList", productImageList);
        req.getSession().setAttribute("productIdAtProductImage", productId);
        req.getSession().setAttribute("numPageProductImage", 1);
        req.getSession().setAttribute("limitNumProductImage", LIMIT_NUM);
        resp.sendRedirect("danhSachHinhAnh.jsp");
    }
    public void decreaseProductImage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int numPageProductImage = (int)req.getSession().getAttribute("numPageProductImage");
        if (numPageProductImage <= 1){
            resp.sendRedirect("danhSachHinhAnh.jsp");
            return;
        }
        numPageProductImage -= 1;
        req.getSession().setAttribute("numPageProductImage", numPageProductImage);
        resp.sendRedirect("danhSachHinhAnh.jsp");
    }
    public void increaseProductImage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int numPageProductPrice = (int)req.getSession().getAttribute("numPageProductImage");
        long productId = (long)req.getSession().getAttribute("productIdAtProductImage");
        List<ProductImage> productImagesNextPage = productImageService.getImageByProductIdWithPagination(productId
                , numPageProductPrice+1, LIMIT_NUM);
        if (productImagesNextPage == null || productImagesNextPage.isEmpty()){
            resp.sendRedirect("danhSachHinhAnh.jsp");
            return;
        }
        List<ProductImage> productImageListCurrent = (List<ProductImage>) req.getSession()
                .getAttribute("productImageList");
        if (productImageListCurrent != null && !productImageListCurrent.contains(productImagesNextPage.get(0))){
            productImageListCurrent.addAll(productImagesNextPage);
        }
        req.getSession().setAttribute("productImageList", productImageListCurrent);
        req.getSession().setAttribute("numPageProductImage", numPageProductPrice+1);
        resp.sendRedirect("danhSachHinhAnh.jsp");
    }
    public void addNewProductImage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long producId = Long.parseLong(req.getParameter("productId"));
        Part imgPrimary = req.getPart("pathPrimary");
        Part imgAlter = req.getPart("pathAlternative");

        if (imgPrimary.getSize() == 0 || imgAlter.getSize() == 0){
            req.getSession().setAttribute("msgErrAddProductImage", "Tất cả các ô input đều không được bỏ trống!");
            resp.sendRedirect("themHinhAnhSanPham.jsp");
            return;
        }
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

        ProductImage productImage = new ProductImage(new Product(producId), (String)uploadResultImgPrimary.get("url"), (String)uploadResultImgAlter.get("url"));

        boolean result = productImageService.add(productImage);
        if (result){
            req.getSession().removeAttribute("msgErrAddProductImage");
            List<ProductImage> productImages = productImageService.getImageByProductIdWithPagination(producId, 1, LIMIT_NUM);
            req.getSession().setAttribute("productImageList", productImages);
            req.getSession().setAttribute("numPageProductManagement", 1);
            resp.sendRedirect("danhSachHinhAnh.jsp");
            return;
        }
        req.getSession().setAttribute("msgErrAddProductImage", "Thêm thất bại!");
        resp.sendRedirect("themHinhAnhSanPham.jsp");
    }
    public void handleOpenUpdProductImage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long productImageId = Long.parseLong(req.getParameter("productImageId"));

        ProductImage productImage = productImageService.findOne(productImageId).orElse(null);
        if (productImage != null){
            req.getSession().setAttribute("selectedProductImage", productImage);
            resp.sendRedirect("capNhatAnhSanPham.jsp");
            return;
        }
        resp.sendRedirect("danhSachHinhAnh.jsp");
    }
    public void updateProductImage(HttpServletRequest req,  HttpServletResponse resp) throws ServletException, IOException {
        long producId = Long.parseLong(req.getParameter("productId"));
        long productImage = Long.parseLong(req.getParameter("productImgId"));
        Part imgPrimary = req.getPart("pathPrimary");
        Part imgAlter = req.getPart("pathAlternative");

        if (imgPrimary.getSize() == 0 || imgAlter.getSize() == 0){
            req.getSession().setAttribute("errUpdProductImage", "Không được để trống field nào!");
            resp.sendRedirect("capNhatAnhSanPham.jsp");
            return;
        }
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

        ProductImage newProductImage = new ProductImage(productImage, new Product(producId), (String)uploadResultImgPrimary.get("url"), (String)uploadResultImgAlter.get("url"));
        boolean result = productImageService.update(newProductImage);
        if (result){
            req.getSession().removeAttribute("errUpdProductImage");
            List<ProductImage> productImages = productImageService.getImageByProductIdWithPagination(producId, 1, LIMIT_NUM);
            req.getSession().setAttribute("numPageProductManagement", 1);
            req.getSession().setAttribute("productImageList", productImages);
            resp.sendRedirect("danhSachHinhAnh.jsp");
            return;
        }
        req.getSession().setAttribute("errUpdProductImage", "Cập nhật thất bại!");
        resp.sendRedirect("capNhatAnhSanPham.jsp");
    }
    public void deleletProductImage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long productImageId = Long.parseLong(req.getParameter("productImageId"));
        long productId = (long)req.getSession().getAttribute("productIdAtProductImage");
        boolean result = productImageService.delete(productImageId);
        if (result){
            req.getSession().setAttribute("msgDeleteActionProductImage", "Xóa thành công!");
            List<ProductImage> productImages = productImageService.getImageByProductIdWithPagination(productId, 1, LIMIT_NUM);
            req.getSession().setAttribute("numPageProductManagement", 1);
            req.getSession().setAttribute("productImageList", productImages);
        } else {
            req.getSession().setAttribute("msgDeleteActionProductImage", "Xóa thất bại!");
        }
        resp.sendRedirect("danhSachHinhAnh.jsp");
    }
}
