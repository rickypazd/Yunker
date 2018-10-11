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
import MODELO.CANCELACION;
import MODELO.CARRERA;
import MODELO.CATEGORIA_VEHICULO;
import MODELO.COSTO;
import MODELO.COSTOS_EXTRAS;
import MODELO.DETALLE_COSTO_CARRERA;
import MODELO.DOCUMENTO;
import MODELO.EVENTOS;
import MODELO.MODIFICACIONES;
import MODELO.MOTIVO_CANCELACION;
import MODELO.PERMISO;
import MODELO.POSICION_VEHICULO;
import MODELO.TCARRERA_ACTIVO;
import MODELO.TIPO_CARRERA;
import MODELO.TOKEN;
import MODELO.TRANSACCION_CREDITO;
import MODELO.TURNOS;
import MODELO.URL;
import MODELO.USUARIO;
import MODELO.USUARIO_CONDUCTOR;
import MODELO.VEHICULO;
import MODELO.VEHICULO_CONDUCTOR;
import MODELO.VEHICULO_TO_CATEGORIA;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.nio.file.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
@WebServlet(name = "adminController", urlPatterns = {"/admin/adminController"})
public class adminController extends HttpServlet {

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
        Conexion con = new Conexion(URL.db_usr, URL.db_pass);
        try {
            //conexion linux
            con.Transacction();
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain");
            String evento = request.getParameter("evento");
            boolean retornar = true;
            String html = "";
            switch (evento) {
                case "set_pos_vehiculo":
                    html = set_pos_vehiculo(request, con);
                    break;
                case "get_pos_vehiculos":
                    html = get_pos_vehiculos(request, con);
                    break;
                case "notificar":
                    html = notificar(request, con);
                    break;
                case "registrar_usuario":
                    html = registrar_usuario(request, con);
                    break;
                case "registrar_usuario_cliente":
                    html = registrar_usuario_cliente(request, con);
                    break;
                case "registrar_usuario_conductor":
                    html = registrar_usuario_conductor(request, con);
                    break;
                case "comprovar_usr":
                    html = comprovar_usr(request, con);
                    break;
                case "actualizar_usuario_conductor":
                    html = actualizar_usuario_conductor(request, con);
                    break;
                case "get_costos_extras":
                    html = get_costos_extras(request, con);
                    break;
                case "get_costos_extras_estado":
                    html = get_costos_extras_estado(request, con);
                    break;
                case "agg_costo_extra":
                    html = agg_costo_extra(request, con);
                    break;
                case "get_categorias_vehiculo":
                    html = get_categorias_vehiculo(request, con);
                    break;
                case "agg_categoria":
                    html = agg_categoria(request, con);
                    break;
                case "login":
                    html = login(request, con);
                    break;
                case "registrar_vehiculo":
                    html = registrar_vehiculo(request, con);
                    break;
                case "comprovar_placa":
                    html = comprovar_placa(request, con);
                    break;
                case "buscar_conductor":
                    html = buscar_conductor(request, con);
                    break;
                case "get_usuario_id_sp":
                    html = get_usuario_id_sp(request, con);
                    break;
                case "get_vehiculos_asignados_cond":
                    html = get_vehiculos_asignados_cond(request, con);
                    break;
                case "get_vehiculos_sin_asignar_cond":
                    html = get_vehiculos_sin_asignar_cond(request, con);
                    break;
                case "asignar_vehiculo":
                    html = asignar_vehiculo(request, con);
                    break;
                case "eliminar_vehiculo":
                    html = eliminar_vehiculo(request, con);
                    break;
                case "get_turno_conductor":
                    html = get_turno_conductor(request, con);
                    break;
                case "get_vehiculo_disponible_con":
                    html = get_vehiculo_disponible_con(request, con);
                    break;
                case "iniciar_turno":
                    html = iniciar_turno(request, con);
                    break;
                case "iniciar_turno_sin_vehiculo":
                    html = iniciar_turno_sin_vehiculo(request, con);
                    break;
                case "confirmar_carrera":
                    html = confirmar_carrera(request, con);
                    break;
                case "conductor_cerca":
                    html = conductor_cerca(request, con);
                    break;
                case "conductor_llego":
                    html = conductor_llego(request, con);
                    break;
                case "inciar_carrera":
                    html = inciar_carrera(request, con);
                    break;
                case "confirmar_compra":
                    html = confirmar_compra(request, con);
                    break;
                case "terminar_carrera":
                    html = terminar_carrera(request, con);
                    break;
                case "registrar_tipo_carrera":
                    html = registrar_tipo_carrera(request, con);
                    break;
                case "cobrar_carrera":
                    html = cobrar_carrera(request, con);
                    break;
                case "agg_creditos":
                    html = agg_creditos(request, con);
                    break;
                case "update_tc_activo":
                    html = update_tc_activo(request, con);
                    break;
                case "get_tc_activo":
                    html = get_tc_activo(request, con);
                    break;
                case "get_carrera_conductor":
                    html = get_carrera_conductor(request, con);
                    break;
                case "agg_motivo_cancelacion":
                    html = agg_motivo_cancelacion(request, con);
                    break;
                case "get_motivos_cancelacion":
                    html = get_motivos_cancelacion(request, con);
                    break;
                case "cancelar_carrera":
                    html = cancelar_carrera(request, con);
                    break;
                case "ok_cancelar_carrera":
                    html = ok_cancelar_carrera(request, con);
                    break;
                case "subir_archibo":
                    html = subir_archibo(request, con);
                    break;
                case "get_tipos_de_documentos":
                    html = get_tipos_de_documentos(request, con);
                    break;
                case "get_documentos_id":
                    html = get_documentos_id(request, con);
                    break;
                case "descargar":
                    descargar(request, response, con);
                    retornar = false;
                    break;
                case "get_documento_faltante":
                    html = get_documento_faltante(request, con);
                    break;
                case "get_usr_con":
                    html = get_usr_con(request, con);
                    break;
                case "subir_foto_perfil":
                    html = subir_foto_perfil(request, con);
                    break;
                case "subir_foto_vehiculo":
                    html = subir_foto_vehiculo(request, con);
                    break;
                case "buscar_vehiculo":
                    html = buscar_vehiculo(request, con);
                    break;
                case "get_vehiculo_id":
                    html = get_vehiculo_id(request, con);
                    break;
                case "actualizar_vehiculo":
                    html = actualizar_vehiculo(request, con);
                    break;
                case "actualizar_token":
                    html = actualizar_token(request, con);
                    break;
                case "ver_foto":
                    html = ver_foto(request, con);
                    break;
                case "is_act_super":
                    html = is_act_super(request, con);
                    break;
                case "terminar_turno":
                    html = terminar_turno(request, con);
                    break;
                case "get_costo":
                    html = get_costo(request, con);
                    break;
                case "get_dist_carrera":
                    html = get_dist_carrera(request, con);
                    break;
                case "cambiar_pass_x_admin":
                    html = cambiar_pass_x_admin(request, con);
                    break;
                case "editar_perfil_cliente":
                    html = editar_perfil_cliente(request, con);
                    break;
                case "cambiar_pass_cliente":
                    html = cambiar_pass_cliente(request, con);
                    break;
                case "marcar_costo_extra":
                    html = marcar_costo_extra(request, con);
                    break;
                case "registrar_usuario_face":
                    html = registrar_usuario_face(request, con);
                    break;
                case "get_transacciones_id":
                    html = get_transacciones_id(request, con);
                    break;
            }
            con.commit();
            con.Close();
            if (retornar) {
                response.getWriter().write(html);
            }
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().write("falso");
        } catch (ParseException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().write("falso");
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().write("falso");
        } catch (Exception ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
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

    private String set_pos_vehiculo(HttpServletRequest request, Conexion con) throws ParseException, SQLException {
        double lat = Double.parseDouble(request.getParameter("lat"));
        double lon = Double.parseDouble(request.getParameter("lon"));
        float bearing = Float.parseFloat(request.getParameter("bearing"));

        int id_vehiculo = Integer.parseInt(request.getParameter("id_vehiculo"));
        POSICION_VEHICULO pv = new POSICION_VEHICULO(con);
        pv.setLAT(lat);
        pv.setLON(lon);
        pv.setID_VEHICULO(id_vehiculo);
        pv.setBearing(bearing);
        pv.setFECHA(new Date());
        return pv.Insertar() + "";

    }

    private String get_pos_vehiculos(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        VEHICULO vei = new VEHICULO(con);
        return vei.todos_vehiculos_activos_ultima_posicion().toString();
    }

    private String notificar(HttpServletRequest request, Conexion con) throws Exception {
        String token = request.getParameter("token");
        //  DataToSend data = new DataToSend(token, "si");
        //Notificador.enviar(data);

        return "";
    }

    private String registrar_usuario(HttpServletRequest request, Conexion con) throws SQLException {
        String nombre = request.getParameter("nombre");
        String apellido_pa = request.getParameter("apellido_pa");
        String apellido_ma = request.getParameter("apellido_ma");
        String usuario = request.getParameter("usuario");
        usuario = usuario.trim();
        String pass = request.getParameter("pass");
        int id_rol = Integer.parseInt(request.getParameter("id_rol"));
        USUARIO usr = new USUARIO(con);
        usr.setNOMBRE(nombre);
        usr.setAPELLIDO_PA(apellido_pa);
        usr.setAPELLIDO_MA(apellido_ma);
        usr.setUSUARIO(usuario);
        usr.setCONTRASENHA(pass);
        usr.setID_ROL(id_rol);
        return usr.Insertar_sin_fecha()+ "";
    }

    private String login(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        String usuario = request.getParameter("usuario");
        String pass = request.getParameter("pass");
        USUARIO usr = new USUARIO(con);
        JSONObject obj = usr.get_por_usr_y_pass(usuario, pass);
        PERMISO per = new PERMISO(con);
        if (obj.getString("exito").equals("si")) {
            JSONArray arrpermisos = per.todos_de_rol(obj.getInt("id_rol"));
            obj.put("permisos", arrpermisos);
        }
        return obj.toString();
    }

    private String registrar_usuario_conductor(HttpServletRequest request, Conexion con) throws SQLException, ParseException, AddressException, MessagingException {
        int id_admin = Integer.parseInt(request.getParameter("id_admin"));
        String nombre = request.getParameter("nombre");
        String apellido_pa = request.getParameter("apellido_pa");
        String apellido_ma = request.getParameter("apellido_ma");
        String usuario = request.getParameter("usuario");
        usuario = usuario.trim();
        String pass = request.getParameter("pass");
        String sexo = request.getParameter("sexo");
        String correo = request.getParameter("correo");
        String fecha_nac = request.getParameter("fecha_nac");
        String ci = request.getParameter("ci");
        String telefono = request.getParameter("telefono");
        String referencia = request.getParameter("referencia");
        String ciudad = request.getParameter("ciudad");
        String numero_licencia = request.getParameter("n_licencia");
        String categoria_licencia = request.getParameter("cat_licencia");
        int id_rol = Integer.parseInt(request.getParameter("id_rol"));
        int id_con_ref = Integer.parseInt(request.getParameter("id_con_ref"));
        String t_estandar = request.getParameter("t_estandar");
        String t_togo = request.getParameter("t_togo");
        String t_maravilla = request.getParameter("t_maravilla");
        String t_super = request.getParameter("t_super");
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        USUARIO_CONDUCTOR usr = new USUARIO_CONDUCTOR(con);
        usr.setNOMBRE(nombre);
        usr.setAPELLIDO_PA(apellido_pa);
        usr.setAPELLIDO_MA(apellido_ma);
        usr.setFECHA_NAC(form.parse(fecha_nac));
        usr.setUSUARIO(usuario);
        usr.setCONTRASENHA(pass);
        usr.setID_ROL(id_rol);
        usr.setSEXO(sexo);
        usr.setCORREO(correo);
        usr.setCI(ci);
        usr.setCIUDAD(ciudad);
        usr.setTELEFONO(telefono);
        usr.setREFERENCIA(referencia);
        usr.setNUMERO_LICENCIA(numero_licencia);
        usr.setCATEGORIA(categoria_licencia);
        usr.setID_CONDUCTOR_REFE(id_con_ref);
        int id = usr.Insertar();
        TCARRERA_ACTIVO activo = new TCARRERA_ACTIVO(con);
        activo.setAct_estandar(Boolean.parseBoolean(t_estandar));
        activo.setAct_togo(Boolean.parseBoolean(t_togo));
        activo.setAct_maravilla(Boolean.parseBoolean(t_maravilla));
        activo.setAct_super(Boolean.parseBoolean(t_super));
        activo.Insertar(id);
        VEHICULO ve = new VEHICULO(con);
        String placa = java.util.UUID.randomUUID().toString();
        ve.setPLACA(placa);
        ve.setMARCA("n/a");
        ve.setMODELO("n/a");
        ve.setANO("n/a");
        ve.setCOLOR("n/a");
        ve.setCHASIS("n/a");
        ve.setNPUERTAS(0);
        ve.setMOTOR("n/a");
        ve.setESTADO(0);
        ve.setTIPO(1);
        int id_vehi = ve.Insertar();
        VEHICULO_CONDUCTOR vc = new VEHICULO_CONDUCTOR(con);
        vc.setID_VEHICULO(id_vehi);
        vc.setID_CONDUCTOR(id);
        vc.Insertar();
        MODIFICACIONES mod = new MODIFICACIONES(con);
        mod.setTIPO(4);
        mod.setID_USR(id);
        mod.setID_ADMIN(id_admin);
        mod.Insertar();
        final String username = URL.usr_correo;
        final String password = URL.pass_correo;
//        para poder enviar correos desde gmail
        Properties props = new Properties();
        // props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "mail.7.com.bo");
        // props.put("mail.smtp.port", "587");
        //  props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        }
        );
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(correo));
        message.setSubject("Bienvenido a siete.");
        String passori = request.getParameter("passori");
        String html = "";
        html += "<div style='text-align: center;'>";
        html += "<img src='http://localhost:8080/siete/img/imglogo.png' width='50%' alt=''/>";
        html += "<h1 style='color:#962c96'>BIENVENIDO A 7</h1>";
        html += "<div style='margin-left: 20%; margin-right: 20%; text-align: start;'>";
        html += "<h3>USUARIO: " + usuario + "</h3>";
        html += "<h3>CONTRASEÑA: " + passori + "</h3>";
        html += "</div>";
        html += "</div>";
        message.setContent(html, "text/html; charset=utf-8");
        Transport.send(message);

