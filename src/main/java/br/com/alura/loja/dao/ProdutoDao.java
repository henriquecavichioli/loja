package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.alura.loja.model.Produto;

public class ProdutoDao {

	private EntityManager em;

	public ProdutoDao(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Produto produto) {
		
		this.em.persist(produto);
		 
	}

	public void atualizar(Produto produto) {
		
		this.em.merge(produto);
		
	}
	
	public void remover(Produto produto) {
		produto = em.merge(produto);
		this.em.remove(produto);
		
	}
	
	public Produto buscaPorId(Long id) {
		Produto produto = em.find(Produto.class, id);
		return produto;
	}
	
	public List<Produto> buscarTodos() {
		String jpql = "SELECT p FROM Produto p ";
		return em.createQuery(jpql, Produto.class).getResultList();
		
	}
	
	public List<Produto> buscarPorNome(String nome) {
		String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome ";
		return em.createQuery(jpql, Produto.class)
				.setParameter("nome", nome)
				.getResultList();
	}
	
    public List<Produto> buscarPorNomeDaCategoria(String nomeCategoria) {
        String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = :nomeCategoria";
        return em.createQuery(jpql, Produto.class)
                .setParameter("nomeCategoria", nomeCategoria)
                .getResultList();
    }
    
	public BigDecimal buscarPrecoDoProdutoComNome(String nome) {
		String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :nome ";
		return em.createQuery(jpql, BigDecimal.class)
				.setParameter("nome", nome)
				.getSingleResult();
	}
	
	public List<Produto> buscarPorParametrosCriteria(String nome,
			BigDecimal preco, LocalDate dataCadastro){
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		Root<Produto> from = query.from(Produto.class);
		
		Predicate filtros = builder.and();
		if (nome!= null && !nome.trim().isEmpty()) {
			filtros = builder.and(filtros, builder.equal(from.get("nome"), nome));
			
		}
		if (preco!= null) {
			filtros = builder.and(filtros, builder.equal(from.get("preco"), preco));
			
		}
		if (dataCadastro!= null) {
			filtros = builder.and(filtros, builder.equal(from.get("dataCadastro"), dataCadastro));
			
		}
		
		query.where(filtros);
		
		return em.createQuery(query).getResultList();
		
	}

}
