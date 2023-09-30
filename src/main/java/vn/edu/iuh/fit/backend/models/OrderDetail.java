package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "order_detail")
@NamedQuery(name = "OrderDetail.findOne", query = "SELECT od from OrderDetail od where od.product.id = :product and od.order.id = :order")
@NamedQuery(name = "OrderDetail.findAll", query = "SELECT od from OrderDetail od")
@NamedQuery(name = "OrderDetail.findAllByOrderId", query = "SELECT od from OrderDetail od where od.order.id = :orderID")
@NamedQuery(name = "OrderDetail.getOrderDetailByNumPage", query = "SELECT od from OrderDetail  od where od.order.id = :orderID")
public class OrderDetail {
    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetail that = (OrderDetail) o;
        return Objects.equals(product, that.product) && Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, order);
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