        return id + "";
    }

    private String get_categorias_vehiculo(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        CATEGORIA_VEHICULO cat = new CATEGORIA_VEHICULO(con);
        return cat.get_categorias().toString();
    }

    private String agg_categoria(HttpServletRequest request, Conexion con) throws SQLException {
        String categoria = request.getParameter("categoria");
        CATEGORIA_VEHICULO cat = new CATEGORIA_VEHICULO(con);
        cat.setCATEGORIA(categoria);
        return cat.Insertar() + "";
    }

    private String registrar_vehiculo(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        String placa = request.getParameter("placa");
        String marca = request.getParameter("marca");
        String modelo = request.getParameter("modelo");
        String anho = request.getParameter("anho");
        String color = request.getParameter("color");
        placa = placa.trim();
        String categorias = request.getParameter("categoria");
        String chasis = request.getParameter("chasis");
        int npuertas = Integer.parseInt(request.getParameter("npuertas"));
        String motor = request.getParameter("motor");
        JSONArray arr = new JSONArray(categorias);
        VEHICULO ve = new VEHICULO(con);
        ve.setPLACA(placa);
        ve.setMARCA(marca);
        ve.setMODELO(modelo);
        ve.setANO(anho);
        ve.setCOLOR(color);
        ve.setCHASIS(chasis);
        ve.setNPUERTAS(npuertas);
        ve.setMOTOR(motor);
        ve.setESTADO(0);
        ve.setTIPO(0);
        int id = ve.Insertar();
        if (id > 0) {
            VEHICULO_TO_CATEGORIA vtc;
            JSONObject obj;
            for (int i = 0; i < arr.length(); i++) {
                obj = arr.getJSONObject(i);
                vtc = new VEHICULO_TO_CATEGORIA(con);
                vtc.setID_CATEGORIA(obj.getInt("id"));
                vtc.setID_VEHICULO(id);
                vtc.Insertar();
            }
            int id_admin = Integer.parseInt(request.getParameter("id_admin"));
            MODIFICACIONES mod = new MODIFICACIONES(con);
            mod.setTIPO(2);
            mod.setID_VEHICULO(id);
            mod.setID_ADMIN(id_admin);
            mod.Insertar();
        } else {
            return "falso";
        }
        return id + "";
    }

    private String buscar_conductor(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        String noCi = request.getParameter("busqueda");
        USUARIO_CONDUCTOR usr = new USUARIO_CONDUCTOR(con);
        return usr.getConductoresPorCiOnomre(noCi).toString();
    }

    private String get_usuario_id_sp(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        USUARIO usr = new USUARIO(con);
        return usr.get_usuario_id_sp(id).toString();

    }

    private String get_vehiculos_asignados_cond(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        VEHICULO ve = new VEHICULO(con);
        return ve.asignados_conductor(id).toString();
    }

    private String get_vehiculos_sin_asignar_cond(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        String busq = request.getParameter("busq");
        VEHICULO ve = new VEHICULO(con);
        return ve.sin_asignar_conductor(id, busq).toString();
    }

    private String asignar_vehiculo(HttpServletRequest request, Conexion con) throws SQLException {
        int id_conductor = Integer.parseInt(request.getParameter("id_conductor"));
        int id_vehiculo = Integer.parseInt(request.getParameter("id_vehiculo"));
        VEHICULO_CONDUCTOR vc = new VEHICULO_CONDUCTOR(con);
        vc.setID_VEHICULO(id_vehiculo);
        vc.setID_CONDUCTOR(id_conductor);
        vc.Insertar();
        int id_admin = Integer.parseInt(request.getParameter("id_admin"));
        MODIFICACIONES mod = new MODIFICACIONES(con);
        mod.setTIPO(5);
        mod.setID_USR(id_conductor);
        mod.setID_VEHICULO(id_vehiculo);
        mod.setID_ADMIN(id_admin);
        mod.Insertar();
        return "exito";
    }

    private String eliminar_vehiculo(HttpServletRequest request, Conexion con) throws SQLException {
        int id_conductor = Integer.parseInt(request.getParameter("id_conductor"));
        int id_vehiculo = Integer.parseInt(request.getParameter("id_vehiculo"));
        VEHICULO_CONDUCTOR vc = new VEHICULO_CONDUCTOR(con);
        vc.setID_VEHICULO(id_vehiculo);
        vc.setID_CONDUCTOR(id_conductor);
        vc.eliminar();
        int id_admin = Integer.parseInt(request.getParameter("id_admin"));
        MODIFICACIONES mod = new MODIFICACIONES(con);
        mod.setTIPO(6);
        mod.setID_USR(id_conductor);
        mod.setID_VEHICULO(id_vehiculo);
        mod.setID_ADMIN(id_admin);
        mod.Insertar();
        return "exito";
    }

    private String get_turno_conductor(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id_conductor = Integer.parseInt(request.getParameter("id"));
        TURNOS tur = new TURNOS(con);
        return tur.getTurnoActivoConductor(id_conductor).toString();
    }

    private String get_vehiculo_disponible_con(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id_conductor = Integer.parseInt(request.getParameter("id"));
        VEHICULO vei = new VEHICULO(con);
        return vei.disponibles_conductor(id_conductor).toString();
    }

    private String iniciar_turno(HttpServletRequest request, Conexion con) throws SQLException {
        int id_vehiculo = Integer.parseInt(request.getParameter("id"));
        int id_conductor = Integer.parseInt(request.getParameter("id_cond"));
        String token = request.getParameter("token");
        TURNOS tur = new TURNOS(con);
        tur.setID_CONDUCTOR(id_conductor);
        tur.setID_VEHICULO(id_vehiculo);
        tur.setFECHA_INICIO(new Date());
        tur.setESTADO(1);
        tur.Insertar();

        return "exito";
    }

    private String iniciar_turno_sin_vehiculo(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id_conductor = Integer.parseInt(request.getParameter("id_cond"));
        String token = request.getParameter("token");
        VEHICULO vehi = new VEHICULO(con);
        JSONObject obj = vehi.get_no_vehiculo_conductor(id_conductor);
        TURNOS tur = new TURNOS(con);
        tur.setID_CONDUCTOR(id_conductor);
        tur.setID_VEHICULO(obj.getInt("id_vehiculo"));
        tur.setFECHA_INICIO(new Date());
        tur.setESTADO(1);
        tur.Insertar_tipo_sin_ve();
        return "exito";
    }

    private String confirmar_carrera(HttpServletRequest request, Conexion con) throws SQLException, JSONException, Exception {
        int id_carrera = Integer.parseInt(request.getParameter("id_carrera"));
        int id_conductor = Integer.parseInt(request.getParameter("id_conductor"));
        CARRERA car = new CARRERA(con);
        int confirmada = car.getConfirmoCarrera(id_carrera);
        if (confirmada == 1) {
            BuscarConductor bc = CarrerasEnBusqueda.get_carreras().get(id_carrera + "");
            bc.setEstado(true);
            CARRERA carrera = bc.getCarrera();
            TURNOS turn = new TURNOS(con);
            JSONObject objturno = turn.getTurnoActivoConductor(id_conductor);
            carrera.setID_TURNO(objturno.getInt("id"));
            carrera.setCon(con);
            int rows = carrera.confirmar_carrera();
            if (rows > 0) {
                JSONObject obj = carrera.getCarreraId(id_carrera);
                CarrerasEnBusqueda.get_carreras().get(id_carrera + "").setJson_Carrera(obj);
                CarrerasEnBusqueda.get_carreras().get(id_carrera + "").setEstado(true);
            }
            return "exito";
        } else {
            return "confirmada";
        }
    }

    private String conductor_cerca(HttpServletRequest request, Conexion con) throws SQLException, JSONException, Exception {
        int id_carrera = Integer.parseInt(request.getParameter("id_carrera"));
        CARRERA carrera = new CARRERA(con);
        JSONObject obj = carrera.getCarreraId(id_carrera);
        TOKEN tok = new TOKEN(con);
        JSONArray arr = tok.get_Token_usr(obj.getInt("id_usuario"));
        JSONObject obj_token;
        for (int j = 0; j < arr.length(); j++) {
            obj_token = arr.getJSONObject(j);
            String token = obj_token.getString("TOKEN");
            DataToSend data = new DataToSend(token, "conductor_cerca", obj.toString(), "");
            boolean resp = Notificador.enviar(data);
            if (resp) {
                break;
            }

        }
        return "exito";
    }

    private String conductor_llego(HttpServletRequest request, Conexion con) throws SQLException, JSONException, Exception {
        int id_carrera = Integer.parseInt(request.getParameter("id_carrera"));
        CARRERA carrera = new CARRERA(con);
        JSONObject obj = carrera.getCarreraId(id_carrera);
        TOKEN tok = new TOKEN(con);
        JSONArray arr = tok.get_Token_usr(obj.getInt("id_usuario"));
        JSONObject obj_token;
        for (int j = 0; j < arr.length(); j++) {
            obj_token = arr.getJSONObject(j);
            String token = obj_token.getString("TOKEN");
            DataToSend data = new DataToSend(token, "conductor_llego", obj.toString(), "");
            boolean resp = Notificador.enviar(data);
            if (resp) {
                break;
            }

        }

        carrera.setID(id_carrera);
        carrera.setFECHA_LLEGADA(new Date());
        carrera.update_llegada();
        return "exito";
    }

    private String inciar_carrera(HttpServletRequest request, Conexion con) throws SQLException, JSONException, Exception {
        int id_carrera = Integer.parseInt(request.getParameter("id_carrera"));
        CARRERA carrera = new CARRERA(con);
        JSONObject obj = carrera.getCarreraId(id_carrera);
        TOKEN tok = new TOKEN(con);
        JSONArray arr = tok.get_Token_usr(obj.getInt("id_usuario"));
        JSONObject obj_token;
        for (int j = 0; j < arr.length(); j++) {
            obj_token = arr.getJSONObject(j);
            String token = obj_token.getString("TOKEN");
            DataToSend data = new DataToSend(token, "Inicio_Carrera", obj.toString(), "");
            boolean resp = Notificador.enviar(data);
            if (resp) {
                break;
            }

        }
        carrera.setID(id_carrera);
        carrera.setFECHA_INICIO(new Date());
        carrera.iniciar_Carrera();
        return "exito";
    }

    private String registrar_tipo_carrera(HttpServletRequest request, Conexion con) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String desc = request.getParameter("desc");
        double costo_minuto = Double.parseDouble(request.getParameter("minuto"));
        double costo_km = Double.parseDouble(request.getParameter("km"));
        double costo_base = Double.parseDouble(request.getParameter("base"));
        int comision = Integer.parseInt(request.getParameter("comision"));
        double cancelacion_basica = Double.parseDouble(request.getParameter("cancelacion_basica"));
        double cancelacion_minuto = Double.parseDouble(request.getParameter("cancelacion_minuto"));
        double cancelacion_basica_conductor = Double.parseDouble(request.getParameter("cancelacion_basica_conductor"));
        double cancelacion_minuto_conductor = Double.parseDouble(request.getParameter("cancelacion_minuto_conductor"));
        int minutos_para_cancelar = Integer.parseInt(request.getParameter("minutos_para_cancelar"));
        int minutos_para_cancelar_conductor = Integer.parseInt(request.getParameter("minutos_para_cancelar_conductor"));
        COSTO costo = new COSTO(con);
        costo.setCOSTO_BASICO(costo_base);
        costo.setCOSTO_METRO(costo_km / 1000);
        costo.setCOSTO_MINUTO(costo_minuto);
        costo.setCOMISION(comision);
        costo.setCOSTO_BASICO_CANCELACION(cancelacion_basica);
        costo.setCOSTO_MINUTO_CANCELACION(cancelacion_minuto);
        costo.setCOSTO_BASICO_CANCELACION_CONDUCTOR(cancelacion_basica_conductor);
        costo.setCOSTO_MINUTO_CANCELACION_CONDUCTOR(cancelacion_minuto_conductor);
        costo.setMINUTOS_PARA_CANCELAR(minutos_para_cancelar);
        costo.setMINUTOS_PARA_CANCELAR_CONDUCTOR(minutos_para_cancelar_conductor);
        int idcosto = costo.Insertar();
        TIPO_CARRERA tipoCarrera = new TIPO_CARRERA(con);
        tipoCarrera.setID(id);
        tipoCarrera.setNOMBRE(nombre);
        tipoCarrera.setDESCRIPCION(desc);
        tipoCarrera.setID_COSTO(idcosto);
        int idTIpo = tipoCarrera.Insertar();
        return idTIpo + "";
    }

    private String terminar_carrera(HttpServletRequest request, Conexion con) throws SQLException, JSONException, Exception {
        int id_carrera = Integer.parseInt(request.getParameter("id_carrera"));
        CARRERA carrera = new CARRERA(con);
        carrera.setID(id_carrera);
        carrera.setFECHA_FIN(new Date());
        carrera.terminar_carrera();
        JSONObject obj = carrera.getCarreraId(id_carrera);
        int id_tipo = obj.getInt("id_tipo");
        COSTO costo = new COSTO(con);
        JSONObject costo_tipo_carrera = costo.get_costo_x_id_tipo(id_tipo);
        POSICION_VEHICULO vei = new POSICION_VEHICULO(con);
        JSONArray recorrido_carrera = vei.get_pos_id_Carrera(id_carrera);
        JSONObject object;
        JSONObject object2;
        double distancia = 0;
        double distanciafinal = 0.0;
        for (int i = 0; i < recorrido_carrera.length() - 1; i++) {
            object = recorrido_carrera.getJSONObject(i);
            object2 = recorrido_carrera.getJSONObject(i + 1);
            distancia += distanceLatLong2(object.getDouble("lat"), object.getDouble("lng"), object2.getDouble("lat"), object2.getDouble("lng"));
        }
        distanciafinal = distancia * 1000;
        distancia = distancia * 1000;
        double minutos = carrera.getTiempoCarreraMinutos(id_carrera);
        double distanciabs = distancia * costo_tipo_carrera.getDouble("costo_metro");
        double tiempobs = minutos * costo_tipo_carrera.getDouble("costo_minuto");
        int total = (int) (costo_tipo_carrera.getDouble("costo_basico") + distanciabs + tiempobs);
        DETALLE_COSTO_CARRERA de_distancia = new DETALLE_COSTO_CARRERA(con);
        de_distancia.setID_CARRERA(id_carrera);
        de_distancia.setTIPO(1);
        de_distancia.setCOSTO(distanciabs);
        de_distancia.setNombre("Distancia");
        de_distancia.Insertar_con_nombre();
        DETALLE_COSTO_CARRERA de_tiempo = new DETALLE_COSTO_CARRERA(con);
        de_tiempo.setID_CARRERA(id_carrera);
        de_tiempo.setTIPO(2);
        de_tiempo.setCOSTO(tiempobs);
        de_tiempo.setNombre("Tiempo");
        de_tiempo.Insertar_con_nombre();
        DETALLE_COSTO_CARRERA de_basico = new DETALLE_COSTO_CARRERA(con);
        de_basico.setID_CARRERA(id_carrera);
        de_basico.setTIPO(3);
        de_basico.setCOSTO(costo_tipo_carrera.getDouble("costo_basico"));
        de_basico.setNombre("Basico");
        de_basico.Insertar_con_nombre();
        COSTOS_EXTRAS extras = new COSTOS_EXTRAS(con);
        JSONArray costos_estras = extras.get_costos_extras_carrera(id_carrera);
        JSONObject temp;
         DETALLE_COSTO_CARRERA detalle;
         double preciotemp;
        for (int i = 0; i < costos_estras.length(); i++) {
            temp = costos_estras.getJSONObject(i);
            detalle = new DETALLE_COSTO_CARRERA(con);
            detalle.setID_CARRERA(id_carrera);
            detalle.setTIPO(5);
            preciotemp=temp.getDouble("costo");
            detalle.setCOSTO(preciotemp);
            detalle.setNombre(temp.getString("nombre"));
            detalle.Insertar_con_nombre();
            total+=preciotemp;
        }
        int comisionpor = costo_tipo_carrera.getInt("comision");
        float aux = (float) (comisionpor * total) / 100;
        carrera.insertarComision(aux);

        //COMPROVAR DEUDAS
        USUARIO usr = new USUARIO(con);
        double creditos = usr.get_creditos_id(obj.getInt("id_usuario"));
        if (creditos < 0) {
            DETALLE_COSTO_CARRERA de_pago_deuda = new DETALLE_COSTO_CARRERA(con);
            de_pago_deuda.setID_CARRERA(id_carrera);
            de_pago_deuda.setTIPO(4);
            de_pago_deuda.setCOSTO(creditos * -1);
            de_pago_deuda.setNombre("Deuda");
            de_pago_deuda.Insertar_con_nombre();
            total += (creditos * -1);
        }
        carrera.setDISTANCIA(distanciafinal);
        carrera.insertarTotal(total);
        return "exito";

    }

    public static double distanciaCoord(double lat1, double lng1, double lat2, double lng2) {
        //double radioTierra = 3958.75;//en millas  
        double radioTierra = 6371.0d;//en kilómetros  
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
        double distancia = radioTierra * va2;
        return distancia;
    }

    public static double distanceLatLong2(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371.0d; // KM: use mile here if you want mile result
        double dLat = toRadian(lat2 - lat1);
        double dLng = toRadian(lng2 - lng1);

        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(toRadian(lat1)) * Math.cos(toRadian(lat2))
                * Math.pow(Math.sin(dLng / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c; // returns result kilometers
    }

    public static double toRadian(double degrees) {
        return (degrees * Math.PI) / 180.0d;
    }

    private String cobrar_carrera(HttpServletRequest request, Conexion con) throws JSONException, SQLException, Exception {
        int id_carrera = Integer.parseInt(request.getParameter("id_carrera"));
        CARRERA carrera = new CARRERA(con);
        JSONObject obj = carrera.getCarreraId(id_carrera);
        JSONArray arr_costo = obj.getJSONArray("detalle_costo");
        JSONObject ob;
        for (int i = 0; i < arr_costo.length(); i++) {
            ob = arr_costo.getJSONObject(i);
            if (ob.getInt("tipo") == 4) {
                TRANSACCION_CREDITO trans_pago_deuda = new TRANSACCION_CREDITO(con);
                trans_pago_deuda.setCANTIDAD(ob.getDouble("costo"));
                trans_pago_deuda.setFECHA(new Date());
                trans_pago_deuda.setID_CARRERA(id_carrera);
                trans_pago_deuda.setID_USUARIO(obj.getInt("id_usuario"));
                trans_pago_deuda.setTIPO(4);
                trans_pago_deuda.pagar_deuda();
                TRANSACCION_CREDITO trans_pago_deuda_conductor = new TRANSACCION_CREDITO(con);
                trans_pago_deuda_conductor.setCANTIDAD(ob.getDouble("costo"));
                trans_pago_deuda_conductor.setFECHA(new Date());
                trans_pago_deuda_conductor.setID_CARRERA(id_carrera);
                trans_pago_deuda_conductor.setID_USUARIO(carrera.getid_conductor(id_carrera));
                trans_pago_deuda_conductor.setTIPO(5);
                trans_pago_deuda_conductor.pagar_deuda_conductor();

            }
        }
        TRANSACCION_CREDITO trans = new TRANSACCION_CREDITO(con);
        trans.setCANTIDAD(obj.getDouble("comision"));
        trans.setFECHA(new Date());
        trans.setID_CARRERA(id_carrera);
        trans.setID_USUARIO(carrera.getid_conductor(id_carrera));
        trans.setTIPO(2);
        String ip = request.getRemoteAddr();
        if (ip.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ipAddress = inetAddress.getHostAddress();
            ip = ipAddress;
        }
        trans.setIP(ip);
        String respTrans = trans.descontar_comision();
        if(obj.getInt("tipo_pago")==2){
                
        }
        if (respTrans.equals("exito")) {
            TOKEN tok = new TOKEN(con);
            JSONArray arr = tok.get_Token_usr(obj.getInt("id_usuario"));
            JSONObject obj_token;
            for (int j = 0; j < arr.length(); j++) {
                obj_token = arr.getJSONObject(j);
                String token = obj_token.getString("TOKEN");
                JSONObject objcarreradatos = carrera.getCarreraDatosCon(id_carrera);
                DataToSend data = new DataToSend(token, "Finalizo_Carrera", objcarreradatos.toString(), "");
                boolean resp = Notificador.enviar(data);
                if (resp) {
                    break;
                }

            }
        }
        CARRERA car = new CARRERA(con);
        car.setID(id_carrera);
        car.cobrar();
        //cobra y termina la carrera
        return respTrans;
    }

    private String agg_creditos(HttpServletRequest request, Conexion con) throws SQLException, UnknownHostException {
        int id_usr = Integer.parseInt(request.getParameter("id_usr"));
        double cantidad = Double.parseDouble(request.getParameter("cantidad"));
        int id_admin = Integer.parseInt(request.getParameter("id_admin"));
        TRANSACCION_CREDITO trans = new TRANSACCION_CREDITO(con);
        trans.setCANTIDAD(cantidad);
        trans.setFECHA(new Date());
        trans.setID_ADMIN(id_admin);
        trans.setID_USUARIO(id_usr);
        trans.setTIPO(1);
        String ip = request.getRemoteAddr();
        if (ip.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ipAddress = inetAddress.getHostAddress();
            ip = ipAddress;
        }
        trans.setIP(ip);
        String resp = trans.insertar_credito();
        return resp;
    }

    private String update_tc_activo(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id_usr = Integer.parseInt(request.getParameter("id_usr"));
        boolean estandar = Boolean.parseBoolean(request.getParameter("estandar"));
        boolean togo = Boolean.parseBoolean(request.getParameter("togo"));
        boolean maravilla = Boolean.parseBoolean(request.getParameter("maravilla"));
        boolean ssuper = Boolean.parseBoolean(request.getParameter("super"));
        TCARRERA_ACTIVO activo = new TCARRERA_ACTIVO(con);
        activo.setEstandar(estandar);
        activo.setTogo(togo);
        activo.setMaravilla(maravilla);
        activo.setSsuper(ssuper);
        activo.setID_CONDUCTOR(id_usr);

        activo.update();
        return activo.getTcActivo(id_usr).toString();
    }

    private String get_tc_activo(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id_usr = Integer.parseInt(request.getParameter("id_usr"));
        TCARRERA_ACTIVO activo = new TCARRERA_ACTIVO(con);
        return activo.getTcActivo(id_usr).toString();
    }

    private String get_carrera_conductor(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id_usr = Integer.parseInt(request.getParameter("id_usr"));
        CARRERA car = new CARRERA(con);
        return car.getCarreraConductor(id_usr).toString();
    }

    private String agg_motivo_cancelacion(HttpServletRequest request, Conexion con) throws SQLException {
        String motivo = request.getParameter("nombre");
        MOTIVO_CANCELACION cancela = new MOTIVO_CANCELACION(con);
        cancela.setNOMBRE(motivo);
        cancela.Insertar();
        return "extio";
    }

    private String get_motivos_cancelacion(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        MOTIVO_CANCELACION cancela = new MOTIVO_CANCELACION(con);
        return cancela.get_motivos().toString();
    }

    private String cancelar_carrera(HttpServletRequest request, Conexion con) throws SQLException, JSONException, ParseException {
        int id_urs = Integer.parseInt(request.getParameter("id_usr"));
        int id_carrera = Integer.parseInt(request.getParameter("id_carrera"));
        int id_tipo = Integer.parseInt(request.getParameter("id_tipo"));
        int tipo_cancelacion = Integer.parseInt(request.getParameter("tipo_cancelacion"));
        COSTO cos = new COSTO(con);
        JSONObject costo = cos.get_costo_x_id_carrera(id_carrera);
        CARRERA car = new CARRERA(con);
        JSONObject carrera = car.getCarreraId(id_carrera);
        JSONObject resp = new JSONObject();
        if (tipo_cancelacion == 1) {
            //cancela el conductor
            String fecha_inicio = carrera.getString("fecha_confirmacion");
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fecha_confir = form.parse(fecha_inicio);
            Date fecha_fin = new Date();
            long diff = fecha_fin.getTime() - fecha_confir.getTime();
            long diffMinutes = diff / (1000 * 60);
            int minutospermitidos = costo.getInt("minutos_para_cancelar_conductor");
            if (diffMinutes > minutospermitidos) {
                double costobasico = costo.getDouble("costo_basico_cancelacion_conductor");
                double costominuto = costo.getDouble("costo_minuto_cancelacion_conductor");
                double total = costobasico + (costominuto * diffMinutes);
                resp.put("exito", "true");
                resp.put("fecha_cancelacion", form.format(fecha_fin));
                resp.put("id_carrera", id_carrera);
                resp.put("id_tipo", id_tipo);
                resp.put("tipo_cancelacion", tipo_cancelacion);
                resp.put("id_usr", id_urs);
                resp.put("total", total);
                resp.put("cobro", "true");
            } else {
                resp.put("exito", "true");
                resp.put("fecha_cancelacion", form.format(fecha_fin));
                resp.put("id_carrera", id_carrera);
                resp.put("id_tipo", id_tipo);
                resp.put("tipo_cancelacion", tipo_cancelacion);
                resp.put("id_usr", id_urs);
                resp.put("total", 0);
                resp.put("cobro", "false");
            }
        } else if (tipo_cancelacion == 2) {
            //cancela el conductor
            String fecha_inicio = carrera.getString("fecha_confirmacion");
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fecha_confir = form.parse(fecha_inicio);
            Date fecha_fin = new Date();
            long diff = fecha_fin.getTime() - fecha_confir.getTime();
            long diffMinutes = diff / (1000 * 60);
            int minutospermitidos = costo.getInt("minutos_para_cancelar");
            if (diffMinutes > minutospermitidos) {
                double costobasico = costo.getDouble("costo_basico_cancelacion");
                double costominuto = costo.getDouble("costo_minuto_cancelacion");
                double total = costobasico + (costominuto * diffMinutes);
                resp.put("exito", "true");
                resp.put("fecha_cancelacion", form.format(fecha_fin));
                resp.put("id_carrera", id_carrera);
                resp.put("id_tipo", id_tipo);
                resp.put("tipo_cancelacion", tipo_cancelacion);
                resp.put("id_usr", id_urs);
                resp.put("total", total);
                resp.put("cobro", "true");
            } else {
                resp.put("exito", "true");
                resp.put("fecha_cancelacion", form.format(fecha_fin));
                resp.put("id_carrera", id_carrera);
                resp.put("id_tipo", id_tipo);
                resp.put("tipo_cancelacion", tipo_cancelacion);
                resp.put("id_usr", id_urs);
                resp.put("total", 0);
                resp.put("cobro", "false");
            }
        }
        return resp.toString();
    }

    private String ok_cancelar_carrera(HttpServletRequest request, Conexion con) throws JSONException, ParseException, UnknownHostException, SQLException, Exception {
        String resp = request.getParameter("json");
        JSONObject obj = new JSONObject(resp);
        CANCELACION can = new CANCELACION(con);
        can.setTOTAL(obj.getDouble("total"));
        can.setID_MOTIVO(obj.getInt("id_tipo"));
        can.setID_CARRERA(obj.getInt("id_carrera"));
        can.setTIPO(obj.getInt("tipo_cancelacion"));
        can.setID_USUARIO(obj.getInt("id_usr"));
        String fecha_inicio = obj.getString("fecha_cancelacion");
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        can.setFECHA(form.parse(fecha_inicio));
        can.Insertar();
        TRANSACCION_CREDITO trans = new TRANSACCION_CREDITO(con);
        trans.setCANTIDAD(can.getTOTAL());
        trans.setFECHA(new Date());
        trans.setID_CARRERA(can.getID_CARRERA());
        trans.setID_USUARIO(can.getID_USUARIO());
        trans.setTIPO(3);
        String ip = request.getRemoteAddr();
        if (ip.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ipAddress = inetAddress.getHostAddress();
            ip = ipAddress;
        }

        trans.setIP(ip);
        String respTrans = trans.pagar_cancelacion_conductor();
        if (respTrans.equals("exito")) {
            TOKEN tok = new TOKEN(con);
            CARRERA car = new CARRERA(con);
            JSONObject obj_carrera = car.getCarreraId(can.getID_CARRERA());
            JSONArray arr = null;
            if (can.getTIPO() == 1) {
                arr = tok.get_Token_usr(obj_carrera.getInt("id_usuario"));
            } else if (can.getTIPO() == 2) {
                arr = tok.get_Token_usr(obj_carrera.getJSONObject("turno").getInt("id_conductor"));
            }
            if (arr != null) {
                JSONObject obj_token;
                for (int j = 0; j < arr.length(); j++) {
                    obj_token = arr.getJSONObject(j);
                    String token = obj_token.getString("TOKEN");
                    DataToSend data = new DataToSend(token, "Carrera_Cancelada", can.getID_CARRERA() + "", "");
                    boolean resps = Notificador.enviar(data);
                    if (resps) {
                        break;
                    }

                }
            }

        }

        return "exito";
    }

    private String subir_archibo(HttpServletRequest request, Conexion con) throws IOException, ServletException, ParseException, SQLException {
        int id_usr = Integer.parseInt(request.getParameter("id_usr"));
        int id_tipo = Integer.parseInt(request.getParameter("id_tipo"));
        String fecha = request.getParameter("fecha");

        for (Part file : request.getParts()) {
            String name = "";
            if (file != null) {
                name = file.getSubmittedFileName();
                if (name != null) {
                    String ruta = request.getSession().getServletContext().getRealPath("/");
                    name = EVENTOS.guardar_file(file, URL.ruta_archibo_docu + "/" + id_usr + "/" + id_tipo + "", name);
                    DOCUMENTO doc = new DOCUMENTO(con);
                    doc.setId_tipo(id_tipo);
                    doc.setId_usr(id_usr);
                    doc.setSRC(URL.ruta_archibo_docu + "/" + id_usr + "/" + id_tipo + "/" + name);
                    if (fecha.length() <= 2) {
                        fecha = "2050-12-12";

                    }
                    SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
                    doc.setFecha_caducidad(form.parse(fecha));
                    doc.Insertar();
                }

            }

        }

        return "exito";
    }

    private String get_tipos_de_documentos(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        DOCUMENTO doc = new DOCUMENTO(con);
        return doc.get_tipos_documentos().toString();
    }

    private String get_documentos_id(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id_usr = Integer.parseInt(request.getParameter("id"));
        DOCUMENTO doc = new DOCUMENTO(con);
        return doc.get_documentos_usr(id_usr).toString();

    }

    private void descargar(HttpServletRequest request, HttpServletResponse response, Conexion con) throws FileNotFoundException, IOException {
        String SRC = request.getParameter("nombre_arc");

        File fileToDownload = new File(SRC);
        FileInputStream in = new FileInputStream(fileToDownload);
        ServletOutputStream out = response.getOutputStream();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileToDownload.getName() + "\"");

        //String mimeType =  new FileTypeMap().getContentType(filePath); 
        response.setContentType(Files.probeContentType(Paths.get(SRC)));
        response.setContentLength(in.available());

        int c;
        while ((c = in.read()) != -1) {
            out.write(c);
        }
        out.flush();
        out.close();
        in.close();
    }

    private String get_documento_faltante(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        DOCUMENTO doc = new DOCUMENTO(con);
        return doc.get_tipo_docu_faltante(id).toString();
    }

    private String get_usr_con(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        USUARIO_CONDUCTOR doc = new USUARIO_CONDUCTOR(con);
        return doc.getConductorId(id).toString();
    }

    private String actualizar_usuario_conductor(HttpServletRequest request, Conexion con) throws ParseException, SQLException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String apellido_pa = request.getParameter("apellido_pa");
        String apellido_ma = request.getParameter("apellido_ma");
        String sexo = request.getParameter("sexo");
        String correo = request.getParameter("correo");
        String fecha_nac = request.getParameter("fecha_nac");
        String ci = request.getParameter("ci");
        String telefono = request.getParameter("telefono");
        String referencia = request.getParameter("referencia");
        String ciudad = request.getParameter("ciudad");
        String t_estandar = request.getParameter("estandar");
        String t_togo = request.getParameter("togo");
        String t_maravilla = request.getParameter("maravilla");
        String t_super = request.getParameter("super");
        String numero_lic = request.getParameter("numero_lic");
        String categoria_lic = request.getParameter("categoria_lic");
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        USUARIO_CONDUCTOR usr = new USUARIO_CONDUCTOR(con);
        usr.setID(id);
        usr.setNOMBRE(nombre);
        usr.setAPELLIDO_PA(apellido_pa);
        usr.setAPELLIDO_MA(apellido_ma);
        usr.setFECHA_NAC(form.parse(fecha_nac));
        usr.setSEXO(sexo);
        usr.setCORREO(correo);
        usr.setCI(ci);
        usr.setCIUDAD(ciudad);
        usr.setTELEFONO(telefono);
        usr.setREFERENCIA(referencia);
        usr.setNUMERO_LICENCIA(numero_lic);
        usr.setCATEGORIA(categoria_lic);
        int row = usr.updatePerfilConductor();
        TCARRERA_ACTIVO activo = new TCARRERA_ACTIVO(con);
        activo.setID_CONDUCTOR(id);
        activo.setAct_estandar(Boolean.parseBoolean(t_estandar));
        activo.setAct_togo(Boolean.parseBoolean(t_togo));
        activo.setAct_maravilla(Boolean.parseBoolean(t_maravilla));
        activo.setAct_super(Boolean.parseBoolean(t_super));
        activo.update_parametros();
        int id_admin = Integer.parseInt(request.getParameter("id_admin"));
        MODIFICACIONES mod = new MODIFICACIONES(con);
        mod.setTIPO(3);
        mod.setID_USR(id);
        mod.setID_ADMIN(id_admin);
        mod.Insertar();
        return row + "";
    }

    private String subir_foto_perfil(HttpServletRequest request, Conexion con) throws IOException, ServletException, SQLException {
        int id_usr = Integer.parseInt(request.getParameter("id_usr"));
        Part file = request.getPart("archibo");
        String name = "";
        String names = "";

        if (file != null) {
            names = file.getSubmittedFileName();
            String ruta = request.getSession().getServletContext().getRealPath("/");
            name = EVENTOS.guardar_file(file, ruta + URL.ruta_foto_prefil + "/" + id_usr + "/", names);
        }
        USUARIO usr = new USUARIO(con);
        usr.setID(id_usr);
        usr.setFOTO_PERFIL(URL.ruta_foto_prefil + "/" + id_usr + "/" + name);
        usr.subir_foto_perfil();

        return "exito";
    }

    private String buscar_vehiculo(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        String noCi = request.getParameter("busqueda");
        VEHICULO vei = new VEHICULO(con);
        return vei.buscar_por_placa(noCi).toString();
    }

    private String get_vehiculo_id(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id_vehiculo = Integer.parseInt(request.getParameter("id"));
        VEHICULO ve = new VEHICULO(con);
        return ve.get_vehiculo_id(id_vehiculo).toString();
    }

    private String subir_foto_vehiculo(HttpServletRequest request, Conexion con) throws IOException, ServletException, SQLException {
        int id_usr = Integer.parseInt(request.getParameter("id"));
        Part file = request.getPart("archibo");
        String name = "";
        String names = "";

        if (file != null) {
            names = file.getSubmittedFileName();
            String ruta = request.getSession().getServletContext().getRealPath("/");
            name = EVENTOS.guardar_file(file, ruta + URL.ruta_foto_vehiculo + "/" + id_usr + "/", names);
        }
        VEHICULO vei = new VEHICULO(con);
        vei.setID(id_usr);
        vei.setFOTO_PERFIL(URL.ruta_foto_vehiculo + "/" + id_usr + "/" + name);
        vei.subir_foto_perfil();
        return "exito";
    }

    private String actualizar_vehiculo(HttpServletRequest request, Conexion con) throws JSONException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String placa = request.getParameter("placa");
        String marca = request.getParameter("marca");
        String modelo = request.getParameter("modelo");
        String anho = request.getParameter("anho");
        String color = request.getParameter("color");
        String chasis = request.getParameter("chasis");
        String motor = request.getParameter("motor");
        int npuertas = Integer.parseInt(request.getParameter("npuertas"));
        String categorias = request.getParameter("categoria");
        JSONArray arr = new JSONArray(categorias);
        VEHICULO ve = new VEHICULO(con);
        ve.setID(id);
        ve.setPLACA(placa);
        ve.setMARCA(marca);
        ve.setMODELO(modelo);
        ve.setANO(anho);
        ve.setCOLOR(color);
        ve.setCHASIS(chasis);
        ve.setNPUERTAS(npuertas);
        ve.setMOTOR(motor);
        ve.update();
        VEHICULO_TO_CATEGORIA vtc = new VEHICULO_TO_CATEGORIA(con);
        JSONArray cat = vtc.get_categorias_de_vehiculo(id);
        JSONObject obj;
        JSONObject obj2;
        boolean exist = false;
        for (int i = 0; i < arr.length(); i++) {
            obj = arr.getJSONObject(i);
            vtc = new VEHICULO_TO_CATEGORIA(con);
            vtc.setID_CATEGORIA(obj.getInt("id"));
            vtc.setID_VEHICULO(id);
            exist = false;
            for (int j = 0; j < cat.length(); j++) {
                obj2 = cat.getJSONObject(j);
                if (obj2.getInt("id_vehiculo") == id && obj2.getInt("id_categoria") == obj.getInt("id")) {
                    exist = true;
                    continue;
                }
            }
            if (obj.getString("estado").equals("true")) {
                if (!exist) {
                    vtc.Insertar();
                }
            } else {
                if (exist) {
                    vtc.eliminar();
                }
            }
        }
        int id_admin = Integer.parseInt(request.getParameter("id_admin"));
        MODIFICACIONES mod = new MODIFICACIONES(con);
        mod.setTIPO(1);
        mod.setID_VEHICULO(id);
        mod.setID_ADMIN(id_admin);
        mod.Insertar();
        //vtc.setID_CATEGORIA(categoria);
        return id + "";
    }

    private String ver_foto(HttpServletRequest request, Conexion con) throws SQLException, JSONException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        DOCUMENTO doc = new DOCUMENTO(con);
        JSONObject obj = doc.get_docu(id);
        String b64 = EVENTOS.obtener_file(obj.getString("src"));
        obj.put("b64", b64);
        return obj.toString();
    }

    private String actualizar_token(HttpServletRequest request, Conexion con) {
        int id = Integer.parseInt(request.getParameter("id_usr"));
        String token = request.getParameter("token");
        TOKEN tok = new TOKEN(con);
        tok.setID_USR(id);
        tok.setTOKEN(token);
        tok.Insertar();
        return "exito";
    }

    private String registrar_usuario_cliente(HttpServletRequest request, Conexion con) throws SQLException, ParseException {
        String nombre = request.getParameter("nombre");
        String apellido_pa = request.getParameter("apellido_pa");
        String apellido_ma = request.getParameter("apellido_ma");
        String usuario = request.getParameter("usuario");
        String pass = request.getParameter("pass");
        String sexo = request.getParameter("sexo");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        String fecha_nac = request.getParameter("fecha");
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");

        USUARIO usr = new USUARIO(con);
        usr.setNOMBRE(nombre);
        usr.setAPELLIDO_PA(apellido_pa);
        usr.setAPELLIDO_MA(apellido_ma);
        usr.setUSUARIO(usuario);
        usr.setCONTRASENHA(pass);
        usr.setCORREO(correo);
        usr.setSEXO(sexo);
        usr.setID_ROL(4);
        usr.setTELEFONO(telefono);
        usr.setFECHA_NAC(form.parse(fecha_nac));
        return usr.Insertar() + "";
    }

    private String is_act_super(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        TCARRERA_ACTIVO act = new TCARRERA_ACTIVO(con);
        return act.getTcActivo(id).toString();
    }

    private String terminar_turno(HttpServletRequest request, Conexion con) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        TURNOS tur = new TURNOS(con);
        tur.Terminar_turno_conductor(id);
        return "exito";
    }

    private String get_costo(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        COSTO costo = new COSTO(con);
        return costo.get_costo_x_id_tipo(id).toString();
    }

    private String get_dist_carrera(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        POSICION_VEHICULO vei = new POSICION_VEHICULO(con);
        JSONArray recorrido_carrera = vei.get_pos_id_Carrera(id);
        JSONObject object;
        JSONObject object2;
        double distancia = 0;
        for (int i = 0; i < recorrido_carrera.length() - 1; i++) {
            object = recorrido_carrera.getJSONObject(i);
            object2 = recorrido_carrera.getJSONObject(i + 1);
            distancia += distanceLatLong2(object.getDouble("lat"), object.getDouble("lng"), object2.getDouble("lat"), object2.getDouble("lng"));
        }
        distancia = distancia * 1000;
        CARRERA car = new CARRERA(con);
        car.setID(87);
        car.setDISTANCIA(distancia);
        car.insertarTotal(7);

        return distancia + " dislat";
    }

    private String comprovar_usr(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        String usr = request.getParameter("usr");
        usr = usr.trim();
        USUARIO user = new USUARIO(con);
        return user.validar_usr(usr) + "";
    }

    private String comprovar_placa(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        String placa = request.getParameter("placa");
        placa = placa.trim();
        VEHICULO vehi = new VEHICULO(con);
        return vehi.validar_placa(placa) + "";
    }

    private String cambiar_pass_x_admin(HttpServletRequest request, Conexion con) throws AddressException, MessagingException, SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id"));
        int id_admin = Integer.parseInt(request.getParameter("id_admin"));
        String passmd5 = request.getParameter("pass");
        String passreal = request.getParameter("passreal");

        USUARIO usr = new USUARIO(con);

        JSONObject obj = usr.get_usr_correo_x_id(id);
        usr.setID(id);
        usr.setCONTRASENHA(passmd5);
        usr.cambiar_pass();
        MODIFICACIONES mod = new MODIFICACIONES(con);
        mod.setTIPO(9);
        mod.setID_USR(id);
        mod.setID_ADMIN(id_admin);
        mod.Insertar();
        final String username = URL.usr_correo;
        final String password = URL.pass_correo;
