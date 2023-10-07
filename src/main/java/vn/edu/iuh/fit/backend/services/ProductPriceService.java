package vn.edu.iuh.fit.backend.services;

import vn.edu.iuh.fit.backend.models.ProductPrice;
import vn.edu.iuh.fit.backend.repositories.ProductPriceRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ProductPriceService {
    private ProductPriceRepository productPriceRepository;

    public ProductPriceService(){
        this.productPriceRepository = new ProductPriceRepository();
    }
    public boolean add(ProductPrice productPrice){
        return productPriceRepository.add(productPrice);
    }
    public boolean update(ProductPrice productPrice){
        return productPriceRepository.update(productPrice);
    }
    public Optional<ProductPrice> findOne(long productId, LocalDateTime priceDateTime){
        return productPriceRepository.findOne(productId, priceDateTime);
    }
    public boolean delete(long productId, LocalDateTime priceDateTime){
        return productPriceRepository.delete(productId, priceDateTime);
    }
    public List<ProductPrice> findAll(){
        return productPriceRepository.findAll();
    }
    public Optional<ProductPrice> getNewestProductPriceByProductId(long id){
        return productPriceRepository.getNewestProductPriceByProductId(id);
    }
    public List<ProductPrice> getProductsByNumPage(long productId, int pageNum, int limitNum){
        return productPriceRepository.getProductsByNumPage(productId, pageNum, limitNum);
    }
}
