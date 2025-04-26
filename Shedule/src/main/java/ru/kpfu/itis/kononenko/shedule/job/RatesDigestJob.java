package ru.kpfu.itis.kononenko.shedule.job;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.kononenko.shedule.service.CurrencyService;
import ru.kpfu.itis.kononenko.shedule.service.MailService;
import ru.kpfu.itis.kononenko.shedule.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class RatesDigestJob {

    private final CurrencyService currencyService;
    private final UserService userService;
    private final MailService mailService;

    @Scheduled(cron = "${rates.cron}", zone = "Europe/Moscow")
    public void sendRates() {

        BigDecimal usdRub = currencyService.getUsdRub().get("USD/RUB");

        String body = """
            Доброе утро!

            1 USD = %s RUB

            Хорошего дня!
            """.formatted(usdRub);

        userService.findAllVerified()
                .forEach(u -> mailService.sendSimpleEmail(
                        u.getEmail(),
                        "Курс USD → RUB на " + LocalDate.now(),
                        body));
    }
}
