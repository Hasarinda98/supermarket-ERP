package com.bci.productcrud.controller;

import com.bci.productcrud.model.GRN;
import com.bci.productcrud.service.GRNService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/grn")
public class GRNController {

    private final GRNService service;

    public GRNController(GRNService service) {
        this.service = service;
    }

  @GetMapping({"", "/", "/list"})
public String listGRNs(Model model) {
    model.addAttribute("grns", service.getAllGRNs());
    return "grn/list";
}

    @GetMapping("/new")
    public String newGRN(Model model) {

        model.addAttribute("grn", new GRN());

        return "grn/form";
    }

    @PostMapping("/save")
    public String saveGRN(
            @ModelAttribute GRN grn) {

        service.saveGRN(grn);

        return "redirect:/grn";
    }

    @GetMapping("/edit/{id}")
    public String editGRN(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "grn",
                service.getGRNById(id)
        );

        return "grn/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteGRN(
            @PathVariable Long id) {

        service.deleteGRN(id);

        return "redirect:/grn";
    }
}