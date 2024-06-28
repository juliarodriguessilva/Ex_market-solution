package br.unibh.gqs.market_solution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import br.unibh.gqs.market_solution.model.CarrinhoCompra;
import br.unibh.gqs.market_solution.model.Cliente;
import br.unibh.gqs.market_solution.model.ItemCompra;
import br.unibh.gqs.market_solution.model.Produto;
import br.unibh.gqs.market_solution.persistence.CarrinhoCompraRepository;
import br.unibh.gqs.market_solution.persistence.ClienteRepository;
import br.unibh.gqs.market_solution.persistence.ItemCompraRepository;
import br.unibh.gqs.market_solution.persistence.ProdutoRepository;

@DataJpaTest(showSql = true)
public class TesteJPA {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemCompraRepository itemCompraRepository;

    @Autowired
    private CarrinhoCompraRepository carrinhoCompraRepository;

  @Test
  public void test01() {
    Cliente cliente = new Cliente("44444444444", "Josué da Silva", "Prata"); 
    Cliente clienteSalvo = clienteRepository.save(cliente);
    assertNotNull(clienteSalvo.getId());

    Optional<Cliente> ret = clienteRepository.findById(clienteSalvo.getId());
    assertTrue(ret.isPresent());
    if (ret.isPresent()){
        assertEquals(ret.get().getId(), clienteSalvo.getId());
    }
  }

  @Test
  public void test02() {
    Produto produto = new Produto("Shampoo XPTO", 
        BigDecimal.valueOf(12.99), 
        new GregorianCalendar(2024, 7, 30).getTime());
    Produto produtoSalvo = produtoRepository.save(produto);

    Produto prod = produtoRepository.findById(produtoSalvo.getId()).orElseThrow();
    assertEquals("Shampoo XPTO", prod.getDescricao());
    assertEquals(BigDecimal.valueOf(12.99), prod.getPreco());
    assertEquals(new GregorianCalendar(2024, 7, 30).getTime(), 
        prod.getDtValidade());
  }

  @Test
  public void test03() {
    List<Produto> list = produtoRepository.findByDescricaoStartsWith("Enxaguante");
    assertEquals(1, list.size());
  }

  @Test
  public void test04() {
    Cliente cliente = new Cliente("77777777777", "Joana Maria", "Bronze");
    Cliente clienteSalvo = clienteRepository.save(cliente);
    assertNotNull(clienteSalvo.getId());

    CarrinhoCompra c = new CarrinhoCompra();
    c.setCliente(clienteSalvo);
    Produto p = produtoRepository.findByDescricaoStartsWith("Enxaguante").get(0);

    ItemCompra i = new ItemCompra(p, 2);

    c.addItemCarrinho(i);
    assertEquals(c.getTotalComDesconto().doubleValue(),59.98);
  }

  @Test
  public void test05() {
    Cliente cliente = new Cliente("77777777777", "Joana Maria", "Bronze");
    Cliente clienteSalvo = clienteRepository.save(cliente);
    assertNotNull(clienteSalvo.getId());

    CarrinhoCompra c = new CarrinhoCompra();
    c.setCliente(clienteSalvo);

    Produto p = produtoRepository.findByDescricaoStartsWith("Enxaguante").get(0);

    ItemCompra i = new ItemCompra(p, 2);
    ItemCompra iSalvo = this.itemCompraRepository.save(i);
    assertNotNull(iSalvo.getId());
    
    c.addItemCarrinho(iSalvo);
    c.ConferirCliente();
    CarrinhoCompra cSalvo = this.carrinhoCompraRepository.save(c);
    assertNotNull(cSalvo.getId());
  }


  @Test
  public void test06() {
    Cliente cliente = new Cliente("77777777777", "Joana Maria", "Bronze");
    Cliente clienteSalvo = clienteRepository.save(cliente);
    assertNotNull(clienteSalvo.getId());
    CarrinhoCompra c = new CarrinhoCompra();
    c.setCliente(clienteSalvo);
    Produto p = produtoRepository.findByDescricaoStartsWith("Enxaguante").get(0);
    
    ItemCompra i = new ItemCompra(p, 2);
    Produto p2 = produtoRepository.findByDescricaoStartsWith("Creme").get(0);
   
    ItemCompra i2 = new ItemCompra(p2, 3);
    c.addItemCarrinho(i);
    c.addItemCarrinho(i2);
    c.ConferirCliente();
    CarrinhoCompra cSalvo = this.carrinhoCompraRepository.save(c);

    assertNotNull(cSalvo.getId());
    
    assertEquals(2, c.getItens().size());
    assertEquals(260.0, c.getTotalComDesconto().intValue());
  }

  @Test
  public void test07() {
    Cliente cliente = new Cliente("77777777777", "Joana Maria", "Bronze");
    Cliente clienteSalvo = clienteRepository.save(cliente);
    assertNotNull(clienteSalvo.getId()); 

    CarrinhoCompra c = new CarrinhoCompra();
    c.setCliente(clienteSalvo);
    CarrinhoCompra cSalvo = this.carrinhoCompraRepository.save(c);
    assertNotNull(cSalvo.getId());
    assertNotNull(cSalvo.getCliente().getId());
  }  

  @Test
  public void test08() {
    Produto produto = new Produto("Sabonete Lux Botanicals Rosas Francesas 85g", BigDecimal.valueOf(2.49), null);

    Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
      produtoRepository.save(produto);
      produtoRepository.flush();
    });
  }  

  @Test
  public void teste09(){
    Cliente cliente = new Cliente("77777777777", "Joana Maria", "Bronze");
    Cliente clienteSalvo = clienteRepository.save(cliente);
    
    cliente.setNomePesquisa("Joana Maria");
    assertNotNull(clienteSalvo.getId());
    cliente.pesquisarCliente();
  }
}
