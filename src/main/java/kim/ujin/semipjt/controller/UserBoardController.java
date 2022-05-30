package kim.ujin.semipjt.controller;

import kim.ujin.semipjt.model.SignupForm;
import kim.ujin.semipjt.service.UserService;
import kim.ujin.semipjt.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserBoardController {

    @Autowired
    UserService userService;

    private Map<String, String> radioGender;

    private Map<String, String> initRadioGender() {
        Map<String, String> radio = new LinkedHashMap<>();

        radio.put("여자", "true");
        radio.put("남자", "false");

        return radio;
    }

    @GetMapping("/user-board")
    public String getUserBoard(Model model) {

        //회원목록 저장
        List<User> userList = userService.selectMany();
        model.addAttribute("userList", userList);

        //회원 총 데이터 건수 저장
        int count = userService.count();
        model.addAttribute("userListCount", count);

        return "login/userBoard";
    }

    @GetMapping("/user-detail/{id:.+}")
    public String getUserDetail (@ModelAttribute SignupForm form,
                                 Model model,
                                 @PathVariable("id") String email_id) {
        //아이디 콘솔에 출력
        System.out.println("email_id = " + email_id);

        radioGender = initRadioGender();
        model.addAttribute("radioGender", radioGender);

        if(email_id != null && email_id.length() > 0) {
            User user = userService.selectOne(email_id);

            form.setEmail_id(user.getEmail_id());
            form.setUsername(user.getUsername());
            form.setGender(user.isGender());

            model.addAttribute("signupForm", form);

        }

        return "login/userDetail";
    }

    @PostMapping(value = "/user-detail", params = "update")
    public String postUserDetailUpdate(@ModelAttribute SignupForm form,
                                       Model model) {
        System.out.println("변경 버튼 실행");

        //update 용 변수
        User user = new User();

        user.setEmail_id(form.getEmail_id());
        user.setPassword(form.getPassword());
        user.setUsername(form.getUsername());
        user.setGender(form.isGender());

        try {
            // 변경실행
            boolean result = userService.updateOne(user);
            if (result == true) {
                model.addAttribute("result", "변경성공");
            } else {
                model.addAttribute("result", "변경실패");
            }
        } catch (DataAccessException e) {
            model.addAttribute("result", "변경실패(트랜잭션 테스트)");
        }

        //사용자 목록화면 표시
        return getUserBoard(model);
    }

    @PostMapping(value = "/user-detail", params = "delete")
    public String postUserDetailDelete(@ModelAttribute SignupForm form,
                                       Model model) {
        System.out.println("삭제 버튼 실행");

        //삭제 실행
        boolean result = userService.deleteOne(form.getEmail_id());

        //삭제 성공 여부 저장
        if (result == true) {
            model.addAttribute("result", "삭제성공");
        } else {
            model.addAttribute("result", "삭제실패");
        }

        // 사용자 목록화면 표시
        return getUserBoard(model);
    }
}
