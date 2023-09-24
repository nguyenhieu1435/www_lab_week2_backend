package vn.edu.iuh.fit.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import vn.edu.iuh.fit.enums.ProductStatus;

import java.util.List;


@Entity
@Table(name = "product")
@NamedQuery(name = "Product.findAll", query = "SELECT p from Product  p")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", columnDefinition = "BIGINT(20)")
    private long product_id;
    @Column(columnDefinition = "VARCHAR(150)", nullable = false)
    private String name;
    @Column(name = "manufacturer_name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String manufacturer;
    @Column(columnDefinition = "VARCHAR(250)", nullable = false)
    private String description;
    @Column(columnDefinition = "VARCHAR(25)", nullable = false)
    private String unit;
    @Column(columnDefinition = "INT(11) SIGNED")
    private ProductStatus status;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductImage> productImageList;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderDetail> orderDetails;

    public Product() {
    }

    public Product(long product_id, String name, String manufacturer, String description, String unit, ProductStatus status) {
        this.product_id = product_id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.description = description;
        this.unit = unit;
        this.status = status;
    }

    public Product(long product_id, String name, String manufacturer, String description, String unit, ProductStatus status, List<ProductImage> productImageList, List<OrderDetail> orderDetails) {
        this.product_id = product_id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.description = description;
        this.unit = unit;
        this.status = status;
        this.productImageList = productImageList;
        this.orderDetails = orderDetails;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public List<ProductImage> getProductImageList() {
        return productImageList;
    }

    public void setProductImageList(List<ProductImage> productImageList) {
        this.productImageList = productImageList;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + product_id +
                ", name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", description='" + description + '\'' +
                ", unit='" + unit + '\'' +
                ", status=" + status +
                '}';
    }
}
