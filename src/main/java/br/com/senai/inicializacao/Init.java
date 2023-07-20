package br.com.senai.inicializacao;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import br.com.senai.entity.Estudante;
import br.com.senai.repository.EstudanteRepository;
import br.com.senai.service.EstudanteService;

@Component
public class Init implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private EstudanteService estudanteService;
	
	@Autowired
	private EstudanteRepository repo;
	
		
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
			
		Estudante estudante1 = new Estudante();
		estudante1.setNome("Marcelo");
		estudante1.setSobreNome("Estruc");
		estudante1.setEmail("estrucmarcelo@gmail.com");
		//estudanteService.salvar(estudante1);
		
				
		Estudante estudante2 = new Estudante();
		estudante2.setNome("Lucas");
		estudante2.setSobreNome("Silva");
		estudante2.setEmail("lucassilva@gmail.com");
		//estudanteService.salvar(estudante2);
		
		Estudante estudante3 = new Estudante();
		estudante3.setNome("Flavio");
		estudante3.setSobreNome("Alberto");
		estudante3.setEmail("flavioalberto@gmail.com");
		//estudanteService.salvar(estudante3);
		
		repo.saveAll(Arrays.asList(estudante1,estudante2,estudante3));
		
		
		//buscar todos os estudantes
		//List<Estudante> listaEstudante = estudanteService.buscarTodosEstudantes();
		//listaEstudante.forEach(estudante -> System.out.println(estudante.getNome()));
		
		//buscar um estudante pelo id
		//Estudante estudante = estudanteService.getEstudanteByID(1);
		//System.out.println(estudante.getNome());
		
		//excluir estudante pelo id
		Boolean flag = estudanteService.deleteEstudante(1);
		System.out.println(flag);
		
		// alterar estudante
		
		Estudante estudante4 = new Estudante();
		estudante4.setNome("Flavio");
		estudante4.setSobreNome("Alberto da Pereira");
		estudante4.setEmail("flaviopereira@gmail.com");
		Estudante estudanteAlterado = estudanteService.updateEstudante(3, estudante4);
		System.out.println(estudanteAlterado.getEmail());
		
		
	}
}
