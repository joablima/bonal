package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.SubCategoria;
import br.com.empresa.bonal.entidades.Cargo;
import br.com.empresa.bonal.entidades.ItemDeProducao;

public class CargoRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(Cargo cargo) {
		em.persist(cargo);
	}

	// m�todo que atualiza um registro
	public void atualizar(Cargo cargo) {

		em.merge(cargo);
	}

	// m�todo que remove um registro
	public void remover(Cargo cargo) {
		em.merge(cargo);
	}

	// m�todo que recupera um objeto pelo id
	public Cargo buscarPorId(Long id) {
		return em.find(Cargo.class, id);
	}

	// m�todo que lista todos os registros
	public List<Cargo> listarTodos() {
		try {
			return em.createQuery("select s from Cargo s", Cargo.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	// m�todo que lista com crit�rios todos os registros
	public List<Cargo> listarPorCriterios(String nome) {
		String jpql = "select s from Cargo s where ";

		if (nome != null)
			jpql += "(s.nome like :pnome or s.codigo like :pcodigo or s.descricao like :pdescricao);";
		jpql += "1 = 1";

		TypedQuery<Cargo> query = em.createQuery(jpql, Cargo.class);

		if (nome != null)
			query.setParameter("pnome", '%' + nome + '%').setParameter("pcodigo", '%' + nome + '%')
					.setParameter("pdescricao", '%' + nome + '%');

		return query.getResultList();
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

}
