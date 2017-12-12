package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.util.JPAUtil;

public class UnidadeDeMedidaRepositorio {

	EntityManager em;

	public UnidadeDeMedidaRepositorio() {
		em = new JPAUtil().getEntityManager();
	}

	// funcao que adiciona uma unidade de medida ao banco de dados
	public void adicionar(UnidadeDeMedida unidadeDeMedida) {
		em.getTransaction().begin();
		em.persist(unidadeDeMedida);
		em.getTransaction().commit();
//		em.close();
	}

	// funcao que atualiza os registros de uma unidade de medida
	public void atualizar(UnidadeDeMedida unidadeDeMedida) {
		em.getTransaction().begin();
		em.merge(unidadeDeMedida);
		em.getTransaction().commit();
		em.close();
	}

	// funcao que remove os registros de uma unidade de medida
	public void remover(UnidadeDeMedida unidadeDeMedida) {
		em.getTransaction().begin();
		em.remove(em.merge(unidadeDeMedida));
		em.getTransaction().commit();
		em.close();
	}

	// funcao que recupera uma unidade de medida de acordo com id
	public UnidadeDeMedida getUnidadeDeMedida(Long id) {
		return em.find(UnidadeDeMedida.class, id);
	}
	//lista unidades de medidas com base em uma chave de pesquisa
	public List<UnidadeDeMedida> listarUnidadesDeMedida(String nome) {
		String jpql = "select u from UnidadeDeMedida u where u.nome like :pnome or u.sigla like :psigla";

		TypedQuery<UnidadeDeMedida> query = em.createQuery(jpql, UnidadeDeMedida.class);

		query.setParameter("pnome", '%' + nome + '%');
		query.setParameter("psigla", '%' + nome + '%');

		System.out.println(jpql);
		return query.getResultList();
	}
}
