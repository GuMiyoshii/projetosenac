package com.sistema.senac.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sistema.senac.modelo.Estado;
import com.sistema.senac.repositorio.EstadoRepositorio;

@Controller
public class EstadoControle {
	
	@Autowired
	private EstadoRepositorio estadoRepositorio;
	
	@GetMapping("/cadastroEstado")
	public ModelAndView cadastrar(Estado estado) { 
		ModelAndView mv = new ModelAndView("administrativo/estados/cadastro");
		mv.addObject("estado",estado);
		return mv;
	}
	
	@PostMapping("/salvarEstado")
	public ModelAndView salvar(Estado estado, BindingResult result) { 
		if(result.hasErrors()) {
			return cadastrar(estado);
	}
		estadoRepositorio.saveAndFlush(estado);
		return cadastrar(new Estado());
	}
	
	@GetMapping("/listarEstado")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/estados/lista");
		mv.addObject("listaEstados", estadoRepositorio.findAll());
		return mv;	
	}
	
	@GetMapping("/editarEstado/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Estado> estado = estadoRepositorio.findById(id);  //busca estado por id e armazena
		return cadastrar(estado.get());		   //chama função cadastrar e carrega valores nela
	}
	
	@GetMapping("/removerEstado/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Estado> estado = estadoRepositorio.findById(id);
		estadoRepositorio.delete(estado.get());
		return listar();
	}
	
}