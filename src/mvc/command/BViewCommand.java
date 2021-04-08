package mvc.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.model.BoardDAO;
import mvc.model.BoardDTO;

//DB�� ����Ǿ��ִ� �Խñ��� ������ �� �� �����ϴ� Ŀ�ǵ� ��ü
public class BViewCommand implements BCommand {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		BoardDAO bDao = BoardDAO.getInstance();
		
		//�������ϴ� �Խñ��� ��ȣ ��������
		int num = Integer.parseInt(request.getParameter("num"));
		//�������ϴ� �Խñۿ� �ִ� ������ �� ��������
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		BoardDTO board = new BoardDTO();
		board = bDao.getBoardByNum(num, pageNum);
		
		request.setAttribute("num", num);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("board", board);
	}

}
