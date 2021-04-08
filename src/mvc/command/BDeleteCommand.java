package mvc.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.model.BoardDAO;

//DB�� ����Ǿ� �ִ� �Խñ� ������ �ϴ� Ŀ�ǵ� ��ü
public class BDeleteCommand implements BCommand{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//�����ϰ��� �ϴ� �Խñ��� ������ �����´�.
		int num = Integer.parseInt(request.getParameter("num"));
		BoardDAO bDao = BoardDAO.getInstance();
		bDao.deleteBoard(num);
	}
}
