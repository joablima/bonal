package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.Bem;
import br.com.empresa.bonal.util.JPAUtil;

public class BemRepositorio {

	EntityManager em;

	public BemRepositorio() {
		em = new JPAUtil().getEntityManager();
	}

	// funcao que adiciona uma bem ao banco de dados
	public void adicionar(Bem bem) {
		em.getTransaction().begin();
		em.persist(bem);
		em.getTransaction().commit();
		em.close();
	}

	// funcao que atualiza os registros de uma bem
	public void atualizar(Bem bem) {
		em.getTransaction().begin();
		em.merge(bem);
		em.getTransaction().commit();
		em.close();
	}

	// funcao que remove os registros de uma bem
	public void remover(Bem bem) {
		em.getTransaction().begin();
		em.remove(em.merge(bem));
		em.getTransaction().commit();
		em.close();
	}

	// funcao que recupera uma bem de acordo com id
	public Bem getBem(Long id) {
		return em.find(Bem.class, id);
	}
	//lista bems com base em uma chave de pesquisa
	public List<Bem> listarBens(String nome) {
		String jpql = "select b from Bem b where b.nome like :pnome or b.codigo like :pcodigo or b.descricao like :pdescricao "
				+ "or b.categoria like :pcategoria or b.quantidade like :pquantidade or b.unidadeDeMedida like :punidadeDeMedida";

		TypedQuery<Bem> query = em.createQuery(jpql, Bem.class);

		query.setParameter("pnome", '%' + nome + '%');
		query.setParameter("pcodigo", '%' + nome + '%');
		query.setParameter("pdescricao", '%' + nome + '%');
		query.setParameter("pcategoria", '%' + nome + '%');
		query.setParameter("pquantidade", '%' + nome + '%');
		query.setParameter("punidadeDeMedida", '%' + nome + '%');

		System.out.println(jpql);
		return query.getResultList();
	}
}
