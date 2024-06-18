package it.fanta.fantanet.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.fanta.fantanet.models.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessJsonController {
    @PostMapping("/processJson")
    public void processJson(@RequestBody String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ApiResponse response = objectMapper.readValue(jsonString, ApiResponse.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
