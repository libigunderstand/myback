package com.rzon.myback.service;

import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.Map;

public interface WaterMarkService {

    public Map<String, Object> getConfig()throws IOException;

    public Map<String, Object> updateConfig(@RequestBody Map<String, Object> configs) throws IOException;
}
