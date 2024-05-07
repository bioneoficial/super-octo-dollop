package br.com.devweb.camadas.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.devweb.camadas.dto.CreateProjetoRequest;
import br.com.devweb.camadas.interfaces.ProjetoRepositoryInterface;
import br.com.devweb.camadas.models.Projeto;
import br.com.devweb.camadas.validator.ProjetoValidator;

@Service
public class ProjetoService {

  @Autowired
  private ProjetoRepositoryInterface projetoRepository;
  public ResponseEntity<String> adicionarProjeto(@RequestBody CreateProjetoRequest projeto) {

    if(!ProjetoValidator.isValid(projeto.nome, projeto.descricao, projeto.status)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome, status e descrição são obrigatórios");
    }
    Projeto proj = new Projeto(projetoRepository.getId() + 1, projeto.getNome(), projeto.getDescricao(), projeto.getData_inicio(), projeto.getTermino(), projeto.status);
    projetoRepository.adicionarProjeto(proj);
    return ResponseEntity.status(HttpStatus.CREATED).body("Projeto adicionado com sucesso");
  }

  public ResponseEntity<String> removerProjeto(@PathVariable Long codigo) {
    Optional<Projeto> projeto = projetoRepository.buscarProjetoPorCodigo(codigo);
    if (projeto.isPresent()) {
      projetoRepository.removerProjeto(projeto.get());
      return ResponseEntity.ok("Projeto removido com sucesso");
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  public ResponseEntity<Projeto> buscarProjetoPorId(@PathVariable Long codigo) {
    Optional<Projeto> projetoOptional = projetoRepository.buscarProjetoPorCodigo(codigo);
    if (projetoOptional.isPresent()) {
      return ResponseEntity.ok(projetoOptional.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
