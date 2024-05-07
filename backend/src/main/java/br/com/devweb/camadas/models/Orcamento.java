package br.com.devweb.camadas.models;

import lombok.Data;

@Data
public class Orcamento {
  
  private Long codigo;
  private String nome_empresa;
  private String descricao;
  private Double valor;
  private String status_pagamento;

}
