package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.empresa.bonal.entidades.BemDeConsumo;
import br.com.empresa.bonal.entidades.BemPermanente;
import br.com.empresa.bonal.entidades.Conta;
import br.com.empresa.bonal.entidades.Fornecedor;
import br.com.empresa.bonal.entidades.Funcionario;
import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.entidades.PedidoCompra;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.util.logging.Logging;

public class PedidoCompraRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(PedidoCompra pedidoCompra) {
		em.persist(pedidoCompra);
	}

	// m�todo que persiste um registro
	public Long adicionarPedidoComRetorno(PedidoCompra pedidoCompra) {
		em.persist(pedidoCompra);

		return pedidoCompra.getId();
	}

	@Logging
	// m�todo que persiste um registro
	public Long adicionarContaComRetorno(Conta conta) {
		em.persist(conta);

		return conta.getId();
	}

	// m�todo que atualiza um registro
	public void atualizar(PedidoCompra pedidoCompra) {
		em.merge(pedidoCompra);
	}

	// m�todo que atualiza um registro
	public void atualizarBemDeConsumo(BemDeConsumo bemDeConsumo) {
		em.merge(bemDeConsumo);
	}

	// m�todo que atualiza um registro
	public void atualizarBemPermanente(BemPermanente bemPermanente) {
		em.merge(bemPermanente);
	}

	// m�todo que remove um registro
	public void remover(PedidoCompra pedidoCompra) {
		em.merge(pedidoCompra);
	}

	// m�todo que recupera um objeto pelo id
	public PedidoCompra buscarPorId(Long id) {
		return em.find(PedidoCompra.class, id);
	}

	// m�todo que recupera um objeto pelo id
	public Conta buscarContaPorId(Long id) {
		return em.find(Conta.class, id);
	}

	// m�todo que lista todos os registros
	public List<PedidoCompra> listarTodos() {
		try {
			return em.createQuery("select o from PedidoCompra o", PedidoCompra.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	// método que lista todos os registros com paginação
	public List<PedidoCompra> listarTodosPaginada(int firstResult, int maxResults) {
		CriteriaQuery<PedidoCompra> query = em.getCriteriaBuilder().createQuery(PedidoCompra.class);
		query.select(query.from(PedidoCompra.class));
		List<PedidoCompra> list = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
		return list;
	}

	// método que verifica se elemento existe
	public ItemDeProducao getItemDeProducaoPorCodigo(String codigo) {
		TypedQuery<ItemDeProducao> query = em
				.createQuery("select c from ItemDeProducao c where c.codigo = :pcodigo", ItemDeProducao.class)
				.setParameter("pcodigo", codigo.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// método que verifica se elemento existe
	public BemDeConsumo getBemDeConsumoPorCodigo(String codigo) {
		TypedQuery<BemDeConsumo> query = em
				.createQuery("select c from BemDeConsumo c where c.codigo = :pcodigo", BemDeConsumo.class)
				.setParameter("pcodigo", codigo.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// método que verifica se elemento existe
	public BemPermanente getBemPermanentePorCodigo(String codigo) {
		TypedQuery<BemPermanente> query = em
				.createQuery("select c from BemPermanente c where c.codigo = :pcodigo", BemPermanente.class)
				.setParameter("pcodigo", codigo.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// método que verifica se elemento existe
	public Fornecedor getFornecedorPorDocumento(String documento) {
		TypedQuery<Fornecedor> query = em
				.createQuery("select c from Fornecedor c where c.documento = :pdocumento", Fornecedor.class)
				.setParameter("pdocumento", documento.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// método que verifica se elemento existe
	public UnidadeDeMedida getUnidadeDeMedidaPorSigla(String sigla) {
		TypedQuery<UnidadeDeMedida> query = em
				.createQuery("select u from UnidadeDeMedida u where u.sigla = :sigla", UnidadeDeMedida.class)
				.setParameter("sigla", sigla.toUpperCase());

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
