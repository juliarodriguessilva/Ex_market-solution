package br.unibh.gqs.market_solution;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.unibh.gqs.market_solution.model.CarrinhoCompra;
import br.unibh.gqs.market_solution.model.Cliente;
import br.unibh.gqs.market_solution.model.ItemCompra;
import br.unibh.gqs.market_solution.model.Produto;
import br.unibh.gqs.market_solution.persistence.CarrinhoCompraRepository;
import br.unibh.gqs.market_solution.persistence.ClienteRepository;
import br.unibh.gqs.market_solution.persistence.ItemCompraRepository;
import br.unibh.gqs.market_solution.persistence.ProdutoRepository;
import br.unibh.gqs.market_solution.rest.ClienteController;
import br.unibh.gqs.market_solution.service.CarrinhoCompraService;
import br.unibh.gqs.market_solution.service.ClienteService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = ClienteController.class)
public class TesteController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClienteService clienteService;

    @MockBean
    CarrinhoCompraService carrinhoCompraService;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private ProdutoRepository produtoRepository;

    @MockBean
    private ItemCompraRepository itemCompraRepository;

    @MockBean
    private CarrinhoCompraRepository carrinhoCompraRepository;

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    @Test
    public void teste01() throws Exception {

        Cliente cliente = new Cliente("777777777777", "Telma", "Ouro");
        given(clienteService.getById(1L)).willReturn(cliente);

        this.mockMvc.perform(get("/cliente/{id}", 1L)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Telma"));

    }

    @Test
    public void teste02() throws Exception {
        
        Cliente c1 = new Cliente("11111111111", "Adriano", "Prata");
        Cliente c2 = new Cliente("777777777777", "Telma", "Ouro");
        List<Cliente> clienteList = new ArrayList<>();
        clienteList.add(c1);
        clienteList.add(c2);
        given(clienteService.getAll()).willReturn(clienteList);

        this.mockMvc.perform(get("/clientes")).andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$[0].cpf").value("11111111111"))
                .andExpect(jsonPath("$[1].cpf").value("777777777777"))                ;
    }

    @Test
    public void teste03() throws Exception {
        CarrinhoCompra carrinho = new CarrinhoCompra();
        carrinho.setId(1L); 
        given(carrinhoCompraService.getById(1L)).willReturn(carrinho);

        Produto produto = new Produto("Shampoo XPTO", BigDecimal.valueOf(12.99), new GregorianCalendar(2024, 7, 30).getTime());
        ItemCompra itemCompra = new ItemCompra(produto, 2);

        this.mockMvc.perform(post("/carrinhocompra/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(itemCompra)))
            .andExpect(status().isOk());
    }
}


