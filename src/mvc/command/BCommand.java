package mvc.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BCommand {
	//execute()�� �ش��ϴ� ����� �ϴ� ���� �ٸ��� ������ü���� �����ο��� �ۼ��� �� ���̴�.
	public void execute(HttpServletRequest request, HttpServletResponse response);
}
