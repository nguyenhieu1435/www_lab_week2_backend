package vn.edu.iuh.fit.backend.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.iuh.fit.backend.database.Connection;
import vn.edu.iuh.fit.backend.enums.EmployeeStatus;
import vn.edu.iuh.fit.backend.models.Employee;

import java.util.List;
import java.util.Optional;

public class EmployeeRepository {
    private EntityManager em;
    private EntityTransaction trans;
    private final Logger logger = LoggerFactory.getLogger(EmployeeRepository.class.getName());

    public EmployeeRepository(){
        this.em = Connection.getInstance().getEntityManagerFactory().createEntityManager();
        this.trans = em.getTransaction();
    }

    public boolean add(Employee employee){
        trans.begin();
        try {
            em.persist(employee);
            trans.commit();
            return true;
        } catch (Exception e){
            trans.rollback();
            logger.error(e.getMessage());
        }
        return false;
    }
    public boolean update(Employee employee){
        trans.begin();
        try {
            em.merge(employee);
            trans.commit();
            return true;
        } catch (Exception e){
            trans.rollback();
            logger.error(e.getMessage());
        }
        return false;
    }
    public Optional<Employee> findOne(long id){
        Employee employee = em.find(Employee.class, id);
        return employee != null ? Optional.of(employee) : Optional.empty();
    }
    public boolean delete(long id){
        trans.begin();
        try {
            Optional<Employee> op = findOne(id);
            if (op.isPresent()){
                Employee employee = op.get();
                employee.setStatus(EmployeeStatus.NoLongerWorking);
                em.merge(employee);
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
    public List<Employee> findAll(){
        TypedQuery<Employee> query = em.createNamedQuery("Employee.findAll", Employee.class);
        query.setParameter("status", EmployeeStatus.NoLongerWorking);
        return query.getResultList();
    }
    public List<Employee> getEmplByPageNum(int numPage, int limitNum){
        // skip = (pageNum - 1)*limitNum, limited = limitNum
        TypedQuery<Employee>query = em.createNamedQuery("Employee.getEmpByPageNum", Employee.class);
        query.setParameter("status", EmployeeStatus.NoLongerWorking);
        query.setFirstResult((numPage - 1) * limitNum);
        query.setMaxResults(limitNum);
        return query.getResultList();
    }
}
