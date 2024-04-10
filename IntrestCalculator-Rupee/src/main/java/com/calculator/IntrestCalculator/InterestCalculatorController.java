package com.calculator.IntrestCalculator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Controller
public class InterestCalculatorController {

    @GetMapping("/")
    public String showForm() {
        return "form";
    }

    @PostMapping("/calculate")
    public @ResponseBody String calculateInterest(@RequestParam double principal,
                                                  @RequestParam double rate,
                                                  @RequestParam String startDate,
                                                  @RequestParam String endDate,
                                                  @RequestParam String interestType) {
        // Convert start and end dates to LocalDate objects
        LocalDate startDateObj = LocalDate.parse(startDate);
        LocalDate endDateObj = LocalDate.parse(endDate);

        // Calculate the difference between dates
        long daysDifference = ChronoUnit.DAYS.between(startDateObj, endDateObj);
        Period period = Period.between(startDateObj, endDateObj);
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();

        // Calculate interest based on the provided principal, rate, and time
        int interest = 0;
        if (interestType.equals("simple")) {
            interest = (int) ((principal * (rate*12) * daysDifference) / (100 * 365));
        } else if (interestType.equals("compound")) {
            interest = (int) (principal * (Math.pow((1 + (rate*12) / 100), (years + (months / 12.0) + (days / 365.0)))) - principal);
        }

        // Build the result string
        StringBuilder result = new StringBuilder();
        result.append("Interest: ").append(interest).append("<br>");
        result.append("Amount: ").append(principal).append("<br>");
        result.append("Total Amount: ").append(principal+interest).append("<br>");
        result.append("Duration: ");
        if (years > 0) {
            result.append(years).append(" year(s) ");
        }
        if (months > 0) {
            result.append(months).append(" month(s) ");
        }
        if (days > 0) {
            result.append(days).append(" day(s)");
        }

        // Return the result string
        return result.toString();
    }

}


