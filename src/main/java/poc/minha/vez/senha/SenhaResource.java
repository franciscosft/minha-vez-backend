package poc.minha.vez.senha;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping(value = "/senhas")
public class SenhaResource {
	private final Logger log = LoggerFactory.getLogger(SenhaResource.class);	
	
	@Autowired
	private SenhaService senhaService;
	
	
	@RequestMapping(value="/normal", method = RequestMethod.POST)
	public ResponseEntity<SenhaDTO> cadastrarSenhaNormal(@RequestParam(required = true) int idCliente){
		SenhaDTO cadastrarSenha = senhaService.cadastrarSenha(false, idCliente);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("senhas/{numero}").buildAndExpand(cadastrarSenha.getNumero()).toUri();
		return ResponseEntity.created(location).body(cadastrarSenha);
	}
	
	@RequestMapping(value="preferencial", method = RequestMethod.POST)
	public ResponseEntity<SenhaDTO> cadastrarSenhaPreferencial(@RequestParam(required = true) int idCliente){
		SenhaDTO cadastrarSenha = senhaService.cadastrarSenha(true, idCliente);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("senhas/{numero}").buildAndExpand(cadastrarSenha.getNumero()).toUri();
		return ResponseEntity.created(location).body(cadastrarSenha);
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Void> buscarSenhas(){
		return ResponseEntity.noContent().build();
	}

}
