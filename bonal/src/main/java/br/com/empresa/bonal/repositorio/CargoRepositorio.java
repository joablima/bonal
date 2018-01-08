package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.Cargo;
import br.com.empresa.bonal.util.JPAUtil;

public class CargoRepositorio {

	final static Logger logger = Logger.getLogger(CargoRepositorio.class);

	// m�todo que persiste um registro
	public void adicionar(Cargo cargo) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(cargo);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que atualiza um registro
	public void atualizar(Cargo cargo) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(cargo);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que remove um registro
	public void remover(Cargo cargo) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(cargo);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que recupera um objeto pelo id
	public Cargo buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		Cargo cargo = em.find(Cargo.class, id);
		em.close();
		return cargo;
	}

	// m�todo que lista todos os registros
	public List<Cargo> listarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.createQuery("select c from Cargo c where c.status = true", Cargo.class).getResultList();
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}

	// m�todo que lista com crit�rios todos os registros
	public List<Cargo> listarPorCriterios(String nome) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "select c from Cargo c where c.nome like :pnome";

		TypedQuery<Cargo> query = em.createQuery(jpql, Cargo.class);

		query.setParameter("pnome", '%' + nome + '%');

		logger.info(jpql);
		List<Cargo> list = query.getResultList();
		em.close();
		return list;
	}

	// método que verifica se elemento existe
	public Cargo cargoExiste(Cargo cargo) {
		EntityManager em = JPAUtil.getEntityManager();

		TypedQuery<Cargo> query = em.createQuery("select c from Cargo c where c.nome = :nome", Cargo.class)
				.setParameter("nome", cargo.getNome());

		try {
			Cargo novocargo = query.getSingleResult();
			return novocargo;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
}
