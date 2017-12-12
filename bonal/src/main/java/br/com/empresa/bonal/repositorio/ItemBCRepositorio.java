package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.ItemBC;
import br.com.empresa.bonal.util.JPAUtil;

public class ItemBCRepositorio {

	EntityManager em;

	public ItemBCRepositorio() {
		em = new JPAUtil().getEntityManager();
	}

	// funcao que adiciona uma unidade de medida ao banco de dados
	public void adicionar(ItemBC itemBC) {
		em.getTransaction().begin();
		em.persist(itemBC);
		em.getTransaction().commit();
		em.close();
	}

	// funcao que atualiza os registros de uma unidade de medida
	public void atualizar(ItemBC itemBC) {
		em.getTransaction().begin();
		em.merge(itemBC);
		em.getTransaction().commit();
		em.close();
	}

	// funcao que remove os registros de uma unidade de medida
	public void remover(ItemBC itemBC) {
		em.getTransaction().begin();
		em.remove(em.merge(itemBC));
		em.getTransaction().commit();
		em.close();
	}

	// funcao que recupera uma unidade de medida de acordo com id
	public ItemBC getItemBC(Long id) {
		return em.find(ItemBC.class, id);
	}
	//lista unidades de medidas com base em uma chave de pesquisa
	public List<ItemBC> listarItensBC(String nome) {
		String jpql = "select i from ItemBC i where i.codigo like :pcodigo or i.dataDeValidade like :pdataDeValidade";

		TypedQuery<ItemBC> query = em.createQuery(jpql, ItemBC.class);

		query.setParameter("pnome", '%' + nome + '%');
		query.setParameter("psigla", '%' + nome + '%');

		System.out.println(jpql);
		return query.getResultList();
	}
}
