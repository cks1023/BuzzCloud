package property;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface commandAction {
	public String requestPro(
			HttpServletRequest request, HttpServletResponse response)
					throws Throwable;
}