//        para poder enviar correos desde gmail
        Properties props = new Properties();
        // props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "mail.7.com.bo");
        // props.put("mail.smtp.port", "587");
        //  props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        }
        );
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(obj.getString("correo")));
        message.setSubject("7: Cambio de Contraseña.");
        String html = "";
        html += "<div style='text-align: center;'>";
        html += "<img src='http://204.93.196.61:8080/siete/img/imglogo.jpeg' width='50%' alt=''/>";
        html += "<h1 style='color:#962c96'>7 SIETE</h1>";
        html += "<h1 style='color:#962c96'>NUEVA CONTRASEÑA</h1>";
        html += "<div style='margin-left: 20%; margin-right: 20%; text-align: start;'>";
        html += "<h3>USUARIO: " + obj.getString("usuario") + "</h3>";
        html += "<h3>CONTRASEÑA: " + passreal + "</h3>";
        html += "</div>";
        html += "</div>";
        message.setContent(html, "text/html; charset=utf-8");
        Transport.send(message);
        return "exito";
        // ----------change passs -------------
    }

    private String confirmar_compra(HttpServletRequest request, Conexion con) throws SQLException, JSONException, Exception {
        int id_carrera = Integer.parseInt(request.getParameter("id_carrera"));
        CARRERA carrera = new CARRERA(con);
        JSONObject obj = carrera.getCarreraId(id_carrera);
        TOKEN tok = new TOKEN(con);
        JSONArray arr = tok.get_Token_usr(obj.getInt("id_usuario"));
        JSONObject obj_token;
        for (int j = 0; j < arr.length(); j++) {
            obj_token = arr.getJSONObject(j);
            String token = obj_token.getString("TOKEN");
            DataToSend data = new DataToSend(token, "confirmo_compra", obj.toString(), "");
            boolean resp = Notificador.enviar(data);
            if (resp) {
                break;
            }

        }
        carrera.setID(id_carrera);
        carrera.setFECHA_INICIO(new Date());
        carrera.confirmarCompra();
        return "exito";
    }

    private String editar_perfil_cliente(HttpServletRequest request, Conexion con) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id_usuario"));
        String nombre = request.getParameter("nombre");
        String apellido_pa = request.getParameter("apellido_pa");
        String apellido_ma = request.getParameter("apellido_ma");
        String telefono = request.getParameter("telefono");
        String correo = request.getParameter("correo");
        USUARIO usr = new USUARIO(con);
        usr.setID(id);
        usr.setNOMBRE(nombre);
        usr.setAPELLIDO_PA(apellido_pa);
        usr.setAPELLIDO_MA(apellido_ma);
        usr.setTELEFONO(telefono);
        usr.setCORREO(correo);
        usr.editar_usuario();
        return "exito";
    }

    private String cambiar_pass_cliente(HttpServletRequest request, Conexion con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String get_costos_extras(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        COSTOS_EXTRAS costos = new COSTOS_EXTRAS(con);
        return costos.get_costos_extras().toString();
    }

    private String agg_costo_extra(HttpServletRequest request, Conexion con) throws SQLException {
        String nombre = request.getParameter("nombre");
        Double costo = Double.parseDouble(request.getParameter("costo"));
        COSTOS_EXTRAS ext = new COSTOS_EXTRAS(con);
        ext.setNOMBRE(nombre);
        ext.setCOSTO(costo);
        return ext.Insertar() + "";
    }

    private String get_costos_extras_estado(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id = Integer.parseInt(request.getParameter("id_carrera"));
        COSTOS_EXTRAS ext = new COSTOS_EXTRAS(con);
        return ext.get_costos_extras_carrera_estado(id).toString();
    }

    private String marcar_costo_extra(HttpServletRequest request, Conexion con) throws SQLException, JSONException, Exception {
        int id_carrera = Integer.parseInt(request.getParameter("id_carrera"));
        int id_costo = Integer.parseInt(request.getParameter("id_costo"));
        COSTOS_EXTRAS costos_extras = new COSTOS_EXTRAS(con);
        int res = costos_extras.marcar(id_carrera, id_costo);
        JSONObject obj_costo_extra = costos_extras.get_costo_extra_id(id_costo);
        if (res > 0) {
            CARRERA carrera = new CARRERA(con);
            JSONObject obj = carrera.getUsuarioCarrera(id_carrera);
            TOKEN tok = new TOKEN(con);
            JSONArray arr = tok.get_Token_usr(obj.getInt("id_usuario"));
            JSONObject obj_token;
            for (int j = 0; j < arr.length(); j++) {
                obj_token = arr.getJSONObject(j);
                String token = obj_token.getString("TOKEN");
                DataToSend data = null;
                if (res == 1) {
                    data = new DataToSend(token, "agrego_costo_extra", obj_costo_extra.toString(), "");
                } else if (res == 2) {
                    data = new DataToSend(token, "elimino_costo_extra", obj_costo_extra.toString(), "");
                }
                boolean resp = Notificador.enviar(data);
                if (resp) {
                    break;
                }

            }
        }
        return "exito";

    }

    private String registrar_usuario_face(HttpServletRequest request, Conexion con) throws SQLException, SQLException, JSONException {
            String nombre=request.getParameter("nombre");
            String apellidos=request.getParameter("apellidos");
            String telefonos=request.getParameter("telefonos");
            String correo=request.getParameter("correo");
            String Sexo=request.getParameter("Sexo"); 
            String id=request.getParameter("id"); 
            USUARIO usr = new USUARIO(con);
            usr.setNOMBRE(nombre);
            usr.setAPELLIDO_PA(apellidos);
            usr.setCORREO(correo);
            usr.setTELEFONO(telefonos);
            usr.setSEXO(Sexo);
            usr.setID_FACE(id);
            int idusr =usr.Insertar_face();
            return "exito";
    }

    private String get_transacciones_id(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
        int id=Integer.parseInt(request.getParameter("id"));
        TRANSACCION_CREDITO tras = new TRANSACCION_CREDITO(con);
        return tras.get_transacciones_id(id).toString();
    }
}
