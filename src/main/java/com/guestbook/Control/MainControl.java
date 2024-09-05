package com.guestbook.Control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainControl {

    @GetMapping("/")
    public String main(Model model) {
        return "index";
    }
}
