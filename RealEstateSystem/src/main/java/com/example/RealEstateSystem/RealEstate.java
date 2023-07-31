package com.example.RealEstateSystem;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class RealEstate {
    @Id
    private String id;
    private String type;
    private BigDecimal price;
    private String description;
    private BigDecimal area;
    private int rooms;
    private String regionID;
    private LocalDate fetchDate;
}
