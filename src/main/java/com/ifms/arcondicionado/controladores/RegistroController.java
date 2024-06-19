package com.ifms.arcondicionado.controladores;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ifms.arcondicionado.modelos.Microcontrolador;
import com.ifms.arcondicionado.modelos.Registro;
import com.ifms.arcondicionado.servicos.MicrocontroladorService;
import com.ifms.arcondicionado.servicos.RegistroService;

@Controller
@RequestMapping("/registro")
public class RegistroController {
    
    @Autowired
    MicrocontroladorService macService;

    @Autowired
    RegistroService rService;

    @PostMapping()
    public ResponseEntity<String> novoRegistro(@RequestParam("mac") String mac, @RequestParam("conteudo") String conteudo) {
        
        Microcontrolador equipConnect = macService.buscarEquipConnect(mac);

        if(equipConnect == null) {
            throw new Error("Mac não reconhecido.");
        }
        
        rService.salvar(new Registro(equipConnect, conteudo, new Date().toString()));
        
        return ResponseEntity.ok().body(conteudo);
    }           


}
