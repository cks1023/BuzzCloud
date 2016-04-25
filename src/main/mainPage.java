package main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import property.commandAction;

	public class mainPage implements commandAction{
		
		@Override
		public String requestPro(HttpServletRequest request,
				HttpServletResponse response) throws Throwable {
		
			return "mainPage/mainPage.jsp";
		}
	}
