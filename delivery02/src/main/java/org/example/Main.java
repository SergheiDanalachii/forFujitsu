package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
//DataBase h2 access from browser : http://localhost:8080/h2-console/login.jsp
//Driver Class : org.h2.Driver
//JDBC URL : jdbc:h2:file:./H2-database
//UserName : sa
//
//Name of the city:
//1 - Tallinn-Harku
//2 - Tartu-Tõravere
//3 - Pärnu

//Vehicle_Type
//1 - Car
//2 - Scooter
//3 - Bike

//Postman
//
//for add new city, vehicleType and price:
//POST http://localhost:8080/delivery-prices/add
//
//{
//"city": "Pärnu",
//"vehicleType": "Bike",
//"price": 2.0
//}
//
// to check price for delivery :
// GET http://localhost:8080/calculateDeliveryFee
//
//{
//"city": "Pärnu",
//"vehicleType": "Bike",
//}
//
//to change price for delivery :
// POST localhost:8080/delivery-prices/update
//
//{
//"city": "Pärnu",
//"vehicleType": "Bike",
//"price": 3.0
//}