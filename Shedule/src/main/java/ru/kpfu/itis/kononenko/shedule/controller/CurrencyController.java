package ru.kpfu.itis.kononenko.shedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kpfu.itis.kononenko.shedule.service.CurrencyService;

import java.time.LocalDate;


@Controller
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/currency")
    public String showPopular(Model model) {
        model.addAttribute("date", LocalDate.now());
        model.addAttribute("rates", currencyService.getPopularToRub());
        return "currency";
    }
}
