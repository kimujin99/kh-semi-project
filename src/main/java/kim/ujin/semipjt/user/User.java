package kim.ujin.semipjt.user;

import lombok.*;

@Data
public class User {

    private String email_id; //아이디
    private String username; //이름
    private String password; //비밀번호
    private boolean gender; //성별
    private String role; //권한

}
