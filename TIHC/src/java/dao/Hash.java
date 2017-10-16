
package dao;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
   private String mensaje;
    
    public Hash(String mensaje)
        {
        this.mensaje = mensaje;
        }


   
public static String resumen;


        public static String hash(String mensaje){ 
        String sumar = "comeymecuetas";
        
        String cifrar = mensaje+sumar;
        byte[] bytesDelMensaje = cifrar.getBytes();

        MessageDigest resumenDelMensaje = null;
        try {
            resumenDelMensaje = MessageDigest.getInstance("MD5");
            byte[] bytesDelResumen = resumenDelMensaje.digest(bytesDelMensaje);

            BigInteger resumenNumero = new BigInteger(1, bytesDelResumen);
            resumen = resumenNumero.toString(16);

            
        } catch (NoSuchAlgorithmException e) {}
        return resumen;
        }
        
    public static String getResumen() {
        return resumen;
    }

    public static void setResumen(String resumen) {
        Hash.resumen = resumen;
    }
        


    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
    

