package vn.edu.iuh.fit.backend.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.iuh.fit.backend.database.Connection;
import vn.edu.iuh.fit.backend.models.ProductPrice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ProductPriceRepository {
    private EntityManager em;
    private EntityTransaction trans;
    private final Logger logger = LoggerFactory.getLogger(ProductImageRepository.class.getName());

    public ProductPriceRepository(){
        this.em = Connection.getInstance().getEntityManagerFactory().createEntityManager();
        this.trans = em.getTransaction();
    }
    public boolean add(ProductPrice productPrice){
        trans.begin();
        try {
            em.persist(productPrice);
            trans.commit();
            return true;
        } catch (Exception e){
            trans.rollback();
            logger.error(e.getMessage());
        }
        return false;
    }
    public boolean update(ProductPrice productPrice){
        trans.begin();
        try {
            em.merge(productPrice);
            trans.commit();
            return true;
        } catch (Exception e){
            trans.rollback();
            logger.error(e.getMessage());
        }
        return false;
    }
    public Optional<ProductPrice> findOne(long productId, LocalDateTime priceDateTime){
        TypedQuery<ProductPrice> query = em.createNamedQuery("ProductPrice.findOne", ProductPrice.class);
        query.setParameter("product", productId);
        query.setParameter("price_date_time", priceDateTime);

        List<ProductPrice> productPriceList = query.getResultList();
        return productPriceList.size() > 0 ? Optional.of(productPriceList.get(0)) : Optional.empty();
    }
    public boolean delete(long productId, LocalDateTime priceDateTime){
        trans.begin();
        try {
            Optional<ProductPrice> op = findOne(productId, priceDateTime);
            if (op.isPresent()){
                em.remove(op.get());
                trans.commit();
                return true;
            }
            trans.rollback();
        } catch (Exception e){
            trans.rollback();
            logger.error(e.getMessage());
        }
        return false;
    }
    public List<ProductPrice> findAll(){
        TypedQuery<ProductPrice> query = em.createNamedQuery("ProductPrice.findAll", ProductPrice.class);
        return query.getResultList();
    }
    public Optional<ProductPrice> getNewestProductPriceByProductId(long id){
        TypedQuery<ProductPrice> query = em.createNamedQuery("ProductPrice.getNewestOneByProductId", ProductPrice.class);
        query.setParameter("productId", id);
        query.setMaxResults(1);
        List<ProductPrice> productPrices = query.getResultList();
        return productPrices.isEmpty() ? Optional.empty() : Optional.of(productPrices.get(0));
    }
}
