package br.com.devweb.camadas.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.devweb.camadas.data.ProjetoRepository;
import br.com.devweb.camadas.dto.ProjetoRequest;
import br.com.devweb.camadas.enums.StatusPagamento;
import br.com.devweb.camadas.models.Projeto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api-v1-0/projetos")
public class ProjetoController {

  @Autowired
  private ProjetoRepository projetoRepository;

  // Rota para adicionar um projeto
  @PostMapping
  public void adicionarProjeto(@RequestBody ProjetoRequest projeto) {

    Projeto proj = new Projeto(projetoRepository.getId() + 1, projeto.getNome(), projeto.getDescricao(), new Date().toString(), new Date().toString(), StatusPagamento.PENDENTE.toString());
    projetoRepository.adicionarProjeto(proj);
  }

  // Rota para remover um projeto específico
  @DeleteMapping("/{codigo}")
  public void removerProjeto(@PathVariable Long codigo) {
      Optional<Projeto> projeto = projetoRepository.buscarProjetoPorCodigo(codigo);
      projeto.ifPresent(projetoRepository::removerProjeto);
  }

  // Rota para listar todos os projetos
  @GetMapping
  public List<Projeto> listarProjetos() {
      return projetoRepository.listarProjetos();
  }

  // Rota para buscar um projeto específico pelo ID
  @GetMapping("/{id}")
  public Optional<Projeto> buscarProjetoPorId(@PathVariable Long codigo) {
      return projetoRepository.buscarProjetoPorCodigo(codigo);
  }

}
