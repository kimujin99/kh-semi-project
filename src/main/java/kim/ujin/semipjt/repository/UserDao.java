package kim.ujin.semipjt.repository;

import kim.ujin.semipjt.user.User;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface UserDao {
    //User 테이블 수 가져오기
    public int count() throws DataAccessException;

    //User 테이블의 데이터 1건 삽입
    public int insertOne(User user) throws DataAccessException;

    //User 테이블의 데이터 1건 얻기
    public User selectOne (String email_id) throws DataAccessException;

    //User 테이블의 모든 데이터 가져오기
    public List<User> selectMany() throws DataAccessException;

    //User 테이블의 데이터 1건 업데이트
    public int updateOne(User user) throws DataAccessException;

    //User 테이블의 데이터 삭제
    public int deleteOne(String email_id) throws DataAccessException;

}
