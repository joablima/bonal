package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.BemPermanente;
import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.entidades.SubCategoria;

public class BemPermanenteRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(BemPermanente bemPermanente) {
		em.persist(bemPermanente);
	}

	// m�todo que atualiza um registro
	public void atualizar(BemPermanente bemPermanente) {

		em.merge(bemPermanente);
	}

	// m�todo que remove um registro
	public void remover(BemPermanente bemPermanente) {
		em.merge(bemPermanente);
	}

	// m�todo que recupera um objeto pelo id
	public BemPermanente buscarPorId(Long id) {
		return em.find(BemPermanente.class, id);
	}

	// m�todo que lista todos os registros
	public List<BemPermanente> listarTodos() {
		try {
			return em.createQuery("select s from BemPermanente s", BemPermanente.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	// m�todo que lista com crit�rios todos os registros
	public List<BemPermanente> listarPorCriterios(String nome) {
		String jpql = "select s from BemPermanente s where ";

		if (nome != null)
			jpql += "(s.nome like :pnome or s.codigo like :pcodigo or s.descricao like :pdescricao or s.marca like :pmarca or s.modelo like :pmodelo);";
		jpql += "1 = 1";

		TypedQuery<BemPermanente> query = em.createQuery(jpql, BemPermanente.class);

		if (nome != null)
			query.setParameter("pnome", '%' + nome + '%')
				.setParameter("pcodigo", '%' + nome + '%')
				.setParameter("pdescricao", '%' + nome + '%')
				.setParameter("pmarca", '%' + nome + '%')
				.setParameter("pmodelo", '%' + nome + '%');

		return query.getResultList();
	}

	// método que verifica se elemento existe
	public BemPermanente codigoExiste(BemPermanente bemPermanente) {
		TypedQuery<BemPermanente> query = em.createQuery("select s from ItemDeProducao s where s.codigo = :pcodigo",
				BemPermanente.class).setParameter("pcodigo", bemPermanente.getCodigo());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// método que verifica se elemento existe
	public SubCategoria getSubCategoriaPorCodigo(String codigo) {
		TypedQuery<SubCategoria> query = em.createQuery("select c from SubCategoria c where c.codigo = :pcodigo",
				SubCategoria.class).setParameter("pcodigo", codigo);

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
					.setParameter("pcodigo", codigo);

			try {
				return query.getSingleResult();
			} catch (Exception e) {
				return null;
			}
		}
}
