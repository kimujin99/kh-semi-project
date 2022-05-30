package kim.ujin.semipjt.user;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

@Data
public class Board {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long board_no;
    private String email_id;
    private String title;
    private String content;
    private Date regdate;

}
