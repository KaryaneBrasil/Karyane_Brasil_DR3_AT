package Test;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class HttpUrlConnectionTest {

    @Test
    public void testListarUsuarios() {
        try {
            URL url=new URL("http://localhost:4567/usuarios");
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            assertEquals(200, connection.getResponseCode());

            BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response=new StringBuilder();
            String line;

            while ((line=reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            assertEquals("[{\"id\":1,\"nome\":\"NovoUsuario\"}]", response.toString());

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
