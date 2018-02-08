package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.Producao;
import br.com.empresa.bonal.entidades.Produto;
import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;

public class ProducaoRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(Producao producao) {

		em.persist(producao);
	}

	// m�todo que atualiza um registro
	public void atualizar(Producao producao) {

		em.merge(producao);
	}

	// m�todo que atualiza um registro
	public void atualizarProduto(Produto produto) {

		em.merge(produto);
	}

	// m�todo que remove um registro
	public void remover(Producao producao) {
		em.merge(producao);
	}

	// m�todo que recupera um objeto pelo id
	public Producao buscarPorId(Long id) {
		return em.find(Producao.class, id);
	}

	// m�todo que lista todos os registros
	public List<Producao> listarTodos() {
		try {
			return em.createQuery("select s from Producao s", Producao.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	// m�todo que lista com crit�rios todos os registros
	public List<Producao> listarPorCriterios(String nome) {
		String jpql = "select s from Producao s where ";

		if (nome != null)
			jpql += "(s.nome like :pnome or s.codigo like :pcodigo or s.descricao like :pdescricao or s.quantidade like :pquantidade );";
		jpql += "1 = 1";

		TypedQuery<Producao> query = em.createQuery(jpql, Producao.class);

		if (nome != null)
			query.setParameter("pnome", '%' + nome + '%').setParameter("pcodigo", '%' + nome + '%')
					.setParameter("pdescricao", '%' + nome + '%').setParameter("pquantidade", '%' + nome + '%');

		return query.getResultList();
	}

	// método que verifica se elemento existe
	public Produto getProdutoPorCodigo(String codigo) {
		TypedQuery<Produto> query = em.createQuery("select c from Produto c where c.codigo = :pcodigo", Produto.class)
				.setParameter("pcodigo", codigo.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// método que verifica se elemento existe
	public UnidadeDeMedida getUnidadeDeMedidaPorSigla(String sigla) {
		TypedQuery<UnidadeDeMedida> query = em
				.createQuery("select c from UnidadeDeMedida c where c.sigla = :psigla", UnidadeDeMedida.class)
				.setParameter("psigla", sigla.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// método que verifica se elemento existe
	public ItemDeProducao getItemDeProducaoPorCodigo(String codigo) {
		TypedQuery<ItemDeProducao> query = em
				.createQuery("select c from ItemDeProducao c where c.codigo = :pcodigo", ItemDeProducao.class)
				.setParameter("pcodigo", codigo.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
