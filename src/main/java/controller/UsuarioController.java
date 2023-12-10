package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import service.UsuarioService;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public UsuarioController(UsuarioService usuarioService, ObjectMapper objectMapper) {
        this.usuarioService = usuarioService;
        this.objectMapper = objectMapper;
    }

    public void respostasRequisicoes() {
        get("/usuarios", this::listarUsuarios, objectMapper::writeValueAsString);
        get("/usuarios/:id", this::buscarUsuario, objectMapper::writeValueAsString);
        delete("/usuarios/:id", this::excluirUsuario, objectMapper::writeValueAsString);
        post("/usuarios", this::inserirUsuario, objectMapper::writeValueAsString);
        put("/usuarios", this::alterarUsuario, objectMapper::writeValueAsString);
    }

    private Object listarUsuarios(Request request, Response response) {
        response.type("application/json");
        response.status(200);
        return usuarioService.listar();
    }

    private Object buscarUsuario(Request request, Response response) {
        response.type("application/json");
        response.status(200);
        int id = Integer.parseInt(request.params(":id"));
        return usuarioService.buscar(id);
    }

    private Object excluirUsuario(Request request, Response response) {
        response.type("application/json");
        response.status(204);
        int id = Integer.parseInt(request.params(":id"));
        usuarioService.excluir(id);
        return "";
    }

    private Object inserirUsuario(Request request, Response response) {
        response.type("application/json");
        response.status(201);
        try {
            UsuarioDTOInput usuarioDTOInput = objectMapper.readValue(request.body(), UsuarioDTOInput.class);
            usuarioService.inserir(usuarioDTOInput);
            return "";
        } catch (Exception e) {
            response.status(400);
            return "Erro ao processar a requisição";
        }
    }

    private Object alterarUsuario(Request request, Response response) {
        response.type("application/json");
        response.status(200);
        try {
            UsuarioDTOInput usuarioDTOInput = objectMapper.readValue(request.body(), UsuarioDTOInput.class);
            usuarioService.alterar(usuarioDTOInput);
            return "";
        } catch (Exception e) {
            response.status(400);
            return "Erro ao processar a requisição";
        }
    }
}
