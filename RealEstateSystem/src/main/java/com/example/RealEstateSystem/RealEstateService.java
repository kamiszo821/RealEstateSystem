package com.example.RealEstateSystem;

import com.example.RealEstateSystem.RealEstate;
import com.example.RealEstateSystem.RealEstateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class RealEstateService {

    @Autowired
    private RealEstateRepository repository;

    private static final String API_URL = "https://run.mocky.io/v3/1fdcb9b7-cce5-4d4e-9a52-e2af8f0d8e9a";
    private static final List<String> REGIONS = Arrays.asList("DLN_WROC_C", "DLN_WROC_PC", "DLN_POZA_WROC", "SL_POL", "SL_KATO", "SL_PN", "M_WAW_CE", "M_WAW_W", "M_WAW_Z", "LUBL", "LUBL_INNE");

    //@Scheduled(cron = "0 0 21 * * ?")
    public void fetchDataFromApi() {
        RestTemplate restTemplate = new RestTemplate();

        for (String region : REGIONS) {
            int page = 0;
            Map<String, Object> response = null;
            do {
                response = restTemplate.getForObject(API_URL + "/" + region + "?page=" + page, Map.class);
                List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("data");
                for (Map<String, Object> record : data) {
                    RealEstate realEstate = new RealEstate();
                    realEstate.setId((String) record.get("id"));
                    realEstate.setType((String) record.get("type"));
                    realEstate.setPrice(new BigDecimal((String) record.get("price")));
                    realEstate.setDescription((String) record.get("description"));
                    realEstate.setArea(new BigDecimal((String) record.get("area")));
                    realEstate.setRooms((Integer) record.get("rooms"));
                    realEstate.setRegionID(region);
                    realEstate.setFetchDate(LocalDate.now());

                    repository.save(realEstate);
                }

                page += 1;
            } while (response != null && page < (Integer) response.get("totalPages"));
        }
    }
}
