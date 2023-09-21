package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.iuh.fit.database.Connection;
import vn.edu.iuh.fit.models.Order;

import java.util.List;
import java.util.Optional;

public class OrderRepository {
    private EntityManager em;
    private EntityTransaction trans;
    private final Logger logger = LoggerFactory.getLogger(OrderRepository.class.getName());

    public OrderRepository(){
        this.em = Connection.getInstance().getEntityManagerFactory().createEntityManager();
        this.trans = em.getTransaction();
    }

    public boolean add(Order order){
        trans.begin();
        try {
            em.persist(order);
            trans.commit();
            return true;
        } catch (Exception e){
            trans.rollback();
            logger.error(e.getMessage());
        }
        return false;
    }

    public boolean update(Order order){
        trans.begin();
        try {
            em.merge(order);
            trans.commit();
            return true;
        } catch (Exception e){
            trans.rollback();
            logger.error(e.getMessage());
        }
        return false;
    }
    public Optional<Order> findOne(long id){
        Order order = em.find(Order.class, id);
        return order != null ? Optional.of(order) : Optional.empty();
    }
    public boolean delete(long id){
        trans.begin();
        try {
            Optional<Order> op = findOne(id);
            if (op.isPresent()){
                em.remove(op.get());
                trans.commit();
                return true;
            }
            trans.rollback();;
        } catch (Exception e){
            trans.rollback();
            logger.error(e.getMessage());
        }
        return false;
    }
    public List<Order> findAll(){
        TypedQuery<Order> query = em.createNamedQuery("Order.findAll", Order.class);
        return query.getResultList();
    }
}
