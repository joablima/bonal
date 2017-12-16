package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.empresa.bonal.entidades.Cargo;
import br.com.empresa.bonal.entidades.Funcionario;
import br.com.empresa.bonal.entidades.QualificacaoProfissional;
import br.com.empresa.bonal.util.JPAUtil;

public class FuncionarioRepositorio {

	// método que persiste um registro
	public void adicionar(Funcionario funcionario, Long cargoId, Long qualificacaoId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		QualificacaoProfissional qualificacao = em.find(QualificacaoProfissional.class, qualificacaoId);
		Cargo cargo = em.find(Cargo.class, cargoId);

		funcionario.setQualificacao(qualificacao);
		funcionario.setCargo(cargo);

		em.persist(funcionario);
		em.getTransaction().commit();
		em.close();
	}

	// método que atualiza um registro
	public void atualizar(Funcionario funcionario, Long cargoId, Long qualificacaoId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		QualificacaoProfissional qualificacao = em.find(QualificacaoProfissional.class, qualificacaoId);
		Cargo cargo = em.find(Cargo.class, cargoId);

		funcionario.setQualificacao(qualificacao);
		funcionario.setCargo(cargo);

		em.merge(funcionario);
		em.getTransaction().commit();
		em.close();
	}

	// método que remove um registro
	public void remover(Funcionario funcionario) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(funcionario));
		em.getTransaction().commit();
		em.close();
	}

	// método que recupera um objeto pelo id
	public Funcionario buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		Funcionario funcionario = em.find(Funcionario.class, id);
		em.close();
		return funcionario;
	}

	// método que lista todos os registros
	public List<Funcionario> listarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<Funcionario> query = em.getCriteriaBuilder().createQuery(Funcionario.class);
		query.select(query.from(Funcionario.class));
		List<Funcionario> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}

	// método que lista com critérios todos os registros
	public List<Funcionario> listarPorCriterios(String nome, Long cargoId, Long qualificacaoId) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "select f from Funcionario f where ";

		if (nome != null)
			jpql += "f.nome like :pnome and ";
		if (cargoId != null)
			jpql += "f.cargo.id = :pcargo and ";
		if (qualificacaoId != null)
			jpql += "f.qualificacao.id = :pqualificacao and ";
		jpql += "1 = 1";

		TypedQuery<Funcionario> query = em.createQuery(jpql, Funcionario.class);

		if (nome != null)
			query.setParameter("pnome", '%' + nome + '%');
		if (cargoId != null)
			query.setParameter("pcargo", cargoId);
		if (qualificacaoId != null)
			query.setParameter("pqualificacao", qualificacaoId);

		System.out.println(jpql);
		List<Funcionario> list = query.getResultList();
		em.close();
		return list;
	}
}
