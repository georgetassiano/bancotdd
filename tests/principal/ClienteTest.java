package principal;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClienteTest {

	private Cliente cliente;
	
	@BeforeEach
	void setUp() throws Exception {
		cliente = new Cliente("George", new Conta(0.0, 12345, 123456));
	}

	@AfterEach
	void tearDown() throws Exception {
		cliente = null;
	}

	@Test
	void deveRetornarSaldoAtualDaContaDoCliente() {
		
		//executa o metodo de ver saldo
		double saldoAtual = cliente.verSaldo();
		
		//define o valor esperado
		double saldoEsperado = 0.0;
		
		//testa se o valor recebido é o mesmo do esperado
		assertEquals(saldoEsperado, saldoAtual);
	}

}
