package com.ifms.arcondicionado.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ifms.arcondicionado.modelos.Modelo;
import com.ifms.arcondicionado.modelos.Sala;
import com.ifms.arcondicionado.repositorios.ModeloRep;
import com.ifms.arcondicionado.servicos.SalaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/cadastro")
public class SalaController {
 	
	@Autowired
	ModeloRep modeloRep;
	
	@Autowired
	SalaService salaService;
	
	   @GetMapping("/sala")
	    String pCadastroSala(Model model) {
	        model.addAttribute("salas", salaService.buscarSalas());
	        model.addAttribute("sala", new Sala());
	        model.addAttribute("modelo", new Modelo());
	        model.addAttribute("modelos", modeloRep.findAll(Sort.by("nome")));
	        model.addAttribute("blocos", salaService.buscarBlocos());
	        
	        return "pCadastroSala_modelo.html";
	    }

	    @PostMapping("/salvarSala")
	    String salvarSala(@Valid Sala sala, BindingResult result, Model model, RedirectAttributes attributes) {
	        if(result.hasErrors()) {
	            return "redirect:/cadastro/sala";
	        }

			// Ex: Bloco: A e Nome : 103 => Nome : A103
			sala.setNome(sala.getBloco() + sala.getNome());

	        if(salaService.salvar(sala) == null) {
				attributes.addFlashAttribute("mensagemSalaErro", "Esta sala já foi cadastrada.");
	        	return "redirect:/cadastro/sala";
			}

			attributes.addFlashAttribute("mensagemSala", "Sala criada com sucesso.");
	        return "redirect:/cadastro/sala";
	        
	    }

	    @GetMapping("/editarSala/{id}")
	    String editarSalaGet(@PathVariable("id") long id, Model model) {
	        Sala sala = salaService.buscarSala(id);

	        model.addAttribute("salas", salaService.buscarSalas());
	        model.addAttribute("sala", sala);
	        model.addAttribute("modelo", new Modelo());
	        model.addAttribute("modelos", modeloRep.findAll(Sort.by("nome")));
	        model.addAttribute("blocos", salaService.buscarBlocos());

	        return "pCadastroSala_modelo";
	    }

	    @PostMapping("/editarSala/{id}")
	    String editarSalaPost(@PathVariable("id") long id, @Valid Sala sala, BindingResult result) {
	        if(result.hasErrors()) {
	            sala.setId(id);
	            return "redirect:/cadastro/sala";
	        }

	        salaService.editar(salaService.buscarSala(id), sala);
	        return "redirect:/cadastro/sala";
	    }

	    @GetMapping("/apagarSala/{id}")
	    public String apagarSala(@PathVariable("id") long id, Model model, RedirectAttributes rd) {
	        Sala s = salaService.buscarSala(id);

	        if(!(s.getEquipamento_sala().isEmpty())) {
	            rd.addFlashAttribute("mensagemSalaErro", "Existem equipamentos relacionados com esta sala.");
	            return "redirect:/cadastro/sala";
	        }

	        salaService.deletar(s);
	        return "redirect:/cadastro/sala";
	    }
}
