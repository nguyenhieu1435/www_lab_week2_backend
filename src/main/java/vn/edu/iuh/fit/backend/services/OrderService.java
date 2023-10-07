package vn.edu.iuh.fit.backend.services;

import vn.edu.iuh.fit.backend.models.Order;
import vn.edu.iuh.fit.backend.repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(){
        this.orderRepository = new OrderRepository();
    }
    public boolean add(Order order){
        return orderRepository.add(order);
    }
    public boolean update(Order order){
        return orderRepository.update(order);
    }
    public Optional<Order> findOne(long id){
        return orderRepository.findOne(id);
    }
    public boolean delete(long id){
        return orderRepository.delete(id);
    }
    public List<Order> findAll(){
        return orderRepository.findAll();
    }
    public List<Order> getOrderByPageNum(int numPage, int limitNum){
        return orderRepository.getOrderByPageNum(numPage, limitNum);
    }
    public Map<LocalDateTime, Integer> getStatisticsOrderByDate(){
        return orderRepository.getStatisticsOrderByDate();
    }
    public double getStatisticsOrderNumByDateRange(LocalDateTime beginDate, LocalDateTime endDate){
        return orderRepository.getStatisticsOrderNumByDateRange(beginDate, endDate);
    }
    public double getStatisticsOrderNumByDateRangeAndEmpId(Long empId, LocalDateTime beginDate, LocalDateTime endDate){
        return orderRepository.getStatisticsOrderNumByDateRangeAndEmpId(empId, beginDate, endDate);
    }

}

