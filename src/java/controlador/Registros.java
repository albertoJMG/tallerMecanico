/*
 * A ELIMINAR
 */
package controlador;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@WebServlet(name = "Registros", urlPatterns = {"/Registros"})
public class Registros extends HttpServlet {

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
        Cliente c = new Cliente();
        Vehiculo v = new Vehiculo();
        List<Vehiculo> listaVehiculos = null;
        Reparacion r = new Reparacion();
        List<Reparacion> listaReparaciones = null;
        Actuacion a = new Actuacion();
        Profesional p = new Profesional();
        Profesional p1 = new Profesional();
        Profesional p2 = new Profesional();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        
//        v.setMarca("Ford");
//        v.setModelo("Mustang");
//        try {
//            v.setFechaPrimeraMatriculacion(formatoFecha.parse("2010-03-23"));
//        } catch (ParseException ex) {
//            System.err.println("Error al leer fecha de primera matriculacion" + ex.getMessage());
//        }
//        v.setColor("#ffffff");
//        t.crearVehiculo(v);
        
        c.setNombre("Alberto");
        c.setApellidos("Marrufo Garcia");
        c.setDireccion("C/Direccion");
        c.setDni("00000000A");
        c.setEmail("alberto@gmail.com");
        c.setNombreUsuario("a1");
        c.setPass("11");
        //listaVehiculos.add(v);
        //c.setVehiculos(listaVehiculos);
        t.crearCliente(c);
//        v.setCliente(c);
        
//        v.setMarca("Seat");
//        v.setModelo("Ibiza");
//        try {
//            v.setFechaPrimeraMatriculacion(formatoFecha.parse("2015-03-23"));
//        } catch (ParseException ex) {
//            System.err.println("Error al leer fecha de primera matriculacion" + ex.getMessage());
//        }
//        v.setColor("#ffffff");
//        t.crearVehiculo(v);
//        
//        listaVehiculos.clear();
        
        
//        c.setNombre("Cristina");
//        c.setApellidos("Herrojo Garcia");
//        c.setDireccion("C/Direccion");
//        c.setDni("11111111A");
//        c.setEmail("cristina@gmail.com");
//        c.setNombreUsuario("a2");
//        c.setPass("22");
//        listaVehiculos.add(v);
//        c.setVehiculos(listaVehiculos);
//        t.crearCliente(c);
//        v.setCliente(c);
        
        p.setNombre("admin");
        p.setApellidos("admin");
        p.setDni("11111111A");
        p.setEmail("admin@admin");
        p.setNombreUsuario("admin");
        p.setPass("admin");
        p.setTipo("administrador");
        t.crearProfesional(p);
        
        p1.setNombre("m1");
        p1.setActivo("T");
        p1.setApellidos("m1");
        p1.setDni("11111111A");
        p1.setEmail("m1@m1");
        p1.setNombreUsuario("m1");
        p1.setPass("m1");
        p1.setEspecialidad("electricidad");
        p1.setTipo("mecanicoJefe");
        t.crearProfesional(p1);
//        
        p2.setNombre("m2");
        p2.setActivo("T");
        p2.setApellidos("m2");
        p2.setDni("11111111A");
        p2.setEmail("m2@m2");
        p2.setNombreUsuario("m2");
        p2.setPass("m2");
        p2.setEspecialidad("electricidad");
        p2.setTipo("mecanico");
        t.crearProfesional(p2);
        
        
        
        
        response.sendRedirect("index.html");
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
