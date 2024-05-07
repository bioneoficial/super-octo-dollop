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
import br.com.devweb.camadas.dto.CreateProjetoRequest;
import br.com.devweb.camadas.enums.StatusPagamento;
import br.com.devweb.camadas.models.Projeto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api-v1-0/projetos")
public class ProjetoController {

  @Autowired
  private ProjetoRepository projetoRepository;

  // Rota para adicionar um projeto
  @PostMapping
  public void adicionarProjeto(@RequestBody CreateProjetoRequest projeto) {

    Projeto proj = new Projeto(projetoRepository.getId() + 1, projeto.getNome(), projeto.getDescricao(), new Date().toString(), new Date().toString(), StatusPagamento.PENDENTE.toString());
    projetoRepository.adicionarProjeto(proj);
  }

  // Rota para remover um projeto específico
  @DeleteMapping("/{codigo}")
  public void removerProjeto(@PathVariable Long codigo) {
      Optional<Projeto> projeto = projetoRepository.buscarProjetoPorCodigo(codigo);
      projeto.ifPresent(projetoRepository::removerProjeto);
  }

  @GetMapping
  public List<Projeto> listarProjetos() {
    return projetoRepository.listarProjetos();
  }

  @GetMapping("/{codigo}")
  public Optional<Projeto> buscarProjetoPorId(@PathVariable Long codigo) {
    System.out.println("Buscando projeto com o código: " + codigo);
    return projetoRepository.buscarProjetoPorCodigo(codigo);
  }

   @PutMapping("/{codigo}")
    public void editarProjeto(@PathVariable Long codigo, @RequestBody Projeto novoProjeto) {
        Optional<Projeto> projetoOptional = projetoRepository.buscarProjetoPorCodigo(codigo);
        projetoOptional.ifPresent(projeto -> {
            if (novoProjeto.getNome() != null) {
                projeto.setNome(novoProjeto.getNome());
            }
            if (novoProjeto.getDescricao() != null) {
                projeto.setDescricao(novoProjeto.getDescricao());
            }
            if (novoProjeto.getData_termino() != null) {
                projeto.setData_termino(novoProjeto.getData_termino());
            }
            projetoRepository.editarProjeto(codigo, novoProjeto);
        });
    }

    // Rota para atualizar a situação de um projeto
    @PutMapping("/{codigo}/status")
    public void atualizarStatusProjeto(@PathVariable Long codigo, @RequestBody StatusPagamento novoStatus) {
        Optional<Projeto> projetoOptional = projetoRepository.buscarProjetoPorCodigo(codigo);
        projetoOptional.ifPresent(projeto -> {
            projeto.setStatus(novoStatus.toString());
            if (novoStatus == StatusPagamento.APROVADO) {
                projeto.setData_inicio(new Date().toString());
            }
            projetoRepository.editarProjeto(codigo, projeto);
        });
    }

}
