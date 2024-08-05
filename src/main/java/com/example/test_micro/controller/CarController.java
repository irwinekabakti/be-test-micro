/*
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

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/cars")
    public ResponseEntity<CustomResponse<Car>> createCar(@RequestBody Car car) {
        Car savedCar = carService.saveCar(car);
        CustomResponse<Car> response = new CustomResponse<>(
                HttpStatus.CREATED, "Car created successfully", "Car successfully created", savedCar
        );
        return response.toResponseEntity();
    }

    @GetMapping("/cars")
    public ResponseEntity<CustomResponse<List<Car>>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        CustomResponse<List<Car>> response = new CustomResponse<>(
                HttpStatus.OK, "Cars retrieved successfully", "Cars successfully retrieved", cars
        );
        return response.toResponseEntity();
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<CustomResponse<Car>> getCarById(@PathVariable Long id) {
        Optional<Car> car = carService.getCarById(id);
        if (car.isPresent()) {
            CustomResponse<Car> response = new CustomResponse<>(
                    HttpStatus.OK, "Car retrieved successfully", "Car successfully retrieved", car.get()
            );
            return response.toResponseEntity();
        } else {
            CustomResponse<Car> response = new CustomResponse<>(
                    HttpStatus.NOT_FOUND, "Car not found"
            );
            return response.toResponseEntity();
        }
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<CustomResponse<Car>> updateCar(@PathVariable Long id, @RequestBody Car car) {
        if (carService.getCarById(id).isPresent()) {
            car.setId(id);
            Car updatedCar = carService.saveCar(car);
            CustomResponse<Car> response = new CustomResponse<>(
                    HttpStatus.OK, "Car updated successfully", "Car successfully updated", updatedCar
            );
            return response.toResponseEntity();
        } else {
            CustomResponse<Car> response = new CustomResponse<>(
                    HttpStatus.NOT_FOUND, "Car not found"
            );
            return response.toResponseEntity();
        }
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteCar(@PathVariable Long id) {
        if (carService.getCarById(id).isPresent()) {
            carService.deleteCar(id);
            CustomResponse<Void> response = new CustomResponse<>(
                    HttpStatus.NO_CONTENT, "Car deleted successfully"
            );
            return response.toResponseEntity();
        } else {
            CustomResponse<Void> response = new CustomResponse<>(
                    HttpStatus.NOT_FOUND, "Car not found"
            );
            return response.toResponseEntity();
        }
    }
}
*/

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
//    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
//        if (carService.getCarById(id).isPresent()) {
//            carService.deleteCar(id);
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

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