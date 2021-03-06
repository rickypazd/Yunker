/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLADOR;

import Conexion.Conexion;
import Firebasse.DataToSend;
import Firebasse.Notificador;
import MODELO.ALMACEN.ALMACEN;
import MODELO.ALMACEN.STOCK;
import MODELO.ARTICULO.ARTICULO;
import MODELO.ARTICULO.ART_CATEGORIA;
import MODELO.ARTICULO.ART_DEPARTAMENTO;
import MODELO.ARTICULO.ART_MARCA;
import MODELO.ARTICULO.ART_UNIDAD_MEDIDA;
import MODELO.ARTICULO.SEG_ARTICULO;
import MODELO.ARTICULO.SEG_ART_CATEGORIA;
import MODELO.ARTICULO.SEG_ART_DEPARTAMENTO;
import MODELO.ARTICULO.SEG_ART_MARCA;
import MODELO.ARTICULO.SEG_ART_UNIDAD_MEDIDA;
import MODELO.COMPRA.COMPRA;
import MODELO.COMPRA.DETALLE_COMPRA;
import MODELO.PERMISO;
import MODELO.PERSONA.PERSONA;
import MODELO.REPUESTO.REPUESTO;
import MODELO.REPUESTO.REP_AUTO;
import MODELO.REPUESTO.REP_AUTO_MARCA;
import MODELO.REPUESTO.REP_AUTO_MODELO;
import MODELO.REPUESTO.REP_AUTO_VERSION;
import MODELO.REPUESTO.REP_AUTO_VERSION_TO_REP_AUTO;
import MODELO.REPUESTO.REP_CATEGORIA;
import MODELO.REPUESTO.REP_ESQUEMA;
import MODELO.REPUESTO.REP_PARTES;
import MODELO.REPUESTO.REP_SUB_CATEGORIA;
import MODELO.REPUESTO.REP_SUB_CATEGORIA_ACTIVA;
import MODELO.REPUESTO.REP_SUB_CATEGORIA_DISPONIBLE;
import MODELO.SEGURIDAD.SEG_MODIFICACIONES;
import UTILES.URL;
import MODELO.USUARIO;
import UTILES.EVENTOS;
import UTILES.RESPUESTA;
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
@WebServlet(name = "repuestosController", urlPatterns = {"/repuestosController"})

public class repuestosController extends HttpServlet {

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

        //<editor-fold defaultstate="collapsed" desc="CONFIG">
        Conexion con = new Conexion(URL.db_usr, URL.db_pass);
        con.Transacction();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        String evento = request.getParameter("evento");
        String tokenAcceso = request.getParameter("TokenAcceso") != null ? request.getParameter("TokenAcceso") : "";
        boolean retornar = true;
        String html = "";
//</editor-fold>

