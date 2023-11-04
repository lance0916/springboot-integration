package com.example.util;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import cn.hutool.core.io.FileUtil;
import com.google.gson.Gson;
import org.yaml.snakeyaml.Yaml;

/**
 * @author WuQinglong
 * @since 2023/10/23 4:42 PM
 */
public class TextUtil {

    /**
     * Json转yaml格式
     */
    public static String jsonToYaml(String json) {
        Gson gson = new Gson();
        Map map = gson.fromJson(json, Map.class);

        Yaml yaml = new Yaml();
        return yaml.dumpAsMap(map);
    }

    public static void main(String[] args) {
        String json = FileUtil.readString("/Users/didi/demo.json", StandardCharsets.UTF_8);
        System.out.println(jsonToYaml(json));

    }

}
