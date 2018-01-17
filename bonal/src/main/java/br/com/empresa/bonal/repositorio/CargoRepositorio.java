package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.Cargo;

public class CargoRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(Cargo cargo) {
		em.persist(cargo);
	}

	// m�todo que atualiza um registro
	public void atualizar(Cargo cargo) {
		em.merge(cargo);
	}

	// m�todo que remove um registro
	public void remover(Cargo cargo) {
		em.merge(cargo);
	}

	// m�todo que recupera um objeto pelo id
	public Cargo buscarPorId(Long id) {
		return em.find(Cargo.class, id);
	}

	// m�todo que lista todos os registros
	public List<Cargo> listarTodos() {
		try {
			return em.createQuery("select c from Cargo c", Cargo.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	// m�todo que lista com crit�rios todos os registros
	public List<Cargo> listarPorCriterios(String nome) {
		String jpql = "select c from Cargo c where c.nome like :pnome";
		return em.createQuery(jpql, Cargo.class).setParameter("pnome", '%' + nome + '%').getResultList();
	}

	// método que verifica se elemento existe
	public Cargo cargoExiste(Cargo cargo) {
		TypedQuery<Cargo> query = em.createQuery("select c from Cargo c where c.nome = :nome", Cargo.class)
				.setParameter("nome", cargo.getNome());
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
