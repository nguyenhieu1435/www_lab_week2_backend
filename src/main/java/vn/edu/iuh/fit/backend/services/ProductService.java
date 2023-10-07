package vn.edu.iuh.fit.backend.services;

import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

public class ProductService {
    private ProductRepository productRepository;

    public ProductService(){
        this.productRepository = new ProductRepository();
    }
    public boolean add(Product product){
        return productRepository.add(product);
    }
    public boolean update(Product product){
        return productRepository.update(product);
    }
    public Optional<Product> findOne(long id){
        return productRepository.findOne(id);
    }
    public boolean delete (long id){
        return productRepository.delete(id);
    }
    public List<Product> findAll(){
        return productRepository.findAll();
    }
    public List<Product> getProductByPageNum(int pageNum, int limitNum){
        return productRepository.getProductByPageNum(pageNum, limitNum);
    }
    public List<Product> getProductAvailableByPageNum(int pageNum, int limitNum){
        return productRepository.getProductAvailableByPageNum(pageNum, limitNum);
    }
}
