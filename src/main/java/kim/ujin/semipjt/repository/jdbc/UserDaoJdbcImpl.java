package kim.ujin.semipjt.repository.jdbc;

import kim.ujin.semipjt.repository.UserDao;
import kim.ujin.semipjt.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public int count() throws DataAccessException {

        int count = jdbc.queryForObject("SELECT COUNT(*) FROM user", Integer.class);

        return count;
    }

    @Override
    public int insertOne(User user) throws DataAccessException {

        String password = passwordEncoder.encode(user.getPassword());

        int rowNumber = jdbc.update("INSERT INTO user(email_id, "
                + "password, "
                + "username, "
                + "gender, "
                + "role) "
                + "VALUES(?, ?, ?, ?, ?)",
                user.getEmail_id(),
                password,
                user.getUsername(),
                user.isGender(),
                user.getRole()
                );

        return rowNumber;
    }

    @Override
    public User selectOne(String email_id) throws DataAccessException {
        Map<String, Object> map = jdbc.queryForMap("SELECT * FROM user"
                + " WHERE email_id = ?", email_id);

        User user = new User();

        user.setEmail_id((String) map.get("email_id"));
        user.setPassword((String) map.get("password"));
        user.setUsername((String) map.get("username"));
        user.setGender((Boolean) map.get("gender"));
        user.setRole((String) map.get("role"));

        return user;
    }

    @Override
    public List<User> selectMany() throws DataAccessException {

        List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM user");

        List<User> userList = new ArrayList<>();

        for (Map<String, Object> map : getList) {
            User user = new User();

            user.setEmail_id((String) map.get("email_id"));
            user.setPassword((String) map.get("password"));
            user.setUsername((String) map.get("username"));
            user.setGender((Boolean) map.get("gender"));
            user.setRole((String) map.get("role"));

            userList.add(user);
        }

        return userList;
    }

    @Override
    public int updateOne(User user) throws DataAccessException {

        String password = passwordEncoder.encode(user.getPassword());

        int rowNumber = jdbc.update("UPDATE user "
                + "SET "
                + "password = ?, "
                + "username = ?, "
                + "gender = ? "
                + "WHERE email_id = ?",
                password,
                user.getUsername(),
                user.isGender(),
                user.getEmail_id());

        return rowNumber;
    }

    @Override
    public int deleteOne(String email_id) throws DataAccessException {

        int rowNumber = jdbc.update("DELETE FROM user WHERE email_id = ?", email_id);

        return rowNumber;
    }

}
