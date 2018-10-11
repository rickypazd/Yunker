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

public class MODIFICACIONES {

    private int ID;
    private int ID_USR;
    private int ID_ADMIN;
    private int ID_VEHICULO;
    private int TIPO;
    private Date FECHA;

    private Conexion con = null;

    public MODIFICACIONES(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public.modificaciones(\n"
                + "	id_admin, id_usr, id_vehiculo, tipo, fecha)\n"
                + "	VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = con.statamet(consulta);

        ps.setInt(1, getID_ADMIN());
        ps.setInt(2, getID_USR());
        ps.setInt(3, getID_VEHICULO());
        ps.setInt(4, getTIPO());
        ps.setTimestamp(5, new Timestamp(new Date().getTime()));
        ps.execute();

        consulta = "select last_value from modificaciones_id_seq";
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_USR() {
        return ID_USR;
    }

    public void setID_USR(int ID_USR) {
        this.ID_USR = ID_USR;
    }

    public int getID_ADMIN() {
        return ID_ADMIN;
    }

    public void setID_ADMIN(int ID_ADMIN) {
        this.ID_ADMIN = ID_ADMIN;
    }

    public int getID_VEHICULO() {
        return ID_VEHICULO;
    }

    public void setID_VEHICULO(int ID_VEHICULO) {
        this.ID_VEHICULO = ID_VEHICULO;
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

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }
    
    public String get_tipo_String(int tipo) {
        switch (tipo) {
            case 1:
                return "Modifico Vehiculo";
            case 2:
                return "Registro Vehiculo";
            case 3:
                return "Modifico Conductor";
            case 4:
                return "Registro Conductor";
            case 5:
                return "Asigno Vehiculo a Conductor";
            case 6:
                return "Desasigno Vehiculo a Conductor";
            case 7:
                return "Subio Documento a Usuario";
            case 8:
                return "Elimino Documento de Usuario";
            case 9:
                return "Cambio Contraseña de Usuario";
        }
        return "";
    }
}
