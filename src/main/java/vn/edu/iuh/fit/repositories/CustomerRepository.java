package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.iuh.fit.database.Connection;
import vn.edu.iuh.fit.models.Customer;

import java.util.List;
import java.util.Optional;

public class CustomerRepository {
    private EntityManager em;
    private EntityTransaction trans;
    private final Logger logger = LoggerFactory.getLogger(CustomerRepository.class.getName());

    public CustomerRepository(){
        this.em = Connection.getInstance().getEntityManagerFactory().createEntityManager();
        trans = em.getTransaction();

    }

    public boolean add(Customer customer){
        trans.begin();
        try {
            em.persist(customer);
            trans.commit();
            return true;
        } catch (Exception e){
            trans.rollback();
            logger.error(e.getMessage());
        }
        return false;
    }

    public boolean update(Customer customer){
        trans.begin();
        try {
            em.merge(customer);
            trans.commit();
            return true;
        } catch (Exception e){
            trans.rollback();
            logger.error(e.getMessage());
        }
        return false;
    }
    public boolean delete(long custId){
        trans.begin();
        try {
            Optional<Customer> op = findOne(custId);
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
    public Optional<Customer> findOne(long custId){
        Customer customer = em.find(Customer.class, custId);

        return customer != null ? Optional.of(customer) : Optional.empty();
    }

    public List<Customer> findAll(){
        TypedQuery<Customer> query = em.createNamedQuery("Customer.findAll", Customer.class);
        return query.getResultList();
    }
}
