package vn.edu.iuh.fit.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "order_detail")
@NamedQuery(name = "OrderDetail.findOne", query = "SELECT od from OrderDetail od where product = :product and order = :order")
@NamedQuery(name = "OrderDetail.findAll", query = "SELECT od from OrderDetail od")
public class OrderDetail {
    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;
    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;
    @Column(columnDefinition = "DOUBLE", nullable = false)
    private double price;
    @Column(columnDefinition = "DOUBLE", nullable = false)
    private double quantity;
    @Column(columnDefinition = "VARCHAR(255)")
    private String note;

    public OrderDetail() {
    }

    public OrderDetail(Product product, Order order, double price, double quantity, String note) {
        this.product = product;
        this.order = order;
        this.price = price;
        this.quantity = quantity;
        this.note = note;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "product=" + product +
                ", order=" + order +
                ", price=" + price +
                ", quantity=" + quantity +
                ", note='" + note + '\'' +
                '}';
    }
}
