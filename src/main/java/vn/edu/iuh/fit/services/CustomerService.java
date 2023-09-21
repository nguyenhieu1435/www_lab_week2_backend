package vn.edu.iuh.fit.services;

import vn.edu.iuh.fit.models.Customer;
import vn.edu.iuh.fit.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;

public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(){
        this.customerRepository = new CustomerRepository();
    }

    public boolean add(Customer customer){
        return customerRepository.add(customer);
    }
    public boolean update(Customer customer){
        return customerRepository.update(customer);
    }
    public boolean delete(long custId) {
        return customerRepository.delete(custId);
    }
    public Optional<Customer> findOne(long custId){
        return customerRepository.findOne(custId);
    }
    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

}
