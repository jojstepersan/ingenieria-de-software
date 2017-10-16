package servlets;

import Data.Admin;
import Data.User;
import dao.ConnectionDB;
import dao.DAOUserImpl;
import entities.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        } 
   
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            User user=null;
       try {
           String usuario= request.getParameter("user");
           String password=request.getParameter("password");
           DAOUserImpl dao=new DAOUserImpl();
           System.out.println("Usuario: "+ usuario + " password: "+ password);
           user=dao.login(usuario, password);
           System.out.println("todo bien");
           if(user!=null)
            {
            HttpSession sesion = request.getSession();      
            sesion.setAttribute("usuario",user.getName());
            sesion.setAttribute("tipo",user instanceof Admin?4:3);
            sesion.setAttribute("id", user.getId());
            sesion.setAttribute("username", user.getUsername());
            sesion.setAttribute("lastname", user.getLastName());
            sesion.setAttribute("pass", user.getPass());
            response.sendRedirect("Index.jsp");
            }
       } catch (SQLException ex) {
            System.out.println("sorry paila el login, llorelo");
            response.sendRedirect("Login.jsp");
           }
        
        
      
    }
   
}
