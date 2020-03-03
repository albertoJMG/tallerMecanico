/*
 * Servlet que recibe los parametros del formulario y crea un nuevo profesional, edita o da de baja
 */
package controlador;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.entidades.Profesional;
import modelo.Taller;

/**
 *
 * @author alberto
 */
@WebServlet(name = "CrearProfesional", urlPatterns = {"/CrearProfesional", "/profesional/CrearProfesional"})
public class CrearProfesional extends HttpServlet {

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
        Profesional p = new Profesional();

        long id = 0;
        boolean baja = request.getParameter("baja") != null;
        boolean editar = request.getParameter("editar") != null;
        boolean editando = request.getParameter("editando") != null;

        if (request.getParameter("id") != null) {
            id = Long.parseLong(request.getParameter("id"));
            p = t.buscarProfesional(id);
        }

        if (baja) {
            //Venimos de gestionProfesional.jsp y queremos dar de "baja" que se trata de eliminar el acceso al profesional

            p.setNombreUsuario("baja" + id);//Establecemos un nombre de usuario cualquiera
            p.setPass("0000");//Establecemos un password cualquiera
            p.setActivo("F");//Cambiamos el estado
            try {
                t.bajaProfesional(p);
            } catch (Exception ex) {
                System.err.println("Error dar de baja al profesional. " + ex.getMessage());
            }
            response.sendRedirect("gestionProfesional.jsp");
            return;
        } else if (editar) {
            //Venimos de GestionProfesional.jsp y cargamos en el formulario de editarProfesional.jsp los datos para su facil edicion
            p = t.buscarProfesional(id);
            request.setAttribute("p", p);
            request.getRequestDispatcher("editarProfesional.jsp").forward(request, response);
        } else {
            //Venimos de registroProfesional.jsp ya sea para crear o para actualizar

            p.setNombreUsuario(request.getParameter("nombreUsuario"));
            p.setPass(request.getParameter("password"));
            p.setDni(request.getParameter("dni"));
            p.setNombre(request.getParameter("nombre"));
            p.setApellidos(request.getParameter("apellidos"));
            p.setEspecialidad(request.getParameter("especialidad"));
            p.setTipo(request.getParameter("tipo"));
            p.setActivo(request.getParameter("activo"));
            p.setEmail(request.getParameter("email"));
            if (editando) {
                //Si estamos editando
                //Comprobamos  si existe un usuario con ese nombre, si existe mostramos mensaje de error
                if (t.buscarProfesionalPorUsuario(request.getParameter("nombreUsuario"))) {
                    p = t.buscarProfesional(id);
                    request.setAttribute("p", p);
                    request.setAttribute("error", "Ese nombre de usuario ya existe. Introduzca otro nombre de usuario");
                    request.getRequestDispatcher("editarProfesional.jsp").forward(request, response);
                } else {
                    try {
                        t.actualizarProfesional(p);
                    } catch (Exception ex) {
                        System.err.println("Error al actualizar empleado. " + ex.getMessage());
                    }
                }

            } else {
                //Si estamos creando uno nuevo
                //Comprobamos  si existe un usuario con ese nombre, si existe mostramos mensaje de error
                if (t.buscarProfesionalPorUsuario(request.getParameter("nombreUsuario"))) {
                    request.setAttribute("error", "Ese nombre de usuario ya existe. Introduzca otro nombre de usuario");
                    request.getRequestDispatcher("editarProfesional.jsp").forward(request, response);
                } else {
                    //Si no hay coincidencia creamos al usuario
                    t.crearProfesional(p);
                }
            }

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
