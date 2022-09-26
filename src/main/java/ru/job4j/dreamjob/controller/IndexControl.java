package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.utils.UserUtil;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexControl {

    @GetMapping("/index")
    public String index(Model model, HttpServletRequest req) {
        model.addAttribute("user", UserUtil.getSessionUser(req));
        return "index";
    }
}