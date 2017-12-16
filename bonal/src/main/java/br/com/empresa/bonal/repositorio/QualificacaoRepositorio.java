package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.empresa.bonal.entidades.QualificacaoProfissional;
import br.com.empresa.bonal.util.JPAUtil;

public class QualificacaoRepositorio {

	// método que persiste um registro
	public void adicionar(QualificacaoProfissional qualificacao) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(qualificacao);
		em.getTransaction().commit();
		em.close();
	}

	// método que atualiza um registro
	public void atualizar(QualificacaoProfissional qualificacao) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(qualificacao);
		em.getTransaction().commit();
		em.close();
	}

	// método que remove um registro
	public void remover(QualificacaoProfissional qualificacao) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(qualificacao));
		em.getTransaction().commit();
		em.close();
	}

	// método que recupera um objeto pelo id
	public QualificacaoProfissional buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		QualificacaoProfissional qualificacao = em.find(QualificacaoProfissional.class, id);
		em.close();
		return qualificacao;
	}

	// método que lista todos os registros
	public List<QualificacaoProfissional> listarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<QualificacaoProfissional> query = em.getCriteriaBuilder()
				.createQuery(QualificacaoProfissional.class);
		query.select(query.from(QualificacaoProfissional.class));
		List<QualificacaoProfissional> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}

	// método que lista com critérios todos os registros
	public List<QualificacaoProfissional> listarPorCriterios(String nome) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "select q from QualificacaoProfissional q where q.titulo like :ptitulo";

		TypedQuery<QualificacaoProfissional> query = em.createQuery(jpql, QualificacaoProfissional.class);

		query.setParameter("ptitulo", '%' + nome + '%');

		System.out.println(jpql);
		List<QualificacaoProfissional> list = query.getResultList();
		em.close();
		return list;
	}
}
