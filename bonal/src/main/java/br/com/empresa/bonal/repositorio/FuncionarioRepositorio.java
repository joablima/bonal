package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.Funcionario;
import br.com.empresa.bonal.entidades.Cargo;
import br.com.empresa.bonal.util.JPAUtil;

public class FuncionarioRepositorio {

	final static Logger logger = Logger.getLogger(FuncionarioRepositorio.class);

	// m�todo que persiste um registro
	public void adicionar(Funcionario funcionario,Long cargoId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		
		Cargo cargo = em.find(Cargo.class, cargoId);

		funcionario.setCargo(cargo);
		
		em.persist(funcionario);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que atualiza um registro
	public void atualizar(Funcionario funcionario, Long cargoId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		Cargo cargo = em.find(Cargo.class, cargoId);

		funcionario.setCargo(cargo);

		em.merge(funcionario);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que remove um registro
	public void remover(Funcionario funcionario) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(funcionario);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que recupera um objeto pelo id	
	public Funcionario buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		Funcionario funcionario = em.find(Funcionario.class, id);
		em.close();
		return funcionario;
	}

	// m�todo que lista todos os registros
	public List<Funcionario> listarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<Funcionario> query = em.getCriteriaBuilder().createQuery(Funcionario.class);
		query.select(query.from(Funcionario.class));
		List<Funcionario> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}	
	
	// m�todo que lista com crit�rios todos os registros
	public List<Funcionario> listarPorCriterios(String nome, Long cargoId) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "select f from Funcionario f where ";

		if (nome != null)
			jpql += "(f.nome like :pnome or f.documento like :pdocumento or f.identificacao like :pidentificacao or f.email like :pemail) and ";
		if (cargoId != null)
			jpql += "p.categoria.id = :pcategoria and ";
		
		jpql += "1 = 1";

		TypedQuery<Funcionario> query = em.createQuery(jpql, Funcionario.class);

		if (nome != null) {
			query.setParameter("pnome", '%' + nome + '%');
			query.setParameter("pdocumento", '%' + nome + '%');
			query.setParameter("pidentificacao", '%' + nome + '%');
			query.setParameter("pemail", '%' + nome + '%');
		}
		if (cargoId != null)
			query.setParameter("pcargo", cargoId);	
		

		logger.info(jpql);
		List<Funcionario> list = query.getResultList();
		em.close();
		return list;
	}
}
