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

public class CATEGORIA_VEHICULO {

    private int ID;
    private String CATEGORIA;

    private Conexion con = null;

    public CATEGORIA_VEHICULO(Conexion con) {
        this.con = con;
    }

    public int Insertar() throws SQLException {
        String consulta = "INSERT INTO public.categoria_vehiculo(\n"
                + "categoria)\n"
                + "VALUES (?);";
        PreparedStatement ps = con.statamet(consulta);

        ps.setString(1, getCATEGORIA());
        ps.execute();

        consulta = "select last_value from categoria_vehiculo_id_seq ";
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

    public JSONArray get_categorias() throws SQLException, JSONException {
        String consulta = "select * from categoria_vehiculo order by(categoria) desc";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj.put("ID", rs.getInt("id"));
            obj.put("CATEGORIA", rs.getString("categoria"));
           
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

    public String getCATEGORIA() {
        return CATEGORIA;
    }

    public void setCATEGORIA(String CATEGORIA) {
        this.CATEGORIA = CATEGORIA;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

   

}
