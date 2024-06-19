package com.ifms.arcondicionado.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ifms.arcondicionado.modelos.Microcontrolador;
import com.ifms.arcondicionado.servicos.MicrocontroladorService;

@RestController
public class PingController {
	
	@Autowired
	MicrocontroladorService macService;
	
	@PostMapping("/ping")
	public String ping(@RequestParam("mac") String macRecebido, @RequestParam("ip") String ipRecebido) {
		
		Microcontrolador mac = macService.buscarEquipConnect(macRecebido);
		
		if(mac != null) {
			mac.setIpv4(ipRecebido);
			mac.setStatus(true);
			macService.salvarEquipConnect(mac);
		}else {
			mac = new Microcontrolador(macRecebido, ipRecebido, true);
			macService.salvarEquipConnect(mac);
		}
		
		return "OK";
	}
	
}
