package vn.edu.iuh.fit.backend.repositories;

import jakarta.ejb.Local;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.iuh.fit.backend.database.Connection;
import vn.edu.iuh.fit.backend.models.Employee;
import vn.edu.iuh.fit.backend.models.Order;

import java.time.LocalDateTime;
import java.util.*;

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
    public List<Order> getOrderByPageNum(int numPage, int limitNum){
        TypedQuery<Order> query = em.createNamedQuery("Order.getOrderByNumPage", Order.class);
        query.setFirstResult((numPage-1)*limitNum);
        query.setMaxResults(limitNum);
        return query.getResultList();
    }
    public Map<LocalDateTime, Integer> getStatisticsOrderByDate(){
       String queryStr = "SELECT o.orderDate, count(o.order_id) FROM Order o group by o.orderDate";
       Map<LocalDateTime, Integer> map = new HashMap<>();
       List<Object[]> obj = em.createQuery(queryStr).getResultList();
        for (Object[] o : obj){
            LocalDateTime localDateTime = LocalDateTime.parse(o[0].toString());
            int num = ((Long)o[1]).intValue();
            map.put(localDateTime, num);
        }
        return map;
    }
    public double getStatisticsOrderNumByDateRange(LocalDateTime beginDate, LocalDateTime endDate){
        String queryStr= "SELECT sum(od.quantity*od.price) from Order o, OrderDetail od where o.order_id = od.product.id " +
                " and o.orderDate >= :beginDate and o.orderDate <= :endDate";
        List obj = em.createQuery(queryStr)
                .setParameter("beginDate", beginDate)
                .setParameter("endDate", endDate)
                .getResultList();
        if (obj == null || obj.isEmpty()){
            return 0;
        } else {
            Object o = obj.get(0);
            return o == null ? 0 : ((Double)o);
        }
    }
    public double getStatisticsOrderNumByDateRangeAndEmpId(Long empId, LocalDateTime beginDate, LocalDateTime endDate){

        String queryStr= "SELECT sum(od.quantity*od.price) FROM Order o, OrderDetail od" +
                " where o.order_id = od.product.id and o.employee.id = :empId" +
                " and o.orderDate >= :beginDate and o.orderDate <= :endDate";
        List obj = em.createQuery(queryStr)
                .setParameter("empId", empId)
                .setParameter("beginDate", beginDate)
                .setParameter("endDate", endDate)
                .getResultList();
        if (obj == null || obj.isEmpty() ){
            return 0;
        } else {
            Object o = obj.get(0);
            return o == null ? 0 : ((Double)o);
        }
    }
}
