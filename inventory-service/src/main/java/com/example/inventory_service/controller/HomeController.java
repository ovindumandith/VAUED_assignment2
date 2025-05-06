package com.example.inventory_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "Welcome to the Inventory Service API. Available endpoints:<br/>" +
                "<ul>" +
                "<li><a href='/api/products'>Products API</a></li>" +
                "<li><a href='/actuator'>Actuator Endpoints</a></li>" +
                "<li><a href='/actuator/prometheus'>Prometheus Metrics</a></li>" +
                "<li><a href='/h2-console'>H2 Database Console</a></li>" +
                "<li><a href='/custom-metrics'>Custom Metrics</a></li>" +
                "</ul>";
    }
}
