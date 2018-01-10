package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.Fornecedor;
import br.com.empresa.bonal.util.JPAUtil;

public class FornecedorRepositorio {

	final static Logger logger = Logger.getLogger(FornecedorRepositorio.class);

	// m�todo que persiste um registro
	public void adicionar(Fornecedor fornecedor) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		
		
		em.persist(fornecedor);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que atualiza um registro
	public void atualizar(Fornecedor fornecedor) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();


		em.merge(fornecedor);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que remove um registro
	public void remover(Fornecedor cliente) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(cliente);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que recupera um objeto pelo id	
	public Fornecedor buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		Fornecedor funcionario = em.find(Fornecedor.class, id);
		em.close();
		return funcionario;
	}

	// m�todo que lista todos os registros
	public List<Fornecedor> listarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<Fornecedor> query = em.getCriteriaBuilder().createQuery(Fornecedor.class);
		query.select(query.from(Fornecedor.class));
		List<Fornecedor> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}	
	
	// m�todo que lista com crit�rios todos os registros
	public List<Fornecedor> listarPorCriterios(String nome) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "select f from Fornecedor f where ";

		if (nome != null)
			jpql += "(f.nome like :pnome or f.documento like :pdocumento or f.identificacao like :pidentificacao or f.email like :pemail) and ";
		
		
		jpql += "1 = 1";

		TypedQuery<Fornecedor> query = em.createQuery(jpql, Fornecedor.class);

		if (nome != null) {
			query.setParameter("pnome", '%' + nome + '%');
			query.setParameter("pdocumento", '%' + nome + '%');
			query.setParameter("pidentificacao", '%' + nome + '%');
			query.setParameter("pemail", '%' + nome + '%');
		}
		
		

		logger.info(jpql);
		List<Fornecedor> list = query.getResultList();
		em.close();
		return list;
	}
}
