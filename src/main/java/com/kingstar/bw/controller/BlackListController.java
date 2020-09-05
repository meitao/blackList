package com.kingstar.bw.controller;


import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import com.kingstar.bw.facade.MatchManagerFacade;
import com.kingstar.bw.service.MatchService;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.Unsafe;

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
    //是否正在离线处理，离线处理未结束不重复操作,false 为不在匹配，true 为在匹配中
    private  volatile boolean run = false;

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
        if(!run){
            synchronized (this){
                if(run){
                    return "正在进行离线匹配!";
                }
                run = true;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    matchService.match();
                    run = false;
                }
            },"offline blacklist").start();
        }else{
            return "正在进行离线匹配!";
        }
        return "ok";
    }
}