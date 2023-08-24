package com.lundong.k3wise.service;

import com.lundong.k3wise.entity.PurchaseContract;

import java.util.List;

/**
 * 采购合同/合同(应付)
 *
 * @author RawChen
 * @date 2023-08-23 17:48
 */
public interface PurchaseContractService {

    void syncPurchaseContract();

    List<PurchaseContract> purchaseContractList();

}
