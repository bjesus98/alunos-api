package br.com.brunajesus.alunosapi.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "alunos")
public class Aluno {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String nome;
private String email;
private String cpf;
private String telefone;
private String matricula;
private String status;
private Boolean ativo;
public Long getId() {
return id;
}
public void setId(Long id) {
this.id = id;
}
public String getNome() {
return nome;
}
public void setNome(String nome) {
this.nome = nome;
}
public String getEmail() {
return email;
}
public void setEmail(String email) {
this.email = email;
}
public String getCpf() {
return cpf;
}
public void setCpf(String cpf) {
this.cpf = cpf;
}
public String getTelefone() {
return telefone;
}
public void setTelefone(String telefone) {
this.telefone = telefone;
}
public String getMatricula() {
return matricula;
}
public void setMatricula(String matricula) {
this.matricula = matricula;
}
public String getStatus() {
return status;
}
public void setStatus(String status) {
this.status = status;
}
public Boolean getAtivo() {
return ativo;
}
public void setAtivo(Boolean ativo) {
this.ativo = ativo;
}
}