package kim.ujin.semipjt.user.principal;

import kim.ujin.semipjt.repository.UserDao;
import kim.ujin.semipjt.repository.jdbc.UserDaoJdbcImpl;
import kim.ujin.semipjt.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    @Qualifier("UserDaoJdbcImpl")
    UserDao dao;

    @Override
    public UserDetails loadUserByUsername(String email_id) throws UsernameNotFoundException {
        User principal = dao.selectOne(email_id);

        if(principal == null) {
            new UsernameNotFoundException("해당 사용자를 찾을수 없습니다.:" + email_id);
        }

        return new PrincipalDetail(principal);
    }
}
