/*
 * Servlet que gestiona la gestion de las Actuaciones
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import librerias.Email;
import librerias.Utilidades;
import modelo.Taller;
import modelo.entidades.Actuacion;
import modelo.entidades.Cliente;
import modelo.entidades.Profesional;
import modelo.entidades.Reparacion;
import modelo.entidades.Vehiculo;

/**
 *
 * @author alberto
 */
@WebServlet(name = "AsignarActuacion", urlPatterns = {"/AsignarActuacion", "/profesional/AsignarActuacion", "/clientes/AsignarActuacion"})
public class AsignarActuacion extends HttpServlet {

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
        Actuacion a = new Actuacion();
        List<Profesional> p = t.getProfesionalesActivos();
        Profesional pro = new Profesional();
        String descripcion = request.getParameter("descripcion");
        //Variables boolean para controlar segun de donde vengamos que debemos hacer 
        boolean autorizar = request.getParameter("autorizar") != null;
        boolean realizar = request.getParameter("realizar") != null;
        boolean ida = request.getParameter("ida") != null;

        Double presupuesto;
        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            fechaActual = formato.parse(formato.format(fechaActual));
        } catch (ParseException ex) {
            System.err.println(ex.getMessage());
        }

        if (descripcion == null && !autorizar && !realizar && !ida) {
            //Vamos a asignarActuacion.jsp,para crear una ACTUACION y la asignamos a un MECANICO. Pasamos los lista con los profesionales activos
            r = t.buscarReparacion(id);

            //Profesional profesional = new Profesional();
            //profesional = t.buscarProfesional(r.getVehiculo().getCliente().getId());
            //request.setAttribute("profesional", profesional);
            //request.getRequestDispatcher("gestionProfesional.jsp").forward(request, response);

            request.setAttribute("profesionalesActivos", p);
            request.setAttribute("reparacionRecibeActuacion", r);
            request.getRequestDispatcher("asignarActuacion.jsp").forward(request, response);
        } else if (autorizar) {
            //Venimos desde seguimientoCliente.jsp y queremos autorizar una ACTUACION (actualizamos)
            a = t.buscarActuacion(id);
            a.setAutorizada("T");
            try {
                t.actualizarActuacion(a);
            } catch (Exception ex) {
                System.err.println("Error al actualizar actuacion. " + ex.getMessage());
            }

            //Volvemos al seguimiento de las reparaciones (CLIENTE)
            Vehiculo veh = new Vehiculo();
            veh = t.buscarVehiculo(a.getReparacion().getVehiculo().getId());
            request.setAttribute("vehiculo", veh);
            request.getRequestDispatcher("SeguimientoCliente").forward(request, response);
        } else if (realizar) {
            //Venimos de gestionProfesional.jsp para comentar y cerrar una ACTUACION (actualizar) 
            a = t.buscarActuacion(id);
            pro = t.buscarProfesional(a.getProfesional().getId());
            request.setAttribute("profesional", pro);
            request.setAttribute("actuacion", a);
            request.getRequestDispatcher("editarActuacion.jsp").forward(request, response);
        } else {
            //En cualquier otro caso
            r = t.buscarReparacion(id);
            if (request.getParameter("ida") != null) {
                //Venimos de editarActuacion.jsp para cerrar una ACTUACION
                a = t.buscarActuacion(Long.parseLong(request.getParameter("ida")));
                a.setComentarios(request.getParameter("comentario"));
                a.setFechaFinalizacion(fechaActual);
                a.setFinalizada("T");
                try {
                    t.actualizarActuacion(a);
                } catch (Exception ex) {
                    System.err.println("Error al actualizar actuacion. " + ex.getMessage());
                }

            } else {
                //Venimos desde asignarActuacion.jsp (MJ) y establecemos una nueva ACTUACION
                a.setDescripcion(request.getParameter("descripcion"));
                pro = t.buscarProfesional(Long.parseLong(request.getParameter("responsable")));

                presupuesto = Double.parseDouble(request.getParameter("presupuesto"));
                a.setPresupuesto(presupuesto);

                //Enviamos email al crear una nueva Actuacion. Dos email distintos segun el presupuesto de la actuacion
                Email email = new Email();
                email.setFrom("tallermecanicoajmg@gmail.com");
                email.setTo(r.getVehiculo().getCliente().getEmail());
                
                if (presupuesto <= 20) {
                    a.setAutorizada("T");
                    email.setSubject("Seguimiento de la reparación");
                    email.setText("Buenas, nuestros mecanicos procederan a realizar la siguiente actuación: \n"
                            + request.getParameter("descripcion")+"\n"
                            + "Como el coste no supera los 20€ la Actuacion se da por autorizada\n"
                            + "Importe Neto: "+presupuesto+" €\n"
                            + "Saludos.");
                }else{
                    email.setSubject("Seguimiento de la reparación");
                    email.setText("Buenas, nuestros mecanicos procederan a realizar la siguiente actuación: \n"
                            + request.getParameter("descripcion")+"\n"
                            + "Como el coste supera los 20€ la Actuacion debe ser Autorizada por usted. Rogamos que entre en la aplicacion y proceda a la autorización.\n"
                            + "Importe Neto: "+presupuesto+" €\n"
                            + "Saludos");
                }
                a.setFechaInicio(fechaActual);
                a.setReparacion(r);
                a.setProfesional(pro);
                t.crearActuacion(a);
                r.getActuaciones().add(a);
                Utilidades u = new Utilidades();
                u.enviarEmail(email);
            }
            
            pro = t.buscarProfesional(Long.parseLong(request.getParameter("profesionalID")));
            request.setAttribute("profesional", pro);
            request.getRequestDispatcher("gestionProfesional.jsp").forward(request, response);

        }

        //r.getActuaciones().add(a);      
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
