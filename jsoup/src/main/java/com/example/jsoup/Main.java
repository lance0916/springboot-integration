package com.example.jsoup;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WuQinglong
 * @since 2023/9/12 3:41 PM
 */
public class Main {

    public static void main(String[] args) throws IOException {

        List<Object> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            String url = "https://www.ttlingqian.com/guanyin/" + i + ".html";
            list.add(toJsonString(url));
            ThreadUtil.sleep(100);
        }
        FileUtil.writeString(JSONUtil.toJsonStr(list), "/Users/didi/guanyin.text", StandardCharsets.UTF_8);

    }

    private static Object toJsonString(String url) throws IOException {
        Map<String, Object> dataMap = new HashMap<>();

        Document document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36 Edg/116.0.1938.76").get();

        // 签的标题
        Element titleElement = document.selectFirst("div.lqtit_result");
        dataMap.put("title", titleElement.text());

        // 签的图片
        Element imgElement = document.selectFirst("div.lqresult").selectFirst("img");
        dataMap.put("img", imgElement.attr("src"));

        // 签的内容
        List<Object> contentList = new ArrayList<>();
        Elements trElements = document.select("tr.fisttd");
        for (Element trElement : trElements) {
            Element keyElement = trElement.selectFirst("td.tab_tit");
            Element valElement = trElement.selectFirst("td.tab_contet");
            String content = valElement.text().replaceAll(" ", "\n");

            contentList.add(MapBuilder.create().put("key", keyElement.text()).put("val", content).build());
        }
        dataMap.put("content", contentList);

        System.out.println("完成抓取: " + url);

        return dataMap;
    }

}
