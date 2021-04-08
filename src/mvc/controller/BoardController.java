package mvc.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.BCommand;
import mvc.command.BDeleteCommand;
import mvc.command.BListCommand;
import mvc.command.BUpdateCommand;
import mvc.command.BViewCommand;
import mvc.command.BWriteCommand;
import mvc.command.BWriteFormCommand;


public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
		actionDo(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		actionDo(request, response);
	}

	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("actionDo");
		
		BCommand com = null;
		String viewPage = null;
		
		//getRequestURI()�� ��û�� ��ü uri�� �����´�.
		String uri = request.getRequestURI();
		System.out.println("URI : " + uri);
		
		//getContextPath()�� ������Ʈ���� ���ϵȴ�.
		String contextPath = request.getContextPath();
		System.out.println("contextPath : " + contextPath);
		
		//���� ����Ǿ���� ������ �̸��� ���� ���̴�.
		String command = uri.substring(contextPath.length());
		System.out.println("command : " + command);
		
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		
		//command���Ͽ� ���� �б⸦ �ϴ� �ڵ�
		//DB�� ����Ǿ� �ִ� ��� �Խñ��� ����ϴ� �κ�
		if(command.equals("/BoardListAction.do")) {    
			System.out.println("------------------------------");
			System.out.println("/BoardListAction.do������ ȣ��");
			System.out.println("------------------------------");
			com = new BListCommand();
			com.execute(request, response);
			System.out.println("BoardListAction�� execute() ���� �Ϸ�");
			viewPage = "./board/list.jsp";
		}
		
		//ȸ���� �α��� ������ �������� �κ�
		else if (command.equals("/BoardWriteForm.do")) { 
			System.out.println("------------------------------");
			System.out.println("/BoardWriteForm.do������ ȣ��");
			System.out.println("------------------------------");
			com = new BWriteFormCommand();
			com.execute(request, response);
			System.out.println("BoardWriteForm�� execute() ���� �Ϸ�");
			viewPage = "./board/writeForm.jsp";
		}
		
		//�Խñ��� ���� db�� �����ϱ�
		else if (command.equals("/BoardWriteAction.do")) { 
			System.out.println("------------------------------");
			System.out.println("/BoardWriteAction.do������ ȣ��");
			System.out.println("------------------------------");
			com = new BWriteCommand();
			com.execute(request, response);
			System.out.println("BoardWriteForm�� execute() ���� �Ϸ�");
			viewPage = "/BoardListAction.do";
		}
		
		//�Խ����� �ش� �Խñ��� Ŭ���ϸ� �󼼳��� �ڵ�
		else if (command.equals("/BoardViewAction.do")) { 
			System.out.println("------------------------------");
			System.out.println("/BoardViewAction.do������ ȣ��");
			System.out.println("------------------------------");
			com = new BViewCommand();
			com.execute(request, response);
			System.out.println("BoardViewAction�� execute() ���� �Ϸ�");
			viewPage = "./board/view.jsp";
		}
			
		//�Խñ��� �����ϴ� �ڵ�
		else if (command.equals("/BoardUpdateAction.do")) { 
			System.out.println("------------------------------");
			System.out.println("/BoardUpdateAction.do������ ȣ��");
			System.out.println("------------------------------");
			com = new BUpdateCommand();
			com.execute(request, response);
			System.out.println("BoardUpdateAction�� execute() ���� �Ϸ�");
			viewPage = "/BoardListAction.do";
		}
	
		//�Խñ��� �����ϴ� �ڵ�
		else if (command.equals("/BoardDeleteAction.do")) { 
			System.out.println("------------------------------");
			System.out.println("/BoardDeleteAction.do������ ȣ��");
			System.out.println("------------------------------");
			com = new BDeleteCommand();
			com.execute(request, response);
			System.out.println("BoardDeleteAction�� execute() ���� �Ϸ�");
			viewPage = "/BoardListAction.do";
		}
		
		
		//���� �б⹮���� ������ ��(.jsp)������ ������ ������ �̵��� �ϴ� �ڵ�
		RequestDispatcher rd = request.getRequestDispatcher(viewPage);
		rd.forward(request, response);
	}

}
