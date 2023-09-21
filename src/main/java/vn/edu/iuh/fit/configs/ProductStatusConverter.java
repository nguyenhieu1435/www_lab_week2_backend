package vn.edu.iuh.fit.configs;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import vn.edu.iuh.fit.enums.ProductStatus;
import vn.edu.iuh.fit.models.Product;

@Converter(autoApply = true)
public class ProductStatusConverter implements AttributeConverter<ProductStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProductStatus productStatus) {
        return productStatus.getStatusNumber();
    }

    @Override
    public ProductStatus convertToEntityAttribute(Integer integer) {
        return ProductStatus.fromStatusNumber(integer);
    }
}
