package kim.ujin.semipjt.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignupForm {

    //입력필수, 이메일 형식
    @NotBlank(groups = ValiGroup1.class)
    @Email(groups = ValiGroup2.class)
    private String email_id;

    //입력필수
    @NotBlank(groups = ValiGroup1.class)
    private String username;

    //입력필수, 8~16자, 영숫자조합
    @NotBlank(groups = ValiGroup1.class)
    @Length(min = 8, max = 16, groups = ValiGroup2.class)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,16}$", groups = ValiGroup3.class)
    private String password;

    //남자만 선택 가능
    @AssertFalse(groups = ValiGroup2.class)
    private boolean gender;

}
