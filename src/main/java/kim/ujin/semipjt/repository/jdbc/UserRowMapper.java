package kim.ujin.semipjt.repository.jdbc;

import kim.ujin.semipjt.user.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();

        user.setEmail_id(rs.getString("email_id"));
        user.setPassword(rs.getString("password"));
        user.setUsername(rs.getString("username"));
        user.setGender(rs.getBoolean("gender"));
        user.setRole(rs.getString("role"));

        return user;
    }
}
