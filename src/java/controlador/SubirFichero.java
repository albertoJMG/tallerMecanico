/*
 * Servlet que permite la subida de las fotos al servidor
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import modelo.Taller;
import modelo.entidades.Actuacion;

/**
 *
 * @author alberto
 */
@WebServlet(name = "SubirFichero", urlPatterns = {"/SubirFichero", "/profesional/SubirFichero"})
public class SubirFichero extends HttpServlet {

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
        Actuacion a = new Actuacion();
        boolean id = request.getParameter("id") != null;
        boolean ida = request.getParameter("ida") != null;

        if (id) {
            //Vamos a subirFoto.jsp
            a = t.buscarActuacion(Long.parseLong(request.getParameter("id")));
            request.setAttribute("actuacion", a);
            request.getRequestDispatcher("subirFoto.jsp").forward(request, response);

        } else if (ida) {
            //Venimos de subirFoto.jsp, subimos la foto y guardamos la ruta para usarla en seguimiento de los Clientes
            //System.out.println(getServletContext().getRealPath("/"));
            Part parte = request.getPart("foto");
            String nombreFichero = parte.getSubmittedFileName();
            String ruta = getServletContext().getRealPath("/img") + "/" + nombreFichero;
            parte.write(ruta);
            a = t.buscarActuacion(Long.parseLong(request.getParameter("ida")));
            a.setFoto("img/"+nombreFichero);
            try {
                t.actualizarActuacion(a);
            } catch (Exception ex) {
                System.err.println("Error al aztualizar la actuacion: "+ex.getMessage() );
            }
            request.setAttribute("actuacion", a);
            request.getRequestDispatcher("editarActuacion.jsp").forward(request, response);
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
