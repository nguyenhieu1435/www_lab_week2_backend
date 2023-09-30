package vn.edu.iuh.fit.backend.models;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_price")
@NamedQuery(name = "ProductPrice.findOne", query = "SELECT pp from ProductPrice pp where pp.product.id = :product and pp.price_date_time = :price_date_time")
@NamedQuery(name = "ProductPrice.findAll", query ="SELECT pp from ProductPrice pp")
@NamedQuery(name = "ProductPrice.getNewestOneByProductId", query = "SELECT pp from ProductPrice pp where pp.product.id = :productId order by pp.price_date_time desc")
public class ProductPrice {
    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(columnDefinition = "VARCHAR(255)")
    private String note;
    @Id
    @JsonbDateFormat(value = "yyyy-MM-dd")
    private LocalDateTime price_date_time;
    @Column(columnDefinition = "DOUBLE", nullable = false)
    private double price;

    public ProductPrice() {

    }

    public ProductPrice(Product product, String note, LocalDateTime price_date_time, double price) {
        this.product = product;
        this.note = note;
        this.price_date_time = price_date_time;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String node) {
        this.note = node;
    }

    public LocalDateTime getPrice_date_time() {
        return price_date_time;
    }

    public void setPrice_date_time(LocalDateTime price_date_time) {
        this.price_date_time = price_date_time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductPrice{" +
                "product=" + product +
                ", note='" + note + '\'' +
                ", price_date_time=" + price_date_time +
                ", price=" + price +
                '}';
    }
}
