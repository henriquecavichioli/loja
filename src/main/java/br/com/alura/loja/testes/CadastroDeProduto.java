package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.model.Categoria;
import br.com.alura.loja.model.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroDeProduto {

	public static void main(String[] args) {

		cadastrarProduto();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		
		Produto p = produtoDao.buscaPorId(1l);
		System.out.println(p.getPreco());

		String nome = "Xiaomi Redmi";
		List<Produto> porNome = produtoDao.buscarPorNome(nome);
		porNome.forEach(p2 -> System.out.println(p2.getNome()));
		
		String nomeCategoria = "CELULARES";
		List<Produto> porNomeDaCategoria = produtoDao.buscarPorNomeDaCategoria(nomeCategoria);
		porNomeDaCategoria.forEach(p3 -> System.out.println(p3.getNome()));
		
		BigDecimal precoDoProduto = produtoDao.buscarPrecoDoProdutoComNome(nome);
		System.out.println("Preco do Produto: " + precoDoProduto);
		
	}

private static void cadastrarProduto() {
	Categoria celulares = new Categoria("CELULARES");
	// Tabela -> categorias
	// id -> 1
	// nome -> 'CELULARES'

	Produto produto = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);
	// Tabela -> produtos
	// id -> 1
	// nome -> 'Xiaomi Redmi'
	// desc -> 'Muito legal'
	// preco -> 800
	// categ -> idCateg = 1

	EntityManager em = JPAUtil.getEntityManager();
	ProdutoDao produtoDao = new ProdutoDao(em);
	CategoriaDao categoriaDao = new CategoriaDao(em);

	em.getTransaction().begin();
	categoriaDao.cadastrar(celulares);
	produtoDao.cadastrar(produto);
	em.getTransaction().commit();
	em.close();
}

}
