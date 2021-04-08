package filter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class LogFileFilter implements Filter {
	PrintWriter writer = null;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("LogFileFilter�ʱ�ȭ...");
		String filename = filterConfig.getInitParameter("filename");
		
		//������ ���� ���
		if(filename == null) {
			throw new ServletException("�α� ������ �̸��� ã�� �� �����ϴ�.");
		}
		try {
//			�Ű������� �޴� ���� FileWriter������Ʈ���� �����ϴµ� true���� ������ ���������
//			�̾ ��� �αױ���� ����� ���� �Ӽ��� ������ ���̴�.
//			PrintWriter�ֽ�Ʈ���� �Ű����� �� true�� autoFlush�� �϶�� ���̴�.
			writer = new PrintWriter(new FileWriter(filename, true), true);
		} catch (IOException e) { e.printStackTrace(); }		
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//��û�� Ŭ���̾�Ʈ�� IP�� ������ �ִ�.
		String clientAddr = request.getRemoteAddr();
		writer.println("������ Ŭ���̾�Ʈ IP :" + clientAddr);
		writer.println("������ URL ��� :" + this.getURLPath(request));
		
		long start = System.currentTimeMillis();
		writer.println("��û ó�� ���� �ð� :" + this.getCurrentTime());
		
		chain.doFilter(request, response);
		long end = System.currentTimeMillis();
		writer.println("��û ó�� ���� �ð� :" + this.getCurrentTime());
		writer.println("��û ó�� �ҿ� �ð� :" + (end-start) + "ms");
		writer.println("===============================================");
	}
	@Override
	public void destroy() {
		System.out.println("LogFileFilter����...");
		writer.close(); //���ҽ� ����		
	}
	public String getCurrentTime() {
		SimpleDateFormat sformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sformat.format(new Date());
	}
	//URI ��� ��������
		public String getURLPath(ServletRequest request) {
			
			HttpServletRequest hRequest = null;
			String currentPath = "";
			String queryString = "";
			
			if(request instanceof HttpServletRequest) {
				hRequest = (HttpServletRequest)request; 
				currentPath = hRequest.getRequestURI(); //URI��������
				//POST����̶�� ?, GET����̶�� getQueryString�� ��ȯ
				queryString = (queryString == null) ? "" : "?" + hRequest.getQueryString();
			}
			return currentPath + queryString;
		}
}