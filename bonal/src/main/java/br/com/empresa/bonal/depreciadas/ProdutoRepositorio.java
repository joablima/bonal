/*package br.com.empresa.bonal.depreciadas;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.Produto;

@SuppressWarnings("serial")
public class ProdutoRepositorio implements Serializable {

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(Produto produto) {
		em.persist(produto);
	}

	// m�todo que atualiza um registro
	public void atualizar(Produto produto) {
		em.merge(produto);
	}

	// m�todo que remove um registro
	public void remover(Produto produto) {
		em.merge(produto);
	}

	// m�todo que recupera um objeto pelo id
	public Produto buscarPorId(Long id) {
		return em.find(Produto.class, id);
	}

	// m�todo que lista todos os registros
	public List<Produto> listarTodos() {
		try {
			return em.createQuery("select p from Produto p", Produto.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	// m�todo que lista com crit�rios todos os registros
	public List<Produto> listarPorCriterios(String nome, Long categoriaId, Long unidadeDeMedidaId) {
		String jpql = "select p from Produto p where ";

		if (nome != null)
			jpql += "(p.nome like :pnome or p.codigo like :pcodigo or p.descricao like :pdescricao) and ";
		if (categoriaId != null)
			jpql += "p.categoria.id = :pcategoria and ";
		if (unidadeDeMedidaId != null)
			jpql += "p.unidadeDeMedida.id = :punidade and ";
		jpql += "1 = 1";

		TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);

		if (nome != null) {
			query.setParameter("pnome", '%' + nome + '%').setParameter("pcodigo", '%' + nome + '%')
					.setParameter("pdescricao", '%' + nome + '%');
		}
		if (categoriaId != null)
			query.setParameter("pcategoria", categoriaId);
		if (unidadeDeMedidaId != null)
			query.setParameter("punidade", unidadeDeMedidaId);

		return query.getResultList();
	}

	// método que verifica se elemento existe
	public Produto codigoExiste(Produto produto) {
		TypedQuery<Produto> query = em.createQuery("select p from Produto p where p.codigo = :codigo", Produto.class)
				.setParameter("codigo", produto.getCodigo());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
*/