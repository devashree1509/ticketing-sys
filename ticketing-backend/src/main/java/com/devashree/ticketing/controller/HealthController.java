package com.devashree.ticketing.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.devashree.ticketing.dto.ApiResponse;

@RestController

public class HealthController {

    @GetMapping("/api/health")
        public ApiResponse<String> checkHealth(){

        return new ApiResponse<>(
                true,
            "Backend is running successfully",
            "OK"
                    );
        }
}
