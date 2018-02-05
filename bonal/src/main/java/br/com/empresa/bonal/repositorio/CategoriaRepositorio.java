package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.util.logging.Logging;

public class CategoriaRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(Categoria categoria) {
		em.persist(categoria);
	}

	// m�todo que atualiza um registro
	public void atualizar(Categoria categoria) {
		em.merge(categoria);
	}

	// m�todo que remove um registro
	public void remover(Categoria categoria) {
		em.merge(categoria);
	}

	// m�todo que recupera um objeto pelo id
	public Categoria buscarPorId(Long id) {
		return em.find(Categoria.class, id);
	}

	// m�todo que lista todos os registros
	public List<Categoria> listarTodos() {
		try {
			return em.createQuery("select c from Categoria c", Categoria.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	// m�todo que lista todos os registros
	public List<Categoria> listarPorTipo(String tipo) {
		try {
			TypedQuery<Categoria> query = em.createQuery("select from categoria where tipo = '"+tipo+"';", Categoria.class);
			System.out.println(query.getResultList().size());
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Logging
	// m�todo que lista com crit�rios todos os registros
	public List<Categoria> listarPorCriterios(String nome) {
		String jpql = "select c from Categoria c where ";

		if (nome != null)
			jpql += "(c.tipo like :ptipo);";
		jpql += "1 = 1";

		TypedQuery<Categoria> query = em.createQuery(jpql, Categoria.class);

		if (nome != null)
			query.setParameter("ptipo", '%' + nome + '%');
		System.out.println(query.getResultList().size());
		return query.getResultList();
	}

	// método que verifica se elemento existe
	public Categoria codigoExiste(Categoria categoria) {
		TypedQuery<Categoria> query = em
				.createQuery("select c from Categoria c where c.codigo = :pcodigo", Categoria.class)
				.setParameter("pcodigo", categoria.getCodigo());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
