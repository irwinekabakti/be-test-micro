package com.example.test_micro.graphql;
import com.example.test_micro.entity.Car;
import com.example.test_micro.service.CarService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.Optional;

@Controller
public class CarResolver {

    private final CarService carService;

    public CarResolver(CarService carService) {
        this.carService = carService;
    }


    @MutationMapping
    public Car saveCar(@Argument String brand, @Argument String model, @Argument int year) {
        Car car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year);
        return carService.saveCar(car);
    }

    @QueryMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @QueryMapping
    public Car getCarById(@Argument Long id) {
        return carService.getCarById(id).orElse(null);
    }

    @MutationMapping
    public Car editCar(@Argument Long id, @Argument String brand, @Argument String model, @Argument Integer year) {
        Optional<Car> optionalCar = carService.getCarById(id);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            if (brand != null) car.setBrand(brand);
            if (model != null) car.setModel(model);
            if (year != null) car.setYear(year);
//            return carService.saveCar(car);
            return carService.updateCar(car);  // Use updateCar instead of saveCar

        }
        return null;
    }

    @MutationMapping
    public String deleteCar(@Argument Long id) {
        if (carService.getCarById(id).isPresent()) {
            carService.deleteCar(id);
            return "Car deleted successfully";
        } else {
            return "Car not found";
        }
    }
}