package mvc.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.model.BoardDAO;

//DB에 저장되어 있는 게시글 삭제를 하는 커맨드 객체
public class BDeleteCommand implements BCommand{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//삭제하고자 하는 게시글의 순번을 가져온다.
		int num = Integer.parseInt(request.getParameter("num"));
		BoardDAO bDao = BoardDAO.getInstance();
		bDao.deleteBoard(num);
	}
}
