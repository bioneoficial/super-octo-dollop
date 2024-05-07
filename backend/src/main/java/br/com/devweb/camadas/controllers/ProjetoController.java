package br.com.devweb.camadas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.devweb.camadas.data.ProjetoRepository;

import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api-v1-0/projetos")
public class ProjetoController {

  @Autowired
  private ProjetoRepository _projetoRepository;

  
  @GetMapping("")
  public String getProject() {
    return _projetoRepository.listarProjetos().toString();
  }
  
}
