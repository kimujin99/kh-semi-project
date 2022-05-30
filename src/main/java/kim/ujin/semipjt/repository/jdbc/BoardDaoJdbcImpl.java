package kim.ujin.semipjt.repository.jdbc;

import kim.ujin.semipjt.repository.BoardDao;
import kim.ujin.semipjt.user.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository("BoardDaoJdbcImpl")
public class BoardDaoJdbcImpl implements BoardDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public int count() throws DataAccessException {

        int count = jdbc.queryForObject("SELECT COUNT(*) FROM board", Integer.class);

        return count;
    }

    @Override
    public int insertOne(Board board) throws DataAccessException {

        int rowNumber = jdbc.update("INSERT INTO board(email_id, "
                        + "title, "
                        + "content) "
                        + "VALUES(?, ?, ?)",
                board.getEmail_id(),
                board.getTitle(),
                board.getContent()
        );

        return rowNumber;
    }

    @Override
    public Board selectOne(Long board_no) throws DataAccessException {

        Map<String, Object> map = jdbc.queryForMap("SELECT * FROM board"
                + " WHERE board_no = ?", board_no);

        Board board = new Board();
        board.setBoard_no((Long) map.get("board_no"));
        board.setEmail_id((String) map.get("email_id"));
        board.setTitle((String) map.get("title"));
        board.setContent((String) map.get("content"));
        board.setRegdate((Date) map.get("regdate"));

        return board;
    }

    @Override
    public List<Board> selectMany() throws DataAccessException {

        List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM board");

        List<Board> boardList = new ArrayList<>();

        for (Map<String, Object> map : getList) {
            Board board = new Board();

            board.setBoard_no((Long) map.get("board_no"));
            board.setEmail_id((String) map.get("email_id"));
            board.setTitle((String) map.get("title"));
            board.setContent((String) map.get("content"));
            board.setRegdate((Date) map.get("regdate"));

            boardList.add(board);
        }

        return boardList;
    }

    @Override
    public int updateOne(Board board) throws DataAccessException {

        int rowNumber = jdbc.update("UPDATE board "
                        + "SET "
                        + "title = ?, "
                        + "content = ? "
                        + "WHERE board_no = ?",
                board.getTitle(),
                board.getContent(),
                board.getBoard_no());

        return rowNumber;
    }

    @Override
    public int deleteOne(Long board_no) throws DataAccessException {

        int rowNumber = jdbc.update("DELETE FROM board WHERE board_no = ?", board_no);

        return rowNumber;
    }

}
