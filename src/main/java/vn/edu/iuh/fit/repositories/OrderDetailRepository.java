package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.iuh.fit.database.Connection;
import vn.edu.iuh.fit.models.OrderDetail;
import vn.edu.iuh.fit.models.ProductPrice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OrderDetailRepository {
    private EntityManager em;
    private EntityTransaction trans;
    private final Logger logger = LoggerFactory.getLogger(OrderDetailRepository.class.getName());

    public OrderDetailRepository(){
        this.em = Connection.getInstance().getEntityManagerFactory().createEntityManager();
        this.trans = em.getTransaction();
    }
    public boolean add(OrderDetail orderDetail){
        trans.begin();
        try {
            em.persist(orderDetail);
            trans.commit();
            return true;
        } catch (Exception e){
            trans.rollback();
            logger.error(e.getMessage());
        }
        return false;
    }
    public boolean update(OrderDetail orderDetail){
        trans.begin();
        try {
            em.merge(orderDetail);
            trans.commit();
            return true;
        } catch (Exception e){
            trans.rollback();
            logger.error(e.getMessage());
        }
        return false;
    }
    public Optional<OrderDetail> findOne(long orderId, long productId){
        TypedQuery<OrderDetail> query = em.createNamedQuery("OrderDetail.findOne", OrderDetail.class);
        query.setParameter("product", productId);
        query.setParameter("order", orderId);
        List<OrderDetail> orderDetailList = query.getResultList();

        return orderDetailList.size() > 0 ? Optional.of(orderDetailList.get(0)) : Optional.empty();
    }
    public boolean delete(long orderId, long productId){
        trans.begin();
        try {
            Optional<OrderDetail> op = findOne(orderId, productId);
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
    public List<OrderDetail> findAll(){
        TypedQuery<OrderDetail> query = em.createNamedQuery("OrderDetail.findAll", OrderDetail.class);
        return query.getResultList();
    }
}
