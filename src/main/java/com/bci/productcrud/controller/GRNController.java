package com.bci.productcrud.controller;

import com.bci.productcrud.model.GRN;
import com.bci.productcrud.model.GRNItem;
import com.bci.productcrud.model.PurchaseOrder;
import com.bci.productcrud.model.PurchaseOrderItem;
import com.bci.productcrud.repository.PurchaseOrderRepository;
import com.bci.productcrud.service.GRNService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/grn")
public class GRNController {

    private final GRNService service;
    private final PurchaseOrderRepository purchaseOrderRepository;

    public GRNController(
            GRNService service,
            PurchaseOrderRepository purchaseOrderRepository) {

        this.service = service;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @GetMapping({"", "/", "/list"})
    public String listGRNs(Model model) {

        model.addAttribute(
                "grns",
                service.getAllGRNs()
        );

        return "grn/list";
    }

    @GetMapping("/new")
    public String newGRN(Model model) {

        GRN grn = new GRN();

        grn.setReceivedDate(LocalDate.now());
        grn.setStatus("RECEIVED");

        GRNItem item = new GRNItem();
        item.setGrn(grn);

        grn.getItems().add(item);

        model.addAttribute(
                "grn",
                grn
        );

        model.addAttribute(
                "purchaseOrders",
                purchaseOrderRepository.findAll()
        );

        return "grn/form";
    }

    @PostMapping("/save")
    public String saveGRN(
            @ModelAttribute("grn") GRN grn) {

        if (grn.getPurchaseOrder() != null
                && grn.getPurchaseOrder().getId() != null) {

            PurchaseOrder po =
                    purchaseOrderRepository
                            .findById(
                                    grn.getPurchaseOrder().getId()
                            )
                            .orElse(null);

            grn.setPurchaseOrder(po);

            if (po != null
                    && po.getItems() != null
                    && !po.getItems().isEmpty()
                    && grn.getItems() != null
                    && !grn.getItems().isEmpty()) {

                PurchaseOrderItem poItem =
                        po.getItems().get(0);

                GRNItem grnItem =
                        grn.getItems().get(0);

                grnItem.setGrn(grn);

                grnItem.setProduct(
                        poItem.getProduct()
                );

                grnItem.setOrderedQuantity(
                        poItem.getQuantity()
                );

                grnItem.setUnitPrice(
                        poItem.getUnitPrice()
                );
            }
        }

        service.saveGRN(grn);

        return "redirect:/grn";
    }

    @GetMapping("/edit/{id}")
    public String editGRN(
            @PathVariable Long id,
            Model model) {

        GRN grn =
                service.getGRNById(id);

        if (grn == null) {
            return "redirect:/grn";
        }

        if (grn.getItems() == null
                || grn.getItems().isEmpty()) {

            GRNItem item =
                    new GRNItem();

            item.setGrn(grn);

            grn.getItems().add(item);
        }

        model.addAttribute(
                "grn",
                grn
        );

        model.addAttribute(
                "purchaseOrders",
                purchaseOrderRepository.findAll()
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