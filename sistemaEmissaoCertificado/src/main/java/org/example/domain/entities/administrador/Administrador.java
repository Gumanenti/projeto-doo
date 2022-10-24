package org.example.domain.entities.administrador;

public class Administrador {
    private String login;
    private String senha;
    private String palavraChave;

    public Administrador(){}

    public Administrador(String login, String senha, String palavraChave) {
        this.login = login;
        this.senha = senha;
        this.palavraChave = palavraChave;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPalavraChave() {
        return palavraChave;
    }

    public void setPalavraChave(String palavraChave) {
        this.palavraChave = palavraChave;
    }
}
