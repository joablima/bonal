package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.BemDeConsumo;
import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.entidades.SubCategoria;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;

public class BemDeConsumoRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(BemDeConsumo bemDeConsumo) {
		
		System.out.println(bemDeConsumo.toString());
		
		em.persist(bemDeConsumo);
	}

	// m�todo que atualiza um registro
	public void atualizar(BemDeConsumo bemDeConsumo) {

		em.merge(bemDeConsumo);
	}

	// m�todo que remove um registro
	public void remover(BemDeConsumo bemDeConsumo) {
		em.merge(bemDeConsumo);
	}

	// m�todo que recupera um objeto pelo id
	public BemDeConsumo buscarPorId(Long id) {
		return em.find(BemDeConsumo.class, id);
	}

	// m�todo que lista todos os registros
	public List<BemDeConsumo> listarTodos() {
		try {
			return em.createQuery("select s from BemDeConsumo s", BemDeConsumo.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	// m�todo que lista com crit�rios todos os registros
	public List<BemDeConsumo> listarPorCriterios(String nome) {
		String jpql = "select s from BemDeConsumo s where ";

		if (nome != null)
			jpql += "(s.nome like :pnome or s.codigo like :pcodigo or s.descricao like :pdescricao or s.quantidade like :pquantidade );";
		jpql += "1 = 1";

		TypedQuery<BemDeConsumo> query = em.createQuery(jpql, BemDeConsumo.class);

		if (nome != null)
			query.setParameter("pnome", '%' + nome + '%')
				.setParameter("pcodigo", '%' + nome + '%')
				.setParameter("pdescricao", '%' + nome + '%')
				.setParameter("pquantidade", '%' + nome + '%');

		return query.getResultList();
	}
	
	// método que verifica se elemento existe
		public Categoria getCategoriaPorCodigo(String codigo) {
			TypedQuery<Categoria> query = em.createQuery("select c from Categoria c where c.codigo = :pcodigo",
					Categoria.class).setParameter("pcodigo", codigo.toUpperCase());

			try {
				return query.getSingleResult();
			} catch (Exception e) {
				return null;
			}
		}

	// método que verifica se elemento existe
	public SubCategoria getSubCategoriaPorCodigo(String codigo) {
		TypedQuery<SubCategoria> query = em.createQuery("select c from SubCategoria c where c.codigo = :pcodigo",
				SubCategoria.class).setParameter("pcodigo", codigo.toUpperCase());

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
