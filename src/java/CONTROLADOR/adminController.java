/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLADOR;

import Conexion.Conexion;
import Firebasse.DataToSend;
import Firebasse.Notificador;
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
import MODELO.PERMISO;
import MODELO.SEGURIDAD.SEG_MODIFICACIONES;
import UTILES.URL;
import MODELO.USUARIO;
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
        con.Transacction();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        String evento = request.getParameter("evento");
        String tokenAcceso = request.getParameter("TokenAcceso");
        boolean retornar = true;
        String html = "";
        if (tokenAcceso.equals(URL.TokenAcceso)) {
            switch (evento) {
                case "registrar_usuario":
                    html = registrar_usuario(request, con);
                    break;
                case "login":
                    html = login(request, con);
                    break;
                case "registrar_articulo":
                    html = registrar_articulo(request, con);
                    break;
                case "registrar_art_categoria":
                    html = registrar_art_categoria(request, con);
                    break;
                case "registrar_art_marca":
                    html = registrar_art_marca(request, con);
                    break;
                case "registrar_art_departamento":
                    html = registrar_art_departamento(request, con);
                    break;
                case "registrar_art_unidad_medida":
                    html = registrar_art_unidad_medida(request, con);
                    break;
                case "get_articulos":
                    html = get_articulos(request, con);
                    break;
                case "get_art_departamentos":
                    html = get_art_departamentos(request, con);
                    break;
                case "get_art_categoria":
                    html = get_art_categoria(request, con);
                    break;
                case "get_art_marca":
                    html = get_art_marca(request, con);
                    break;
                case "get_art_unidad_medida":
                    html = get_art_unidad_medida(request, con);
                    break;
                case "descargar":
                    descargar(request, response, con);
                    retornar = false;
                    break;
                default:
                    RESPUESTA resp = new RESPUESTA(0, "Servisis: No se encontro el parametro evento.", "Servicio no encontrado.", "{}");
                    html = resp.toString();
                    break;
            }
        } else {
            RESPUESTA resp = new RESPUESTA(0, "Servisis: Token de acceso erroneo.", "Token denegado", "{}");
            html = resp.toString();
        }
        con.commit();
        con.Close();
        if (retornar) {
            response.getWriter().write(html);
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
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "No se encontro el archibo.", "{}");
        } catch (IOException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "No se encontro el archibo.", "{}");
        }

    }

    private String registrar_usuario(HttpServletRequest request, Conexion con) {
        try {
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
            RESPUESTA resp = new RESPUESTA(1, "", "Usuario registrado con exito.", usr.Insertar_sin_fecha() + "");
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar usuario.", "{}");
            return resp.toString();
        }
    }

    private String login(HttpServletRequest request, Conexion con) {
        try {

            String usuario = request.getParameter("usuario");
            String pass = request.getParameter("pass");
            USUARIO usr = new USUARIO(con);
            JSONObject obj = usr.get_por_usr_y_pass(usuario, pass);
            PERMISO per = new PERMISO(con);
            RESPUESTA resp = new RESPUESTA(0, "", "No se encontro el usuario.", obj.toString());
            if (obj.getString("exito").equals("si")) {
                JSONArray arrpermisos = per.todos_de_rol(obj.getInt("id_rol"));
                obj.put("permisos", arrpermisos);
                resp.setEstado(1);
                resp.setResp(obj.toString());
                resp.setMensaje("Login exitoso.");
            }
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al buscar el usuario.", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir a JSON.", "{}");
            return resp.toString();
        }

    }

    private String registrar_articulo(HttpServletRequest request, Conexion con) {
        try {
            int id_usr = Integer.parseInt(request.getParameter("id_usr"));
            String clave = request.getParameter("clave");
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            int id_departamento =Integer.parseInt(request.getParameter("id_departamento")) ;//data
            int id_categoria =Integer.parseInt(request.getParameter("id_categoria")) ;//data
            int id_marca =Integer.parseInt(request.getParameter("id_marca")) ;//data
            int id_unidad_medida =Integer.parseInt(request.getParameter("id_unidad_medida")) ;//data
            int factor = Integer.parseInt(request.getParameter("factor"));
            double precio_compra_red = Double.parseDouble(request.getParameter("precio_compra_red"));
            double precio_venta_ref = Double.parseDouble(request.getParameter("precio_venta_ref"));
            double margen = Double.parseDouble(request.getParameter("margen"));
            ARTICULO articulo = new ARTICULO(con);
            articulo.setCLAVE(clave);
            articulo.setNOMBRE(nombre);
            articulo.setDESCRIPCION(descripcion);
            articulo.setID_DEPARTAMENTO(id_departamento);
            articulo.setID_CATEGORIA(id_categoria);
            articulo.setID_UNIDAD_MEDIDA(id_unidad_medida);
            articulo.setID_MARCA(id_marca);
            articulo.setFACTOR(factor);
            articulo.setPRECIO_COMPRA_REF(id_marca);
            articulo.setPRECIO_VENTA_REF(precio_venta_ref);
            articulo.setMARGEN_DE_UTILIDAD(margen);
            int id = articulo.Insertar();
            articulo.setID(id);
            SEG_ARTICULO seg_articulo = new SEG_ARTICULO(con, articulo);
            seg_articulo.setESTADO(1);
            int id_seg_articulo = seg_articulo.Insertar();
            SEG_MODIFICACIONES seg_modificaciones = new SEG_MODIFICACIONES(con);
            seg_modificaciones.setID_USR(id_usr);
            seg_modificaciones.setTBL_NOMBRE("art_categoria");
            seg_modificaciones.setTBL_ID(id);
            seg_modificaciones.setMENSAJE("Se inserto un nuevo articulo.");
            //TODO: insertar ip
            seg_modificaciones.setIP("192.168.0.0");
            seg_modificaciones.setTIPO(1);
            seg_modificaciones.Insertar();
            RESPUESTA resp = new RESPUESTA(1, "", "Articulo registrado con exito.", articulo.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar el articulo.", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir a JSON.", "{}");
            return resp.toString();
        }
    }

    private String registrar_art_categoria(HttpServletRequest request, Conexion con) {
        try {
            int id_usr = Integer.parseInt(request.getParameter("id_usr"));
            String nombre = request.getParameter("nombre");
            ART_CATEGORIA art_categoria = new ART_CATEGORIA(con);
            art_categoria.setNOMBRE(nombre);
            int id = art_categoria.Insertar();
            art_categoria.setID(id);
            SEG_ART_CATEGORIA seg_art_categoria = new SEG_ART_CATEGORIA(con, art_categoria);
            seg_art_categoria.setESTADO(1);
            int id_seg_art_categoria = seg_art_categoria.Insertar();
            SEG_MODIFICACIONES seg_modificaciones = new SEG_MODIFICACIONES(con);
            seg_modificaciones.setID_USR(id_usr);
            seg_modificaciones.setTBL_NOMBRE("art_categoria");
            seg_modificaciones.setTBL_ID(id);
            seg_modificaciones.setMENSAJE("Se inserto la un nueva categoria de articulo.");
            //TODO: insertar ip
            seg_modificaciones.setIP("192.168.0.0");
            seg_modificaciones.setTIPO(1);
            seg_modificaciones.Insertar();
            RESPUESTA resp = new RESPUESTA(1, "", "Categoria de articulo registrado con exito.", art_categoria.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar la categoria de articulo.", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir a JSON.", "{}");
            return resp.toString();
        }
    }

    private String registrar_art_marca(HttpServletRequest request, Conexion con) {
        try {
            int id_usr = Integer.parseInt(request.getParameter("id_usr"));
            String nombre = request.getParameter("nombre");
            ART_MARCA art_marca = new ART_MARCA(con);
            art_marca.setNOMBRE(nombre);
            int id = art_marca.Insertar();
            art_marca.setID(id);
            SEG_ART_MARCA seg_art_marca = new SEG_ART_MARCA(con, art_marca);
            seg_art_marca.setESTADO(1);
            int id_seg_art_categoria = seg_art_marca.Insertar();
            SEG_MODIFICACIONES seg_modificaciones = new SEG_MODIFICACIONES(con);
            seg_modificaciones.setID_USR(id_usr);
            seg_modificaciones.setTBL_NOMBRE("art_marca");
            seg_modificaciones.setTBL_ID(id);
            seg_modificaciones.setMENSAJE("Se inserto la un nueva marca de articulo.");
            //TODO: insertar ip
            seg_modificaciones.setIP("192.168.0.0");
            seg_modificaciones.setTIPO(1);
            seg_modificaciones.Insertar();
            RESPUESTA resp = new RESPUESTA(1, "", "Marca de articulo registrado con exito.", art_marca.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar la marca de articulo.", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir a JSON.", "{}");
            return resp.toString();
        }
    }

    private String registrar_art_departamento(HttpServletRequest request, Conexion con) {
        try {
            int id_usr = Integer.parseInt(request.getParameter("id_usr"));
            String nombre = request.getParameter("nombre");
            ART_DEPARTAMENTO art_departamento = new ART_DEPARTAMENTO(con);
            art_departamento.setNOMBRE(nombre);
            int id = art_departamento.Insertar();
            art_departamento.setID(id);
            SEG_ART_DEPARTAMENTO seg_art_departamento = new SEG_ART_DEPARTAMENTO(con, art_departamento);
            seg_art_departamento.setESTADO(1);
            int id_seg_art_categoria = seg_art_departamento.Insertar();
            SEG_MODIFICACIONES seg_modificaciones = new SEG_MODIFICACIONES(con);
            seg_modificaciones.setID_USR(id_usr);
            seg_modificaciones.setTBL_NOMBRE("art_departamento");
            seg_modificaciones.setTBL_ID(id);
            seg_modificaciones.setMENSAJE("Se inserto la un nuevo departamento de articulo.");
            //TODO: insertar ip
            seg_modificaciones.setIP("192.168.0.0");
            seg_modificaciones.setTIPO(1);
            seg_modificaciones.Insertar();
            RESPUESTA resp = new RESPUESTA(1, "", "Departamento de articulo registrado con exito.", art_departamento.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar el Departamento de articulo.", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir a JSON.", "{}");
            return resp.toString();
        }
    }

    private String registrar_art_unidad_medida(HttpServletRequest request, Conexion con) {
        try {
            int id_usr = Integer.parseInt(request.getParameter("id_usr"));
            String nombre = request.getParameter("nombre");
            ART_UNIDAD_MEDIDA art_unidad_medida = new ART_UNIDAD_MEDIDA(con);
            art_unidad_medida.setNOMBRE(nombre);
            int id = art_unidad_medida.Insertar();
            art_unidad_medida.setID(id);
            SEG_ART_UNIDAD_MEDIDA seg_art_unidad_medida = new SEG_ART_UNIDAD_MEDIDA(con, art_unidad_medida);
            seg_art_unidad_medida.setESTADO(1);
            int id_seg_art_unidad_medida = seg_art_unidad_medida.Insertar();
            SEG_MODIFICACIONES seg_modificaciones = new SEG_MODIFICACIONES(con);
            seg_modificaciones.setID_USR(id_usr);
            seg_modificaciones.setTBL_NOMBRE("art_unidad_medida");
            seg_modificaciones.setTBL_ID(id);
            seg_modificaciones.setMENSAJE("Se inserto una nueva unidad de medida.");
            //TODO: insertar ip
            seg_modificaciones.setIP("192.168.0.0");
            seg_modificaciones.setTIPO(1);
            seg_modificaciones.Insertar();
            RESPUESTA resp = new RESPUESTA(1, "", "Unidad de medida de articulo registrado con exito.", art_unidad_medida.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar la unidad de medida de articulo.", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir a JSON.", "{}");
            return resp.toString();
        }
    }

    private String get_art_departamentos(HttpServletRequest request, Conexion con) {
        try {
            ART_DEPARTAMENTO art_departamento = new ART_DEPARTAMENTO(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", art_departamento.gelAll().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener departamentos de articulos.", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir a JSON.", "{}");
            return resp.toString();
        }
    }

    private String get_art_categoria(HttpServletRequest request, Conexion con) {
        try {
            ART_CATEGORIA art_categoria = new ART_CATEGORIA(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", art_categoria.gelAll().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener categorias de articulos.", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir a JSON.", "{}");
            return resp.toString();
        }
    }

    private String get_art_marca(HttpServletRequest request, Conexion con) {
        try {
            ART_MARCA art_marca = new ART_MARCA(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", art_marca.gelAll().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener marcas de articulos.", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir a JSON.", "{}");
            return resp.toString();
        }
    }

    private String get_art_unidad_medida(HttpServletRequest request, Conexion con) {
        try {
            ART_UNIDAD_MEDIDA art_unidad_medida = new ART_UNIDAD_MEDIDA(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", art_unidad_medida.gelAll().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener unidad de medidas de articulos.", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir a JSON.", "{}");
            return resp.toString();
        }
    }

    private String get_articulos(HttpServletRequest request, Conexion con) {
           try {
            ARTICULO articulo = new ARTICULO(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", articulo.getAll().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener articulos.", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir a JSON.", "{}");
            return resp.toString();
        }
    }

}
