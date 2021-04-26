package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Action;
import service.ActionForward;
import service.FoodAddAction;
import service.ProductAddAction;


/**
 * Servlet implementation class StoreController
 */
@WebServlet("*.do")
public class Starbucks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
public void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		
		System.out.println("requestURI:"+ requestURI);  // /model2/BoardAddAction.do
		System.out.println("contextPath:"+ contextPath);// /model2
		System.out.println("command:"+ command);
		
		Action action = null;
		ActionForward forward = null;
		
		// 상품등록
		if(command.equals("/ProductAddAction.do")) {
			try {
				action = new ProductAddAction();
				forward = action.execute(request, response);

			}catch(Exception e) {
				e.printStackTrace();
			}
			


			
		// 음식등록
		}else if(command.equals("/FoodAddAction.do")) {
			try {
				action = new FoodAddAction();
				forward = action.execute(request, response);

			}catch(Exception e) {
				e.printStackTrace();
			}
					
		}
	
		
		//forwarding 처리
		if(forward != null) {
			if(forward.isRedirect()) {	//redirect 방식으로 포워딩(전달)
				response.sendRedirect(forward.getPath());
			}else {						//dispatcher 방식으로 포워딩
				RequestDispatcher dispatchar = 
				request.getRequestDispatcher(forward.getPath());
				dispatchar.forward(request, response);
			}
		}
		
		
	} // doProcess() end

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("get");
	
		doProcess(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("post");
		
		doProcess(request, response);
	}
	
	}