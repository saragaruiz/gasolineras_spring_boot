package org.gestion.proyecto_gasolinera.apis;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiCliente {
    public static String login (String username, String pass){
        try{
            URL url = new URL("http://localhost:8080/clientes/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            String json = """ 
                {
                "username":"%s",
                "pass":"%s"
                }
                """.formatted(username, pass);

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();
            int code = conn.getResponseCode();
            InputStream is = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null){
                response.append(line);
            }
            return response.toString();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
