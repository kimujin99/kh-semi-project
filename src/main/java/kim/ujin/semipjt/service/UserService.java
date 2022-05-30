package kim.ujin.semipjt.service;

import kim.ujin.semipjt.repository.UserDao;
import kim.ujin.semipjt.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    @Qualifier("UserDaoJdbcImpl")
    UserDao dao;

    //Repository 에서 insertOne 메소드를 호출하여 boolean 값으로 성공 여부 반환
    //false = 실패
    //true = 성공
    public boolean insert(User user) {
        int rowNumber = dao.insertOne(user);

        boolean result = false;

        if(rowNumber > 0) {
            result = true;
        }
        return result;
    }

    //Repository 에서 count 메소드를 호출하여 int 형으로 모든 데이터건수 반환
    public int count() {
        return dao.count();
    }

    //Repository 에서 selectMany 메소드를 호출하여 모든 데이터를 List 형으로 반환
    public List<User> selectMany() {
        return dao.selectMany();
    }

    //Repository 에서 selectOne 메소드를 호출하여 1건의 데이터를 User 형으로 반환
    public User selectOne(String email_id) {
        return dao.selectOne(email_id);
    }

    //Repository 에서 updateOne 메소드를 호출하여 boolean 값으로 성공 여부 반환
    //false = 실패
    //true = 성공
    public boolean updateOne (User user) {
        boolean result = false;

        int rowNumber = dao.updateOne(user);

        if(rowNumber > 0) {
            result = true;
        }
        return result;
    }

    //Repository 에서 deleteOne 메소드를 호출하여 boolean 값으로 성공 여부 반환
    //false = 실패
    //true = 성공
    public boolean deleteOne (String email_id) {

        int rowNumber = dao.deleteOne(email_id);

        boolean result = false;
        if(rowNumber > 0) {
            result = true;
        }
        return result;
    }
    
}
