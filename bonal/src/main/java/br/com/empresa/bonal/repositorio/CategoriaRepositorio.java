package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.util.JPAUtil;

public class CategoriaRepositorio {

	EntityManager em;

	public CategoriaRepositorio() {
		em = new JPAUtil().getEntityManager();
	}

	// funcao que adiciona uma categoria ao banco de dados
	public void adicionar(Categoria categoria) {
		em.getTransaction().begin();
		em.persist(categoria);
		em.getTransaction().commit();
		em.close();
	}

	// funcao que atualiza os registros de uma categoria
	public void atualizar(Categoria categoria) {
		em.getTransaction().begin();
		em.merge(categoria);
		em.getTransaction().commit();
		em.close();
	}

	// funcao que remove os registros de uma categoria
	public void remover(Categoria categoria) {
		em.getTransaction().begin();
		em.remove(em.merge(categoria));
		em.getTransaction().commit();
		em.close();
	}

	// funcao que recupera uma categoria de acordo com id
	public Categoria getCategoria(Long id) {
		return em.find(Categoria.class, id);
	}
	//lista categorias com base em uma chave de pesquisa
	public List<Categoria> listarCategorias(String nome) {
		String jpql = "select c from Categoria c where c.nome like :pnome or c.codigo like :pcodigo or c.descricao like :pdescricao";

		TypedQuery<Categoria> query = em.createQuery(jpql, Categoria.class);

		query.setParameter("pnome", '%' + nome + '%');
		query.setParameter("pcodigo", '%' + nome + '%');
		query.setParameter("pdescricao", '%' + nome + '%');

		System.out.println(jpql);
		return query.getResultList();
	}
}
