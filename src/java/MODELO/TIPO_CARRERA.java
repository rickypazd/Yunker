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

public class TIPO_CARRERA {

    private int ID;
    private String NOMBRE;
    private String DESCRIPCION;
    private int ID_COSTO;
    private Conexion con = null;

    public TIPO_CARRERA(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public.tipo_carrera(\n"
                + "	id, nombre, descripcion, id_costo)\n"
                + "	VALUES (? ,? , ?, ?);";
        PreparedStatement ps = con.statamet(consulta);

        ps.setInt(1, getID());
        ps.setString(2, getNOMBRE());
        ps.setString(3, getDESCRIPCION());
        ps.setInt(4, getID_COSTO());    
        ps.execute();

        consulta = "select last_value from tipo_carrera_id_seq ";
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

    public JSONArray get_Token_usr(int id) throws SQLException, JSONException {
        String consulta = "select * from token where id_usr=" + id + " order by(id) desc";
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public int getID_COSTO() {
        return ID_COSTO;
    }

    public void setID_COSTO(int ID_COSTO) {
        this.ID_COSTO = ID_COSTO;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

}
