package poc.minha.vez;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import poc.minha.vez.exceptions.OperacaoNegadaException;
import poc.minha.vez.senha.Senha;
import poc.minha.vez.senha.SenhaDTO;
import poc.minha.vez.senha.SenhaService;
import poc.minha.vez.senha.SituacaoSenhaEnum;
import poc.minha.vez.senha.exceptions.SenhaNaoEncontradaException;
import poc.minha.vez.usuario.UsuarioDTO;
import poc.minha.vez.usuario.UsuarioService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GereciadorSenhaTest {

	@Autowired
	private SenhaService senhaService;
	
	@Autowired
	private UsuarioService usuarioService;

	private UsuarioDTO gerente;
	
	
	@Before
	public void beforeTest() {	
		gerente = usuarioService.cadastrarGerente();
	}
	
	/*
	 * O banco dados em memória não possui nenhum registro, então obrigatoriamente a primeira senha será N0001
	 */
	@Test
	public void testIncrementarSenhas() {
		//Verificiando se cada cliente foi cadastrado e ID corresponde a estratégia 
		UsuarioDTO cliente1 = usuarioService.cadastrarCliente();
		Integer id1 = cliente1.getId();
		Assert.assertNotNull(id1);
		Assert.assertEquals(id1.intValue(), 1);
		
		UsuarioDTO cliente2 = usuarioService.cadastrarCliente();
		Integer id2 = cliente2.getId();
		Assert.assertNotNull(id2);
		Assert.assertEquals(id2.intValue(), 2);
		
		UsuarioDTO cliente3 = usuarioService.cadastrarCliente();
		Integer id3 = cliente3.getId();
		Assert.assertNotNull(id3);
		Assert.assertEquals(id3.intValue(), 3);

		//Cadastrando primeira senha: normal (N0001)
		SenhaDTO n0001 = senhaService.cadastrarSenha(false, id1);
		Assert.assertNotNull(n0001.getId());
		Assert.assertEquals("N0001", n0001.getNumero());
		
		//Cadastrando segunda senha: normal (N0002)
		SenhaDTO n0002 = senhaService.cadastrarSenha(false, id1);
		Assert.assertNotNull(n0002.getId());
		Assert.assertEquals("N0002", n0002.getNumero());
		
		//Cadastrando terceira senha: preferencial (P0003) 
		SenhaDTO n0003 = senhaService.cadastrarSenha(true, id1);
		Assert.assertNotNull(n0003.getId());
		Assert.assertEquals("P0003", n0003.getNumero());
	}
	
	
	@Test
	public void testZerarSenha() throws OperacaoNegadaException {
		UsuarioDTO gerente = usuarioService.cadastrarGerente();
		senhaService.zerarContador(gerente.getId());
		SenhaDTO senha = senhaService.cadastrarSenha(false, gerente.getId());
		Assert.assertEquals("N0001", senha.getNumero());
		senhaService.zerarContador(gerente.getId());
		senha = senhaService.cadastrarSenha(false, gerente.getId());
		Assert.assertEquals("N0001", senha.getNumero());
	}
	
	@Test(expected = OperacaoNegadaException.class)
	public void testOperacaoNegada() throws OperacaoNegadaException {
		UsuarioDTO cadastrarCliente = usuarioService.cadastrarCliente();
		senhaService.zerarContador(cadastrarCliente.getId());
	}
	
	@Test
	public void testBuscarSenhaPendentes() throws OperacaoNegadaException {
		UsuarioDTO cliente = usuarioService.cadastrarCliente();
		Integer idCliente = cliente.getId();
		senhaService.cadastrarSenha(false, idCliente);
		List<Senha> buscarSenhasPorSituacao = senhaService.buscarSenhasPorSituacao(SituacaoSenhaEnum.PENDENTE);
		Assert.assertTrue(!buscarSenhasPorSituacao.isEmpty());
	}
	
	
	@Test
	public void testGerenciarSenhas() throws OperacaoNegadaException, SenhaNaoEncontradaException {
		senhaService.zerarContador(gerente.getId());
		
		UsuarioDTO cliente = usuarioService.cadastrarCliente();
		senhaService.cadastrarSenha(false, cliente.getId());
		senhaService.cadastrarSenha(false, cliente.getId());

		SenhaDTO puxarSenha = senhaService.puxarSenha(gerente.getId());
		Assert.assertEquals("N0001", puxarSenha.getNumero());
		Assert.assertEquals(SituacaoSenhaEnum.ATENDENDO, puxarSenha.getSituacaoSenha());
		senhaService.concluirSenha(puxarSenha, gerente.getId());
		
		SenhaDTO puxarSenha2 = senhaService.puxarSenha(gerente.getId());
		Assert.assertEquals("N0002", puxarSenha2.getNumero());
		Assert.assertEquals(SituacaoSenhaEnum.ATENDENDO, puxarSenha2.getSituacaoSenha());
	}
	
}
