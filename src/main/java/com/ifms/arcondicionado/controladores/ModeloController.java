package com.ifms.arcondicionado.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ifms.arcondicionado.modelos.Modelo;
import com.ifms.arcondicionado.modelos.Sala;
import com.ifms.arcondicionado.servicos.ModeloService;
import com.ifms.arcondicionado.servicos.SalaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/cadastro")
public class ModeloController {
	
	@Autowired
	ModeloService modeloService;
	
	@Autowired
	SalaService salaService;
	
	@GetMapping("/modelo")
    String pCadastroModelo(Model model) {
        model.addAttribute("modelo", new Modelo());
        model.addAttribute("modelos", modeloService.buscarModelos());
        model.addAttribute("salas", salaService.buscarSalas());
        model.addAttribute("sala", new Sala());
        model.addAttribute("blocos", salaService.buscarBlocos());
        return "pCadastroSala_modelo";
    }

    @PostMapping("/salvarModelo")
    String salvarModelo(@Valid Modelo modelo, BindingResult result, Model model, RedirectAttributes attributes) {
        if(result.hasErrors()) {
            return "redirect:/cadastro/modelo";
        }

        if(modeloService.salvarModelo(modelo) == null ) {
            attributes.addFlashAttribute("mensagemModeloErro", "Este modelo já foi cadastrado.");
            return "redirect:/cadastro/modelo";
        }

        attributes.addFlashAttribute("mensagemModelo", "Modelo criado com sucesso.");
        return "redirect:/cadastro/modelo";
    }

    @GetMapping("/editarModelo/{id}")
    String editarModeloGet(@PathVariable("id") long id, Model model) {
        Modelo modelo = modeloService.buscarModelo(id);
        
        model.addAttribute("modelo", modelo);
        model.addAttribute("modelos", modeloService.buscarModelos());
        model.addAttribute("salas", salaService.buscarSalas());
        model.addAttribute("sala", new Sala());
        model.addAttribute("blocos", salaService.buscarBlocos());

        return "pCadastroSala_modelo";
    }

    @Transactional
    @PostMapping("/editarModelo/{id}")
    String editarEquipamentoPost(@PathVariable("id") long id, @Valid Modelo modelo, BindingResult result) {
        if(result.hasErrors()) {
            modelo.setId(id);
            return "redirect:/cadastro/modelo";
        }

        modeloService.salvarModelo(modelo);
        return "redirect:/cadastro/modelo";
    }

    @GetMapping("/apagarModelo/{id}")
    public String apagarModelo(@PathVariable("id") long id, Model model, RedirectAttributes rd) {
        Modelo m = modeloService.buscarModelo(id);

        if(!(m.getEquipamento_modelo().isEmpty())) {
            rd.addFlashAttribute("mensagemModeloErro", "Existem equipamentos relacionados com este modelo.");
            return "redirect:/cadastro/modelo";
        }

        if(!(m.getComando().isEmpty())) {
            rd.addFlashAttribute("mensagemModeloErro", "Existem comandos relacionados com este modelo.");
            return "redirect:/cadastro/modelo";
        }

        modeloService.deletarModelo(m);
        return "redirect:/cadastro/modelo";
    }
}
