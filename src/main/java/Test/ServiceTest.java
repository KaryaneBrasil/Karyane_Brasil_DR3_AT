package Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UsuarioDTOInput;
import dto.UsuarioDTOOutput;
import org.testng.annotations.Test;
import service.UsuarioService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {

    @Test
    public void testInsercaoUsuario() {
        UsuarioService usuarioService=new UsuarioService();
        UsuarioDTOInput usuarioDTOInput=new UsuarioDTOInput(1, "NomeUsuario", "SenhaUsuario");

        usuarioService.inserir(usuarioDTOInput);
        List<UsuarioDTOOutput> usuarios=usuarioService.listar();

        assertEquals(1, usuarios.size());
    }

    @Test
    public void testListagemUsuariosHTTP() throws IOException {
        URL url=new URL("http://localhost:4567/usuarios");
        HttpURLConnection connection=(HttpURLConnection) url.openConnection();

        // Configurar o método HTTP (GET)
        connection.setRequestMethod("GET");

        // Obter o código de resposta
        int responseCode=connection.getResponseCode();

        // Ler a resposta do servidor
        StringBuilder response=new StringBuilder();
        try (BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line=reader.readLine()) != null) {
                response.append(line);
            }
        }

        // Verificar se o código de resposta é 200 (OK)
        assertEquals(200, responseCode);
    }

    @Test
    public void testInsercaoUsuarioAPIServico() throws Exception {
        // Gerar dados de usuário da API pública randomuser.me
        URL randomUserUrl=new URL("https://randomuser.me/api/");
        HttpURLConnection randomUserConnection=(HttpURLConnection) randomUserUrl.openConnection();
        randomUserConnection.setRequestMethod("GET");

        int randomUserResponseCode=randomUserConnection.getResponseCode();
        assertEquals(200, randomUserResponseCode);

        // Ler a resposta da API pública
        StringBuilder randomUserResponse=new StringBuilder();
        try (BufferedReader reader=new BufferedReader(new InputStreamReader(randomUserConnection.getInputStream()))) {
            String line;
            while ((line=reader.readLine()) != null) {
                randomUserResponse.append(line);
            }
        }

        // Converter a resposta da API pública para um objeto JsonNode usando o ObjectMapper
        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode randomUserJsonNode=objectMapper.readTree(randomUserResponse.toString());

        // Extrair os dados do usuário da resposta da API pública (ajuste conforme necessário)
        String nomeUsuario=randomUserJsonNode.at("/results/0/name/first").asText();
        String senhaUsuario=randomUserJsonNode.at("/results/0/login/password").asText();

        // Fazer uma requisição para a sua API
        URL suaApiUrl=new URL("http://localhost:4567/usuarios");
        HttpURLConnection suaApiConnection=(HttpURLConnection) suaApiUrl.openConnection();
        suaApiConnection.setRequestMethod("POST");
        suaApiConnection.setDoOutput(true);

        // Enviar dados do usuário gerados para a sua API
        String inputJson="{\"id\":1,\"nome\":\"" + nomeUsuario + "\",\"senha\":\"" + senhaUsuario + "\"}";
        try (var os=suaApiConnection.getOutputStream()) {
            byte[] inputBytes=inputJson.getBytes(StandardCharsets.UTF_8);
            os.write(inputBytes, 0, inputBytes.length);
        }

        // Obter o código de resposta da sua API
        int suaApiResponseCode=suaApiConnection.getResponseCode();

        // Verificar se o código de resposta é 201 (Created)
        assertEquals(201, suaApiResponseCode);

    }
}
