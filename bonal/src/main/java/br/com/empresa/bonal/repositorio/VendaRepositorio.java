package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.Cliente;
import br.com.empresa.bonal.entidades.Funcionario;
import br.com.empresa.bonal.entidades.Venda;
import br.com.empresa.bonal.util.logging.Logging;

public class VendaRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(Venda venda) {
		em.persist(venda);
	}

	@Logging
	// m�todo que persiste um registro
	public Long adicionarComRetorno(Venda venda) {
		em.persist(venda);
		return venda.getId();
	}

	// m�todo que atualiza um registro
	public void atualizar(Venda venda) {
		em.merge(venda);
	}

	// m�todo que remove um registro
	public void remover(Venda venda) {
		em.merge(venda);
	}

	// m�todo que recupera um objeto pelo id
	public Venda buscarPorId(Long id) {
		return em.find(Venda.class, id);
	}

	// m�todo que lista todos os registros
	public List<Venda> listarTodos() {
		try {
			return em.createQuery("select c from Venda c", Venda.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	// m�todo que lista com crit�rios todos os registros
	public List<Venda> listarPorCriterios(String nome) {
		String jpql = "select c from Venda c where ";

		if (nome != null)
			jpql += "(c.nome like :pnome or c.codigo like :pcodigo or c.descricao like :pdescricao);";
		jpql += "1 = 1";

		TypedQuery<Venda> query = em.createQuery(jpql, Venda.class);

		if (nome != null)
			query.setParameter("pnome", '%' + nome + '%').setParameter("pcodigo", '%' + nome + '%')
					.setParameter("pdescricao", '%' + nome + '%');

		return query.getResultList();
	}

	// método que verifica se elemento existe
	public Cliente getClientePorDocumento(String documento) {
		TypedQuery<Cliente> query = em
				.createQuery("select c from Cliente c where c.documento = :pdocumento", Cliente.class)
				.setParameter("pdocumento", documento.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// método que verifica se elemento existe
	public Funcionario getFuncionarioPorDocumento(String documento) {
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
