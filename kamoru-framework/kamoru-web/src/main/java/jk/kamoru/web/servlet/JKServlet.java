package jk.kamoru.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jk.kamoru.KAMORU;
import lombok.extern.slf4j.Slf4j;

/**
 * Servlet implementation class JKServlet
 */
@Slf4j
public final class JKServlet extends HttpServlet {
	
	private static final long serialVersionUID = KAMORU.SERIAL_VERSION_UID;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JKServlet() {
        super();
        log.info("JKServlet creat");
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		log.info("JKServlet init");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("I'm alive!");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/manager/jk.kamoru.jsp");
		dispatcher.forward(request, response);
	}

}
