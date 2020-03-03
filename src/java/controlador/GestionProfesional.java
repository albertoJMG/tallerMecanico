/*
 * Servlet para las consultas de las reparaciones por fecha y por cliente
 */
package controlador;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.entidades.Reparacion;
import modelo.Taller;
import modelo.entidades.Cliente;
import modelo.entidades.Vehiculo;

/**
 *
 * @author alberto
 */
@WebServlet(name = "GestionProfesional", urlPatterns = {"/GestionProfesional", "/profesional/GestionProfesional"})
public class GestionProfesional extends HttpServlet {

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
        List<Reparacion> reparaciones;
        Cliente c = new Cliente();
        Reparacion r = new Reparacion();
        reparaciones = t.getReparaciones();
        boolean cliente = request.getParameter("cliente") != null;
        boolean rep = request.getParameter("rep") != null;

        
        if (cliente) {
            //Venimos de gestionProfesional.jsp con una busqueda de reparaciones por cliente
            long id = Long.parseLong(request.getParameter("cliente")); //Es el id del cliente en el tag SELECT
            c = t.buscarCliente(id);
            reparaciones.clear();
            List<Vehiculo> v = c.getVehiculos();
            for (int i = 0; i < v.size(); i++) {
                for (Reparacion rp : v.get(i).getReparaciones()) {
                    if(rp.getFechaTerminacion() != null){
                        reparaciones.add(rp);
                    }
                }
            }

            request.setAttribute("vehiculos", c.getVehiculos());
            request.setAttribute("reparacionConsulta", reparaciones);
            request.getRequestDispatcher("gestionProfesional.jsp").forward(request, response);
        } else if (rep) {
            //Venimos de gestionProfesional.jsp mediante la busqueda por fecha
            reparaciones = t.getReparacionesPorFecha(request.getParameter("rep"));
            request.setAttribute("reparacionConsulta", reparaciones);
            request.getRequestDispatcher("gestionProfesional.jsp").forward(request, response);

        } else {
            response.sendRedirect("gestionProfesional.jsp");
            return;
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
