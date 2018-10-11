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

public class VEHICULO_CONDUCTOR {

    private int ID_CONDUCTOR;
    private int ID_VEHICULO;
    private Conexion con = null;

    public VEHICULO_CONDUCTOR(Conexion con) {
        this.con = con;
    }

    public boolean Insertar() throws SQLException {
        String consulta = "INSERT INTO public.vehiculo_de_usuario(\n"
                + "id_vehiculo, id_conductor)\n"
                + "VALUES (?, ?);";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, getID_VEHICULO());
        ps.setInt(2, getID_CONDUCTOR());
        boolean res = ps.execute();
        ps.close();
        return res;
    }

    public boolean eliminar() throws SQLException {
        String consulta = "DELETE FROM public.vehiculo_de_usuario\n"
                + "WHERE id_conductor=? and id_vehiculo=?;";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, getID_CONDUCTOR());
        ps.setInt(2, getID_VEHICULO());
        boolean res = ps.execute();
        ps.close();
        return res;
    }

    public JSONArray get(int id) throws SQLException, JSONException {
        String consulta = "select * from token where id_usr=" + id;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("id"));
            obj.put("TOKEN", rs.getString("token"));
            obj.put("ID_USR", rs.getInt("id_usr"));
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public int getID_CONDUCTOR() {
        return ID_CONDUCTOR;
    }

    public void setID_CONDUCTOR(int ID_CONDUCTOR) {
        this.ID_CONDUCTOR = ID_CONDUCTOR;
    }

    public int getID_VEHICULO() {
        return ID_VEHICULO;
    }

    public void setID_VEHICULO(int ID_VEHICULO) {
        this.ID_VEHICULO = ID_VEHICULO;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

}
