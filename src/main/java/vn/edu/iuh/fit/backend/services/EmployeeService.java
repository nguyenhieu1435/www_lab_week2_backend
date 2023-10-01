package vn.edu.iuh.fit.backend.services;

import vn.edu.iuh.fit.backend.models.Employee;
import vn.edu.iuh.fit.backend.repositories.EmployeeRepository;

import java.util.List;
import java.util.Optional;

public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(){
        this.employeeRepository = new EmployeeRepository();
    }
    public boolean add(Employee employee){
        return employeeRepository.add(employee);
    }
    public boolean update(Employee employee){
        return employeeRepository.update(employee);
    }
    public boolean delete(long id){
        return employeeRepository.delete(id);
    }
    public Optional<Employee> findOne(long id){
        return employeeRepository.findOne(id);
    }
    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public List<Employee> getEmplByPageNum(int numPage, int limitNum){
        return employeeRepository.getEmplByPageNum(numPage, limitNum);
    }
}
