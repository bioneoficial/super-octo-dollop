package br.com.devweb.camadas.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.devweb.camadas.models.Projeto;
import lombok.Data;

@Data
@Repository
public class ProjetoRepository {
  
  private List<Projeto> projetos = new ArrayList<>();

  // Método para adicionar um projeto
  public void adicionarProjeto(Projeto projeto) {
    projetos.add(projeto);
  }

  // Método para remover um projeto específico
  public void removerProjeto(Projeto projeto) {
      projetos.remove(projeto);
  }

  // Método para listar todos os projetos
  public List<Projeto> listarProjetos() {
      return projetos;
  }

  public long getId() {
    return projetos.size();
  }
  // Método para buscar um projeto específico pelo ID
  public Optional<Projeto> buscarProjetoPorCodigo(Long codigo) {
      return projetos.stream()
                    .filter(projeto -> projeto.getCodigo() == codigo)
                    .findFirst();
  }

  public boolean editarProjeto(Long codigo, Projeto projetoAtualizado) {
    Optional<Projeto> projetoOptional = buscarProjetoPorCodigo(codigo);
    if (projetoOptional.isPresent()) {
      Projeto projetoExistente = projetoOptional.get();
      projetoExistente.setNome(projetoAtualizado.getNome());
      projetoExistente.setDescricao(projetoAtualizado.getDescricao());
      projetoExistente.setData_termino(projetoAtualizado.getData_termino());
      return true; 
    }
    return false; 
  }

}
