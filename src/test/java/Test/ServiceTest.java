package Test;

import dto.UsuarioDTOOutput;
import org.junit.jupiter.api.Test;
import service.UsuarioService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {

    @Test
    public void testInsercaoUsuario() {
        UsuarioService usuarioService=new UsuarioService();
        usuarioService.inserirUsuario("Nome", "Email");
        List<UsuarioDTOOutput> listaUsuarios=usuarioService.listarUsuarios();
        assertEquals(1, listaUsuarios.size());
    }
}
