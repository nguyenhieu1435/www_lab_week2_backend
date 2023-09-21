package vn.edu.iuh.fit.services;

import vn.edu.iuh.fit.models.OrderDetail;
import vn.edu.iuh.fit.repositories.OrderDetailRepository;

import java.util.List;
import java.util.Optional;

public class OrderDetailService {
    private OrderDetailRepository orderDetailRepository ;

    public OrderDetailService(){
        this.orderDetailRepository = new OrderDetailRepository();
    }

    public boolean add(OrderDetail orderDetail){
        return orderDetailRepository.add(orderDetail);
    }
    public boolean update(OrderDetail orderDetail){
        return orderDetailRepository.update(orderDetail);
    }
    public boolean delete(long orderId, long productId){
        return orderDetailRepository.delete(orderId, productId);
    }
    public Optional<OrderDetail> findOne(long orderId, long productId){
        return orderDetailRepository.findOne(orderId, productId);
    }
    public List<OrderDetail> findAll(){
        return orderDetailRepository.findAll();
    }

}

