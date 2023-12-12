package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UsuarioDTOInput;
import lombok.Getter;
import service.UsuarioService;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class UsuarioController {

    private final UsuarioService usuarioService=new UsuarioService();
    @Getter
    private final ObjectMapper objMapper;

    public UsuarioController(ObjectMapper objMapper) {
        this.objMapper=objMapper;

        get("/usuarios", (Request request, Response response) -> {
            response.type("application/json");
            response.status(200);
            String json;
            json=objMapper.writeValueAsString(usuarioService.listarUsuarios());
            return json;
        });

        get("/usuarios/:id", (request, response) -> {
            response.type("application/json");
            String idParam=request.params("id");
            long id=Long.parseLong(idParam);
            String json=objMapper.writeValueAsString(usuarioService.obterUsuario(id));
            response.status(200);
            return json;
        });

        post("/usuarios", (request, response) -> {
            UsuarioDTOInput usuarioDTOInput=objMapper.readValue(request.body(), UsuarioDTOInput.class);
            usuarioService.adicionarUsuario(usuarioDTOInput);
            response.type("application/json");
            response.status(201);
            return "Usuário inserido com sucesso.";
        });

        put("/usuarios", (request, response) -> {
            UsuarioDTOInput usuarioDTOInput=objMapper.readValue(request.body(), UsuarioDTOInput.class);
            usuarioService.alterarUsuario(usuarioDTOInput);
            response.type("application/json");
            response.status(200);
            return "Usuário alterado com sucesso.";
        });

        delete("/usuarios/:id", (request, response) -> {
            response.type("application/json");
            String idParam=request.params("id");
            long id=Long.parseLong(idParam);
            usuarioService.removerUsuario(id);
            response.status(200);
            return "Usuário excluído com sucesso.";
        });
    }

}
