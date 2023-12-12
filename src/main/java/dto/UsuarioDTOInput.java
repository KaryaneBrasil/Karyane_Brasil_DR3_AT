package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTOInput {
    private int id;
    private String nome;
    private String senha;
    @Getter
    private String email;

    public UsuarioDTOInput() {
    }

    public UsuarioDTOInput(String nome, String senha) {
        this.nome=nome;
        this.senha=senha;
    }

    public void setEmail(String email) {
        this.email=email;
    }

}
