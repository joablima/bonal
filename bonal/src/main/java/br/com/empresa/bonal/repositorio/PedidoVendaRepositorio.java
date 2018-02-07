package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.empresa.bonal.entidades.Cliente;
import br.com.empresa.bonal.entidades.PedidoVenda;
import br.com.empresa.bonal.entidades.Produto;

public class PedidoVendaRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(PedidoVenda pedidoVenda) {
		em.persist(pedidoVenda);
	}

	// m�todo que atualiza um registro
	public void atualizar(PedidoVenda pedidoVenda) {
		em.merge(pedidoVenda);
	}

	// m�todo que remove um registro
	public void remover(PedidoVenda pedidoVenda) {
		em.merge(pedidoVenda);
	}

	// m�todo que recupera um objeto pelo id
	public PedidoVenda buscarPorId(Long id) {
		return em.find(PedidoVenda.class, id);
	}

	// m�todo que lista todos os registros
	public List<PedidoVenda> listarTodos() {
		try {
			return em.createQuery("select o from PedidoVenda o", PedidoVenda.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	// método que lista todos os registros com paginação
	public List<PedidoVenda> listarTodosPaginada(int firstResult, int maxResults) {
		CriteriaQuery<PedidoVenda> query = em.getCriteriaBuilder().createQuery(PedidoVenda.class);
		query.select(query.from(PedidoVenda.class));
		List<PedidoVenda> list = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
		return list;
	}
	

	// método que verifica se elemento existe
	public Produto getProdutoPorCodigo(String codigo) {
		TypedQuery<Produto> query = em.createQuery("select c from Produto c where c.codigo = :pcodigo", Produto.class)
				.setParameter("pcodigo", codigo.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
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

}
