/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Data.Captain;
import Data.Ship;
import dao.DAOCrewmanImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Valentina
 */
@WebServlet(name = "EditCaptainServlet", urlPatterns = {"/EditCaptainServlet"})
public class EditCaptainServlet extends HttpServlet {

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
           response.sendRedirect("CrudCaptain.jsp");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditCapainServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditCapainServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            System.out.println("llll");
            String name = request.getParameter("name");
            System.out.println("name" + name);
            int id = Integer.valueOf(request.getParameter("id"));
            System.out.println("id"+id);
            String lastName = String.valueOf(request.getParameter("lastName"));
            System.out.println("lastName:"+lastName);
            Ship ship = new Ship();
            int code = Integer.valueOf(request.getParameter("ship"));
            System.out.println("code"+code);
            ship.setCodeShip(code);
            Captain cap = new Captain();
            cap.setName(name);
            cap.setId(id);
            cap.setLastName(lastName);
            cap.setShip(ship);
            System.out.println("Barco" + ship);
            DAOCrewmanImpl daoCaptain = new DAOCrewmanImpl();
            daoCaptain.edit(cap);
            processRequest(request, response);
        } catch (SQLException ex) {
                
            System.out.println("No se pudo editar ");
            
                    
        } 
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
