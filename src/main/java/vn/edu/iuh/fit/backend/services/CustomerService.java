package vn.edu.iuh.fit.backend.services;

import vn.edu.iuh.fit.backend.models.Customer;
import vn.edu.iuh.fit.backend.repositories.CustomerRepository;

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
    public List<Customer> getCustByNumPage(int numPage, int limitNum){
        return customerRepository.getCustByNumPage(numPage, limitNum);
    }

}
