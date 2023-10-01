package vn.edu.iuh.fit.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
@NamedQuery(name="Customer.getPageByNum", query = "SELECT c FROM Customer c")
@Table(name = "customer")
@JsonIgnoreProperties("orderList")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id", columnDefinition = "BIGINT(20)")
    private long id;
    @Column(name = "cust_name", columnDefinition = "VARCHAR(150)", nullable = false)
    private String name;
    @Column(columnDefinition = "VARCHAR(250)", nullable = false)
    private String address;
    @Column(columnDefinition = "VARCHAR(15)", nullable = false)
    private String phone;
    @Column(columnDefinition = "VARCHAR(150)")
    private String email;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> orderList;

    public Customer() {
    }

    public Customer(long id) {
        this.id = id;
    }

    public Customer(String name, String address, String phone, String email) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public Customer(long id, String name, String address, String phone, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public Customer(long id, String name, String address, String phone, String email, List<Order> orderList) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.orderList = orderList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
