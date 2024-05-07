package br.com.devweb.camadas.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.devweb.camadas.data.ProjetoRepository;
import br.com.devweb.camadas.dto.CreateProjetoRequest;
import br.com.devweb.camadas.enums.StatusProjeto;
import br.com.devweb.camadas.models.Projeto;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api-v1-0/projetos")
public class ProjetoController {

  @Autowired
  private ProjetoRepository projetoRepository;

  @PostMapping
  public ResponseEntity<String> adicionarProjeto(@RequestBody CreateProjetoRequest projeto) {
    Projeto proj = new Projeto(projetoRepository.getId() + 1, projeto.getNome(), projeto.getDescricao(), projeto.getData_inicio(), projeto.getTermino(), StatusProjeto.PENDENTE.toString());
    projetoRepository.adicionarProjeto(proj);
    return ResponseEntity.status(HttpStatus.CREATED).body("Projeto adicionado com sucesso");
  }

  @DeleteMapping("/{codigo}")
  public ResponseEntity<String> removerProjeto(@PathVariable Long codigo) {
    Optional<Projeto> projeto = projetoRepository.buscarProjetoPorCodigo(codigo);
    if (projeto.isPresent()) {
      projetoRepository.removerProjeto(projeto.get());
      return ResponseEntity.ok("Projeto removido com sucesso");
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping
  public ResponseEntity<List<Projeto>> listarProjetos() {
    List<Projeto> projetos = projetoRepository.listarProjetos();
    return ResponseEntity.ok(projetos);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Projeto> buscarProjetoPorId(@PathVariable Long codigo) {
    Optional<Projeto> projetoOptional = projetoRepository.buscarProjetoPorCodigo(codigo);
    if (projetoOptional.isPresent()) {
      return ResponseEntity.ok(projetoOptional.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<String> editarProjeto(@PathVariable Long codigo, @RequestBody Projeto novoProjeto) {
    Optional<Projeto> projetoOptional = projetoRepository.buscarProjetoPorCodigo(codigo);
    if (projetoOptional.isPresent()) {
      Projeto projetoExistente = projetoOptional.get();
      if (novoProjeto.getNome() != null) {
        projetoExistente.setNome(novoProjeto.getNome());
      }
      if (novoProjeto.getDescricao() != null) {
        projetoExistente.setDescricao(novoProjeto.getDescricao());
      }
      if (novoProjeto.getData_termino() != null) {
        projetoExistente.setData_termino(novoProjeto.getData_termino());
      }
      projetoRepository.editarProjeto(codigo, projetoExistente);
      return ResponseEntity.ok("Projeto editado com sucesso");
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{codigo}/status")
  public ResponseEntity<String> atualizarStatusProjeto(@PathVariable Long codigo, @RequestBody StatusProjeto novoStatus) {
    Optional<Projeto> projetoOptional = projetoRepository.buscarProjetoPorCodigo(codigo);
    if (projetoOptional.isPresent()) {
      Projeto projeto = projetoOptional.get();
      projeto.setStatus(novoStatus.toString());
      projetoRepository.editarProjeto(codigo, projeto);
      return ResponseEntity.ok("Status do projeto atualizado com sucesso");
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