        if (tokenAcceso.equals(URL.TokenAcceso)) {
            switch (evento) {
                //<editor-fold defaultstate="collapsed" desc="REPUESTO">
                case "registrar_repuesto":
                    html = registrar_repuesto(request, con);
                    break;
                case "getById_repuesto":
                    html = getById_repuesto(request, con);
                    break;
                case "getAll_repuesto":
                    html = getAll_repuesto(request, con);
                    break;
                case "getAll_repuesto_de_vehiculo":
                    html = getAll_repuesto_de_vehiculo(request, con);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REP_AUTO">
                case "registrar_rep_auto":
                    html = registrar_rep_auto(request, con);
                    break;

                case "getBy_id_marca_and_id_modelo":
                    html = getBy_id_marca_and_id_modelo(request, con);
                    break;

                case "get_vehiculos_disponibles_by_id_repuesto":
                    html = get_vehiculos_disponibles_by_id_repuesto(request, con);
                    break;
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REP_AUTO_MARCA">
                case "registrar_rep_auto_marca":
                    html = registrar_rep_auto_marca(request, con);
                    break;
                case "getAll_rep_auto_marca":
                    html = getAll_rep_auto_marca(request, con);
                    break;
                case "getAll_rep_auto_marca_registrados":
                    html = getAll_rep_auto_marca_registrados(request, con);
                    break;

                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REP_AUTO_MODELO">
                case "registrar_rep_auto_modelo":
                    html = registrar_rep_auto_modelo(request, con);
                    break;
                case "get_rep_auto_modelo_by_id_rep_auto_marca":
                    html = get_rep_auto_modelo_by_id_rep_auto_marca(request, con);
                    break;
                case "get_rep_auto_modelo_by_id_rep_auto_marca_registrados":
                    html = get_rep_auto_modelo_by_id_rep_auto_marca_registrados(request, con);
                    break;

                //</editor-fold>                    
                //<editor-fold defaultstate="collapsed" desc="REP_AUTO_VERSION">
                case "registrar_rep_auto_version":
                    html = registrar_rep_auto_version(request, con);
                    break;
                case "getAll_rep_auto_version":
                    html = getAll_rep_auto_version(request, con);
                    break;
                case "getAll_rep_auto_version_by_id_auto":
                    html = getAll_rep_auto_version_by_id_auto(request, con);
                    break;

                //</editor-fold>                    
                //<editor-fold defaultstate="collapsed" desc="REP_CATEGORIA">
                case "registrar_rep_categoria":
                    html = registrar_rep_categoria(request, con);
                    break;
                case "getAll_rep_categoria":
                    html = getAll_rep_categoria(request, con);
                    break;
                case "getAll_rep_categoria_de_vehiculo":
                    html = getAll_rep_categoria_de_vehiculo(request, con);
                    break;

                //</editor-fold>         
                //<editor-fold defaultstate="collapsed" desc="REP_ESQUEMA">
                case "registrar_rep_esquema":
                    html = registrar_rep_esquema(request, con);
                    break;
                case "getById_rep_esquema":
                    html = getById_rep_esquema(request, con);
                    break;

                case "registrar_rep_partes":
                    html = registrar_rep_partes(request, con);
                    break;

                case "getById_rep_partes":
                    html = getById_rep_partes(request, con);
                    break;

                case "getAll_rep_partes_by_id_repuesto":
                    html = getAll_rep_partes_by_id_repuesto(request, con);
                    break;

//                case "getAll_rep_categoria":
//                    html = getAll_rep_categoria(request, con);
//                    break;
                //</editor-fold>         
                //<editor-fold defaultstate="collapsed" desc="REP_SUB_CATEGORIA">
                case "registrar_rep_sub_categoria":
                    html = registrar_rep_sub_categoria(request, con);
                    break;
                case "getAll_rep_sub_categoria":
                    html = getAll_rep_sub_categoria(request, con);
                    break;
                case "get_rep_sub_categoria_by_id_rep_categoria":
                    html = get_rep_sub_categoria_by_id_rep_categoria(request, con);
                    break;
                case "get_rep_sub_categoria_by_id_rep_categoria_de_id_vehiculo":
                    html = get_rep_sub_categoria_by_id_rep_categoria_de_id_vehiculo(request, con);
                    break;

                //</editor-fold>                    
                //<editor-fold defaultstate="collapsed" desc="REP_SUB_CATEGORIA_ACTIVA">
                case "registrar_rep_sub_categoria_activa":
                    html = registrar_rep_sub_categoria_activa(request, con);
                    break;
//                case "getAll_rep_sub_categoria":
//                    html = getAll_rep_sub_categoria(request, con);
//                    break;
//                case "get_rep_sub_categoria_by_id_rep_categoria":
//                    html = get_rep_sub_categoria_by_id_rep_categoria(request, con);
//                    break;

                //</editor-fold>                    
                //<editor-fold defaultstate="collapsed" desc="UTILES">
                case "descargar":
                    descargar(request, response, con);
                    retornar = false;
                    break;
                default:
                    RESPUESTA resp = new RESPUESTA(0, "Servisis: No se encontro el parametro evento.", "Servicio no encontrado.", "{}");
                    html = resp.toString();
                    break;
//</editor-fold>

            }
        } else {
            RESPUESTA resp = new RESPUESTA(0, "Servisis: Token de acceso erroneo.", "Token denegado", "{}");
            html = resp.toString();
        }
        //<editor-fold defaultstate="collapsed" desc="RESPUESTA">
        con.commit();
        con.Close();
        if (retornar) {
            response.getWriter().write(html);
        }
//</editor-fold>
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

