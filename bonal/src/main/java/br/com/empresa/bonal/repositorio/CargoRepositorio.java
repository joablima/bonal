package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.Cargo;
import br.com.empresa.bonal.util.JPAUtil;

public class CargoRepositorio {

	EntityManager em;

	public CargoRepositorio() {
		em = new JPAUtil().getEntityManager();
	}

	// funcao que adiciona um cargo ao banco de dados
	public void adicionar(Cargo cargo) {
		em.getTransaction().begin();
		em.persist(cargo);
		em.getTransaction().commit();
		em.close();
	}

	// funcao que atualiza os registros de um cargo
	public void atualizar(Cargo cargo) {
		em.getTransaction().begin();
		em.merge(cargo);
		em.getTransaction().commit();
		em.close();
	}

	// funcao que remove os registros de um cargo
	public void remover(Cargo cargo) {
		em.getTransaction().begin();
		em.remove(em.merge(cargo));
		em.getTransaction().commit();
		em.close();
	}

	// funcao que recupera um cargo de acordo com id
	public Cargo getCargo(Long id) {
		return em.find(Cargo.class, id);
	}

	public List<Cargo> listarCargos(String nome) {
		String jpql = "select c from Cargo c where c.nome like :pnome";

		TypedQuery<Cargo> query = em.createQuery(jpql, Cargo.class);

		query.setParameter("pnome", '%' + nome + '%');

		System.out.println(jpql);
		return query.getResultList();
	}
}
