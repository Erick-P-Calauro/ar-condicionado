package com.ifms.arcondicionado.controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ifms.arcondicionado.modelos.Agenda;
import com.ifms.arcondicionado.servicos.AgendaService;
import com.ifms.arcondicionado.servicos.EquipamentoService;
import com.ifms.arcondicionado.servicos.SalaService;
import com.ifms.arcondicionado.servicos.TipoComandoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/cadastro")
public class AgendaController {
	
	@Autowired
	AgendaService a;
	
	@Autowired
	EquipamentoService e;
	
	@Autowired
	TipoComandoService t;

	@Autowired
	SalaService s;
	
	@GetMapping("/agenda")
	public String indexAgenda(Model model) {
		
		model.addAttribute("equipamentos", e.buscarEquipamentos());
		model.addAttribute("agenda", new Agenda());
		model.addAttribute("agendas", a.buscarAgendas());
		model.addAttribute("tiposComando", t.buscarTiposComando());
		model.addAttribute("blocos", s.buscarBlocos());
		model.addAttribute("salas", s.buscarSalas());

		return "pCadastroAgenda";
	}
	
	@PostMapping("/salvarAgenda/")
	public String salvarAgenda(@Valid Agenda agenda, BindingResult result, Model model, RedirectAttributes rd) {
		
		if(result.hasErrors()) {
			System.out.println("Erro -> " + result.toString());
			return "redirect:/cadastro/agenda";
		}
		
		a.salvar(agenda);


		return "redirect:/cadastro/agenda";
		
	}

	@GetMapping("/apagarAgenda/{id}")
    public String apagarAgenda(@PathVariable("id") long id, Model model, RedirectAttributes rd) {
        a.deletar(a.buscarAgenda(id));
        return "redirect:/cadastro/agenda";
    }

}
