package com.example.test_micro.controller;
import com.example.test_micro.entity.Car;
import com.example.test_micro.service.CarService;
import com.example.test_micro.service.CloudinaryService;
import com.example.test_micro.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CarController {
    private CarService carService;
    private final CloudinaryService cloudinaryService;

    public CarController (CarService carService, CloudinaryService cloudinaryService) {
        this.carService = carService;
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/cars")
//    public Car createCar(@RequestBody Car car) {
//        return carService.saveCar(car);
//    }
    public ResponseEntity<Car> createCar(@RequestParam String brand,
                                         @RequestParam String model,
                                         @RequestParam int year,
                                         @RequestParam(required = false) MultipartFile file) {
        try {
            String imageUrl = null;
            if (file != null && !file.isEmpty()) {
                Map<String, String> uploadResult = cloudinaryService.uploadFile(file);
                imageUrl = uploadResult.get("url");
            }

            Car car = new Car();
            car.setBrand(brand);
            car.setModel(model);
            car.setYear(year);
            car.setImage(imageUrl);

            Car savedCar = carService.saveCar(car);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/cars")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Optional<Car> car = carService.getCarById(id);
        return car.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/cars/{id}")
//    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car car) {
//        if (carService.getCarById(id).isPresent()) {
//            car.setId(id);
//            return ResponseEntity.ok(carService.saveCar(car));
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    public ResponseEntity<Car> updateCar(@PathVariable Long id,
                                         @RequestParam String brand,
                                         @RequestParam String model,
                                         @RequestParam int year,
                                         @RequestParam(required = false) MultipartFile file) {
        Optional<Car> optionalCar = carService.getCarById(id);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            car.setBrand(brand);
            car.setModel(model);
            car.setYear(year);

            if (file != null && !file.isEmpty()) {
                Map<String, String> uploadResult = cloudinaryService.uploadFile(file);
                String imageUrl = uploadResult.get("url");
                car.setImage(imageUrl);
            }

            Car updatedCar = carService.saveCar(car);
            return ResponseEntity.ok(updatedCar);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("/cars/{id}")
    public ResponseEntity<CustomResponse<Car>> deleteCar(@PathVariable Long id) {
        Optional<Car> carOptional = carService.getCarById(id);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            carService.deleteCar(id);
            CustomResponse<Car> response = new CustomResponse<>(
                    HttpStatus.OK, "Success", "Car deleted successfully", car
            );
            return response.toResponseEntity();
        } else {
            CustomResponse<Car> response = new CustomResponse<>(
                    HttpStatus.NOT_FOUND, "Car not found"
            );
            return response.toResponseEntity();
        }
    }
}
