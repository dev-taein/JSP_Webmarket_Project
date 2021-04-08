package mvc.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.model.BoardDAO;


//�Խ����� �Խñ��� �ۼ��ϱ� ���ؼ� ����� ���� �������� ��ü
public class BWriteFormCommand implements BCommand {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		BoardDAO bDao = BoardDAO.getInstance();
		String id = request.getParameter("id");
		String name = bDao.getLoginName(id);
		request.setAttribute("name", name);
	}
}
