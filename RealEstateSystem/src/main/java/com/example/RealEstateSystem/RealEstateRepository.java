package com.example.RealEstateSystem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface RealEstateRepository extends JpaRepository<RealEstate, String> {
    @Query("SELECT AVG(price) FROM RealEstate re WHERE re.regionID = :regionID AND re.area >= :minArea AND re.area <= :maxArea AND re.type IN :types AND re.fetchDate >= :dateSince AND re.fetchDate <= :dateUntil")
    BigDecimal findAveragePrice(@Param("regionID") String regionID,
                                @Param("minArea") BigDecimal minArea,
                                @Param("maxArea") BigDecimal maxArea,
                                @Param("types") List<String> types,
                                @Param("dateSince") LocalDate dateSince,
                                @Param("dateUntil") LocalDate dateUntil);
}
