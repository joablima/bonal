package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.Categoria;

public class CategoriaRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(CategoriaRepositorio.class);

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(Categoria categoria) {
		logger.info("Categoria sendo adicionada, irá ser persistida nesse momento");
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
		Categoria categoria = em.find(Categoria.class, id);
		return categoria;
	}

	// m�todo que lista todos os registros
	public List<Categoria> listarTodos() {
		try {
			return em.createQuery("select c from Categoria c", Categoria.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	// m�todo que lista com crit�rios todos os registros
	public List<Categoria> listarPorCriterios(String nome) {
		String jpql = "select c from Categoria c where ";

		if (nome != null)
			jpql += "(c.nome like :pnome or c.codigo like :pcodigo or c.descricao like :pdescricao);";
		jpql += "1 = 1";

		TypedQuery<Categoria> query = em.createQuery(jpql, Categoria.class);

		if (nome != null) {
			query.setParameter("pnome", '%' + nome + '%');
			query.setParameter("pcodigo", '%' + nome + '%');
			query.setParameter("pdescricao", '%' + nome + '%');
		}

		logger.info(jpql);
		List<Categoria> list = query.getResultList();
		return list;
	}

	// método que verifica se elemento existe
	public Categoria codigoExiste(Categoria categoria) {
		TypedQuery<Categoria> query = em.createQuery("select c from Categoria c where c.codigo = :pcodigo",
				Categoria.class);
		query.setParameter("pcodigo", categoria.getCodigo());

		try {
			Categoria novaCategoria = query.getSingleResult();
			return novaCategoria;
		} catch (Exception e) {
			return null;
		}
	}
}
