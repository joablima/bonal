package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.Endereco;
import br.com.empresa.bonal.util.JPAUtil;

public class EnderecoRepositorio {

	EntityManager em;

	public EnderecoRepositorio() {
		em = new JPAUtil().getEntityManager();
	}

	// Método que adiciona um cargo ao banco de dados
	public void adicionar(Endereco endereco) {
		em.getTransaction().begin();
		em.merge(endereco);
		em.getTransaction().commit();
	}

	// Método que atualiza os registros
	public void atualizar(Endereco endereco) {
		em.getTransaction().begin();
		em.merge(endereco);
		em.getTransaction().commit();
	}

	// Método que remove os registros
	public void remover(Endereco endereco) {
		em.getTransaction().begin();
		em.remove(em.merge(endereco));
		em.getTransaction().commit();
		em.close();
	}

	// Método que recupera um objeto pelo id
	public Endereco getEndereco(Long id) {
		return em.find(Endereco.class, id);
	}

	// funcao que encerra o entitymanager e o factory
	// public void encerrar() {
	// em.close();
	// emf.close();
	// }

	public List<Endereco> listarEnderecos(String nome) {
		String jpql = "select e from Endereco e where e.logradouro like :plogradouro or e.cep like :pcep";

		TypedQuery<Endereco> query = em.createQuery(jpql, Endereco.class);

		query.setParameter("plogradouro", '%' + nome + '%');
		query.setParameter("pcep", '%' + nome + '%');
//		System.out.println(jpql);
		return query.getResultList();
	}
}
