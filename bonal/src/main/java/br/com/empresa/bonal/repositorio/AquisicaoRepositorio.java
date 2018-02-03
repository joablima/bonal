package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.Aquisicao;
import br.com.empresa.bonal.entidades.Fornecedor;
import br.com.empresa.bonal.entidades.Funcionario;
import br.com.empresa.bonal.util.logging.Logging;

public class AquisicaoRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(Aquisicao aquisicao) {
		em.persist(aquisicao);
	}

	@Logging
	// m�todo que persiste um registro
	public Long adicionarComRetorno(Aquisicao aquisicao) {
		em.persist(aquisicao);
		return aquisicao.getId();
	}

	// m�todo que atualiza um registro
	public void atualizar(Aquisicao aquisicao) {
		em.merge(aquisicao);
	}

	// m�todo que remove um registro
	public void remover(Aquisicao aquisicao) {
		em.merge(aquisicao);
	}

	// m�todo que recupera um objeto pelo id
	public Aquisicao buscarPorId(Long id) {
		return em.find(Aquisicao.class, id);
	}

	// m�todo que lista todos os registros
	public List<Aquisicao> listarTodos() {
		try {
			return em.createQuery("select c from Aquisicao c", Aquisicao.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	
	// método que verifica se elemento existe
	public Fornecedor getFornecedorPorDocumento(String documento) {
		TypedQuery<Fornecedor> query = em
				.createQuery("select c from Fornecedor c where c.documento = :pdocumento", Fornecedor.class)
				.setParameter("pdocumento", documento.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// método que verifica se elemento existe
	public Funcionario getFuncionarioPorDocumento(String documento) {
		TypedQuery<Funcionario> query = em
				.createQuery("select c from Funcionario c where c.documento = :pdocumento", Funcionario.class)
				.setParameter("pdocumento", documento.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
