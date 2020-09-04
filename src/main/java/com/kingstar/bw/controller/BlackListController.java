package com.kingstar.bw.controller;


import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import com.kingstar.bw.facade.MatchManagerFacade;
import com.kingstar.bw.service.MatchService;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/blackList")
public class BlackListController {
    @Autowired
    private MatchManagerFacade matchManagerFacade;
    @Autowired
    MatchService matchService;

    @RequestMapping(value = {"/match"}, method = {RequestMethod.POST}, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public  String  match(HttpServletRequest request, HttpServletResponse response, @RequestBody Search search) {
        ChainContext chainContext = new ChainContext();
        chainContext.setSearch(search);
        List<ChainContext> list = matchManagerFacade.match(chainContext);

        return JSONArray.toJSONString(list);
    }

    @RequestMapping(value = {"/underLineMatch"}, method = {RequestMethod.POST}, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public  String  underLineMatch(HttpServletRequest request, HttpServletResponse response) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                matchService.match();
            }
        },"offline blacklist").start();

        return "";
    }
}