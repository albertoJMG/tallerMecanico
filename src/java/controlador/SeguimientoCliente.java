/*
 * Servlet Controlador de las acciones de Seguimiento de las reparaciones de los vehiculos
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Taller;
import modelo.entidades.Actuacion;
import modelo.entidades.Reparacion;
import modelo.entidades.Vehiculo;

/**
 *
 * @author alberto
 */
@WebServlet(name = "seguimientoCliente", urlPatterns = {"/SeguimientoCliente", "/clientes/SeguimientoCliente"})
public class SeguimientoCliente extends HttpServlet {

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
        Vehiculo v = (Vehiculo) request.getAttribute("vehiculo");
        List<Reparacion> r = null;
        //Long id = 0;
        if (v == null) {
            //Venimos de gestionCliente.jsp para actorizar una ACTUACION
            long id = Long.parseLong(request.getParameter("id"));
            v = t.buscarVehiculo(id);
            r = v.getReparaciones();

        } else {
            //No nos salimos de seguimientoCliente.jsp(SeguimientoCliente) asi vemos en tiempo real el cambio de las ACTUACIONES de "Autorizar" a "Autorizada"
            r = v.getReparaciones();
        }
        request.setAttribute("reparaciones", r);
        request.setAttribute("vehiculo", v);
        request.getRequestDispatcher("seguimientoCliente.jsp").forward(request, response);

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
