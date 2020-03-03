/*
 * Servlet Controlador de Reparaciones.
 */
package controlador;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import librerias.Email;
import librerias.Utilidades;
import modelo.entidades.Reparacion;
import modelo.entidades.Vehiculo;
import modelo.Taller;
import modelo.entidades.Cliente;
import modelo.entidades.Profesional;

/**
 *
 * @author alberto
 */
@WebServlet(name = "RegistrarReparacion", urlPatterns = {"/RegistrarReparacion", "/clientes/RegistrarReparacion", "/profesional/RegistrarReparacion"})
public class RegistrarReparacion extends HttpServlet {

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
        long id = Long.parseLong(request.getParameter("id"));
        Taller t = new Taller();
        
        Reparacion r = new Reparacion();
        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        try {
            fechaActual = formato.parse(formato.format(fechaActual));
        } catch (ParseException ex) {
            System.err.println(ex.getMessage());
        }
        String descripcion = request.getParameter("descripcion");
        boolean cerrar = request.getParameter("cerrar") != null;
        
        if(descripcion == null && !cerrar){
            //Venimos de gestionCliente.jsp y vamos a solicitarReparacion.jsp para crear una reparacion
            Vehiculo v = t.buscarVehiculo(id);
            request.setAttribute("vehiculo", v);
            request.getRequestDispatcher("solicitarReparacion.jsp").forward(request, response);
        }else if(cerrar){
            //Venimos de gestionProfesional.jsp cerrando la reparacion (MECANICO JEFE)
            r = t.buscarReparacion(id);
            
            r.setFechaTerminacion(fechaActual);
            try {
                t.actualizarReparacion(r);
            } catch (Exception ex) {
                System.err.println("Error al actualizar reparacion: "+ex.getMessage());
            }
            //Para que aparezcan reflejados los cambio en la pagina gestionProfesional.jsp al cerrar una actuacion.
            Profesional temp = t.buscarProfesional(Long.parseLong(request.getParameter("idp")));
            
            //Envio de Email al finalizar(cerrar) una Reparacion
            Email email = new Email();
            email.setFrom("tallermecanicoajmg@gmail.com");
            email.setTo(r.getVehiculo().getCliente().getEmail());
            email.setSubject("Finalizacion de las reparaciones");
            email.setText("Buenas:\n Las reparaciones de su vehiculo "+r.getVehiculo().getMarca()+" "+ r.getVehiculo().getModelo()+" han finalizado.\n"
                    + "Puede pasar a recogerlo en horario de apertura\n"
                    + "Asi mismo, le recordamos que tiene a su disposicion la factura de la reparacion con ID: "+r.getId()+ " Puede acceder a ella desde su cuenta "
                    + "en la aplicacion web en la seccion 'Seguimiento' del vehiculo en cuestion\n Saludos.");
            Utilidades u = new Utilidades();
            u.enviarEmail(email);
            
            request.setAttribute("r", r);
            request.setAttribute("profesional", temp);
            request.getRequestDispatcher("gestionProfesional.jsp").forward(request, response);
        }else {
            //Venimos de solicitarReparacion.jsp (CLIENTE)
            Vehiculo v = t.buscarVehiculo(id);
            r.setDescripcionAveria(descripcion);
            r.setFechaSolicitud(fechaActual);
            r.setVehiculo(v);
            t.crearReparacion(r);
            v.getReparaciones().add(r);
//            try {
//                t.actualizarVehiculo(v);
//            } catch (Exception ex) {
//                System.err.println("Error al actualizar vehiculo: "+ ex.getMessage());
//            }
            Cliente c = t.buscarCliente(v.getCliente().getId());
            
            request.setAttribute("cliente", c);
            request.getRequestDispatcher("gestionCliente.jsp").forward(request, response);

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
