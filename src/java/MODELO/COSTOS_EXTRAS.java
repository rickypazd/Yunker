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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class COSTOS_EXTRAS {

    private int ID;
    private double COSTO;
    private String NOMBRE;

    private Conexion con = null;

    public COSTOS_EXTRAS(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public.costos_extras(\n"
                + "	nombre, costo)\n"
                + "	VALUES (?, ?);";
        PreparedStatement ps = con.statamet(consulta);
        ps.setString(1, getNOMBRE());
        ps.setDouble(2, getCOSTO());
        ps.execute();
        consulta = "select last_value from costos_extras_id_seq";
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

    public JSONArray get_costos_extras() throws SQLException, JSONException {
        String consulta = "select * from costos_extras";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("nombre", rs.getString("nombre"));
            obj.put("costo", rs.getDouble("costo"));
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }
    public JSONObject get_costo_extra_id(int id) throws SQLException, JSONException {
        String consulta = "select * from costos_extras where id="+id;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONObject obj= new JSONObject();
        if (rs.next()) {
            obj.put("id", rs.getInt("id"));
            obj.put("nombre", rs.getString("nombre"));
            obj.put("costo", rs.getDouble("costo"));
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONArray get_costos_extras_carrera_estado(int id_carrera) throws SQLException, JSONException {
        String consulta = "SELECT cos.*, case when cosca.id_costo_extra is null then false else true end as estado\n"
                + "from \n"
                + "	costos_extras cos FULL OUTER join\n"
                + "	(select * from costos_extras_carrera where id_carrera=" + id_carrera + ") cosca\n"
                + "	on cos.id=cosca.id_costo_extra";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("nombre", rs.getString("nombre"));
            obj.put("costo", rs.getDouble("costo"));
            obj.put("estado", rs.getBoolean("estado"));
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }
    public JSONArray get_costos_extras_carrera(int id_carrera) throws SQLException, JSONException {
        String consulta = "select * from costos_extras_carrera cec, costos_extras ce where cec.id_carrera="+id_carrera+" and cec.id_costo_extra=ce.id";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("nombre", rs.getString("nombre"));
            obj.put("costo", rs.getDouble("costo"));
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public int marcar(int id_carrera, int id_costo) throws SQLException {
        String consulta = "select * from costos_extras_carrera where id_carrera=" + id_carrera + " and id_costo_extra=" + id_costo;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        String subConsulta = "";
        int res =0;
        if (rs.next()) {
           res= eliminar(id_carrera, id_costo);
        } else {
            res=insertar(id_carrera, id_costo);
        }
        ps.close();
        rs.close();
        return res;
    }

    public int insertar(int id_carrera, int id_costo) throws SQLException {
        String consulta = "INSERT INTO public.costos_extras_carrera(\n"
                + "	id_carrera, id_costo_extra)\n"
                + "	VALUES (?, ?);";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, id_carrera);
        ps.setInt(2, id_costo);
        ps.execute();
        ps.close();
        return 1;
    }

    public int eliminar(int id_carrera, int id_costo) throws SQLException {
        String consulta = "DELETE FROM public.costos_extras_carrera\n"
                + "	WHERE id_carrera=? and id_costo_extra=?;";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, id_carrera);
        ps.setInt(2, id_costo);
        ps.execute();
        ps.close();
        return 2;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getCOSTO() {
        return COSTO;
    }

    public void setCOSTO(double COSTO) {
        this.COSTO = COSTO;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

}
