package kim.ujin.semipjt.controller;

import kim.ujin.semipjt.model.BoardForm;
import kim.ujin.semipjt.model.GroupOrder;
import kim.ujin.semipjt.service.BoardService;
import kim.ujin.semipjt.user.Board;
import kim.ujin.semipjt.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/board")
    public String getBoard(Model model) {

        // board 리스트 반환, 추가
        List<Board> boardList = boardService.selectMany();

        int count = boardService.count();

        model.addAttribute("boardList", boardList);
        model.addAttribute("boardListCount", count);

        return "board/board";
    }

    @GetMapping("/board/insert")
    public String getInsert(@ModelAttribute BoardForm boardForm,
                            Principal principal,
                            Model model) {

        model.addAttribute("boardForm", boardForm);
        model.addAttribute("email_id", principal.getName());

        return "board/boardInsert";
    }


    @PostMapping("/board/insert")
    public String postInsert(@ModelAttribute BoardForm boardForm,
                             Principal principal,
                             Model model) {

        //확인용 콘솔 출력
        System.out.println(boardForm);

        //insert 용 변수
        Board board = new Board();
        board.setEmail_id(principal.getName());
        board.setTitle(boardForm.getTitle());
        board.setContent(boardForm.getContent());

        try {

            //데이터 등록
            boolean result = boardService.insert(board);

            //등록 결과 추가
            if (result == true) {
                model.addAttribute("result", "변경성공");
            } else {
                model.addAttribute("result", "변경실패");
            }

        } catch (DataAccessException e) {
            model.addAttribute("result", "변경실패(트랜잭션 테스트)");
        }

        return getBoard(model);
    }

    @GetMapping("/board-detail/{id}")
    public String getBoardDetail(@ModelAttribute BoardForm form,
                                 Model model,
                                 Principal principal,
                                 @PathVariable("id") Long board_no) {

        //아이디 콘솔에 출력
        System.out.println("board_no = " + board_no);

        if(board_no != null && board_no > 0) {

            Board board = boardService.selectOne(board_no);

            form.setBoard_no(board.getBoard_no());
            form.setEmail_id(board.getEmail_id());
            form.setTitle(board.getTitle());
            form.setContent(board.getContent());
            form.setRegdate(board.getRegdate());

            model.addAttribute("boardForm", form);

            if(form.getEmail_id().equals(principal.getName())) {
                model.addAttribute("btn_result", true);
            } else {
                model.addAttribute("btn_result", false);

            }

            return "board/boardDetail";
        }

        return getBoard(model);
    }

    @PostMapping(value = "/board-detail", params = "update")
    public String postBoardDetailUpdate(@ModelAttribute BoardForm form,
                                        @RequestParam("board_no") Long board_no,
                                        Model model) {

        System.out.println("변경 버튼 실행");

        if(board_no != null && board_no > 0) {

            Board board = boardService.selectOne(board_no);

            form.setBoard_no(board.getBoard_no());
            form.setEmail_id(board.getEmail_id());
            form.setTitle(board.getTitle());
            form.setContent(board.getContent());
            form.setRegdate(board.getRegdate());

            model.addAttribute("boardForm", form);

            return "board/boardUpdate";
        }

        return getBoard(model);
    }

    @PostMapping("/board-detail/update")
    public String postBoardDetailUpdateExecute(@ModelAttribute BoardForm form,
                                               @RequestParam("board_no") Long board_no,
                                               Model model) {

        System.out.println("변경 버튼 실행");

        Board board = new Board();

        board.setBoard_no(board_no);
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());

        try {
            // 변경실행
            boolean result = boardService.updateOne(board);
            if (result == true) {
                model.addAttribute("result", "수정성공");
            } else {
                model.addAttribute("result", "수정실패");
            }
        } catch (DataAccessException e) {
            model.addAttribute("result", "수정실패(트랜잭션 테스트)");
        }

        return getBoard(model);
    }

    @PostMapping(value = "/board-detail", params = "delete")
    public String postBoardDetailDelete(@RequestParam("board_no") Long board_no,
                                        Model model) {

        System.out.println("삭제 버튼 실행");

        boolean result = boardService.deleteOne(board_no);

        if (result == true) {
            model.addAttribute("result", "삭제성공");
        } else {
            model.addAttribute("result", "삭제실패");
        }

        return getBoard(model);

    }
}
