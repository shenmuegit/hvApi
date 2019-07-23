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

    @GetMapping(value = "/index")
    public String index(){
        return "ublue/index";
    }

    @GetMapping(value = "/11")
    public String aa(){
        return "ublue/22";
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
}
