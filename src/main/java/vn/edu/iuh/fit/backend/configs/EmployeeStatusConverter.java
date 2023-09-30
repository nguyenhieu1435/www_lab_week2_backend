package vn.edu.iuh.fit.backend.configs;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import vn.edu.iuh.fit.backend.enums.EmployeeStatus;

@Converter(autoApply = true)
public class EmployeeStatusConverter implements AttributeConverter<EmployeeStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EmployeeStatus employeeStatus) {
        return employeeStatus.getNumberStatus();
    }

    @Override
    public EmployeeStatus convertToEntityAttribute(Integer i) {
        return EmployeeStatus.fromStatusNumber(i);
    }
}
