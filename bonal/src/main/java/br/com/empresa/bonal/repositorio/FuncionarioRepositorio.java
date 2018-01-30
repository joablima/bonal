package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.Cargo;
import br.com.empresa.bonal.entidades.Funcionario;

public class FuncionarioRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(Funcionario funcionario) {

		em.persist(funcionario);
	}

	// m�todo que atualiza um registro
	public void atualizar(Funcionario funcionario) {

		em.merge(funcionario);
	}

	// m�todo que remove um registro
	public void remover(Funcionario funcionario) {
		em.merge(funcionario);
	}

	// m�todo que recupera um objeto pelo id
	public Funcionario buscarPorId(Long id) {
		return em.find(Funcionario.class, id);
	}

	// m�todo que lista todos os registros
	public List<Funcionario> listarTodos() {
		try {
			return em.createQuery("select s from Funcionario s", Funcionario.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	// m�todo que lista com crit�rios todos os registros
	public List<Funcionario> listarPorCriterios(String nome) {
		String jpql = "select s from Funcionario s where ";

		if (nome != null)
			jpql += "(s.nome like :pnome or s.documento like :pdocumento or s.identificacao like :pidentificacao);";
		jpql += "1 = 1";

		TypedQuery<Funcionario> query = em.createQuery(jpql, Funcionario.class);

		if (nome != null)
			query.setParameter("pnome", '%' + nome + '%').setParameter("pdocumento", '%' + nome + '%')
					.setParameter("pidentificacao", '%' + nome + '%');

		return query.getResultList();
	}

	// método que verifica se elemento existe
	public Cargo getCargoPorCodigo(String codigo) {
		TypedQuery<Cargo> query = em
				.createQuery("select c from Cargo c where c.codigo = :pcodigo", Cargo.class)
				.setParameter("pcodigo", codigo.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// método que verifica se elemento existe
	public Funcionario getFuncionarioPorCpf(String documento) {
		TypedQuery<Funcionario> query = em
				.createQuery("select c from Funcionario c where c.documento = :pdocumento", Funcionario.class)
				.setParameter("pdocumento", documento.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
