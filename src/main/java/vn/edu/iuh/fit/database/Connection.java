package vn.edu.iuh.fit.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Connection {
    private static Connection instance;
    private EntityManagerFactory emf;

    private Connection(){
        emf = Persistence.createEntityManagerFactory("www_lab_week2_backend");
    }
    public static Connection getInstance(){
        if (instance == null){
            instance = new Connection();
        }
        return instance;
    }
    public EntityManagerFactory getEntityManagerFactory(){
        return emf;
    }

    public static void main(String[] args) {
        EntityManager em = Connection.getInstance().getEntityManagerFactory().createEntityManager();


    }
}
