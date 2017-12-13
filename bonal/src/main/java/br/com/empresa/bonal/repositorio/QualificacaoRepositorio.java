package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.QualificacaoProfissional;
import br.com.empresa.bonal.util.JPAUtil;

public class QualificacaoRepositorio {

	EntityManager em;

	public QualificacaoRepositorio() {
		em = new JPAUtil().getEntityManager();
	}

	// funcao que adiciona um cargo ao banco de dados
	public void adicionar(QualificacaoProfissional qualificacao) {
		em.getTransaction().begin();
		em.persist(qualificacao);
		em.getTransaction().commit();
		em.close();
	}

	// funcao que atualiza os registros de um cargo
	public void atualizar(QualificacaoProfissional qualificacao) {
		em.getTransaction().begin();
		em.merge(qualificacao);
		em.getTransaction().commit();
		em.close();
	}

	// funcao que remove os registros de um cargo
	public void remover(QualificacaoProfissional qualificacao) {
		em.getTransaction().begin();
		em.remove(em.merge(qualificacao));
		em.getTransaction().commit();
		em.close();
	}

	// funcao que recupera um cargo de acordo com id
	public QualificacaoProfissional getQualificacao(Long id) {
		return em.find(QualificacaoProfissional.class, id);
	}

	public List<QualificacaoProfissional> listarQualificacoes(String nome) {
		String jpql = "select q from QualificacaoProfissional q where q.titulo like :ptitulo";

		TypedQuery<QualificacaoProfissional> query = em.createQuery(jpql, QualificacaoProfissional.class);

		query.setParameter("ptitulo", '%' + nome + '%');

		System.out.println(jpql);
		return query.getResultList();
	}
}
