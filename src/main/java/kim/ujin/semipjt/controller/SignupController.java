package kim.ujin.semipjt.controller;

import kim.ujin.semipjt.model.GroupOrder;
import kim.ujin.semipjt.model.SignupForm;
import kim.ujin.semipjt.service.UserService;
import kim.ujin.semipjt.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;

    private Map<String, String> radioGender;

    private Map<String, String> initRadioGender() {
        Map<String, String> radio = new LinkedHashMap<>();

        radio.put("여자", "true");
        radio.put("남자", "false");

        return radio;
    }

    @GetMapping("/signup")
    public String getSignup(@ModelAttribute SignupForm form, Model model) {

        radioGender = initRadioGender();
        model.addAttribute("signupForm", form);
        model.addAttribute("radioGender", radioGender);

        return "login/signup";
    }

    @PostMapping("/signup")
    public String postSignup(@ModelAttribute @Validated(GroupOrder.class) SignupForm form,
                             BindingResult bindingResult,
                             Model model) {

        //에러가 있으면 메시지와 함께 get 메소드 호출
        if(bindingResult.hasErrors()) {
            return getSignup(form, model);
        }

        //form 으로 들어온 내용을 콘솔에 출력
        System.out.println(form);

        //insert 용 변수
        User user = new User();
        user.setEmail_id(form.getEmail_id());
        user.setPassword(form.getPassword());
        user.setUsername(form.getUsername());
        user.setGender(form.isGender());
        user.setRole("ROLE_GENERAL");

        //데이터 등록
        boolean result = userService.insert(user);

        //등록 결과 콘솔에 출력
        if (result == true) {
            System.out.println("등록성공");
        } else {
            System.out.println("등록실패");
       }

        return "redirect:/login/login";
    }

}
