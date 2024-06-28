package br.unibh.gqs.market_solution;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.unibh.gqs.market_solution.model.CarrinhoCompra;
import br.unibh.gqs.market_solution.model.Cliente;
import br.unibh.gqs.market_solution.model.ItemCompra;
import br.unibh.gqs.market_solution.model.Produto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TesteRest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void test01()
    {
        assertTrue(
                this.restTemplate
                        .getForObject("http://localhost:" + port + "/cliente/1", Cliente.class)
                        .getCpf().equals("11111111111"));
    }

    @Test
    public void test02() {
        assertTrue(
                this.restTemplate
                        .getForObject("http://localhost:" + port + "/produtos", List.class).size() == 3);
    }

    @Test
    public void test03() {
        assertTrue(
                this.restTemplate
                        .getForObject("http://localhost:" + port + "/carrinhocompra/1", CarrinhoCompra.class).getId().equals(1L));
    }

    @Test
    public void test04() {
        Cliente novoCliente = new Cliente("44444444444", "Patricia Ramos", "Ouro");

        ResponseEntity<Cliente> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/cliente", novoCliente, Cliente.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        Cliente clienteAdicionado = responseEntity.getBody();
        assertNotNull(clienteAdicionado);
        assertNotNull(clienteAdicionado.getId());

        Cliente clienteObtido = restTemplate.getForObject("http://localhost:" + port + "/cliente/" + clienteAdicionado.getId(), Cliente.class);
        assertNotNull(clienteObtido);
        assertTrue(clienteAdicionado.getId().equals(clienteObtido.getId()));
    }

    @Test
    public void test05() {
        Cliente novoCliente = new Cliente("44444444444", "Patricia Ramos", "Ouro");

        ResponseEntity<Cliente> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/cliente", novoCliente, Cliente.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        Cliente clienteAdicionado = responseEntity.getBody();
        assertNotNull(clienteAdicionado);
        assertNotNull(clienteAdicionado.getId());

        clienteAdicionado.setNome("Amanda Moreira");
        restTemplate.put("http://localhost:" + port + "/cliente", clienteAdicionado);

        Cliente clienteAtualizado = restTemplate.getForObject("http://localhost:" + port + "/cliente/" + clienteAdicionado.getId(), Cliente.class);

        assertNotNull(clienteAtualizado);
        assertEquals("Amanda Moreira", clienteAtualizado.getNome());
    }

  

    @Test
    public void test06() {
        Cliente novoCliente = new Cliente("55555555555", "Pamela Roberta", "Ouro");
        ResponseEntity<Cliente> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/cliente", novoCliente, Cliente.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        Cliente clienteAdicionado = responseEntity.getBody();
        assertNotNull(clienteAdicionado);
        assertNotNull(clienteAdicionado.getId());

        restTemplate.delete("http://localhost:" + port + "/cliente?id=" + clienteAdicionado.getId());
        Cliente clienteDeletado = restTemplate.getForObject("http://localhost:" + port + "/cliente/" + clienteAdicionado.getId(), Cliente.class);

        assertEquals(null, clienteDeletado.getId(), "Cliente deletado foi encontrado no sistema.");
        assertEquals(null, clienteDeletado.getCpf(), "CPF do cliente deletado foi encontrado no sistema.");
        assertEquals(null, clienteDeletado.getNome(), "Nome do cliente deletado foi encontrado no sistema.");
        assertEquals(null, clienteDeletado.getClasseBonus(), "Classe bônus do cliente deletado foi encontrado no sistema.");
    }
    @Test
    public void test07() {
        Cliente novoCliente = new Cliente("66666666666", "Marcelo Mota", "Bronze");

        ResponseEntity<Cliente> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/cliente", novoCliente, Cliente.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        Cliente clienteAdicionado = responseEntity.getBody();
        assertNotNull(clienteAdicionado);
        assertNotNull(clienteAdicionado.getId());

        CarrinhoCompra carrinhoAchado = restTemplate.getForObject("http://localhost:" + port + "/carrinhocompra/1", CarrinhoCompra.class);
        assertNotNull(carrinhoAchado);

        ResponseEntity<Produto> responseProduto = restTemplate.getForEntity("http://localhost:" + port + "/produto/2", Produto.class);
        assertEquals(HttpStatus.OK, responseProduto.getStatusCode());
        assertNotNull(responseProduto.getBody());

        Produto produto = responseProduto.getBody();
        assertNotNull(produto);

        ItemCompra itemCompra = new ItemCompra(produto, 2);

        carrinhoAchado.addItemCarrinho(itemCompra);
        assertTrue(carrinhoAchado.getItens().contains(itemCompra));
    }
}

