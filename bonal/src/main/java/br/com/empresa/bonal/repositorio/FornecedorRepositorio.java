package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.logging.log4j.Logger;

import br.com.empresa.bonal.entidades.Fornecedor;

public class FornecedorRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;
	
	@Inject
	private Logger logger;

	// m�todo que persiste um registro
	public void adicionar(Fornecedor fornecedor) {
		em.persist(fornecedor);
	}

	// m�todo que atualiza um registro
	public void atualizar(Fornecedor fornecedor) {
		em.merge(fornecedor);
	}

	// m�todo que remove um registro
	public void remover(Fornecedor cliente) {
		em.merge(cliente);
	}

	// m�todo que recupera um objeto pelo id
	public Fornecedor buscarPorId(Long id) {
		Fornecedor funcionario = em.find(Fornecedor.class, id);
		return funcionario;
	}

	// m�todo que lista todos os registros
	public List<Fornecedor> listarTodos() {
		CriteriaQuery<Fornecedor> query = em.getCriteriaBuilder().createQuery(Fornecedor.class);
		query.select(query.from(Fornecedor.class));
		List<Fornecedor> list = em.createQuery(query).getResultList();
		return list;
	}

	// m�todo que lista com crit�rios todos os registros
	public List<Fornecedor> listarPorCriterios(String nome) {
		String jpql = "select f from Fornecedor f where ";

		if (nome != null)
			jpql += "(f.nome like :pnome or f.documento like :pdocumento or f.identificacao like :pidentificacao or f.email like :pemail) and ";
		jpql += "1 = 1";

		TypedQuery<Fornecedor> query = em.createQuery(jpql, Fornecedor.class);

		if (nome != null) {
			query.setParameter("pnome", '%' + nome + '%');
			query.setParameter("pdocumento", '%' + nome + '%');
			query.setParameter("pidentificacao", '%' + nome + '%');
			query.setParameter("pemail", '%' + nome + '%');
		}

		logger.info(jpql);
		List<Fornecedor> list = query.getResultList();
		return list;
	}
}
