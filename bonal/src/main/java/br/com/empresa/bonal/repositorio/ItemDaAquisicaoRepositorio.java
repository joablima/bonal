package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.ItemDaAquisicao;
import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.entidades.Produto;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.entidades.Aquisicao;
import br.com.empresa.bonal.entidades.Fornecedor;
import br.com.empresa.bonal.entidades.Funcionario;
import br.com.empresa.bonal.util.logging.Logging;

public class ItemDaAquisicaoRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(ItemDaAquisicao itemDaAquisicao) {

		em.persist(itemDaAquisicao);
	}

	// m�todo que atualiza um registro
	public void atualizar(ItemDaAquisicao itemDaAquisicao) {

		em.merge(itemDaAquisicao);
	}

	// m�todo que atualiza um registro
	public void atualizar(Aquisicao aquisicao) {

		em.merge(aquisicao);
	}

	// m�todo que remove um registro
	public void remover(ItemDaAquisicao itemDaAquisicao) {
		em.merge(itemDaAquisicao);
	}

	// m�todo que recupera um objeto pelo id
	public ItemDaAquisicao getItemDaAquisicaoPorId(Long id) {
		return em.find(ItemDaAquisicao.class, id);
	}

	// m�todo que recupera um objeto pelo id
	public Produto getProdutoPorId(Long id) {
		return em.find(Produto.class, id);
	}

	// m�todo que recupera um objeto pelo id
	public Aquisicao getAquisicaoPorId(Long id) {
		return em.find(Aquisicao.class, id);
	}

	// m�todo que lista todos os registros
	public List<ItemDaAquisicao> listarTodos() {
		try {
			return em.createQuery("select s from ItemDaAquisicao s", ItemDaAquisicao.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Logging
	// m�todo que lista todos os registros
	public List<ItemDaAquisicao> listarTodosPorAquisicao(Long id) {
		String jpql = "select c from ItemDaAquisicao c where aquisicao = " + id;

		TypedQuery<ItemDaAquisicao> query = em.createQuery(jpql, ItemDaAquisicao.class);

		return query.getResultList();
	}

	// método que verifica se elemento existe
	public UnidadeDeMedida getUnidadeDeMedidaPorSigla(String sigla) {
		TypedQuery<UnidadeDeMedida> query = em
				.createQuery("select c from UnidadeDeMedida c where c.sigla = :psigla", UnidadeDeMedida.class)
				.setParameter("psigla", sigla.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
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
