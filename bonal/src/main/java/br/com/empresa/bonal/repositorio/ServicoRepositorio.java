package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.logging.log4j.Logger;

import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.Servico;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;

@SuppressWarnings("serial")
public class ServicoRepositorio implements Serializable {

	@Inject
	EntityManager em;
	
	@Inject
	private Logger logger;

	// m�todo que persiste um registro
	public void adicionar(Servico servico, Long categoriaId, Long unidadeDeMedidaId) {
		UnidadeDeMedida unidadeDeMedida = em.find(UnidadeDeMedida.class, unidadeDeMedidaId);
		Categoria categoria = em.find(Categoria.class, categoriaId);

		servico.setUnidadeDeMedida(unidadeDeMedida);
		servico.setCategoria(categoria);

		em.persist(servico);
	}

	// m�todo que atualiza um registro
	public void atualizar(Servico servico, Long categoriaId, Long unidadeDeMedidaId) {
		UnidadeDeMedida unidadeDeMedida = em.find(UnidadeDeMedida.class, unidadeDeMedidaId);
		Categoria categoria = em.find(Categoria.class, categoriaId);

		servico.setUnidadeDeMedida(unidadeDeMedida);
		servico.setCategoria(categoria);

		em.merge(servico);
	}

	// m�todo que remove um registro
	public void remover(Servico servico) {
		em.remove(em.merge(servico));
	}

	// m�todo que recupera um objeto pelo id
	public Servico buscarPorId(Long id) {
		Servico bem = em.find(Servico.class, id);
		return bem;
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

		if (nome != null) {
			query.setParameter("pnome", '%' + nome + '%');
			query.setParameter("pcodigo", '%' + nome + '%');
			query.setParameter("pdescricao", '%' + nome + '%');
		}
		if (categoriaId != null)
			query.setParameter("pcategoria", categoriaId);
		if (unidadeDeMedidaId != null)
			query.setParameter("punidade", unidadeDeMedidaId);

		logger.info(jpql);
		List<Servico> list = query.getResultList();
		return list;
	}
}
