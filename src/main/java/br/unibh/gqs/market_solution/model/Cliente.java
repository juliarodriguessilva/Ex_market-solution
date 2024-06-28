package br.unibh.gqs.market_solution.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_CLIENTE")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 11, nullable = false, unique = true)
    private String cpf;

    @Column(length = 200, nullable = false)
    private String nome;

    @Column(length = 30, nullable = false, name = "classe_bonus")
    private String classeBonus;

    private String nomePesquisa;
    
    public Cliente() {
    }

    public Cliente(String cpf, String nome, String classeBonus) {
        this.cpf = cpf;
        this.nome = nome;
        this.classeBonus = classeBonus;
    }

    public String getNomePesquisa(){
        return nomePesquisa;
    }

    public void setNomePesquisa(String nomePesquisa){
        this.nomePesquisa = nomePesquisa;
    }


    public void pesquisarCliente(){

        if(this.nomePesquisa == this.nome){
            System.out.println("Cliente não encontrado!");
        } else {
            throw new IllegalArgumentException("O 'cliente' não pôde ser encontrado");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getClasseBonus() {
        return classeBonus;
    }

    public void setClasseBonus(String classeBonus) {
        this.classeBonus = classeBonus;
    }

    @Override
    public String toString() {
        return "Cliente [id=" + id + ", cpf=" + cpf + ", nome=" + nome + ", classeBonus=" + classeBonus + "]";
    }
        
}
