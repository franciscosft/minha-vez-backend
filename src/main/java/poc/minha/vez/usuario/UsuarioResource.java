package poc.minha.vez.usuario;


import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {
	private final Logger log = LoggerFactory.getLogger(UsuarioResource.class);	
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> visualizarUsuarios() {
		log.info("Acessando endpoint Usuarios");
		List<UsuarioDTO> usuarios = usuarioService.getUsuarios();
		return ResponseEntity.ok().body(usuarios);
	}
	
	@RequestMapping(value="{idUsuario}", method = RequestMethod.GET)
	public ResponseEntity<?> visualizarUsuarios(@PathVariable int idUsuario) {
		log.info("Acessando endpoint Usuarios");
			UsuarioDTO usuario = usuarioService.getUsuario(idUsuario);
			return ResponseEntity.ok().body(usuario);
	}
	
	@RequestMapping(value= "/cliente", method = RequestMethod.POST)
	public ResponseEntity<?> cadastrarUsuario() {
		log.info("Acessando endpoint Usuarios");
		UsuarioDTO cliente = usuarioService.cadastrarCliente();
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/usuarios/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).body(cliente);
	}
	
	@RequestMapping(value= "/gerente", method = RequestMethod.POST)
	public ResponseEntity<?> cadastrarGerente() {
		log.info("Acessando endpoint Usuarios");
		UsuarioDTO cliente = usuarioService.cadastrarGerente();
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).body(cliente);
	}
	
	

}
