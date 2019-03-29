package bbcat.controller;

import bbcat.constant.Result;
import bbcat.entity.V9;
import bbcat.service.V9Service;
import bbcat.vo.V9Vo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/h")
public class IndexController {

    @Autowired
    private V9Service v9s;

    private static final Map<String,String> HEADER = new HashMap<String,String>(){{
        put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0");
        put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        put("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        put("Referer","http://e0302.p29.rocks/video.php?category=rf&page=1");
        put("Alt-Used","e0302.p29.rocks:443");
        put("Connection","keep-alive");
        put("Upgrade-Insecure-Requests","1");
        put("Pragma","no-cache");
        put("Cache-Control","no-cache");
    }};


    @GetMapping(value = "/index")
    public String index(){
        return "ublue/index";
    }

    @GetMapping(value = "/details")
    public String details(V9Vo v9Vo, Model model){
        model.addAttribute("url",v9Vo.getUrl());
        return "ublue/details";
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public Result indexData(V9Vo v9Vo){
        IPage<V9> page = v9s.selectV9List(v9Vo.getPage());
        return Result.success(page.getRecords(),page.getTotal());
    }

    @GetMapping(value = "/mp4")
    @ResponseBody
    public Result mp4(V9Vo v9Vo){
        String body = getRes(v9Vo.getUrl());
        if(null == body){
            return Result.fail(null);
        }
        Element eles = Jsoup.parse(body).select("div.example-video-container").first();
        String script = eles.selectFirst("script").toString();
        script = script.substring(script.indexOf("(\"")+1,script.lastIndexOf("\")")+1);
        String[] scriptArray = script.split(",");
        for (int i = 0; i < scriptArray.length; i++) {
            scriptArray[i] = scriptArray[i].substring(1,scriptArray[i].length()-1);
        }
        return Result.success(scriptArray);
    }

    private static String getRes(String url){
        try {
            return Jsoup.connect(url)
                    .headers(HEADER)
                    .execute()
                    .body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
