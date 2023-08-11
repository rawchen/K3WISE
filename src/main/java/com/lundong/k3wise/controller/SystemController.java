package com.lundong.k3wise.controller;

import com.lundong.k3wise.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RawChen
 * @date 2023-06-25 14:02
 */
@RestController
@RequestMapping("/api/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    /**
     * 同步采购申请单
     *
     * @return
     */
    @GetMapping("/syncPurchaseRequisition")
    public String initDepartment() {
        return systemService.syncPurchaseRequisition();
    }
}
