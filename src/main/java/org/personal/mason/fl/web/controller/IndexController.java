package org.personal.mason.fl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA.
 * PoUser: xvitcoder
 * Date: 12/20/12
 * Time: 5:27 PM
 */
@Controller
@RequestMapping("/")
public class IndexController {


    @RequestMapping
    public String getIndexPage() {
        return "index";
    }

    @RequestMapping("/view")
    public String getRailwayStationPartialPage(@RequestParam("vn") String viewName) {
        return viewName;
    }
}
