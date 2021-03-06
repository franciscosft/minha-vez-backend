package poc.minha.vez.senha;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poc.minha.vez.contador.ContadorService;
import poc.minha.vez.exceptions.OperacaoNegadaException;
import poc.minha.vez.senha.exceptions.SenhaNaoEncontradaException;
import poc.minha.vez.usuario.TipoUsuarioEnum;
import poc.minha.vez.usuario.Usuario;
import poc.minha.vez.usuario.UsuarioDTO;
import poc.minha.vez.usuario.UsuarioService;


@Service
public class SenhaService {
	private final Logger log = LoggerFactory.getLogger(SenhaService.class);	

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ContadorService contadorService;
	
	@Autowired
	private SenhaDAO senhaDAO;
	
	public SenhaDTO cadastrarSenha(boolean isPreferencial, int idUsuario) {
		Usuario usuario = recuperarUsuario(idUsuario);
		
		Senha senha = new Senha();
		senha.setUsuario(usuario);
		senha.setSituacaoSenha(SituacaoSenhaEnum.PENDENTE);
		senha.setPreferencial(isPreferencial);
		senha.setNumero(getNumero(isPreferencial));
		
		senha  = senhaDAO.save(senha);
		
		return toDTO(senha);
		
	}

	private Usuario recuperarUsuario(int clientId) {
		UsuarioDTO dto = usuarioService.getUsuario(clientId);
		Usuario usuario = usuarioService.toEntity(dto);
		return usuario;
	}

	private SenhaDTO toDTO(Senha senha) {
		SenhaDTO senhaDTO = new SenhaDTO();
		senhaDTO.setId(senha.getId());
		senhaDTO.setNumero(senha.getNumero());
		senhaDTO.setSituacaoSenha(senha.getSituacaoSenha());
		senhaDTO.setUsuario(usuarioService.toDTO(senha.getUsuario()));
		senhaDTO.setPreferencial(senha.isPreferencial());
		return senhaDTO;
	}

	private String getNumero(boolean isPreferencial) {
		int numero = contadorService.getNumero();
		String format = isPreferencial ? "P%04d" : "N%04d"; 
		return String.format(format, numero);
	}

	public void zerarContador(int idUsuario) throws OperacaoNegadaException {
		Usuario usuario = recuperarUsuario(idUsuario);
		if(usuario.getTipoUsuario().equals(TipoUsuarioEnum.GERENTE)){
			contadorService.zerarContador();
			List<Senha> senhasPendentes = senhaDAO.findBySituacaoSenha(SituacaoSenhaEnum.PENDENTE);
			senhasPendentes.forEach(senha -> senha.setSituacaoSenha(SituacaoSenhaEnum.CONCLUIDO));
			senhaDAO.saveAll(senhasPendentes);
		} else {
			throw new OperacaoNegadaException();
		}
	}

	public List<Senha> buscarSenhasPorSituacao(SituacaoSenhaEnum situacao) {
		return senhaDAO.findBySituacaoSenha(situacao);
	}

	public SenhaDTO puxarSenha(Integer idUsuario) throws OperacaoNegadaException, SenhaNaoEncontradaException {
		Usuario recuperarUsuario = recuperarUsuario(idUsuario);
		if(recuperarUsuario.getTipoUsuario() == TipoUsuarioEnum.GERENTE) {
			Senha senha = senhaDAO.findBySituacaoSenhaOrderByIdAsc(SituacaoSenhaEnum.PENDENTE).get(0);
			if(senha != null) {
				senha.setSituacaoSenha(SituacaoSenhaEnum.ATENDENDO);
				return toDTO(senhaDAO.save(senha));
			} else {
				throw new SenhaNaoEncontradaException();
			}
		} else {
			throw new OperacaoNegadaException();
		}
		
	}

	public void concluirSenha(SenhaDTO puxarSenha, Integer idUsuario) throws SenhaNaoEncontradaException {
		Usuario recuperarUsuario = recuperarUsuario(idUsuario);
		if(recuperarUsuario.getTipoUsuario() == TipoUsuarioEnum.GERENTE) {
			Optional<Senha> senha = senhaDAO.findById(puxarSenha.getId());
			Senha orElseThrow = senha.orElseThrow(() -> new SenhaNaoEncontradaException());
			orElseThrow.setSituacaoSenha(SituacaoSenhaEnum.CONCLUIDO);
			senhaDAO.save(orElseThrow);
		}
	}

}
