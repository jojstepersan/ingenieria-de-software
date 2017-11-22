/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Data.Crewman;
import Data.Ship;
import dao.DAOCrewmanImpl;
import dao.DAOShipImpl;
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
@WebServlet(name = "EditCrewmanServlet", urlPatterns = {"/EditCrewmanServlet"})
public class EditCrewmanServlet extends HttpServlet {

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
            out.println("<title>Servlet EditCrewmanServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditCrewmanServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String name = request.getParameter("name");
            System.out.println("name" + name);
            int id = Integer.valueOf(request.getParameter("id"));
            System.out.println("id"+id);
            String lastName = String.valueOf(request.getParameter("lastName"));
            System.out.println("lastName:"+lastName);
            DAOCrewmanImpl daoCrewman = new DAOCrewmanImpl();
            Ship ship = new Ship();
            String nameShip = request.getParameter("ship");
            
            Crewman crew = new Crewman();
            crew.setName(name);
            crew.setId(id);
            crew.setLastName(lastName);
            crew.setShip((new DAOShipImpl()).getByName(nameShip));
            System.out.println("Barco" + ship);
            
            daoCrewman.edit(crew);
            processRequest(request, response);
        } catch (SQLException ex) {
        } catch (Exception e){
                
            System.out.println("No se pudo editar crewman");
            
                    
        } 
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
