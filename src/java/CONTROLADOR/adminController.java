/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLADOR;

import Conexion.Conexion;
import Firebasse.DataToSend;
import Firebasse.Notificador;
import MODELO.PERMISO;
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
            boolean retornar = true;
            String html = "";
            switch (evento) {
                case "registrar_usuario":
                    html = registrar_usuario(request, con);
                case "login":
                    html = login(request, con);
                    break;
                case "descargar":
                    descargar(request, response, con);
                    retornar = false;
                    break;
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
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al buscar el usuario.", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir a JSON.", "{}");
            return resp.toString();
        }

    }
}
