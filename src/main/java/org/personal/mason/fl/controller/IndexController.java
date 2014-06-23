package org.personal.mason.fl.controller;

import org.personal.mason.fl.domain.repository.CommentMerchantRepository;
import org.personal.mason.fl.domain.repository.CommentProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * User: xvitcoder
 * Date: 12/20/12
 * Time: 5:27 PM
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    CommentMerchantRepository commentMerchantRepository;

    @Autowired
    CommentProductRepository commentProductRepository;

    @RequestMapping
    public String getIndexPage() {
        return "index";
    }
}
