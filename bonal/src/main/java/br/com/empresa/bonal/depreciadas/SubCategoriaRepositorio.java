/*package br.com.empresa.bonal.depreciadas;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.SubCategoria;

public class SubCategoriaRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(SubCategoria subCategoria) {
		em.persist(subCategoria);
	}

	// m�todo que atualiza um registro
	public void atualizar(SubCategoria subCategoria) {

		em.merge(subCategoria);
	}

	// m�todo que remove um registro
	public void remover(SubCategoria subCategoria) {
		em.merge(subCategoria);
	}

	// m�todo que recupera um objeto pelo id
	public SubCategoria buscarPorId(Long id) {
		return em.find(SubCategoria.class, id);
	}

	// m�todo que lista todos os registros
	public List<SubCategoria> listarTodos() {
		try {
			return em.createQuery("select s from SubCategoria s", SubCategoria.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	// m�todo que lista todos os registros
	public List<SubCategoria> listarPorCategoria(Categoria c) {
		try {
			return em.createQuery("select s from SubCategoria s where categoria = "+c.getId()+";", SubCategoria.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	// m�todo que lista com crit�rios todos os registros
	public List<SubCategoria> listarPorCriterios(String nome) {
		String jpql = "select s from SubCategoria s where ";

		if (nome != null)
			jpql += "(s.nome like :pnome or s.codigo like :pcodigo or s.descricao like :pdescricao);";
		jpql += "1 = 1";

		TypedQuery<SubCategoria> query = em.createQuery(jpql, SubCategoria.class);

		if (nome != null)
			query.setParameter("pnome", '%' + nome + '%').setParameter("pcodigo", '%' + nome + '%')
					.setParameter("pdescricao", '%' + nome + '%');

		return query.getResultList();
	}

	// método que verifica se elemento existe
	public SubCategoria codigoExiste(SubCategoria subCategoria) {
		TypedQuery<SubCategoria> query = em
				.createQuery("select s from SubCategoria s where s.codigo = :pcodigo", SubCategoria.class)
				.setParameter("pcodigo", subCategoria.getCodigo());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// método que verifica se elemento existe
	public Categoria getCategoriaPorCodigo(String codigo) {
		TypedQuery<Categoria> query = em
				.createQuery("select c from Categoria c where c.codigo = :pcodigo", Categoria.class)
				.setParameter("pcodigo", codigo.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
*/