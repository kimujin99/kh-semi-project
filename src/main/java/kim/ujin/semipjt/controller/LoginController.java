package kim.ujin.semipjt.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public String getLogin(Model model){
        return "login/login";
    }

    @PostMapping("/login")
    public String postLogin(Model model) {
        return "redirect:/board/board";
    }

    @PostMapping("/logout")
    public String postLogout() {
        return "redirect:/login/login";
    }

}
