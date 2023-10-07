package vn.edu.iuh.fit.backend.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.iuh.fit.backend.database.Connection;
import vn.edu.iuh.fit.backend.enums.ProductStatus;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductPrice;

import java.util.List;
import java.util.Optional;

public class ProductRepository {
    private EntityManager em;
    private EntityTransaction trans;
    private final Logger logger = LoggerFactory.getLogger(ProductRepository.class.getName());

    public ProductRepository(){
        this.em = Connection.getInstance().getEntityManagerFactory().createEntityManager();
        this.trans = em.getTransaction();
    }

    public boolean add(Product product){
        trans.begin();
        try {
            em.persist(product);
            trans.commit();
            return true;
        } catch (Exception e){
            trans.rollback();
            logger.error(e.getMessage());
        }
        return false;
    }
    public boolean update(Product product){
        trans.begin();
        try {
            em.merge(product);
            trans.commit();
            return true;
        } catch (Exception e){
            trans.rollback();
            logger.error(e.getMessage());
        }
        return false;
    }
    public Optional<Product> findOne(long id){
        Product product = em.find(Product.class, id);
        return product != null ? Optional.of(product) : Optional.empty();
    }
    public boolean delete (long id){
        trans.begin();
        try {
            Optional<Product> op = findOne(id);
            if (op.isPresent()){
                Product product = op.get();
                product.setStatus(ProductStatus.NoLongerInBusiness);
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

    public List<Product> findAll(){
        TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
        query.setParameter("status", ProductStatus.NoLongerInBusiness);
        return query.getResultList();
    }

    public List<Product> getProductByPageNum(int pageNum, int limitNum){
        TypedQuery<Product> query = em.createNamedQuery("Product.getProductByPageNum", Product.class);
        query.setParameter("status", ProductStatus.NoLongerInBusiness);
        query.setFirstResult((pageNum-1)*limitNum);
        query.setMaxResults(limitNum);
        return query.getResultList();
    }
    public List<Product> getProductAvailableByPageNum(int pageNum, int limitNum){
        TypedQuery<Product> query = em.createNamedQuery("Product.getProductAvailableByPageNum", Product.class);
        query.setParameter("status", ProductStatus.DoingBusiness);
        query.setFirstResult((pageNum-1)*limitNum);
        query.setMaxResults(limitNum);
        return query.getResultList();
    }
}
