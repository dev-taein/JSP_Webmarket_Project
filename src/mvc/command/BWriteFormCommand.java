package mvc.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.model.BoardDAO;


//게시판의 게시글을 작성하기 위해서 사용자 명을 가져오는 객체
public class BWriteFormCommand implements BCommand {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		BoardDAO bDao = BoardDAO.getInstance();
		String id = request.getParameter("id");
		String name = bDao.getLoginName(id);
		request.setAttribute("name", name);
	}
}
