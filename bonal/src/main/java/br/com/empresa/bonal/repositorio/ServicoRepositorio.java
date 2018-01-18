package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.entidades.Servico;
import br.com.empresa.bonal.entidades.SubCategoria;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;

public class ServicoRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(Servico servico) {
		em.persist(servico);
	}

	// m�todo que atualiza um registro
	public void atualizar(Servico servico) {

		em.merge(servico);
	}

	// m�todo que remove um registro
	public void remover(Servico servico) {
		em.merge(servico);
	}

	// m�todo que recupera um objeto pelo id
	public Servico buscarPorId(Long id) {
		return em.find(Servico.class, id);
	}

	// m�todo que lista todos os registros
	public List<Servico> listarTodos() {
		try {
			return em.createQuery("select s from Servico s", Servico.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	// m�todo que lista com crit�rios todos os registros
	public List<Servico> listarPorCriterios(String nome) {
		String jpql = "select s from Servico s where ";

		if (nome != null)
			jpql += "(s.nome like :pnome or s.codigo like :pcodigo or s.descricao like :pdescricao);";
		jpql += "1 = 1";

		TypedQuery<Servico> query = em.createQuery(jpql, Servico.class);

		if (nome != null)
			query.setParameter("pnome", '%' + nome + '%').setParameter("pcodigo", '%' + nome + '%')
					.setParameter("pdescricao", '%' + nome + '%');

		return query.getResultList();
	}

	// método que verifica se elemento existe
	public Servico codigoExiste(Servico servico) {
		TypedQuery<Servico> query = em
				.createQuery("select s from ItemDeProducao s where s.codigo = :pcodigo", Servico.class)
				.setParameter("pcodigo", servico.getCodigo());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// método que verifica se elemento existe
	public SubCategoria getSubCategoriaPorCodigo(String codigo) {
		TypedQuery<SubCategoria> query = em
				.createQuery("select c from SubCategoria c where c.codigo = :pcodigo", SubCategoria.class)
				.setParameter("pcodigo", codigo);

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// método que verifica se elemento existe
	public UnidadeDeMedida getUnidadeDeMedidaPorCodigo(String codigo) {
		TypedQuery<UnidadeDeMedida> query = em
				.createQuery("select c from UnidadeDeMedida c where c.codigo = :pcodigo", UnidadeDeMedida.class)
				.setParameter("pcodigo", codigo);

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
