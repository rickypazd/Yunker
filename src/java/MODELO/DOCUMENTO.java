package MODELO;

import Conexion.Conexion;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.Timestamp;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DOCUMENTO {

    private int ID;
    private String SRC;
    private int id_tipo;
    private int id_usr;
    private Date fecha_caducidad;

    private Conexion con = null;

    public DOCUMENTO(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public.documento(\n"
                + "	 src, id_usuario, id_tipo, fecha_caducidad)\n"
                + "	VALUES (?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);

        ps.setString(1, getSRC());
        ps.setInt(2, getId_usr());
        ps.setInt(3, getId_tipo());
        ps.setTimestamp(4, new Timestamp(getFecha_caducidad().getTime()));
        ps.execute();

        consulta = "select last_value from documento_id_seq ";
        ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        int id = 0;
        if (rs.next()) {
            id = rs.getInt("last_value");
        }
        rs.close();
        ps.close();
        return id;
    }

    public JSONObject get_docu(int id) throws SQLException, JSONException {
        String consulta = "select doc.*, td.nombre as nombredocu , td.need_fecha from documento doc, tipo_documento td\n"
                + "where doc.id="+id+" and doc.id_tipo=td.id";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        if (rs.next()) {
             obj = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("src", rs.getString("src"));
            obj.put("fecha_caducidad", rs.getString("fecha_caducidad"));
            obj.put("id_usuario", rs.getInt("id_usuario"));
            obj.put("id_tipo", rs.getInt("id_tipo"));
            obj.put("nombredocu", rs.getString("nombredocu"));
            obj.put("need_fecha", rs.getBoolean("need_fecha"));
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONArray get_tipos_documentos() throws SQLException, JSONException {
        String consulta = "SELECT * \n"
                + "	FROM public.tipo_documento tp order by (tp.id) asc;";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("nombre", rs.getString("nombre"));
            obj.put("need_fecha", rs.getBoolean("need_fecha"));
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public JSONArray get_documentos_usr(int id) throws SQLException, JSONException {
        String consulta = "SELECT dc.*, td.nombre as nombredocu, td.need_fecha\n"
                + "	FROM public.documento dc, public.tipo_documento td where dc.id_usuario=" + id + " and dc.id_tipo=td.id order by(fecha_caducidad) desc";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("src", rs.getString("src"));
            obj.put("fecha_caducidad", rs.getString("fecha_caducidad"));
            obj.put("id_usuario", rs.getInt("id_usuario"));
            obj.put("id_tipo", rs.getInt("id_tipo"));
            obj.put("nombredocu", rs.getString("nombredocu"));
            obj.put("need_fecha", rs.getBoolean("need_fecha"));
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public JSONArray get_tipo_docu_faltante(int id) throws SQLException, JSONException {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        String consulta = "select tp.* from tipo_documento tp left join (select dc.* from documento dc where dc.id_usuario='" + id + "' and dc.fecha_caducidad > '" + form.format(new Date()) + "')doc \n"
                + "on tp.id=doc.id_tipo\n"
                + "where doc.id_tipo is null";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("nombre", rs.getString("nombre"));
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSRC() {
        return SRC;
    }

    public void setSRC(String SRC) {
        this.SRC = SRC;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public int getId_usr() {
        return id_usr;
    }

    public void setId_usr(int id_usr) {
        this.id_usr = id_usr;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    public Date getFecha_caducidad() {
        return fecha_caducidad;
    }

    public void setFecha_caducidad(Date fecha_caducidad) {
        this.fecha_caducidad = fecha_caducidad;
    }

}
