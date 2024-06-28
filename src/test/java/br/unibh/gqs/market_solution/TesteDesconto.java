package br.unibh.gqs.market_solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.unibh.gqs.market_solution.model.CarrinhoCompra;
import br.unibh.gqs.market_solution.model.Cliente;
import br.unibh.gqs.market_solution.model.ItemCompra;
import br.unibh.gqs.market_solution.model.Produto;

import br.unibh.gqs.market_solution.persistence.ProdutoRepository;

@SpringBootTest
public class TesteDesconto {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    public void test01() {
        Produto p = produtoRepository.findByDescricaoStartsWith("Enxaguante").get(0);
        ItemCompra i = new ItemCompra(p, 2);
        CarrinhoCompra c = new CarrinhoCompra();
        Cliente cliente = new Cliente("11122233344", "amanda", "bronze");
        c.setCliente(cliente);
        c.addItemCarrinho(i);

        assertEquals(59.98, c.getTotalComDesconto().doubleValue());
    }

    @Test
    public void test02() {
        Produto p = produtoRepository.findByDescricaoStartsWith("Enxaguante").get(0);
        ItemCompra i = new ItemCompra(p, 2);
        CarrinhoCompra c = new CarrinhoCompra();
        Cliente cliente = new Cliente("11122233344", "amanda", "prata");
        c.setCliente(cliente);
        c.addItemCarrinho(i);

        assertEquals(58.1806, c.getTotalComDesconto().doubleValue());
    }

    @Test
    public void test03() {
        Produto p = produtoRepository.findByDescricaoStartsWith("Enxaguante").get(0);
        ItemCompra i = new ItemCompra(p, 2);
        CarrinhoCompra c = new CarrinhoCompra();
        Cliente cliente = new Cliente("11122233344", "amanda", "ouro");
        c.setCliente(cliente);
        c.addItemCarrinho(i);

        assertEquals(56.981, c.getTotalComDesconto().doubleValue());
    }

    @Test
    public void test04() {
        Produto p = produtoRepository.findByDescricaoStartsWith("Enxaguante").get(0);
        ItemCompra i = new ItemCompra(p, 4);
        CarrinhoCompra c = new CarrinhoCompra();
        Cliente cliente = new Cliente("11122233344", "amanda", "bronze");
        c.setCliente(cliente);
        c.addItemCarrinho(i);

        assertEquals(116.3612, c.getTotalComDesconto().doubleValue());
    }

    @Test
    public void test05() {
        Produto p = produtoRepository.findByDescricaoStartsWith("Enxaguante").get(0);
        ItemCompra i = new ItemCompra(p, 4);
        CarrinhoCompra c = new CarrinhoCompra();
        Cliente cliente = new Cliente("11122233344", "amanda", "prata");
        c.setCliente(cliente);
        c.addItemCarrinho(i);

        assertEquals(113.962, c.getTotalComDesconto().doubleValue());
    }

    @Test
    public void test06() {
        Produto p = produtoRepository.findByDescricaoStartsWith("Enxaguante").get(0);
        ItemCompra i = new ItemCompra(p, 4);
        CarrinhoCompra c = new CarrinhoCompra();
        Cliente cliente = new Cliente("11122233344", "amanda", "ouro");
        c.setCliente(cliente);
        c.addItemCarrinho(i);

        assertEquals(110.3632, c.getTotalComDesconto().doubleValue());
    }
}
