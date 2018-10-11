/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HILOS;

import Firebasse.DataToSend;
import Firebasse.Notificador;
import MODELO.CARRERA;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ricardopazdemiquel
 */
public class BuscarConductor implements Runnable {

    Thread t;
    CARRERA carrera;
    JSONObject json_Carrera;
    JSONObject usuario;
    boolean estado;
    private int cant;

    public BuscarConductor(CARRERA carrera) {
        this.carrera = carrera;
        estado = false;
        t = new Thread(this, "Hilo");
        t.start();
        cant = 0;
    }

    @Override
    public void run() {

        while (cant < 5) {
            try {
                cant++;
                usuario = carrera.getCliente_por_id(carrera.getID_USUARIO());
                JSONArray arr = null;
                switch (carrera.getID_TIPO()) {
                        case 1:
                        arr = carrera.getConductoresActivos_ESTANDAR();
                        break;
                    case 2:
                        arr = carrera.getConductoresActivos_TOGO();
                        break;
                    case 3:
                        arr = carrera.getConductoresActivos_MARAVILLA();
                        break;
                    case 4:
                        arr = carrera.getConductoresActivos_SUPER();
                        break;
                    case 5: //4x4
                        arr = carrera.getConductoresActivos_carrera(2);
                        break;
                    case 6: // Camioneta
                        arr = carrera.getConductoresActivos_carrera(4);
                        break;
                    case 7: //3 Filas
                        arr = carrera.getConductoresActivos_carrera(3);
                        break;
                }
                JSONObject objec;
                JSONObject vehiculo;
                ordenamiento or;
                ordenamiento[] ordenador = new ordenamiento[arr.length()];
                if (carrera.getID_TIPO() == 2) {
                    for (int i = 0; i < arr.length(); i++) {
                        objec = arr.getJSONObject(i);
                        vehiculo = objec.getJSONObject("vehiculo");
                        or = new ordenamiento((int) distanciaCoord(vehiculo.getDouble("lat"), vehiculo.getDouble("lng"), carrera.getLATFINAL(), carrera.getLNGFINAL()), objec);
                        ordenador[i] = or;
                    }
                } else {
                    for (int i = 0; i < arr.length(); i++) {
                        objec = arr.getJSONObject(i);
                        vehiculo = objec.getJSONObject("vehiculo");
                        or = new ordenamiento((int) distanciaCoord(vehiculo.getDouble("lat"), vehiculo.getDouble("lng"), carrera.getLATINICIAL(), carrera.getLNGINICIAL()), objec);
                        ordenador[i] = or;
                    }
                }
                ordenador = OrdenarBurbuja(ordenador);

                JSONArray arr_token;
                JSONObject obj_token;
                JSONObject obj;

                for (int i = 0; i < ordenador.length; i++) {
                    if (estado) {
                        break;
                    } else {
                        obj = ordenador[i].getTurno();
                        arr_token = obj.getJSONArray("tokens");
                        for (int j = 0; j < arr_token.length(); j++) {
                            obj_token = arr_token.getJSONObject(j);
                            String token = obj_token.getString("TOKEN");
                            DataToSend data = new DataToSend(token, "confirmar_carrera", carrera.toJSOnobject().toString(), usuario.toString(), carrera.getID() + "");
                            boolean resp = Notificador.enviar(data);
                            if (resp) {
                                break;
                            }
                        }
                        Thread.sleep(12000);
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(BuscarConductor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(BuscarConductor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(BuscarConductor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(BuscarConductor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public Thread getT() {
        return t;
    }

    public void setT(Thread t) {
        this.t = t;
    }

    public CARRERA getCarrera() {
        return carrera;
    }

    public void setCarrera(CARRERA carrera) {
        this.carrera = carrera;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public JSONObject getJson_Carrera() {
        return json_Carrera;
    }

    public void setJson_Carrera(JSONObject json_Carrera) {
        this.json_Carrera = json_Carrera;
    }

    public static double distanciaCoord(double lat1, double lng1, double lat2, double lng2) {
        //double radioTierra = 3958.75;//en millas  
        double radioTierra = 6371;//en kilómetros  
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
        double distancia = radioTierra * va2;
        return distancia;
    }

    public static ordenamiento[] OrdenarBurbuja(ordenamiento[] n) {
        ordenamiento temp;
        int t = n.length;
        for (int i = 1; i < t; i++) {
            for (int k = t - 1; k >= i; k--) {
                if (n[k].getDist() < n[k - 1].getDist()) {
                    temp = n[k];
                    n[k] = n[k - 1];
                    n[k - 1] = temp;
                }
            }
        }
        return n;
    }

    public class ordenamiento {

        private int dist;
        private JSONObject turno;

        public ordenamiento(int dist, JSONObject turno) {
            this.dist = dist;
            this.turno = turno;
        }

        public int getDist() {
            return dist;
        }

        public void setDist(int dist) {
            this.dist = dist;
        }

        public JSONObject getTurno() {
            return turno;
        }

        public void setTurno(JSONObject turno) {
            this.turno = turno;
        }

    }
}
