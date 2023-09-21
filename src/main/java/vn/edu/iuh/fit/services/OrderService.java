package vn.edu.iuh.fit.services;

import vn.edu.iuh.fit.models.Order;
import vn.edu.iuh.fit.repositories.OrderRepository;

import java.util.List;
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
}

