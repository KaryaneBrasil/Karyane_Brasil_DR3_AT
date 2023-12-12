package service;

import DAO.GenericDao;
import dto.UsuarioDTOInput;
import dto.UsuarioDTOOutput;
import model.Usuario;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;

public class UsuarioService {

    private static final ModelMapper modelMapper=new ModelMapper();

    public List<UsuarioDTOOutput> listarUsuarios() {
        try (GenericDao<Usuario> usuarioDAO=new GenericDao<>(Usuario.class)) {
            usuarioDAO.begin();
            List<Usuario> usuarios=usuarioDAO.findAll();
            usuarioDAO.end();
            return modelMapper.map(usuarios, List.class);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public UsuarioDTOOutput obterUsuario(long id) {
        try (GenericDao<Usuario> usuarioDAO=new GenericDao<>(Usuario.class)) {
            usuarioDAO.begin();
            UsuarioDTOOutput usuarioDTOOutput=modelMapper.map(usuarioDAO.findById(id), UsuarioDTOOutput.class);
            usuarioDAO.end();
            return usuarioDTOOutput;
        } catch (Exception e) {
            return null;
        }
    }

    public void adicionarUsuario(UsuarioDTOInput usuarioDTOInput) {
        Usuario usuario=modelMapper.map(usuarioDTOInput, Usuario.class);
        try (GenericDao<Usuario> usuarioDAO=new GenericDao<>(Usuario.class)) {
            usuarioDAO.begin();
            usuarioDAO.create(usuario);
            usuarioDAO.end();
        } catch (Exception ignored) {

        }
    }

    public void alterarUsuario(UsuarioDTOInput usuarioDTOInput) {
        Usuario usuario=modelMapper.map(usuarioDTOInput, Usuario.class);
        try (GenericDao<Usuario> usuarioDAO=new GenericDao<>(Usuario.class)) {
            usuarioDAO.begin();
            usuarioDAO.update(usuario);
            usuarioDAO.end();
        } catch (Exception ignored) {

        }
    }

    public void removerUsuario(long id) {
        try (GenericDao<Usuario> usuarioDAO=new GenericDao<>(Usuario.class)) {
            usuarioDAO.begin();
            Usuario usuario=usuarioDAO.findById(id);
            if (usuario != null) {
                usuarioDAO.delete(id);
            }
            usuarioDAO.end();
        } catch (Exception ignored) {
        }
    }

    public void inserirUsuario(UsuarioDTOInput usuarioDTOInput) {
        Usuario usuario=modelMapper.map(usuarioDTOInput, Usuario.class);
        try (GenericDao<Usuario> usuarioDAO=new GenericDao<>(Usuario.class)) {
            usuarioDAO.begin();
            usuarioDAO.create(usuario);
            usuarioDAO.end();
        } catch (Exception ignored) {

        }
    }

    public void inserirUsuario(String nome, String email) {
        UsuarioDTOInput usuarioDTOInput=new UsuarioDTOInput();
        usuarioDTOInput.setNome(nome);
        usuarioDTOInput.setEmail(email);
        inserirUsuario(usuarioDTOInput);
    }
}
