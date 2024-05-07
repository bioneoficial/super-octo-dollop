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

  // Método para buscar um projeto específico pelo ID
  public Optional<Projeto> buscarProjetoPorId(Long codigo) {
      return projetos.stream()
                    .filter(projeto -> projeto.getCodigo() == codigo)
                    .findFirst();
  }

}
