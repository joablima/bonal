package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.Cargo;

public class CargoRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(CargoRepositorio.class);

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
		Cargo cargo = em.find(Cargo.class, id);
		return cargo;
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

		TypedQuery<Cargo> query = em.createQuery(jpql, Cargo.class);

		query.setParameter("pnome", '%' + nome + '%');

		logger.info(jpql);
		List<Cargo> list = query.getResultList();
		return list;
	}

	// método que verifica se elemento existe
	public Cargo cargoExiste(Cargo cargo) {
		TypedQuery<Cargo> query = em.createQuery("select c from Cargo c where c.nome = :nome", Cargo.class)
				.setParameter("nome", cargo.getNome());

		try {
			Cargo novocargo = query.getSingleResult();
			return novocargo;
		} catch (Exception e) {
			return null;
		}
	}
}
