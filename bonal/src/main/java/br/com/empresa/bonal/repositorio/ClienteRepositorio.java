package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.logging.log4j.Logger;

import br.com.empresa.bonal.entidades.Cliente;

public class ClienteRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;
	
	@Inject
	private Logger logger;

	// m�todo que persiste um registro
	public void adicionar(Cliente cliente) {
		em.persist(cliente);
	}

	// m�todo que atualiza um registro
	public void atualizar(Cliente cliente) {
		em.merge(cliente);
	}

	// m�todo que remove um registro
	public void remover(Cliente cliente) {
		em.merge(cliente);
	}

	// m�todo que recupera um objeto pelo id
	public Cliente buscarPorId(Long id) {
		Cliente funcionario = em.find(Cliente.class, id);
		return funcionario;
	}

	// m�todo que lista todos os registros
	public List<Cliente> listarTodos() {
		CriteriaQuery<Cliente> query = em.getCriteriaBuilder().createQuery(Cliente.class);
		query.select(query.from(Cliente.class));
		List<Cliente> list = em.createQuery(query).getResultList();
		return list;
	}

	// m�todo que lista com crit�rios todos os registros
	public List<Cliente> listarPorCriterios(String nome) {
		String jpql = "select c from Cliente c where ";

		if (nome != null)
			jpql += "(c.nome like :pnome or c.documento like :pdocumento or c.identificacao like :pidentificacao or c.email like :pemail) and ";

		jpql += "1 = 1";

		TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);

		if (nome != null) {
			query.setParameter("pnome", '%' + nome + '%');
			query.setParameter("pdocumento", '%' + nome + '%');
			query.setParameter("pidentificacao", '%' + nome + '%');
			query.setParameter("pemail", '%' + nome + '%');
		}

		logger.info(jpql);
		List<Cliente> list = query.getResultList();
		return list;
	}
}
