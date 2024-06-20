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
import com.ifms.arcondicionado.modelos.Comando;
import com.ifms.arcondicionado.servicos.ComandoService;
import com.ifms.arcondicionado.servicos.ModeloService;
import com.ifms.arcondicionado.servicos.TipoComandoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/cadastro")
public class ComandoController {
	
	@Autowired
	ComandoService comandoService;
	
	@Autowired
	ModeloService modeloService;
	
	@Autowired
	TipoComandoService tipoComandoService;
	
	@GetMapping("/comando")
    String pCadastroComando(Model model) {
        model.addAttribute("comando", new Comando());
        model.addAttribute("comandos", comandoService.buscarComandos());
        model.addAttribute("modelos", modeloService.buscarModelos());
        model.addAttribute("tiposComando", tipoComandoService.buscarTiposComando());
        return "pCadastroComando";
    }
    
	/* Alterar cadastro de comando para comportar tipoComando */
    @PostMapping("/salvarComando")
    String salvarComando(@Valid Comando comando, BindingResult result, RedirectAttributes attributes, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("comandos", comandoService.buscarComandos());
            model.addAttribute("modelos", modeloService.buscarModelos());
            model.addAttribute("tiposComando", tipoComandoService.buscarTiposComando());
            return "pCadastroComando";
        }

        if(comando.getRaw().isEmpty() && comando.getHexadecimal().isEmpty()) {
            model.addAttribute("comandos", comandoService.buscarComandos());
            model.addAttribute("modelos", modeloService.buscarModelos());
            model.addAttribute("tiposComando", tipoComandoService.buscarTiposComando());
            model.addAttribute("mensagemErro", "Raw ou Hexadecimal deve ser preenchido.");
            return "pCadastroComando";
        }

        comando.setRaw(comando.getRaw().replaceAll("[^\\w,#]", ""));
        
        comandoService.salvar(comando);
        return "redirect:/cadastro/comando";
    }

    @GetMapping("/editarComando/{id}")
    String editarComandoGet(@PathVariable("id") long id, Model model) {
        Comando comando = comandoService.buscarComando(id);
       
        comando.setRaw(comando.getRaw().replaceAll("[^\\w,#]", ""));
        
        model.addAttribute("comando", comando);
        model.addAttribute("comandos", comandoService.buscarComandos());
        model.addAttribute("modelos", modeloService.buscarModelos());
        model.addAttribute("tiposComando", comando.getTipoComando());

        return "pCadastroComando";
    }

    @Transactional
    @PostMapping("/editarComando/{id}")
    String editarComandoPost(@PathVariable("id") long id, @Valid Comando comando, BindingResult result, Model model) {
        if(result.hasErrors()) {
            comando.setId(id);
            model.addAttribute("comando", comando);
            model.addAttribute("comandos", comandoService.buscarComandos());
            model.addAttribute("modelos", modeloService.buscarModelos());
            model.addAttribute("tiposComando", tipoComandoService.buscarTiposComando());
            return "pCadastroComando";
        }

        comandoService.editar(comandoService.buscarComando(id), comando);
        return "redirect:/cadastro/comando";
    }

    @GetMapping("/apagarComando/{id}")
    public String apagarComando(@PathVariable("id") long id, Model model) {
    	comandoService.deletar(comandoService.buscarComando(id));
        return "redirect:/cadastro/comando";
    }
    
}