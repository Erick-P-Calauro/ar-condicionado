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
import com.ifms.arcondicionado.modelos.Equipamento;
import com.ifms.arcondicionado.servicos.EquipamentoService;
import com.ifms.arcondicionado.servicos.MicrocontroladorService;
import com.ifms.arcondicionado.servicos.ModeloService;
import com.ifms.arcondicionado.servicos.SalaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/cadastro")
public class EquipamentoController {
	
	@Autowired
	EquipamentoService equipamentoService;
	
	@Autowired
	SalaService salaService;
	
	@Autowired
	ModeloService modeloService;
	
	@Autowired
	MicrocontroladorService macAddrService;
	
	@GetMapping("/equipamento")
    String pCadastroMicrocontrolador(Model model) {
        model.addAttribute("equipamento", new Equipamento());
        model.addAttribute("equipamentos", equipamentoService.buscarEquipamentos());
        model.addAttribute("salas", salaService.buscarSalas());
        model.addAttribute("modelos", modeloService.buscarModelos());
        model.addAttribute("equipConnects", macAddrService.buscarEquipConnects());
        
        return "pCadastroMicrocontrolador";
    }
	
	@GetMapping("/salvarEquipamento/{endereco}")
	String pCadastroEquipamento(@PathVariable("endereco") String endereco, Model model) {
		model.addAttribute("equipamento", new Equipamento(macAddrService.buscarEquipConnect(endereco)));
        model.addAttribute("salas", salaService.buscarSalas());
        model.addAttribute("modelos", modeloService.buscarModelos());
        model.addAttribute("equipConnects", macAddrService.buscarEquipConnects());
        
        return "pCadastroEquipamento";
	}

    @PostMapping("/salvarEquipamento")
    String salvarEquipamento(@Valid Equipamento equipamento, BindingResult result, Model model, RedirectAttributes attributes) {
        if(result.hasErrors()) {; 	
            return "pCadastroEquipamento";
        }
       
        equipamentoService.salvar(equipamento);
        attributes.addFlashAttribute("mensagem", "Equipamento criado com sucesso.");
        return "redirect:/cadastro/equipamento";
    }

    @GetMapping("/editarEquipamento/{id}")
    String editarEquipamentoGet(@PathVariable("id") long id, Model model) {
        Equipamento equipamento = equipamentoService.buscarEquipamento(id);
    	
        model.addAttribute("equipamento", equipamento);
        model.addAttribute("equipamentos", equipamentoService.buscarEquipamentos());
        model.addAttribute("salas", salaService.buscarSalas());
        model.addAttribute("modelos", modeloService.buscarModelos());
        model.addAttribute("equipConnects", macAddrService.buscarEquipConnects());
        

        return "pCadastroEquipamento";
    }


    @PostMapping("/editarEquipamento/{id}")
    String editarModeloPost(@PathVariable("id") long id, @Valid Equipamento equipamento, BindingResult result, Model model) {
        if(result.hasErrors()) {
            equipamento.setId(id);
            model.addAttribute("equipamento", equipamento);
            model.addAttribute("salas", salaService.buscarSalas());
            model.addAttribute("modelos", modeloService.buscarModelos());
            model.addAttribute("equipConnects", macAddrService.buscarEquipConnects());
            return "pCadastroEquipamento";
        }

        equipamentoService.editar(equipamentoService.buscarEquipamento(id), equipamento);
        return "redirect:/cadastro/equipamento";
    }

    @GetMapping("/apagarEquipamento/{id}")
    public String apagarEquipamento(@PathVariable("id") Long id, Model model) {
    	equipamentoService.deletar(equipamentoService.buscarEquipamento(id));
        return "redirect:/cadastro/equipamento";
    }
    
    @GetMapping("/apagarEquipConnect/{endereco}")
    public String apagarEquipConnect(@PathVariable("endereco") String endereco, Model model) {
    	macAddrService.deletar(macAddrService.buscarEquipConnect(endereco));
        return "redirect:/cadastro/equipamento";
    }
}
