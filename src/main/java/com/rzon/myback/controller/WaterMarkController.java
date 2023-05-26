package com.rzon.myback.controller;

import com.rzon.myback.model.ResponseData;
import com.rzon.myback.model.Result;
import com.rzon.myback.service.WaterMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * @author Gary
 */
@RestController
public class WaterMarkController {

    @Autowired
    private WaterMarkService waterMarkService;

    @GetMapping("/config")
    public Result<Map<String, Object>> getConfig()throws IOException {

        Map<String, Object> res = waterMarkService.getConfig();

        return ResponseData.success(res);
    }

    @PostMapping("/config/update")
    public Result<Map<String, Object>> updateConfig(@RequestBody Map<String, Object> configs) throws IOException {

        Map<String, Object> res = waterMarkService.updateConfig(configs);

        return ResponseData.success(res);
    }
}
