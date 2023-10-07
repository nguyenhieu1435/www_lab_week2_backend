package vn.edu.iuh.fit.backend.database;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import vn.edu.iuh.fit.backend.repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.Map;

public class Connection {
    private static Connection instance;
    private EntityManagerFactory emf;

    private Connection() {
        emf = Persistence.createEntityManagerFactory("www_lab_week2_backend");
    }

    public static Connection getInstance() {
        if (instance == null) {
            instance = new Connection();
        }
        return instance;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public static void main(String[] args) {
//        EntityManager em = Connection.getInstance().getEntityManagerFactory().createEntityManager();
//        Dotenv dotenv = Dotenv.load();
//        Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
//        cloudinary.config.secure = true;
//        System.out.println(cloudinary.config.cloudName);
//        OrderRepository orderRepository = new OrderRepository();
//        System.out.println(orderRepository.getStatisticsOrderByDate());
//        System.out.println(orderRepository.getStatisticsOrderNumByDateRange(LocalDateTime.of(2023, 8, 21, 0, 0, 0), LocalDateTime.of(2023, 8, 23, 0, 0, 0)));
//        System.out.println(orderRepository.getStatisticsOrderNumByDateRangeAndEmpId(1l, LocalDateTime.of(2023, 8, 21, 0, 0, 0), LocalDateTime.of(2023, 8, 23, 0, 0, 0)));
    }

}
