package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.Cargo;
import br.com.empresa.bonal.entidades.Conta;
import br.com.empresa.bonal.entidades.Fornecedor;
import br.com.empresa.bonal.entidades.Funcionario;
import br.com.empresa.bonal.util.logging.Logging;

public class ContaRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que recupera um objeto pelo id
	public Conta buscarPorId(Long id) {
		return em.find(Conta.class, id);
	}

	// m�todo que atualiza um registro
	public void atualizar(Conta conta) {

		em.merge(conta);
	}

	// m�todo que lista todos os registros
	public List<Conta> listarTodos() {
		try {
			return em.createQuery("select c from Conta c", Conta.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

}
