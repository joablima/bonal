package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.Cargo;
import br.com.empresa.bonal.entidades.Cliente;

public class ClienteRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

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
		return em.find(Cliente.class, id);
	}

	// m�todo que lista todos os registros
	public List<Cliente> listarTodos() {
		try {
			return em.createQuery("select s from Cliente s", Cliente.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	// m�todo que lista com crit�rios todos os registros
	public List<Cliente> listarPorCriterios(String nome) {
		String jpql = "select s from Cliente s where ";

		if (nome != null)
			jpql += "(s.nome like :pnome or s.documento like :pdocumento or s.identificacao like :pidentificacao);";
		jpql += "1 = 1";

		TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);

		if (nome != null)
			query.setParameter("pnome", '%' + nome + '%').setParameter("pdocumento", '%' + nome + '%')
					.setParameter("pidentificacao", '%' + nome + '%');

		return query.getResultList();
	}



	// método que verifica se elemento existe
	public Cliente getClientePorDocumento(String documento) {
		TypedQuery<Cliente> query = em
				.createQuery("select c from Cliente c where c.documento = :pdocumento", Cliente.class)
				.setParameter("pdocumento", documento.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
