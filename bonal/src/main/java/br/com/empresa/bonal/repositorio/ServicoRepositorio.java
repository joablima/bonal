package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.empresa.bonal.entidades.Servico;

@SuppressWarnings("serial")
public class ServicoRepositorio implements Serializable {

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
		CriteriaQuery<Servico> query = em.getCriteriaBuilder().createQuery(Servico.class);
		query.select(query.from(Servico.class));
		List<Servico> list = em.createQuery(query).getResultList();
		return list;
	}

	// m�todo que lista com crit�rios todos os registros
	public List<Servico> listarPorCriterios(String nome, Long categoriaId, Long unidadeDeMedidaId) {
		String jpql = "select s from Servico s where ";

		if (nome != null)
			jpql += "(s.nome like :pnome or s.codigo like :pcodigo or s.descricao like :pdescricao) and ";
		if (categoriaId != null)
			jpql += "s.categoria.id = :pcategoria and ";
		if (unidadeDeMedidaId != null)
			jpql += "s.unidadeDeMedida.id = :punidade and ";
		jpql += "1 = 1";

		TypedQuery<Servico> query = em.createQuery(jpql, Servico.class);

		if (nome != null)
			query.setParameter("pnome", '%' + nome + '%')
			.setParameter("pcodigo", '%' + nome + '%')
			.setParameter("pdescricao", '%' + nome + '%');
		
		if (categoriaId != null)
			query.setParameter("pcategoria", categoriaId);
		if (unidadeDeMedidaId != null)
			query.setParameter("punidade", unidadeDeMedidaId);

		return query.getResultList();
	}

	public Servico codigoExiste(Servico servico) {
		TypedQuery<Servico> query = em.createQuery("select s from Servico s where s.codigo = :pcodigo", Servico.class)
				.setParameter("pcodigo", servico.getCodigo());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
