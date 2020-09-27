package poc.minha.vez.senha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poc.minha.vez.contador.ContadorService;
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
	
	public SenhaDTO cadastrarSenha(boolean isPreferencial, int clientId) {
		UsuarioDTO dto = usuarioService.getUsuario(clientId);
		Usuario usuario = usuarioService.toEntity(dto);
		
		Senha senha = new Senha();
		senha.setUsuario(usuario);
		senha.setSituacaoSenha(SituacaoSenhaEnum.PENDENTE);
		senha.setPreferencial(isPreferencial);
		senha.setNumero(getNumero(isPreferencial));
		
		senha  = senhaDAO.save(senha);
		
		return toDTO(senha);
		
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

}
