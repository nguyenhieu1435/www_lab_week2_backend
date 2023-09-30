package vn.edu.iuh.fit.backend.services;

import vn.edu.iuh.fit.backend.models.ProductImage;
import vn.edu.iuh.fit.backend.repositories.ProductImageRepository;

import java.util.List;
import java.util.Optional;

public class ProductImageService {
    private ProductImageRepository productImageRepository;

    public ProductImageService(){
        this.productImageRepository = new ProductImageRepository();
    }
    public boolean add(ProductImage productImage){
        return productImageRepository.add(productImage);
    }
    public boolean update(ProductImage productImage){
        return productImageRepository.update(productImage);
    }
    public Optional<ProductImage> findOne(long id){
        return productImageRepository.findOne(id);
    }
    public boolean delete (long id){
        return productImageRepository.delete(id);
    }
    public List<ProductImage> findAll(){
        return productImageRepository.findAll();
    }
}
