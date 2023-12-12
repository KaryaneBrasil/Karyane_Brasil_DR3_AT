package Test;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class HttpUrlConnectionApiTest {

    @Test
    public void testInserirUsuarioComApiPublica() {
        try {
            URL apiURL=new URL("https://randomuser.me/api/");
            HttpURLConnection apiConnection=(HttpURLConnection) apiURL.openConnection();
            apiConnection.setRequestMethod("GET");
            assertEquals(200, apiConnection.getResponseCode());

            BufferedReader apiReader=new BufferedReader(new InputStreamReader(apiConnection.getInputStream()));
            StringBuilder apiResponse=new StringBuilder();
            String apiLine;

            while ((apiLine=apiReader.readLine()) != null) {
                apiResponse.append(apiLine);
            }
            apiReader.close();


            URL url=new URL("http://localhost:4567/usuarios");
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            assertEquals(201, connection.getResponseCode());

            connection.disconnect();
            apiConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
