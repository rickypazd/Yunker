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

public class VEHICULO_TO_CATEGORIA {

    private int ID_VEHICULO;
    private int ID_CATEGORIA;

    private Conexion con = null;

    public VEHICULO_TO_CATEGORIA(Conexion con) {
        this.con = con;
    }

    public boolean Insertar() throws SQLException {
        String consulta = "INSERT INTO public.vehiculo_to_categoria(\n"
                + "id_vehiculo, id_categoria)\n"
                + "VALUES (?, ?);";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, getID_VEHICULO());
        ps.setInt(2, getID_CATEGORIA());
        ps.execute();
        ps.close();
        return true;
    }

    public boolean eliminar() throws SQLException {
        String consulta = "DELETE FROM public.vehiculo_to_categoria \n"
                + "WHERE id_vehiculo=? and id_categoria=?";
        PreparedStatement ps = con.statamet(consulta);

        ps.setInt(1, getID_VEHICULO());
        ps.setInt(2, getID_CATEGORIA());
        ps.execute();
        ps.close();
        return true;
    }

    public JSONArray get_categorias_de_vehiculo(int id) throws SQLException, JSONException {
        String consulta = "select * from vehiculo_to_categoria where id_vehiculo=" + id;
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("id_vehiculo", rs.getInt("id_vehiculo"));
            obj.put("id_categoria", rs.getInt("id_categoria"));
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public int getID_VEHICULO() {
        return ID_VEHICULO;
    }

    public void setID_VEHICULO(int ID_VEHICULO) {
        this.ID_VEHICULO = ID_VEHICULO;
    }

    public int getID_CATEGORIA() {
        return ID_CATEGORIA;
    }

    public void setID_CATEGORIA(int ID_CATEGORIA) {
        this.ID_CATEGORIA = ID_CATEGORIA;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

}
