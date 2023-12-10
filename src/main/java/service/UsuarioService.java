package service;

import dto.UsuarioDTOInput;
import dto.UsuarioDTOOutput;
import model.Usuario;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioService {

    private final List<Usuario> listaUsuarios=new ArrayList<>();
    private final ModelMapper modelMapper=new ModelMapper();

    public List<UsuarioDTOOutput> listar() {
        List<UsuarioDTOOutput> usuariosDTO=new ArrayList<>();
        for (Usuario usuario : listaUsuarios) {
            usuariosDTO.add(modelMapper.map(usuario, UsuarioDTOOutput.class));
        }
        return usuariosDTO;
    }

    public void inserir(UsuarioDTOInput usuarioDTOInput) {
        Usuario usuario=modelMapper.map(usuarioDTOInput, Usuario.class);
        listaUsuarios.add(usuario);
    }

    public void alterar(UsuarioDTOInput usuarioDTOInput) {
        Usuario usuario=modelMapper.map(usuarioDTOInput, Usuario.class);
        // Lógica para substituir o usuário existente na lista
    }

    public UsuarioDTOOutput buscar(int id) {
        Optional<Usuario> usuarioOptional=listaUsuarios.stream()
                .filter(usuario -> usuario.getId() == id)
                .findFirst();
        return usuarioOptional.map(usuario -> modelMapper.map(usuario, UsuarioDTOOutput.class))
                .orElse(null);
    }

    public void excluir(int id) {
        listaUsuarios.removeIf(usuario -> usuario.getId() == id);
    }
}
