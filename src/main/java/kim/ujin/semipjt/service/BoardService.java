package kim.ujin.semipjt.service;

import kim.ujin.semipjt.repository.BoardDao;
import kim.ujin.semipjt.user.Board;
import kim.ujin.semipjt.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    @Autowired
    @Qualifier("BoardDaoJdbcImpl")
    BoardDao dao;

    public int count() {
        return dao.count();
    }

    public List<Board> selectMany() {
        return dao.selectMany();
    }

    public boolean insert(Board board) {

        int rowNumber = dao.insertOne(board);

        boolean result = false;

        if(rowNumber > 0) {
            result = true;
        }
        return result;
    }

    public Board selectOne(Long board_no) {
        return dao.selectOne(board_no);
    }

    public boolean updateOne (Board board) {
        boolean result = false;

        int rowNumber = dao.updateOne(board);

        if(rowNumber > 0) {
            result = true;
        }
        return result;
    }

    public boolean deleteOne (Long board_no) {

        int rowNumber = dao.deleteOne(board_no);

        boolean result = false;
        if(rowNumber > 0) {
            result = true;
        }
        return result;
    }
}
