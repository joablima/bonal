package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.Cliente;
import br.com.empresa.bonal.entidades.Cargo;
import br.com.empresa.bonal.util.JPAUtil;

public class ClienteRepositorio {

	final static Logger logger = Logger.getLogger(ClienteRepositorio.class);

	// m�todo que persiste um registro
	public void adicionar(Cliente cliente) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		
		
		em.persist(cliente);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que atualiza um registro
	public void atualizar(Cliente cliente) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();


		em.merge(cliente);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que remove um registro
	public void remover(Cliente cliente) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(cliente);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que recupera um objeto pelo id	
	public Cliente buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		Cliente funcionario = em.find(Cliente.class, id);
		em.close();
		return funcionario;
	}

	// m�todo que lista todos os registros
	public List<Cliente> listarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<Cliente> query = em.getCriteriaBuilder().createQuery(Cliente.class);
		query.select(query.from(Cliente.class));
		List<Cliente> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}	
	
	// m�todo que lista com crit�rios todos os registros
	public List<Cliente> listarPorCriterios(String nome) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "select c from Cliente c where ";

		if (nome != null)
			jpql += "(c.nome like :pnome or c.documento like :pdocumento or c.identificacao like :pidentificacao or c.email like :pemail) and ";
		
		
		jpql += "1 = 1";

		TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);

		if (nome != null) {
			query.setParameter("pnome", '%' + nome + '%');
			query.setParameter("pdocumento", '%' + nome + '%');
			query.setParameter("pidentificacao", '%' + nome + '%');
			query.setParameter("pemail", '%' + nome + '%');
		}
		
		

		logger.info(jpql);
		List<Cliente> list = query.getResultList();
		em.close();
		return list;
	}
}
