package com.bci.productcrud.service;

import com.bci.productcrud.model.GRN;
import java.util.List;

public interface GRNService {

    List<GRN> getAllGRNs();

    GRN saveGRN(GRN grn);

    GRN getGRNById(Long id);

    void deleteGRN(Long id);
}