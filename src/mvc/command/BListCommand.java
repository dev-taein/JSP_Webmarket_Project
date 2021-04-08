package mvc.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.model.BoardDAO;
import mvc.model.BoardDTO;


//게시판의 리스트를 직접적으로 가져오는 Command객체이다.
public class BListCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BListCommand 시작");
		BoardDAO bDao = BoardDAO.getInstance();
		ArrayList<BoardDTO> boardlist = new ArrayList<BoardDTO>();
		
		int pageNum = 1;
		int limit = 5;  //한페이지에 나타낼 게시글 수
		
		//페이지 값이 null아니라면 해당 페이지를 숫자로 변환하여 저장함.
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		
		String items = request.getParameter("items");
		String text = request.getParameter("text");
		
		//DB저장되어 있는 게시글 총 갯수를 가져옴
		int total_record = bDao.getListCount(items, text);  
		//DB에 저장되어 있는 실제 게시글을 가져옴
		boardlist = bDao.getBoardList(pageNum, limit, items, text); 
		
		int total_page = 0;
		
		//총 페이지 수를 구하고 있는 코드
		if(total_record % limit == 0) {
			total_page = total_record / limit;
			Math.floor(total_page);
		}
		//나머지가 0이 아니라면..나머지가 1~4라는 소리이다.이것을 표식하기 위해서
		//total_page에 1을 증가시키고 있다.
		else {
			total_page = total_record / limit;
			Math.floor(total_page);
			total_page += 1;			
		}
		//request객체에 각각에 해당하는 값을 지정하고 있다.
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("total_page", total_page);
		request.setAttribute("total_record", total_record);
		request.setAttribute("boardlist", boardlist);
		System.out.println("BListCommand 수행완료");
	}
}

