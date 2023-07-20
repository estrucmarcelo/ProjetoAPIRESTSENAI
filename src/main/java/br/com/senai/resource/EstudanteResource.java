package br.com.senai.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.commons.ExampleValues;
import br.com.senai.dto.EstudanteDTO;
import br.com.senai.entity.Estudante;
import br.com.senai.service.EstudanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@RestController
@RequestMapping("estudantes")
public class EstudanteResource {

	@Autowired
	private EstudanteService estudanteService;
	
	@Autowired
	private ModelMapper mapper;
		
	@GetMapping
	public ResponseEntity<List<EstudanteDTO>> buscarTodosEstudantes() {
	 List<Estudante> listaEstudantes = estudanteService.buscarTodosEstudantes();
	 List<EstudanteDTO> listaEstudanteDTO = 
			 listaEstudantes.stream().map(estudante -> 
			 mapper.map(estudante, EstudanteDTO.class)).collect(Collectors.toList());
	 return ResponseEntity.ok().body(listaEstudanteDTO);
	}
	
	
	@GetMapping("/{id}")
	@Operation(description = "Retorna o resgistro do estudante por id")
	public ResponseEntity<EstudanteDTO> buscarEstudantePeloID(@PathVariable("id") 
	@Schema(example= ExampleValues.idEstudante) Integer id) {
		Estudante estudante = estudanteService.getEstudanteByID(id);
		EstudanteDTO estudanteDTO = mapper.map(estudante, EstudanteDTO.class);
		return ResponseEntity.ok().body(estudanteDTO);
		
	}
	
	
	@PostMapping
	@Operation(description = "Cadastrar estudante")
	public ResponseEntity<EstudanteDTO> cadastrarEstudante(@Valid
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					content = @Content
					(examples = {
								@ExampleObject(name="Exemplo de Estudante", value = ExampleValues.exemploEstudante)
						}
					)
			)
					
			
			@RequestBody EstudanteDTO estudanteDTO) {
		
		Estudante estudante = mapper.map(estudanteDTO, Estudante.class);
		
		estudante = estudanteService.salvar(estudante);
		
		EstudanteDTO novoEstudante = mapper.map(estudante, EstudanteDTO.class);
		
		return ResponseEntity.ok().body(novoEstudante);
				
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EstudanteDTO> atualizarEstudante(@PathVariable @Schema(example= ExampleValues.idEstudante) Integer id,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					content = @Content
					(examples = {
								@ExampleObject(name="Exemplo de Estudante Alterar", value = ExampleValues.exemploEstudanteAlterar)
						}
					)
			)
			@RequestBody EstudanteDTO estudanteDTO) {
		
		Estudante estudante = mapper.map(estudanteDTO, Estudante.class);
		
		estudante = estudanteService.updateEstudante(id, estudante);
		
		EstudanteDTO novoEstudante = mapper.map(estudante, EstudanteDTO.class);
		
		return ResponseEntity.ok().body(novoEstudante);
				
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> excluirEstudante(@PathVariable @Schema(example= ExampleValues.idEstudante) Integer id) {
		
		Boolean flag = estudanteService.deleteEstudante(id);
		
		return ResponseEntity.ok().body(flag);
		
	}
	
	
	@GetMapping("paginacao")
	public Page<Estudante> buscarEstudantePorPaginacao
	(@RequestParam @Schema(example= ExampleValues.pagina) Integer pagina,
			@RequestParam @Schema(example= ExampleValues.itensPorPagina) Integer itensPorPagina,
			@RequestParam @Schema(example= ExampleValues.ordenacao) String ordenacao,
			@RequestParam @Schema(example= ExampleValues.tipoOrdenacao) String tipoOrdenacao) {
		
		//Direction direction = Direction.ASC;
		PageRequest page = PageRequest.of(pagina, itensPorPagina,
				(tipoOrdenacao.equals("ASC")? Sort.by("nome").ascending(): Sort.by("nome").descending()));
		//PageRequest page = PageRequest.of(pagina, itensPorPagina);
		
		return estudanteService.buscarEstudantePorPaginacao(page);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
