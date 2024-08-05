package com.example.test_micro.controller;
import com.example.test_micro.entity.Car;
import com.example.test_micro.service.CarService;
import com.example.test_micro.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CarController {

    private CarService carService;

    public CarController (CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/cars")
    public Car createCar(@RequestBody Car car) {
        return carService.saveCar(car);
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
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car car) {
        if (carService.getCarById(id).isPresent()) {
            car.setId(id);
            return ResponseEntity.ok(carService.saveCar(car));
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
