package logon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

import javaBean.logonBean;
import javaBean.memberDataBean;
import property.commandAction;

public class logonProcess implements commandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {

		logonBean lb = new logonBean();

		String idType = (String) request.getParameter("idType");

		switch (idType) {

		case "naver":

			memberDataBean mdb = new memberDataBean();
			mdb.setId(0);
			mdb.setEmail((String) request.getParameter("email"));
			mdb.setIdType((String) request.getParameter("idType"));
			mdb.setNickname((String) request.getParameter("nickname"));
			mdb.setPassword((String) request.getParameter(""));
			mdb.setReg_date(new Timestamp(System.currentTimeMillis()));

			if (lb.joinMember(mdb, request) == false)
				request.setAttribute("joinResult", false);
			else
				request.setAttribute("joinResult", true);
			break;

		case "google":

			break;

		case "facebook":

			break;

		default:

		}

	return "logonPage/mainPage.jsp";
    }
}
