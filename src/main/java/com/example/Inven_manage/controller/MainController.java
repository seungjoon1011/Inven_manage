package com.example.Inven_manage.controller;

import com.example.Inven_manage.dto.viewDto;
import com.example.Inven_manage.repository.StockViewRepository;
import com.example.Inven_manage.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final StockService stockService;

    private final StockViewRepository stockViewRepository;

    @GetMapping("/")
    public String getProductStockSummary(Model model) {

        List<viewDto> summaries = stockViewRepository.findAllsm();
        model.addAttribute("summaries", summaries);

        return "stockView";
    }
}
