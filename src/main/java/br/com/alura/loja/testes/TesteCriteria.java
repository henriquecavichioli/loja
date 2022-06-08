package br.com.alura.loja.testes;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.model.Categoria;
import br.com.alura.loja.model.Cliente;
import br.com.alura.loja.model.ItemPedido;
import br.com.alura.loja.model.Pedido;
import br.com.alura.loja.model.Produto;
import br.com.alura.loja.util.JPAUtil;

public class TesteCriteria {

	public static void main(String[] args) {

		popularBancoDeDados();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		produtoDao.buscarPorParametrosCriteria(null, null, null);
	}

	private static void popularBancoDeDados() {
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

		Cliente cliente = new Cliente("Rodrigo", "123456");
		// Tabela -> clientes
		// id -> 1
		// nome -> 'Rodrigo'
		// cpf -> '123456'

		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, pedido, produto));
		// Tabela -> pedidos
		//

		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);
		ClienteDao clienteDao = new ClienteDao(em);
		PedidoDao pedidoDao = new PedidoDao(em);

		em.getTransaction().begin();
		categoriaDao.cadastrar(celulares);
		produtoDao.cadastrar(produto);
		clienteDao.cadastrar(cliente);
		pedidoDao.cadastrar(pedido);
		em.getTransaction().commit();
		em.close();
	}
}
