/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLADOR;

import Conexion.Conexion;
import Firebasse.DataToSend;
import Firebasse.Notificador;
import HILOS.BuscarConductor;
import HILOS.CarrerasEnBusqueda;
import MODELO.CARRERA;
import MODELO.PERMISO;
import MODELO.POSICION_VEHICULO;
import MODELO.PRODUCTO;
import MODELO.PUNTUACION_CARRERA;
import MODELO.TOKEN;
import MODELO.URL;
import MODELO.USUARIO;
import MODELO.USUARIO_CONDUCTOR;
import MODELO.VEHICULO;
import java.io.*;

import java.nio.file.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author RICKY
 */
@MultipartConfig
@WebServlet(name = "indexController", urlPatterns = {"/indexController"})
public class indexController extends HttpServlet {

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
        try {
            Conexion con = new Conexion(URL.db_usr, URL.db_pass); //conexion linux

            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain");
            String evento = request.getParameter("evento");
            boolean retornar = true;
            String html = "";
            switch (evento) {
                case "login":
                    html = login(request, con);
                    break;
                case "login_conductor":
                    html = login_conductor(request, con);
                    break;
                case "get_usuario":
                    html = get_usuario(request, con);
                    break;
                case "login_cliente":
                    html = login_cliente(request, con);
                    break;
                case "buscar_carrera":
                    html = buscar_carrera(request, con);
                    break;
                case "buscar_carrera_togo":
                    html = buscar_carrera_togo(request, con);
                    break;
                case "get_carrera_id":
                    html = get_carrera_id(request, con);
                    break;
                case "get_carrera_id_togo":
                    html = get_carrera_id_togo(request, con);
                    break;
                case "get_carrera_id_recorrido":
                    html = get_carrera_id_recorrido(request, con);
                    break;
                case "get_pos_conductor_x_id_carrera":
                    html = get_pos_conductor_x_id_carrera(request, con);
                    break;
                case "finalizo_carrera_cliente":
                    html = finalizo_carrera_cliente(request, con);
                    break;
                case "get_carrera_cliente":
                    html = get_carrera_cliente(request, con);
                    break;
                case "get_info_con_carrera":
                    html = get_info_con_carrera(request, con);
                    break;
                case "get_mis_viajes":
                    html = get_mis_viajes(request, con);
                    break;
                case "get_mis_viajes_conductor":
                    html = get_mis_viajes_conductor(request, con);
                    break;
                case "get_viaje_detalle":
                    html = get_viaje_detalle(request, con);
                    break;
                case "get_viaje_detalle_conductor":
                    html = get_viaje_detalle_conductor(request, con);
                    break;
                case "get_cliente_x_id":
                    html = get_cliente_x_id(request, con);
                    break;
                case "get_productos_x_id_carrera":
                    html = get_productos_x_id_carrera(request, con);
                    break;
                case "get_mis_viajes_actu":
                    html = get_mis_viajes_actu(request, con);
                    break;
                case "get_historial_ubic":
                    html = get_historial_ubic(request, con);
                    break;
                case "get_usuario_face":
                    html = get_usuario_face(request, con);
                    break;
                case "enviar_mensaje":
                    html = enviar_mensaje(request, con);
                    break;

            }
            con.Close();
            if (retornar) {
                response.getWriter().write(html);
            }
        } catch (SQLException ex) {
            Logger.getLogger(indexController.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().write("falso");
        } catch (JSONException ex) {
            Logger.getLogger(indexController.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().write("falso");
        } catch (Exception ex) {
            Logger.getLogger(indexController.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().write("falso");
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

    private String login(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        String usuario = request.getParameter("usuario");
        String pass = request.getParameter("pass");
        String token = request.getParameter("token");
        USUARIO usr = new USUARIO(con);
        JSONObject obj = usr.get_por_usr_y_pass(usuario, pass);
        PERMISO per = new PERMISO(con);
        if (obj.getString("exito").equals("si")) {
            JSONArray arrpermisos = per.todos_de_rol(obj.getInt("id_rol"));
            obj.put("permisos", arrpermisos);
            TOKEN tok = new TOKEN(con);
            tok.setID_USR(obj.getInt("id"));
            tok.setTOKEN(token);
            tok.Insertar();
        }
        return obj.toString();
    }

    private String login_conductor(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        String usuario = request.getParameter("usuario");
        String pass = request.getParameter("pass");
        String token = request.getParameter("token");
        USUARIO usr = new USUARIO(con);
        JSONObject obj = usr.getConductor_por_usr_y_pass(usuario, pass);
        PERMISO per = new PERMISO(con);
        if (obj.getString("exito").equals("si")) {
            JSONArray arrpermisos = per.todos_de_rol(obj.getInt("id_rol"));
            obj.put("permisos", arrpermisos);
            TOKEN tok = new TOKEN(con);
            tok.setID_USR(obj.getInt("id"));
            tok.setTOKEN(token);
            tok.Insertar();
        }
        return obj.toString();
    }

    private String login_cliente(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
    String usuario = request.getParameter("usuario");
        String pass = request.getParameter("pass");
        String token = request.getParameter("token");
        USUARIO usr = new USUARIO(con);
        JSONObject obj = usr.getCliente_por_usr_y_pass(usuario, pass);
        PERMISO per = new PERMISO(con);
        if (obj.getString("exito").equals("si")) {
            JSONArray arrpermisos = per.todos_de_rol(obj.getInt("id_rol"));
            obj.put("permisos", arrpermisos);
            TOKEN tok = new TOKEN(con);
            tok.setID_USR(obj.getInt("id"));
            tok.setTOKEN(token);
            tok.Insertar();
        }
        return obj.toString();
    }

    private String buscar_carrera(HttpServletRequest request, Conexion con) throws SQLException {
        double latIncio = Double.parseDouble(request.getParameter("latInicio"));
        double lngIncio = Double.parseDouble(request.getParameter("lngInicio"));
        double latFin = Double.parseDouble(request.getParameter("latFin"));
        double lngFin = Double.parseDouble(request.getParameter("lngFin"));
        int tipo_carrera = Integer.parseInt(request.getParameter("tipo"));
        int tipo_pago = Integer.parseInt(request.getParameter("tipo_pago"));
        String token = request.getParameter("token");
        int id = Integer.parseInt(request.getParameter("id"));
        CARRERA carrera = new CARRERA(con);
        carrera.setID_USUARIO(id);
        carrera.setLATINICIAL(latIncio);
        carrera.setLATFINAL(latFin);
        carrera.setLNGINICIAL(lngIncio);
        carrera.setLNGFINAL(lngFin);
        carrera.setID_TIPO(tipo_carrera);
        carrera.setFECHA_PEDIDO(new Date());
        carrera.setTIPO_PAGO(tipo_pago);
        int id_carrera = carrera.insertar_pedir_carrera();
        carrera.setID(id_carrera);
        BuscarConductor busqueda = new BuscarConductor(carrera);
        CarrerasEnBusqueda.get_carreras().put(id_carrera + "", busqueda);
        busqueda.run();
        JSONObject obj = busqueda.getJson_Carrera();
        if (obj != null) {
            return obj.toString();
        } else {
            return "falso";
        }
    }

    private String buscar_carrera_togo(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        double latIncio = 0;
        double lngIncio = 0;
        double latFin = Double.parseDouble(request.getParameter("latFin"));
        double lngFin = Double.parseDouble(request.getParameter("lngFin"));
        int tipo_carrera = Integer.parseInt(request.getParameter("tipo"));
        int tipo_pago = Integer.parseInt(request.getParameter("tipo_pago"));
        JSONArray productos = new JSONArray(request.getParameter("productos"));
        String token = request.getParameter("token");
        int id = Integer.parseInt(request.getParameter("id"));
        CARRERA carrera = new CARRERA(con);
        carrera.setID_USUARIO(id);
        carrera.setLATINICIAL(latIncio);
        carrera.setLATFINAL(latFin);
        carrera.setLNGINICIAL(lngIncio);
        carrera.setLNGFINAL(lngFin);
        carrera.setID_TIPO(tipo_carrera);
        carrera.setFECHA_PEDIDO(new Date());
        carrera.setTIPO_PAGO(tipo_pago);
        int id_carrera = carrera.insertar_pedir_carrera();
        JSONObject obj;
        PRODUCTO produc;
        for (int i = 0; i < productos.length(); i++) {
            obj = productos.getJSONObject(i);
            produc = new PRODUCTO(con);
            produc.setID_CARRERA(id_carrera);
            produc.setCANTIDAD(obj.getInt("cantidad"));
            produc.setPRODUCTO(obj.getString("producto"));
            produc.setDESCRIPCION(obj.getString("descripcion"));
            produc.Insertar();
        }
        if (tipo_carrera == 2) {
            carrera.setPRODUCTOS(productos);
        }
        carrera.setID(id_carrera);
        BuscarConductor busqueda = new BuscarConductor(carrera);
        CarrerasEnBusqueda.get_carreras().put(id_carrera + "", busqueda);
        busqueda.run();
        JSONObject obje = busqueda.getJson_Carrera();
        if (obje != null) {
            return obje.toString();
        } else {
            return "falso";
        }
    }

    private String get_carrera_id(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        CARRERA car = new CARRERA(con);
        return car.getCarreraId(id).toString();
    }

    private String get_pos_conductor_x_id_carrera(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        POSICION_VEHICULO pv = new POSICION_VEHICULO(con);
        return pv.get_posicion_x_id_carrera(id).toString();
    }

    private String finalizo_carrera_cliente(HttpServletRequest request, Conexion con) throws SQLException {
        int id_carrera = Integer.parseInt(request.getParameter("id_carrera"));
        double calificacion = Double.parseDouble(request.getParameter("calificacion"));
        String mensaje = request.getParameter("mensaje");
        boolean amable=Boolean.parseBoolean(request.getParameter("amable"));
        boolean buena_ruta=Boolean.parseBoolean(request.getParameter("buena_ruta"));
        boolean auto_limpio=Boolean.parseBoolean(request.getParameter("auto_limpio"));
        //calificacion en estrellas del conductor
        PUNTUACION_CARRERA puntuacion = new PUNTUACION_CARRERA(con);
        puntuacion.setID_CARRERA(id_carrera);
        puntuacion.setSTAR(calificacion);
        puntuacion.setMENSAJE(mensaje);
        puntuacion.setAMABLE(amable);
        puntuacion.setBUENA_RUTA(buena_ruta);
        puntuacion.setAUTO_LIMPIO(auto_limpio);
        int id = puntuacion.Insertar();
        return "exito";
    }

    private String get_carrera_cliente(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id_usr = Integer.parseInt(request.getParameter("id_usr"));
        CARRERA car = new CARRERA(con);
        return car.getCarreraCliente(id_usr).toString();
    }

    private String get_info_con_carrera(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id_usr = Integer.parseInt(request.getParameter("id_carrera"));
        USUARIO_CONDUCTOR uc = new USUARIO_CONDUCTOR(con);
        return uc.get_datos_con_carrera(id_usr).toString();
    }

    private String get_usuario(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id_usr = Integer.parseInt(request.getParameter("id"));
        USUARIO usr = new USUARIO(con);
        return usr.get_usuario_id_sp(id_usr).toString();
    }

    private String get_carrera_id_recorrido(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        CARRERA car = new CARRERA(con);
        return car.getCarreraIdrecorrido(id).toString();
    }

    private String get_mis_viajes(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        CARRERA car = new CARRERA(con);
        return car.get_mis_viajes(id).toString();
    }

    private String get_viaje_detalle(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        CARRERA car = new CARRERA(con);
        return car.get_detalle_carrera(id).toString();
    }

    private String get_cliente_x_id(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        USUARIO usr = new USUARIO(con);
        return usr.getCliente_por_id(id).toString();
    }

    private String get_carrera_id_togo(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        CARRERA car = new CARRERA(con);
        return car.getCarreraId_togo(id).toString();
    }

    private String get_productos_x_id_carrera(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        PRODUCTO pro = new PRODUCTO(con);
        return pro.get_productos_id_carrera(id).toString();
    }

    private String get_mis_viajes_actu(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        String fecha = request.getParameter("fecha");
        CARRERA car = new CARRERA(con);
        return car.get_mis_viajes_fecha(id, fecha).toString();

    }

    private String get_mis_viajes_conductor(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        CARRERA car = new CARRERA(con);
        return car.get_mis_viajes_conductor(id).toString();
    }

    private String get_viaje_detalle_conductor(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        CARRERA car = new CARRERA(con);
        return car.get_detalle_carrera_conductor(id).toString();
    }

    private String get_historial_ubic(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        CARRERA car = new CARRERA(con);
        return car.get_historial_ubic(id).toString();
    }

    private String get_usuario_face(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        String id= request.getParameter("id_usr");
        USUARIO usr = new USUARIO(con);
        return usr.getClienteFace(id).toString();
    }

    private String enviar_mensaje(HttpServletRequest request, Conexion con) throws JSONException, SQLException, Exception {
        String mensaje= request.getParameter("mensaje");
        int id_emisor=Integer.parseInt(request.getParameter("id_emisor"));
        int id_receptor=Integer.parseInt(request.getParameter("id_receptor"));
        JSONObject obj = new JSONObject();
        obj.put("mensaje", mensaje);
        obj.put("id_emisor", id_emisor);
        obj.put("id_receptor", id_receptor);
        TOKEN tok = new TOKEN(con);
        JSONArray arr = tok.get_Token_usr(id_receptor);
        JSONObject obj_token;
        for (int j = 0; j < arr.length(); j++) {
            obj_token = arr.getJSONObject(j);
            String token = obj_token.getString("TOKEN");
            DataToSend data = new DataToSend(token, "mensaje_recibido", obj.toString(), "");
            boolean resp = Notificador.enviar(data);
            if (resp) {
                break;
            }
        }
        return "exito";
    }
}
