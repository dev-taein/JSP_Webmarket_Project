package mvc.command;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.model.BoardDAO;
import mvc.model.BoardDTO;


//게시글을 작성하고 해당 게시글을 DB에 저장하는 커맨드 객체
public class BWriteCommand implements BCommand {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		BoardDAO bDao = BoardDAO.getInstance();
		
		BoardDTO board = new BoardDTO();
		board.setId(request.getParameter("id"));
		board.setName(request.getParameter("name"));
		board.setSubject(request.getParameter("subject"));
		board.setContent(request.getParameter("content"));
		
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy/MM/dd(HH:mm:ss");
		String regist_day = sFormat.format(new Date());
		board.setRegist_day(regist_day);
		board.setHit(0);
		board.setIp(request.getRemoteAddr());
		
		//DB에 저장하는 메서드 호출
		bDao.insertBoard(board);
	}
}
