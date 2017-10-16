/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Data.Captain;
import dao.DAOCrewmanImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Valentina
 */
@WebServlet(name = "DeletedCrewmanServlet", urlPatterns = {"/DeletedCrewmanServlet"})
public class DeletedCrewmanServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.sendRedirect("CrudCrewman.jsp");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DeletedCaptainServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeletedCaptainServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            int id=Integer.valueOf(request.getParameter("Id"));
        try {
            
            DAOCrewmanImpl dao=new DAOCrewmanImpl();
            dao.delete(id);
            System.out.println("borrado crewman");
        } catch (SQLException ex) {
            System.out.println("no se encontro crewman");  }
            
            
        processRequest(request, response);
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        Integer id  = Integer.valueOf(request.getParameter("id"));
//        String name = request.getParameter("nameCaptain");
//        String lasName = request.getParameter("lastNameCaptain");
//        
//        Captain captain = new Captain(id, name, lasName);
//
//       DAOCrewmanImpl dcrewman=new DAOCrewmanImpl();
//        try {
//
//            dcrewman.delete(id);
//        } catch (SQLException ex) {
//            System.out.println("No se puede borrar el capitan"); }
//        processRequest(request, response);}

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
