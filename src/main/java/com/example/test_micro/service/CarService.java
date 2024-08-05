package com.example.test_micro.service;
import com.example.test_micro.entity.Car;
import com.example.test_micro.repository.CarRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class CarService {

    private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public Car updateCar(Car car) {
        return saveCar(car);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}
