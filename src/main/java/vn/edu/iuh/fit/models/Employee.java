package vn.edu.iuh.fit.models;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.*;
import org.checkerframework.checker.units.qual.C;
import vn.edu.iuh.fit.enums.EmployeeStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "employee")
@NamedQuery(name = "Employee.findAll", query="SELECT e from Employee e")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id", columnDefinition = "BIGINT(20)")
    private long id;
    @Column(name = "full_name", columnDefinition = "VARCHAR(150)", nullable = false)
    private String fullname;
    @Column(columnDefinition = "VARCHAR(150)")
    private String email;
    @Column(columnDefinition = "VARCHAR(15)", nullable = false)
    private String phone;
    @Column(columnDefinition = "VARCHAR(250)", nullable = false)
    private String address;
    @Column(columnDefinition = "DATETIME(6)", nullable = false)
    @JsonbDateFormat(value = "yyyy-MM-dd")
    private LocalDateTime dob;
    @Column(columnDefinition = "INT(11) SIGNED", nullable = false)
    private EmployeeStatus status;
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> lstOrder;

    public Employee() {
    }

    public Employee(long id, String fullname, String email, String phone, String address, LocalDateTime dob, EmployeeStatus status) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.status = status;
    }

    public Employee(long id, String fullname, String email, String phone, String address, LocalDateTime dob, EmployeeStatus status, List<Order> lstOrder) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.status = status;
        this.lstOrder = lstOrder;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getDob() {
        return dob;
    }

    public void setDob(LocalDateTime dob) {
        this.dob = dob;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public List<Order> getLstOrder() {
        return lstOrder;
    }

    public void setLstOrder(List<Order> lstOrder) {
        this.lstOrder = lstOrder;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", dob=" + dob +
                ", status=" + status +
                '}';
    }
}
