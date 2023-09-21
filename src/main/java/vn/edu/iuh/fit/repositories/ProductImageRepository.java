package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.iuh.fit.database.Connection;
import vn.edu.iuh.fit.enums.ProductStatus;
import vn.edu.iuh.fit.models.Product;
import vn.edu.iuh.fit.models.ProductImage;

import java.util.List;
import java.util.Optional;

public class ProductImageRepository {
    private EntityManager em;
    private EntityTransaction trans;
    private Logger logger = LoggerFactory.getLogger(ProductImageRepository.class.getName());

    public ProductImageRepository(){
        this.em = Connection.getInstance().getEntityManagerFactory().createEntityManager();
        this.trans = em.getTransaction();
    }

    public boolean add(ProductImage productImage){
        trans.begin();
        try {
            em.persist(productImage);
            trans.commit();
            return true;
        } catch (Exception e){
            trans.rollback();
            logger.error(e.getMessage());
        }
        return false;
    }
    public boolean update(ProductImage productImage){
        trans.begin();
        try {
            em.merge(productImage);
            trans.commit();
            return true;
        } catch (Exception e){
            trans.rollback();
            logger.error(e.getMessage());
        }
        return false;
    }
    public Optional<ProductImage> findOne(long id){
        ProductImage productImage = em.find(ProductImage.class, id);
        return productImage != null ? Optional.of(productImage) : Optional.empty();
    }
    public boolean delete (long id){
        trans.begin();
        try {
            Optional<ProductImage> op = findOne(id);
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

    public List<ProductImage> findAll(){
        TypedQuery<ProductImage> query = em.createNamedQuery("ProductImage.findAll", ProductImage.class);
        return query.getResultList();
    }
}
