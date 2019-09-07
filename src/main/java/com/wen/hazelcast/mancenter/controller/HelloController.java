package com.wen.hazelcast.mancenter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: wen <br>
 * @date: 2019/09/07 23:54 <br>
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public Map<String,Object> hello(HttpServletRequest request, @RequestParam(value = "name", required = false, defaultValue = "wen") String name) {
        Map<String,Object> map = new HashMap<>(3);
        map.put("code",200);
        map.put("msg","SUCCESS");
        map.put("data","Hello " + name);
        return map;
    }

}
