/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Data.Ship;
import dao.DAOShipImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kevin
 */
@WebServlet(name = "Readship", urlPatterns = {"/Readship"})
public class Readship extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            DAOShipImpl dao = new DAOShipImpl();
            List barcos = dao.read();
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<style> table, th, td {border: 1px solid black;}</style>");
            out.println("<title>Servlet Readship</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Lista de Barcos</h1>");
            out.println("<table style='width:100%'>");
            out.println("<tr>");
            out.println("<th>Codigo barco</th>");
            out.println("<th>Name Ship</th>");
            out.println("<th>weight</th>");
            out.println("<th>Estado</th>");
            out.println("<th>Fecha Adquisicion</th>");
            out.println("<th>Fecha Manteminmiento</th>");
            out.println("<th>Accion</th>");
            out.println("</tr>");
            for (int i = 0; i < barcos.size(); i++) {
                Ship aux = (Ship) barcos.get(i);
                out.println("<tr>");
                out.println("<td>" + aux.getCodeShip() + "</td>");
                out.println("<td>" + aux.getName() + "</td>");
                out.println("<td>" + aux.getWeight() + "</td>");
                out.println("<td>" + dao.state_text(aux.getState()) + "</td>");
                out.println("<td>" + aux.getDateAcquisition() + "</td>");
                out.println("<td>" + aux.getDateOfLastMaintenance() + "</td>");
                out.println("<td><form action=\"UpdateShip.jsp\" method=\"post\">");
                out.println("<input type=\"hidden\" value=\"" + aux.getCodeShip() + "\" name=\"cod\" />");
                out.println("<button type=\"submit\">Actualizar</button>");
                out.println("</form></td>");
                out.println("<td><form action=\"DeleteShip\" method=\"post\">");
                out.println("<input type=\"hidden\" value=\"" + aux.getCodeShip() + "\" name=\"cod\" />");
                out.println("<button type=\"submit\">Borrar</button>");
                out.println("</form></td>");
                out.println("</tr>");
            }
            dao.cerrar();
            out.println("</table>");
            out.println("<a href=\"CreateShip.jsp\">Add ship</a>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Readship.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
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
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Readship.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
