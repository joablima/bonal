package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.empresa.bonal.entidades.Funcionario;

public class FuncionarioRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(Funcionario funcionario) {
		em.persist(funcionario);
	}

	// m�todo que atualiza um registro
	public void atualizar(Funcionario funcionario) {
		em.merge(funcionario);
	}

	// m�todo que remove um registro
	public void remover(Funcionario funcionario) {
		em.merge(funcionario);
	}

	// m�todo que recupera um objeto pelo id
	public Funcionario buscarPorId(Long id) {
		return em.find(Funcionario.class, id);
	}

	// m�todo que lista todos os registros
	public List<Funcionario> listarTodos() {
		CriteriaQuery<Funcionario> query = em.getCriteriaBuilder().createQuery(Funcionario.class);
		query.select(query.from(Funcionario.class));
		List<Funcionario> list = em.createQuery(query).getResultList();
		return list;
	}

	// m�todo que lista com crit�rios todos os registros
	public List<Funcionario> listarPorCriterios(String nome, Long cargoId) {
		String jpql = "select f from Funcionario f where ";

		if (nome != null)
			jpql += "(f.nome like :pnome or f.documento like :pdocumento or f.identificacao like :pidentificacao or f.email like :pemail) and ";
		if (cargoId != null)
			jpql += "p.categoria.id = :pcategoria and ";

		jpql += "1 = 1";

		TypedQuery<Funcionario> query = em.createQuery(jpql, Funcionario.class);

		if (nome != null) {
			query.setParameter("pnome", '%' + nome + '%')
			.setParameter("pdocumento", '%' + nome + '%')
			.setParameter("pidentificacao", '%' + nome + '%')
			.setParameter("pemail", '%' + nome + '%');
		}
		if (cargoId != null)
			query.setParameter("pcargo", cargoId);

		return query.getResultList();
	}
}
