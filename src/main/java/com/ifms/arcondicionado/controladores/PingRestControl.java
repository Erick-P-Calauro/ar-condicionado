package com.ifms.arcondicionado.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ifms.arcondicionado.modelos.EquipConnect;
import com.ifms.arcondicionado.servicos.EquipConnectService;

@RestController
public class PingRestControl {
	
	@Autowired
	EquipConnectService macService;
	
	@PostMapping("/ping")
	public String ping(@RequestParam("mac") String macRecebido, @RequestParam("ip") String ipRecebido) {
		
		EquipConnect mac = macService.buscarEquipConnect(macRecebido);
		
		if(mac != null) {
			mac.setIp(ipRecebido);
			mac.setStatus(true);
			macService.salvarEquipConnect(mac);
		}else {
			mac = new EquipConnect(macRecebido, ipRecebido, true);
			macService.salvarEquipConnect(mac);
		}
		
		return "OK";
	}
	
}
