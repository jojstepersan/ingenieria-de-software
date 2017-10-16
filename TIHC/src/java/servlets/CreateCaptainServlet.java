/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Data.Captain;
import Data.Crewman;
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
@WebServlet(name = "CreateCaptainServlet", urlPatterns = {"/CreateCaptainServlet"})
public class CreateCaptainServlet extends HttpServlet {

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
        response.sendRedirect("Index.jsp");
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id  = Integer.valueOf(request.getParameter("id"));
        String name = request.getParameter("name");
        String lasName = request.getParameter("lastName");
        Crewman captian = new Crewman(id, name, lasName);
        DAOCrewmanImpl dcrewman=new DAOCrewmanImpl();
        System.out.printf("name:%s lastname:%s cod %d\n",name,lasName,id);
        try {
            dcrewman.create(captian);
        } catch (SQLException ex) {
            System.out.println("no se pudo crear el capitan :("); 
        }
        processRequest(request, response);
    }

     
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
