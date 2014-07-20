package org.personal.mason.fl.web.controller;

import org.springframework.mobile.device.Device;
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
//@RequestMapping("/")
public class IndexController {


    @RequestMapping({"/", "/index"})
    public String getIndexPage(Device device) {
        if(device != null && device.isMobile() || device.isTablet()){
            return "mobile/" + "index";
        } else {
            return "normal/" + "index";
        }
//        return "mobile/" + "index";
    }

    @RequestMapping("/view")
    public String getRailwayStationPartialPage(Device device,
                                               @RequestParam("vn") String viewName) {
        if(device != null && device.isMobile() || device.isTablet()){
            return "mobile/" + viewName;
        } else {
            return "normal/" + viewName;
        }
//        return "mobile/" + viewName;
    }
}
