package by.itacademy.car.rental.silina.controller;

import by.itacademy.car.rental.silina.dto.CarDTO;
import by.itacademy.car.rental.silina.service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final ModelMapper modelMapper;

    public CarController(CarService carService, ModelMapper modelMapper) {
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/get/{id}")
    public @ResponseBody
    CarDTO getCarById(@PathVariable("id") Integer carId) {
        return modelMapper.map(carService.getCarById(carId), CarDTO.class);
    }

    @GetMapping("")
    public String getAllCars(Model model) {
        model.addAttribute("cars", carService.getAll());
        return "showAllCars";
    }
}
