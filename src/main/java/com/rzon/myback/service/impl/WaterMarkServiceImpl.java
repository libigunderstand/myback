package com.rzon.myback.service.impl;

import com.alibaba.fastjson.JSON;
import com.rzon.myback.service.WaterMarkService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

@Service("WaterMarkService")
public class WaterMarkServiceImpl implements WaterMarkService {
    public File getFile() throws IOException {
        String path = System.getProperty("user.dir");

        File file = new File(path + File.separator + "config" + File.separator + "config.json");
        if(!file.exists()) {
            File parent = new File(file.getParent());
            if(!parent.exists()) {
                 parent.mkdirs();
            }
            file.createNewFile();
        }

        return file;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getConfig()throws IOException {
        File file = this.getFile();
        String jsonStr = "";
        try {
            FileReader fileReader = new FileReader(file);
            Reader reader = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object jsonParse = JSON.parse(jsonStr);

        return (Map<String, Object>) jsonParse;
    }

    public Map<String, Object> updateConfig(@RequestBody Map<String, Object> configs) throws IOException {
        File file = this.getFile();
        try {
            if(configs != null){
                FileUtils.writeStringToFile(file, JSON.toJSONString(configs), "UTF-8");
            }
        }catch (IOException e){
            System.out.println("error-writeFile: "+e);
        }
        return configs;
    }
}
