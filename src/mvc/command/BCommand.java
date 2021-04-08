package mvc.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BCommand {
	//execute()는 해당하는 기능을 하는 것을 다르게 구현객체에서 구현부에서 작성이 될 것이다.
	public void execute(HttpServletRequest request, HttpServletResponse response);
}
