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

public class TRANSACCION_CREDITO {

    private int ID;
    private int ID_USUARIO;
    private int ID_CARRERA;
    private int ID_ADMIN;
    private int TIPO;
    private Date FECHA;
    private String IP;
    private double CANTIDAD;

    private Conexion con = null;

    public TRANSACCION_CREDITO(Conexion con) {
        this.con = con;
    }

    public String insertar_credito() throws SQLException {
        String consulta = "{ call insertar_credito( ?, ?, ?, ?, ?, ?) }";
        CallableStatement ps = con.callable_statamet(consulta);
        ps.setInt(1, getID_ADMIN());
        ps.setInt(2, getID_USUARIO());
        ps.setInt(3, getTIPO());
        ps.setTimestamp(4, new Timestamp(getFECHA().getTime()));
        ps.setDouble(5, getCANTIDAD());
        ps.setString(6, getIP());
        ps.execute();
        ResultSet rs = (ResultSet) ps.getResultSet();
        String resp = "falso";
        if (rs.next()) {
            resp = rs.getString(1);
        }
        rs.close();
        ps.close();
        return resp;
    }

    public String descontar_comision() throws SQLException {
        String consulta = "{ call descontar_comision( ?, ?, ?, ?, ?, ?) }";
        CallableStatement ps = con.callable_statamet(consulta);
        ps.setInt(1, getID_CARRERA());
        ps.setInt(2, getID_USUARIO());
        ps.setInt(3, getTIPO());
        ps.setTimestamp(4, new Timestamp(getFECHA().getTime()));
        ps.setDouble(5, getCANTIDAD());
        ps.setString(6, getIP());
        ps.execute();
        ResultSet rs = (ResultSet) ps.getResultSet();
        String resp = "falso";
        if (rs.next()) {
            resp = rs.getString(1);
        }
        rs.close();
        ps.close();
        return resp;
    }

    public String pagar_cancelacion_conductor() throws SQLException {
        String consulta = "{ call pagar_cancelacion_conductor( ?, ?, ?, ?, ?, ?) }";
        CallableStatement ps = con.callable_statamet(consulta);
        ps.setInt(1, getID_CARRERA());
        ps.setInt(2, getID_USUARIO());
        ps.setInt(3, getTIPO());
        ps.setTimestamp(4, new Timestamp(getFECHA().getTime()));
        ps.setDouble(5, getCANTIDAD());
        ps.setString(6, getIP());
        ps.execute();
        ResultSet rs = (ResultSet) ps.getResultSet();
        String resp = "falso";
        if (rs.next()) {
            resp = rs.getString(1);
        }
        rs.close();
        ps.close();
        return resp;
    }

    public String pagar_deuda() throws SQLException {
        String consulta = "{ call pagar_deuda( ?, ?, ?, ?, ?, ?) }";
        CallableStatement ps = con.callable_statamet(consulta);
        ps.setInt(1, getID_CARRERA());
        ps.setInt(2, getID_USUARIO());
        ps.setInt(3, getTIPO());
        ps.setTimestamp(4, new Timestamp(getFECHA().getTime()));
        ps.setDouble(5, getCANTIDAD());
        ps.setString(6, getIP());
        ps.execute();
        ResultSet rs = (ResultSet) ps.getResultSet();
        String resp = "falso";
        if (rs.next()) {
            resp = rs.getString(1);
        }
        rs.close();
        ps.close();
        return resp;
    }

    public String pagar_deuda_conductor() throws SQLException {

        String consulta = "{ call pagar_deuda_conductor(?, ?, ?, ?, ?, ?) }";
        CallableStatement ps = con.callable_statamet(consulta);
        ps.setInt(1, getID_CARRERA());
        ps.setInt(2, getID_USUARIO());
        ps.setInt(3, getTIPO());
        ps.setTimestamp(4, new Timestamp(getFECHA().getTime()));
        ps.setDouble(5, getCANTIDAD());
        ps.setString(6, getIP());
        ps.execute();
        ResultSet rs = (ResultSet) ps.getResultSet();
        String resp = "falso";
        if (rs.next()) {
            resp = rs.getString(1);
        }
        rs.close();
        ps.close();
        return resp;
    }

    public JSONArray get_transacciones_id(int id) throws SQLException, JSONException {
        String consulta = "select * from transaccion_credito where id_usuario="+id+" and cantidad > 0\n"
                + "order by fecha desc";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("id", rs.getInt("id"));
            obj.put("id_admin", rs.getInt("id_admin"));
            obj.put("fecha", rs.getString("fecha"));
            obj.put("tipo", rs.getInt("tipo"));
            obj.put("tipo_nombre", get_tipo(rs.getInt("tipo")));
            obj.put("cantidad", rs.getDouble("cantidad"));
            obj.put("id_usuario", rs.getInt("id_usuario"));
            obj.put("id_carrera", rs.getInt("id_carrera"));
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

    public int getID_USUARIO() {
        return ID_USUARIO;
    }

    public void setID_USUARIO(int ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }

    public int getID_CARRERA() {
        return ID_CARRERA;
    }

    public void setID_CARRERA(int ID_CARRERA) {
        this.ID_CARRERA = ID_CARRERA;
    }

    public int getID_ADMIN() {
        return ID_ADMIN;
    }

    public void setID_ADMIN(int ID_ADMIN) {
        this.ID_ADMIN = ID_ADMIN;
    }

    public int getTIPO() {
        return TIPO;
    }

    public void setTIPO(int TIPO) {
        this.TIPO = TIPO;
    }

    public Date getFECHA() {
        return FECHA;
    }

    public void setFECHA(Date FECHA) {
        this.FECHA = FECHA;
    }

    public double getCANTIDAD() {
        return CANTIDAD;
    }

    public void setCANTIDAD(double CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String get_tipo(int tipo) {
        switch (tipo) {
            case 1:
                return "Credito agregado";
            case 2:
                return "Pago de comisión";
            case 3:
                return "Cobro de cancelacion";
            case 4:
                return "Pago de deuda";
            case 5:
                return "Pago de deuda";
        }
        return "";
    }

}
