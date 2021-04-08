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
		
		//getRequestURI()는 요청된 전체 uri를 가져온다.
		String uri = request.getRequestURI();
		System.out.println("URI : " + uri);
		
		//getContextPath()는 프로젝트명이 리턴된다.
		String contextPath = request.getContextPath();
		System.out.println("contextPath : " + contextPath);
		
		//직접 실행되어야할 파일의 이름을 얻어내는 것이다.
		String command = uri.substring(contextPath.length());
		System.out.println("command : " + command);
		
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		
		//command패턴에 따라서 분기를 하는 코드
		//DB에 저장되어 있는 모든 게시글을 출력하는 부분
		if(command.equals("/BoardListAction.do")) {    
			System.out.println("------------------------------");
			System.out.println("/BoardListAction.do페이지 호출");
			System.out.println("------------------------------");
			com = new BListCommand();
			com.execute(request, response);
			System.out.println("BoardListAction의 execute() 실행 완료");
			viewPage = "./board/list.jsp";
		}
		
		//회원의 로그인 정보를 가져오는 부분
		else if (command.equals("/BoardWriteForm.do")) { 
			System.out.println("------------------------------");
			System.out.println("/BoardWriteForm.do페이지 호출");
			System.out.println("------------------------------");
			com = new BWriteFormCommand();
			com.execute(request, response);
			System.out.println("BoardWriteForm의 execute() 실행 완료");
			viewPage = "./board/writeForm.jsp";
		}
		
		//게시글을 쓰고 db에 저장하기
		else if (command.equals("/BoardWriteAction.do")) { 
			System.out.println("------------------------------");
			System.out.println("/BoardWriteAction.do페이지 호출");
			System.out.println("------------------------------");
			com = new BWriteCommand();
			com.execute(request, response);
			System.out.println("BoardWriteForm의 execute() 실행 완료");
			viewPage = "/BoardListAction.do";
		}
		
		//게시판의 해당 게시글을 클릭하면 상세내용 코드
		else if (command.equals("/BoardViewAction.do")) { 
			System.out.println("------------------------------");
			System.out.println("/BoardViewAction.do페이지 호출");
			System.out.println("------------------------------");
			com = new BViewCommand();
			com.execute(request, response);
			System.out.println("BoardViewAction의 execute() 실행 완료");
			viewPage = "./board/view.jsp";
		}
			
		//게시글을 수정하는 코드
		else if (command.equals("/BoardUpdateAction.do")) { 
			System.out.println("------------------------------");
			System.out.println("/BoardUpdateAction.do페이지 호출");
			System.out.println("------------------------------");
			com = new BUpdateCommand();
			com.execute(request, response);
			System.out.println("BoardUpdateAction의 execute() 실행 완료");
			viewPage = "/BoardListAction.do";
		}
	
		//게시글을 삭제하는 코드
		else if (command.equals("/BoardDeleteAction.do")) { 
			System.out.println("------------------------------");
			System.out.println("/BoardDeleteAction.do페이지 호출");
			System.out.println("------------------------------");
			com = new BDeleteCommand();
			com.execute(request, response);
			System.out.println("BoardDeleteAction의 execute() 실행 완료");
			viewPage = "/BoardListAction.do";
		}
		
		
		//위의 분기문에서 설정된 뷰(.jsp)파일을 가지고 페이지 이동을 하는 코드
		RequestDispatcher rd = request.getRequestDispatcher(viewPage);
		rd.forward(request, response);
	}

}
