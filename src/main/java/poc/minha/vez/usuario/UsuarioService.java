package poc.minha.vez.usuario;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poc.minha.vez.usuario.exceptions.UsuarioNaoEncontradoException;

@Service
public class UsuarioService {
	private final Logger log = LoggerFactory.getLogger(UsuarioService.class);	


	@Autowired
	private UsuarioDAO dao;

	public List<UsuarioDTO> getUsuarios() {
		return dao.findAll().stream().map(u -> toDTO(u)).collect(Collectors.toList());
	}

	public Usuario salvarUsuario(UsuarioDTO usuarioDTO) {
		Usuario usuario = toEntity(usuarioDTO);
		usuario = dao.save(usuario);
		return usuario;
	}

	public Usuario toEntity(UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario();
		if (usuarioDTO.getId() != null) {
			usuario.setId(usuarioDTO.getId());
		}
		usuario.setTipoUsuario(usuarioDTO.getTipoUsuario());
		return usuario;
	}

	public UsuarioDTO toDTO(Usuario u) {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setId(u.getId());
		usuarioDTO.setTipoUsuario(u.getTipoUsuario());
		return usuarioDTO;
	}

	public UsuarioDTO cadastrarCliente() {
		Usuario usuario = new Usuario();
		usuario.setTipoUsuario(TipoUsuarioEnum.CLIENTE);
		return toDTO(dao.save(usuario));
	}
	
	public UsuarioDTO cadastrarGerente() {
		Usuario usuario = new Usuario();
		usuario.setTipoUsuario(TipoUsuarioEnum.GERENTE);
		return toDTO(dao.save(usuario));
	}

	public UsuarioDTO getUsuario(int idUsuario) throws UsuarioNaoEncontradoException {
		Optional<Usuario> findById = dao.findById(idUsuario);
		if(findById.isPresent()) {
			return toDTO(findById.get());
		} else {
			log.error("Não foi encontrado o usuário de ID: {}", idUsuario);
			throw new UsuarioNaoEncontradoException(idUsuario);
		}
	}

}
