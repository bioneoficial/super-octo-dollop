package br.com.devweb.camadas.models;

import lombok.Data;

@Data
public class Projeto {
  private Long codigo;
  private String nome;
  private String descricao;
  private String data_inicio;
  private String data_termino;
  private String status;

}
