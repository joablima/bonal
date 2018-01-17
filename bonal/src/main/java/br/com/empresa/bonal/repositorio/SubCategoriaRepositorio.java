package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.Logger;

import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.SubCategoria;

public class SubCategoriaRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;
	
	@Inject
	private Logger logger;

	// m�todo que persiste um registro
	public void adicionar(SubCategoria subCategoria) {
		logger.info("SubCategoria sendo adicionada, irá ser persistida nesse momento");
		
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
		SubCategoria subCategoria = em.find(SubCategoria.class, id);
		return subCategoria;
	}

	// m�todo que lista todos os registros
	public List<SubCategoria> listarTodos() {
		try {
			return em.createQuery("select s from SubCategoria s", SubCategoria.class).getResultList();
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

		if (nome != null) {
			query.setParameter("pnome", '%' + nome + '%');
			query.setParameter("pcodigo", '%' + nome + '%');
			query.setParameter("pdescricao", '%' + nome + '%');
		}

		logger.info(jpql);
		List<SubCategoria> list = query.getResultList();
		return list;
	}

	// método que verifica se elemento existe
	public SubCategoria codigoExiste(SubCategoria subCategoria) {
		TypedQuery<SubCategoria> query = em.createQuery("select s from SubCategoria s where s.codigo = :pcodigo",
				SubCategoria.class);
		query.setParameter("pcodigo", subCategoria.getCodigo());

		try {
			SubCategoria novaSubCategoria = query.getSingleResult();
			return novaSubCategoria;
		} catch (Exception e) {
			return null;
		}
	}
	

	// método que verifica se elemento existe
	public Categoria getCategoriaPorCodigo(String codigo) {
		TypedQuery<Categoria> query = em.createQuery("select c from Categoria c where c.codigo = :pcodigo",
				Categoria.class);
		query.setParameter("pcodigo", codigo);

		try {
			Categoria categoria = query.getSingleResult();
			return categoria;
		} catch (Exception e) {
			return null;
		}
	}
}
