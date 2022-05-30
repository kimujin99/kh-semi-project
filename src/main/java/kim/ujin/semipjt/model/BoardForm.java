package kim.ujin.semipjt.model;

import kim.ujin.semipjt.user.User;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class BoardForm {

    private Long board_no;

    @NotBlank
    private String email_id;

    @NotBlank
    @Length(max = 100)
    private String title;

    @NotBlank
    @Length(max = 1000)
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regdate;

}