    //<editor-fold defaultstate="collapsed" desc="DOWNLOAD">
    private void descargar(HttpServletRequest request, HttpServletResponse response, Conexion con) {
        try {
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
        } catch (FileNotFoundException ex) {
            con.rollback();
            Logger.getLogger(repuestosController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "No se encontro el archibo.", "{}");
        } catch (IOException ex) {
            con.rollback();
            Logger.getLogger(repuestosController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "No se encontro el archibo.", "{}");
        }

    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="REPUESTO">

    private String registrar_repuesto(HttpServletRequest request, Conexion con) {
        String nameAlert = "repuesto";
        try {
            REPUESTO repuesto = new REPUESTO(con);
            repuesto.setID_SUB_CATEGORIA(pInt(request, "id_sub_categoria"));
            repuesto.setNOMBRE(pString(request, "nombre"));
            repuesto.setDESCRIPCION(pString(request, "descripcion"));
            repuesto.setFABRICANTE(pString(request, "fabricante"));
            repuesto.setOTROS_NOMBRES(pString(request, "otros_nombres"));
            repuesto.setPRECIO(pDouble(request, "precio"));
            repuesto.setSERIE(pString(request, "serie"));
            int id = repuesto.Insertar();
            repuesto.setID(id);
            Part file = request.getPart("foto");
            String name = "";
            String names = "";
            if (file != null) {
                names = file.getSubmittedFileName();
                String ruta = request.getSession().getServletContext().getRealPath("/");
                name = EVENTOS.guardar_file(file, ruta + URL.ruta_foto_repuesto + "/" + id + "/", names);
            }
            repuesto.setURL_FOTO(URL.ruta_foto_repuesto + "/" + id + "/" + name);
            repuesto.subir_foto_perfil();
            RESPUESTA resp = new RESPUESTA(1, "", nameAlert + " registrado con exito.", repuesto.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        } catch (IOException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al subir foto.", "{}");
            return resp.toString();
        } catch (ServletException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al subir foto.", "{}");
            return resp.toString();
        }
    }

    private String getById_repuesto(HttpServletRequest request, Conexion con) {
        String nameAlert = "repuesto";
        try {
            REPUESTO repuesto = new REPUESTO(con);
            int id = pInt(request, "id");
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", repuesto.getById(id).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String getAll_repuesto(HttpServletRequest request, Conexion con) {
        String nameAlert = "repuesto";
        try {
            REPUESTO repuesto = new REPUESTO(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", repuesto.getAll().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
    private String getAll_repuesto_de_vehiculo(HttpServletRequest request, Conexion con) {
        String nameAlert = "repuesto";
        try {
            REPUESTO repuesto = new REPUESTO(con);
            int id_sub_cat = pInt(request, "id_sub_categoria_activa");
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", repuesto.getAll_by_id_sub_cat_activa(id_sub_cat).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="REP_AUTO">

    private String registrar_rep_auto(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_auto";
        try {
            REP_AUTO rep_auto = new REP_AUTO(con);
            rep_auto.setID_MARCA(pInt(request, "id_marca"));
            rep_auto.setID_MODELO(pInt(request, "id_modelo"));
            rep_auto.setANHO(pString(request, "anho"));
            rep_auto.setCLAVE(pString(request, "clave"));
            rep_auto.setMARCA(pString(request, "marca"));
            rep_auto.setMODELO(pString(request, "modelo"));
            String versiones = pString(request, "versiones");
            JSONArray arr_versiones = new JSONArray(versiones);
            int id = rep_auto.Insertar();
            rep_auto.setID(id);
            JSONObject obj_version;
            REP_AUTO_VERSION_TO_REP_AUTO rep_auto_version_to_rep_auto = new REP_AUTO_VERSION_TO_REP_AUTO(con);
            rep_auto_version_to_rep_auto.setID_REP_AUTO(id);
            for (int i = 0; i < arr_versiones.length(); i++) {
                obj_version = arr_versiones.getJSONObject(i);
                rep_auto_version_to_rep_auto.setID_REP_AUTO_VERSION(obj_version.getInt("id"));
                rep_auto_version_to_rep_auto.Insertar();
            }
            RESPUESTA resp = new RESPUESTA(1, "", nameAlert + " registrado con exito.", rep_auto.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String getBy_id_marca_and_id_modelo(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_auto";
        try {
            REP_AUTO rep_auto = new REP_AUTO(con);
            int id_marca = pInt(request, "id_marca");
            int id_modelo = pInt(request, "id_modelo");
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", rep_auto.getBy_id_marca_and_id_modelo(id_marca, id_modelo).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String get_vehiculos_disponibles_by_id_repuesto(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_auto";
        try {
            REP_AUTO rep_auto = new REP_AUTO(con);
            int repuesto = pInt(request, "id_repuesto");
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", rep_auto.getAllById_repuesto(repuesto).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="REP_AUTO_MARCA">

    private String registrar_rep_auto_marca(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_auto_marca";
        try {
            REP_AUTO_MARCA rep_auto_marca = new REP_AUTO_MARCA(con);
            rep_auto_marca.setNOMBRE(pString(request, "nombre"));
            int id = rep_auto_marca.Insertar();
            rep_auto_marca.setID(id);
            Part file = request.getPart("foto");
            String name = "";
            String names = "";
            if (file != null) {
                names = file.getSubmittedFileName();
                String ruta = request.getSession().getServletContext().getRealPath("/");
                name = EVENTOS.guardar_file(file, ruta + URL.ruta_foto_rep_auto_marca + "/" + id + "/", names);
            }
            rep_auto_marca.setURL_FOTO(URL.ruta_foto_rep_auto_marca + "/" + id + "/" + name);

            rep_auto_marca.subir_foto_perfil();
            RESPUESTA resp = new RESPUESTA(1, "", nameAlert + " registrado con exito.", rep_auto_marca.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        } catch (IOException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al subir foto.", "{}");
            return resp.toString();
        } catch (ServletException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al subir foto.", "{}");
            return resp.toString();
        }
    }

    private String getAll_rep_auto_marca(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_auto_marca";
        try {
            REP_AUTO_MARCA rep_auto_marca = new REP_AUTO_MARCA(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", rep_auto_marca.getAll().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String getAll_rep_auto_marca_registrados(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_auto_marca";
        try {
            REP_AUTO_MARCA rep_auto_marca = new REP_AUTO_MARCA(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", rep_auto_marca.getAll_autos_registrados().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="REP_AUTO_MODELO">

    private String registrar_rep_auto_modelo(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_auto_modelo";
        try {
            REP_AUTO_MODELO rep_auto_modelo = new REP_AUTO_MODELO(con);
            rep_auto_modelo.setNOMBRE(pString(request, "nombre"));
            rep_auto_modelo.setID_REP_AUTO_MARCA(pInt(request, "id_rep_auto_marca"));
            int id = rep_auto_modelo.Insertar();
            rep_auto_modelo.setID(id);
            Part file = request.getPart("foto");
            String name = "";
            String names = "";
            if (file != null) {
                names = file.getSubmittedFileName();
                String ruta = request.getSession().getServletContext().getRealPath("/");
                name = EVENTOS.guardar_file(file, ruta + URL.ruta_foto_rep_auto_modelo + "/" + id + "/", names);
            }
            rep_auto_modelo.setURL_FOTO(URL.ruta_foto_rep_auto_modelo + "/" + id + "/" + name);
            rep_auto_modelo.subir_foto_perfil();
            RESPUESTA resp = new RESPUESTA(1, "", nameAlert + " registrado con exito.", rep_auto_modelo.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        } catch (IOException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al subir foto.", "{}");
            return resp.toString();
        } catch (ServletException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al subir foto.", "{}");
            return resp.toString();
        }
    }

    private String getAll_rep_auto_modelo(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_auto_modelo";
        try {
            REP_AUTO_MODELO rep_auto_modelo = new REP_AUTO_MODELO(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", rep_auto_modelo.getAll().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String get_rep_auto_modelo_by_id_rep_auto_marca(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_auto_modelo";
        try {
            REP_AUTO_MODELO rep_auto_modelo = new REP_AUTO_MODELO(con);
            int id_rep_auto_marca = pInt(request, "id_rep_auto_marca");
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", rep_auto_modelo.getBy_id_rep_auto_marca(id_rep_auto_marca).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String get_rep_auto_modelo_by_id_rep_auto_marca_registrados(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_auto_modelo";
        try {
            REP_AUTO_MODELO rep_auto_modelo = new REP_AUTO_MODELO(con);
            int id_rep_auto_marca = pInt(request, "id_rep_auto_marca");
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", rep_auto_modelo.getBy_id_rep_auto_marca_registrados(id_rep_auto_marca).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="REP_AUTO_VERSION">

    private String registrar_rep_auto_version(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_auto_version";
        try {
            REP_AUTO_VERSION rep_auto_version = new REP_AUTO_VERSION(con);
            rep_auto_version.setNOMBRE(pString(request, "nombre"));
            int id = rep_auto_version.Insertar();
            rep_auto_version.setID(id);
            RESPUESTA resp = new RESPUESTA(1, "", nameAlert + " registrado con exito.", rep_auto_version.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String getAll_rep_auto_version(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_auto_version";
        try {
            REP_AUTO_VERSION rep_auto_version = new REP_AUTO_VERSION(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", rep_auto_version.getAll().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String getAll_rep_auto_version_by_id_auto(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_auto_version";
        try {
            REP_AUTO_VERSION rep_auto_version = new REP_AUTO_VERSION(con);
            int id = pInt(request, "id");
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", rep_auto_version.getById_auto(id).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="REP_CATEGORIA">

    private String registrar_rep_categoria(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_categoria";
        try {
            REP_CATEGORIA rep_categoria = new REP_CATEGORIA(con);
            rep_categoria.setNOMBRE(pString(request, "nombre"));
            int id = rep_categoria.Insertar();
            rep_categoria.setID(id);
            RESPUESTA resp = new RESPUESTA(1, "", nameAlert + " registrado con exito.", rep_categoria.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String getAll_rep_categoria(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_categoria";
        try {
            REP_CATEGORIA rep_categoria = new REP_CATEGORIA(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", rep_categoria.getAll().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
    private String getAll_rep_categoria_de_vehiculo(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_categoria";
        try {
            REP_CATEGORIA rep_categoria = new REP_CATEGORIA(con);
            int id_vehiculo = pInt(request, "id_vehiculo");
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", rep_categoria.getAll_by_id_vehiculo(id_vehiculo).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="REP_SUB_CATEGORIA">

    private String registrar_rep_sub_categoria(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_sub_categoria";
        try {
            REP_SUB_CATEGORIA rep_sub_categoria = new REP_SUB_CATEGORIA(con);
            rep_sub_categoria.setNOMBRE(pString(request, "nombre"));
            rep_sub_categoria.setID_REP_CATEGORIA(pInt(request, "id_rep_categoria"));
            int id = rep_sub_categoria.Insertar();
            rep_sub_categoria.setID(id);
            RESPUESTA resp = new RESPUESTA(1, "", nameAlert + " registrado con exito.", rep_sub_categoria.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String getAll_rep_sub_categoria(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_sub_categoria";
        try {
            REP_SUB_CATEGORIA rep_sub_categoria = new REP_SUB_CATEGORIA(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", rep_sub_categoria.getAll().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String get_rep_sub_categoria_by_id_rep_categoria(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_sub_categoria";
        try {
            REP_SUB_CATEGORIA rep_sub_categoria = new REP_SUB_CATEGORIA(con);
            int id_rep_categoria = pInt(request, "id_rep_categoria");
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", rep_sub_categoria.getBy_id_rep_categoria(id_rep_categoria).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
    private String get_rep_sub_categoria_by_id_rep_categoria_de_id_vehiculo(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_sub_categoria";
        try {
            REP_SUB_CATEGORIA rep_sub_categoria = new REP_SUB_CATEGORIA(con);
            int id_rep_categoria = pInt(request, "id_rep_categoria");
            int id_vehiculo = pInt(request, "id_vehiculo");
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", rep_sub_categoria.getBy_id_rep_categoria_de_id_vehiculo(id_rep_categoria,id_vehiculo).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="REP_SUB_CATEGORIA_ACTIVA">

    private String registrar_rep_sub_categoria_activa(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_sub_categoria_activa";
        try {
            REP_SUB_CATEGORIA_ACTIVA rep_sub_categoria_activa = new REP_SUB_CATEGORIA_ACTIVA(con);
            int id_repuesto = pInt(request, "id_repuesto");
            int id_sub_cat = pInt(request, "id_sub_categoria");
            int id_version = pInt(request, "id_version");
            JSONObject obj = rep_sub_categoria_activa.getById_rep_version(id_sub_cat, id_version);
            REP_SUB_CATEGORIA_DISPONIBLE rep_sub_categoria_disponible = new REP_SUB_CATEGORIA_DISPONIBLE(con);
            if (obj.getBoolean("exito")) {
                rep_sub_categoria_disponible.setID_REPUESTO(id_repuesto);
                rep_sub_categoria_disponible.setID_REP_SUB_CATEGORIA_ACTIVA(obj.getInt("id"));
            } else {
                rep_sub_categoria_activa.setID_REP_AUTO_TO_REP_VERSION(id_version);
                rep_sub_categoria_activa.setID_REP_SUB_CATEGORIA(id_sub_cat);
                int id = rep_sub_categoria_activa.Insertar();
                rep_sub_categoria_activa.setID(id);
                rep_sub_categoria_disponible.setID_REPUESTO(id_repuesto);
                rep_sub_categoria_disponible.setID_REP_SUB_CATEGORIA_ACTIVA(id);
            }
            int id_sub_cat_disponible = rep_sub_categoria_disponible.Insertar();
            REP_AUTO auto = new REP_AUTO(con);

            RESPUESTA resp = new RESPUESTA(1, "", nameAlert + " registrado con exito.", auto.getByIdVersion(id_version).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="REP_ESQUEMA">

    private String registrar_rep_esquema(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_esquema";
        try {
            REP_ESQUEMA rep_esquema = new REP_ESQUEMA(con);
            rep_esquema.setID_REPUESTO(pInt(request, "id_repuesto"));
            int id = rep_esquema.Insertar();
            rep_esquema.setID(id);
            Part file = request.getPart("foto");
            String name = "";
            String names = "";
            if (file != null) {
                names = file.getSubmittedFileName();
                String ruta = request.getSession().getServletContext().getRealPath("/");
                name = EVENTOS.guardar_file(file, ruta + URL.ruta_foto_rep_esquema + "/" + id + "/", names);
            }
            rep_esquema.setURL_FOTO(URL.ruta_foto_rep_esquema + "/" + id + "/" + name);

            rep_esquema.subir_foto_perfil();
            RESPUESTA resp = new RESPUESTA(1, "", nameAlert + " registrado con exito.", rep_esquema.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        } catch (IOException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al subir foto.", "{}");
            return resp.toString();
        } catch (ServletException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al subir foto.", "{}");
            return resp.toString();
        }
    }

    private String getById_rep_esquema(HttpServletRequest request, Conexion con) {
        String nameAlert = "rep_esquema";
        try {
            REP_ESQUEMA rep_esquema = new REP_ESQUEMA(con);//llamo a la clase
            int id = pInt(request, "id_repuesto");
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", rep_esquema.getById(id).toString());//utilzo el evento de esa clase
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String registrar_rep_partes(HttpServletRequest request, Conexion con) {
        String nameAlert = "registrar_rep_partes";
        try {
            REP_PARTES rep_partes = new REP_PARTES(con);
            rep_partes.setCODIGO(pString(request, "var_codigo"));
            rep_partes.setNOMBRE(pString(request, "var_nombre"));
            rep_partes.setMARCA(pString(request, "var_marca"));
            rep_partes.setPRECIO(pDouble(request, "var_precio"));
            rep_partes.setDESCRIPCION(pString(request, "var_descripcion"));
            rep_partes.setID_REPUESTO(pInt(request, "var_id_repuesto"));

            int id = rep_partes.Insertar();

            //rep_partes.setID(id);
            //Part file = request.getPart("var_url_foto");
            //String name = "";
            //String names = "";
            rep_partes.subir_foto_perfil();
            RESPUESTA resp = new RESPUESTA(1, "", nameAlert + " registrado con exito.", rep_partes.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String getById_rep_partes(HttpServletRequest request, Conexion con) {
        String nameAlert = "getById_rep_partes";
        try {
            REP_PARTES obj_rep_partes = new REP_PARTES(con);//llamo a la clase
            int id = pInt(request, "var_id");//obtengo el var_codigo de tipo input
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", obj_rep_partes.getById(id).toString());//me envia esta respuesta
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String getAll_rep_partes_by_id_repuesto(HttpServletRequest request, Conexion con) {
        String nameAlert = "getall_rep_partes";
        try {
             REP_PARTES obj_rep_partes = new REP_PARTES(con);//llamo a la clase
            int id_repuesto = pInt(request, "var_id_repuesto");//obtengo el var_codigo de tipo input
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", obj_rep_partes.getAll_rep_partes_by_id_repuesto(id_repuesto).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="PARSERS">
    private int parseInt(String val) {
        return Integer.parseInt(val);
    }

    private String pString(HttpServletRequest request, String key) {
        return request.getParameter(key);
    }

    private int pInt(HttpServletRequest request, String key) {
        return Integer.parseInt(request.getParameter(key));
    }

    private double pDouble(HttpServletRequest request, String key) {
        return Double.parseDouble(request.getParameter(key));
    }
//</editor-fold>

}
