package poc.minha.vez.contador;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContadorService {

	@Autowired
	private ContadorDAO dao;
	
	public int getNumero() {
		Optional<Contador> findById = dao.findById(1);
		Contador contador = new Contador();
		if(!findById.isPresent()) {
			contador.setNumero(1);
			contador = dao.save(contador);
		} else {
			contador = findById.get();
			contador.contar();
			contador = dao.save(contador);
		}
		return contador.getNumero();
	}

}

