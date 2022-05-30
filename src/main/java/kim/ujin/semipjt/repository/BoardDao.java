package kim.ujin.semipjt.repository;

import kim.ujin.semipjt.user.Board;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface BoardDao {

    // board 테이블의 총 데이터 수 가져오기
    public int count() throws DataAccessException;
    // board 테이블의 데이터를 1건 삽입
    public int insertOne(Board board) throws DataAccessException;
    // board 테이블의 데이터를 1건 얻기
    public Board selectOne(Long board_no) throws DataAccessException;
    // board 테이블의 모든 데이터 가져오기
    public List<Board> selectMany() throws DataAccessException;
    // board 테이블의 데이터를 1건 업데이트
    public int updateOne(Board board) throws DataAccessException;
    // board 테이블의 데이터를 1건 삭제
    public int deleteOne(Long board_no) throws DataAccessException;

}
