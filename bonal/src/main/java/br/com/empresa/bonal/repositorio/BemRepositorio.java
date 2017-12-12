package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.Bem;
import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.util.JPAUtil;

public class BemRepositorio {

	EntityManager em;

	public BemRepositorio() {
		em = new JPAUtil().getEntityManager();
	}

	// funcao que adiciona uma bem ao banco de dados
	public void adicionar(Bem bem, Long categoriaId, Long unidadeDeMedidaId) {
		em.getTransaction().begin();

		UnidadeDeMedida unidadeDeMedida = em.find(UnidadeDeMedida.class, unidadeDeMedidaId);
		Categoria categoria = em.find(Categoria.class, categoriaId);

		bem.setUnidadeDeMedida(unidadeDeMedida);
		bem.setCategoria(categoria);

		em.persist(bem);
		em.getTransaction().commit();
		em.close();
	}

	// funcao que atualiza os registros de uma bem
	public void atualizar(Bem bem, Long categoriaId, Long unidadeDeMedidaId) {
		em.getTransaction().begin();

		UnidadeDeMedida unidadeDeMedida = em.find(UnidadeDeMedida.class, unidadeDeMedidaId);
		Categoria categoria = em.find(Categoria.class, categoriaId);

		bem.setUnidadeDeMedida(unidadeDeMedida);
		bem.setCategoria(categoria);

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

	// lista bems com base em uma chave de pesquisa
	public List<Bem> listarBens(String nome, Long categoriaId, Long unidadeDeMedidaId) {
		String jpql = "select b from Bem b where ";

		if (nome != null)
			jpql += "(b.nome like :pnome or b.codigo like :pcodigo or b.descricao like :pdescricao) and ";
		if (categoriaId != null)
			jpql += "b.categoria.id = :pcategoria and ";
		if (unidadeDeMedidaId != null)
			jpql += "b.unidadeDeMedida.id = :punidade and ";
		jpql += "1 = 1";

		TypedQuery<Bem> query = em.createQuery(jpql, Bem.class);

		if (nome != null) {
			query.setParameter("pnome", '%' + nome + '%');
			query.setParameter("pcodigo", '%' + nome + '%');
			query.setParameter("pdescricao", '%' + nome + '%');
		}
		if (categoriaId != null)
			query.setParameter("pcategoria", categoriaId);
		if (unidadeDeMedidaId != null)
			query.setParameter("punidade", unidadeDeMedidaId);

		System.out.println(jpql);
		return query.getResultList();
	}
}
