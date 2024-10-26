package com.ifms.arcondicionado.controladores;

import com.ifms.arcondicionado.modelos.Comando;
import com.ifms.arcondicionado.modelos.Equipamento;
import com.ifms.arcondicionado.servicos.ComandoService;
import com.ifms.arcondicionado.servicos.EquipamentoService;
import com.ifms.arcondicionado.servicos.ModeloService;
import com.ifms.arcondicionado.servicos.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/comandos")
public class ExecutarComando {

    @Autowired
    EquipamentoService equipamentoService;
    
    @Autowired
    ComandoService comandoService;
    
    @Autowired
    ModeloService modeloService;

    @Autowired
    RegistroService registroService;
    
    @GetMapping("/executar")
    String executar(@RequestParam("equipamento") Long idEquipamento, @RequestParam("comando") Long idComando, Model model, RedirectAttributes rd) throws Exception {

        Equipamento equipamento = equipamentoService.buscarEquipamento(idEquipamento);
        Comando comando = comandoService.buscarComando(idComando);
        
        // Definindo parâmetros da requisição
        String ipParam = equipamento.getMicrocontrolador().getIpv4();
        String modeloParam =equipamento.getModelo().getNome();
        String comandoParam = "";

        if(comando == null) {
        	return "redirect:/";
        }
        
        if(comando.getRaw().isEmpty()) {
        	comandoParam = comando.getHexadecimal();
        }else {
        	comandoParam = comando.getRaw();
        }

        System.out.print("COMANDO PARAM => "+ comandoParam);
 
        HttpClient client = HttpClient.newBuilder()
                        .build();

        HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("http://"+ipParam+"/command?comando="+comandoParam+"&modelo="+modeloParam))
                                .build();

        System.out.print("\nRequisição lançada, aguardando resposta...");

        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String corpoResponse = response.join().body();
        int codigoResponse = response.join().statusCode();
        
        System.out.println("\nResposta : " + corpoResponse + " - "+ codigoResponse);

        // Adicionando Log
        registroService.adicionarRegistroComando(corpoResponse, codigoResponse, comandoParam);
        model.addAttribute("equipamentos", equipamentoService.buscarEquipamentos());
        return "redirect:/";
    }
}
