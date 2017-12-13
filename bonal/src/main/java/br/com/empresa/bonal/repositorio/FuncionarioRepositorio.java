package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.Cargo;
import br.com.empresa.bonal.entidades.Funcionario;
import br.com.empresa.bonal.entidades.QualificacaoProfissional;
import br.com.empresa.bonal.util.JPAUtil;

public class FuncionarioRepositorio {

	EntityManager em;

	public FuncionarioRepositorio() {
		em = new JPAUtil().getEntityManager();
	}

	// funcao que adiciona uma bem ao banco de dados
	public void adicionar(Funcionario funcionario, Long cargoId, Long qualificacaoId) {
		em.getTransaction().begin();

		QualificacaoProfissional qualificacao = em.find(QualificacaoProfissional.class, qualificacaoId);
		Cargo cargo = em.find(Cargo.class, cargoId);

		funcionario.setQualificacao(qualificacao);
		funcionario.setCargo(cargo);

		em.persist(funcionario);
		em.getTransaction().commit();
		em.close();
	}

	// funcao que atualiza os registros de uma bem
	public void atualizar(Funcionario funcionario, Long cargoId, Long qualificacaoId) {
		em.getTransaction().begin();

		QualificacaoProfissional qualificacao = em.find(QualificacaoProfissional.class, qualificacaoId);
		Cargo cargo = em.find(Cargo.class, cargoId);

		funcionario.setQualificacao(qualificacao);
		funcionario.setCargo(cargo);

		em.merge(funcionario);
		em.getTransaction().commit();
		em.close();
	}

	// funcao que remove os registros de uma bem
	public void remover(Funcionario funcionario) {
		em.getTransaction().begin();
		em.remove(em.merge(funcionario));
		em.getTransaction().commit();
		em.close();
	}

	// funcao que recupera uma bem de acordo com id
	public Funcionario getFuncionario(Long id) {
		return em.find(Funcionario.class, id);
	}

	// lista bems com base em uma chave de pesquisa
	public List<Funcionario> listarFuncionarios(String nome, Long cargoId, Long qualificacaoId) {
		String jpql = "select f from Funcionario f where ";

		if (nome != null)
			jpql += "(f.nome like :pnome or f.formacao like :pformacao) and ";
		if (cargoId != null)
			jpql += "f.cargo.id = :pcargo and ";
		if (qualificacaoId != null)
			jpql += "f.qualificacao.id = :pqualificacao and ";
		jpql += "1 = 1";

		TypedQuery<Funcionario> query = em.createQuery(jpql, Funcionario.class);

		if (nome != null) {
			query.setParameter("pnome", '%' + nome + '%');
			query.setParameter("pformacao", '%' + nome + '%');
		}
		if (cargoId != null)
			query.setParameter("pcargo", cargoId);
		if (qualificacaoId != null)
			query.setParameter("pqualificacao", qualificacaoId);

		System.out.println(jpql);
		return query.getResultList();
	}
}
