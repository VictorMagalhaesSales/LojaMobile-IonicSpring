package com.appmobilespring.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.appmobilespring.domain.Categoria;
import com.appmobilespring.dto.CategoriaDTO;
import com.appmobilespring.repositories.CategoriaRepository;
import com.appmobilespring.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public List<CategoriaDTO> findAll(){
		List<Categoria> lista = repository.findAll();
		return ListToDTO(lista);
	}
	
	public Page<CategoriaDTO> findPage(Integer page, Integer size, String direction, String orderBy){
		Pageable pageable = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		Page<Categoria> list = repository.findAll(pageable);
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));
		return listDto;
	}
	
	public Categoria insert(CategoriaDTO dto) {
		return repository.save(new Categoria(dto.getId(), dto.getNome()));
	}
	
	public Categoria update(CategoriaDTO dto) {
		find(dto.getId());
		return repository.save(new Categoria(dto.getId(), dto.getNome()));
	}
	
	public void delete(Integer id){
		find(id);
		repository.deleteById(id);
	}
	
	public List<CategoriaDTO> ListToDTO(List<Categoria> lista){
		List<CategoriaDTO> listaDTO = lista.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return listaDTO;
	}

}
