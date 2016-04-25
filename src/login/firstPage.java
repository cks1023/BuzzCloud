package login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import property.commandAction;

	public class firstPage implements commandAction{
		
		@Override
		public String requestPro(HttpServletRequest request,
				HttpServletResponse response) throws Throwable {
		
			return "loginPage/userLogin.jsp";
		}
	}

