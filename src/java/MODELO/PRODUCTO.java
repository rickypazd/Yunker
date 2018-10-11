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

public class PRODUCTO {

    private int ID;
    private int ID_CARRERA;
    private Date FECHA_MARCADO;
    private int CANTIDAD;
    private String PRODUCTO;
    private String DESCRIPCION;
    private int ESTADO;
    private Conexion con = null;

    public PRODUCTO(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public.producto(\n"
                + "	id_carrera, producto, descripcion, cantidad, estado)\n"
                + "	VALUES (?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, getID_CARRERA());
        ps.setString(2, getPRODUCTO());
        ps.setString(3, getDESCRIPCION());
        ps.setInt(4, getCANTIDAD());
        ps.setInt(5, getESTADO());
        ps.execute();
        consulta = "select last_value from producto_id_seq";
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

    public JSONArray get_productos_id_carrera(int id) throws SQLException, JSONException {
        String consulta = "select * from producto \n" +
                        "where id_carrera="+id;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("producto", rs.getString("producto"));
            obj.put("descripcion", rs.getString("descripcion"));
            obj.put("cantidad", rs.getInt("cantidad"));
            obj.put("estado", rs.getInt("estado"));
            obj.put("hora_marcado", rs.getString("hora_marcado"));
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

    public int getID_CARRERA() {
        return ID_CARRERA;
    }

    public void setID_CARRERA(int ID_CARRERA) {
        this.ID_CARRERA = ID_CARRERA;
    }

    public Date getFECHA_MARCADO() {
        return FECHA_MARCADO;
    }

    public void setFECHA_MARCADO(Date FECHA_MARCADO) {
        this.FECHA_MARCADO = FECHA_MARCADO;
    }

    public int getCANTIDAD() {
        return CANTIDAD;
    }

    public void setCANTIDAD(int CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }

    public String getPRODUCTO() {
        return PRODUCTO;
    }

    public void setPRODUCTO(String PRODUCTO) {
        this.PRODUCTO = PRODUCTO;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public int getESTADO() {
        return ESTADO;
    }

    public void setESTADO(int ESTADO) {
        this.ESTADO = ESTADO;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

}
