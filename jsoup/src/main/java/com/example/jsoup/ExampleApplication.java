package com.example.jsoup;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExampleApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String url = "https://www.ttlingqian.com/guanyin/1.html";
        String html = HttpRequest.get(url).execute().body();

        Document document = Jsoup.parse(html);
        Element element = document.selectFirst("lqresult");




    }

}
