package servlets;

import dao.ConnectionDB;
import entities.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

   
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher dispacher = request.getRequestDispatcher("index.jsp");
        dispacher.forward(request, response);

    }       public static String comparar;
            Usuario us = new Usuario();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

      

        ConnectionDB con = new ConnectionDB();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out= response.getWriter();
        request.getRequestDispatcher("login.jsp").include(request, response);
        String usuario= request.getParameter("user");
        String password=request.getParameter("password");
        comparar = dao.Hash.hash(password);
        //System.out.println(name+":"+password+":"+comparar);
        
      //  String res = con.buscarUsuarios(usuario, comparar);
        //System.out.println(res);
        
              
        /*
            if(res.equals("true")){
            HttpSession actual=request.getSession(true);
           actual.setAttribute("logueado",usuario); 
           int tipo = con.buscarTipoUsuario(usuario);
           actual.setAttribute("tipoUsuario", tipo);
           int idU = con.buscarIdU(usuario);
           actual.setAttribute("idU", idU);
                System.out.println("------>"+tipo);
            response.sendRedirect("index.jsp");
        }else{
            out.println("<h1>El Usuario o la contraseña incorrectos<h1>");  
        response.sendRedirect("login.jsp");
        }
        out.close();*/
    }
   
}
