package com.example.RealEstateSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class RealEstateController {
    @Autowired
    private RealEstateRepository repository;


    @Autowired
    private RealEstateService realEstateService;


    @GetMapping("/api/fetch-data")
    public void fetchData() {
        realEstateService.fetchDataFromApi();
    }
    @GetMapping("/api/real-estates-stats/{regionID}")
    public Map<String, BigDecimal> getAveragePrice(@PathVariable String regionID, @RequestParam String size, @RequestParam List<String> types, @RequestParam String dateSince, @RequestParam String dateUntil) {
        BigDecimal minArea;
        BigDecimal maxArea;

        switch (size.toUpperCase()) {
            case "S":
                minArea = new BigDecimal("18");
                maxArea = new BigDecimal("45");
                break;
            case "M":
                minArea = new BigDecimal("46");
                maxArea = new BigDecimal("80");
                break;
            case "L":
                minArea = new BigDecimal("81");
                maxArea = new BigDecimal("400");
                break;
            default:
                throw new IllegalArgumentException("Invalid size parameter");
        }

        LocalDate fromDate = LocalDate.parse(dateSince, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate toDate = LocalDate.parse(dateUntil, DateTimeFormatter.ofPattern("yyyyMMdd"));

        BigDecimal averagePrice = repository.findAveragePrice(regionID, minArea, maxArea, types, fromDate, toDate);

        return Collections.singletonMap("avgValue", averagePrice);
    }
}
