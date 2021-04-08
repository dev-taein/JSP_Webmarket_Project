package mvc.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.model.BoardDAO;
import mvc.model.BoardDTO;

//DB에 저장되어있는 게시글을 보고자 할 때 실행하는 커맨드 객체
public class BViewCommand implements BCommand {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		BoardDAO bDao = BoardDAO.getInstance();
		
		//보고자하는 게시글의 번호 가져오기
		int num = Integer.parseInt(request.getParameter("num"));
		//보고자하는 게시글에 있는 페이지 수 가져오기
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		BoardDTO board = new BoardDTO();
		board = bDao.getBoardByNum(num, pageNum);
		
		request.setAttribute("num", num);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("board", board);
	}

}
