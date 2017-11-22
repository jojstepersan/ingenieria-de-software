/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Data.Contract;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "CreateContractServlet", urlPatterns = {"/CreateContractServlet"})
public class CreateContractServlet extends HttpServlet {

    public static ArrayList<Contract> listaContratos;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           
        }
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       Contract contrato= new Contract();
       contrato.setNombre(request.getParameter("nombre"));
       contrato.setIdentificacion(Integer.parseInt(request.getParameter("identificacion")));
       contrato.setTipoCarga(request.getParameter("tipoCarga"));
       contrato.setCantidadPeso(Integer.parseInt(request.getParameter("cantidadPeso")));
       contrato.setOrigen(request.getParameter("origen"));
       contrato.setDestino(request.getParameter("destino"));
       contrato.setDescripcion(request.getParameter("descripcion"));
       contrato.setNumeroRadicacion(Integer.parseInt(request.getParameter("radicacion")));
       listaContratos.add(contrato);
       response.sendRedirect("ContractCreated.jsp");
        }
    

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
