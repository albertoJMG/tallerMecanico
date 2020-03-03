/*
 * Servlet Controlador del registro de los vehiculos
 */
package controlador;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.entidades.Cliente;
import modelo.entidades.Vehiculo;
import modelo.Taller;

/**
 *
 * @author alberto
 */
@WebServlet(name = "RegistrarVehiculo", urlPatterns = {"/RegistrarVehiculo", "/clientes/RegistrarVehiculo"})
public class RegistrarVehiculo extends HttpServlet {

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
        Taller t = new Taller();
        Vehiculo v = new Vehiculo();
        Cliente c = (Cliente)request.getSession().getAttribute("cliente");
        v.setMarca(request.getParameter("marca"));
        v.setModelo(request.getParameter("modelo"));
        //Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            v.setFechaPrimeraMatriculacion(formato.parse(request.getParameter("fecha")));
        } catch (ParseException ex) {
            System.err.println("Error al leer fecha de matriculacion" + ex.getMessage());
        }
        v.setColor(request.getParameter("color"));
        v.setCliente(c);
        t.crearVehiculo(v);
        c.getVehiculos().add(v);
        response.sendRedirect("gestionCliente.jsp");
        return;
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
        processRequest(request, response);
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
        processRequest(request, response);
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
