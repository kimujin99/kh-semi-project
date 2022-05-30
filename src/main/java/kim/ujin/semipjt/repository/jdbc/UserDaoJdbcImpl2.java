package kim.ujin.semipjt.repository.jdbc;

import kim.ujin.semipjt.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserDaoJdbcImpl2")
public class UserDaoJdbcImpl2 extends UserDaoJdbcImpl {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public User selectOne(String email_id) {
        String sql = "SELECT * FROM user WHERE user_id = ?";

        //RowMapper 생성
        UserRowMapper rowMapper = new UserRowMapper();

        return jdbc.queryForObject(sql, rowMapper, email_id);
    }

    @Override
    public List<User> selectMany() {
        String sql = "SELECT * FROM user";

        //RowMapper 생성
        UserRowMapper rowMapper = new UserRowMapper();

        return jdbc.query(sql, rowMapper);

    }
}
