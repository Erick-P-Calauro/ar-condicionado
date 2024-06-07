package com.ifms.arcondicionado.components;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.ifms.arcondicionado.modelos.Agenda;
import com.ifms.arcondicionado.modelos.EquipConnect;
import com.ifms.arcondicionado.servicos.AgendaService;
import com.ifms.arcondicionado.servicos.EquipConnectService;

import jakarta.transaction.Transactional;

/**
 * Classe que tem como objetivo realizar a verificação do horário atual e servir
 * como base para as funcionalidades de agenda de comandos e ping de microcontroladores
 * 
 * <p>@CommandLineRunner : disponibiliza função que é executada antes do resto do sistema.</p>
 * 
 * @since Release 1.0
 * @version 1.0
 * */
@Component
@Transactional
public class TimerComponent implements CommandLineRunner{
	
	@Autowired
	AgendaService agendaService;
	
	@Autowired
	EquipConnectService macService;
	
	/**
	 * <p>Método principal que é executado antes do resto do sistema</p>
	 * 
	 * <p>Implementa um contador programado para executar um trecho de código de minuto em minuto</p>
	 * */
	@Override
	public void run(String... args) throws Exception {
		
		// Timer Component
		Timer timer = new Timer();
		
		// Faltar completar o agendamento de comandos
		TimerTask tarefa = new TimerTask() {			
			@Override
            public void run() {
				resetarStatus();
				agendar();
            }
        };
        
        timer.schedule(tarefa, 0, 60000);	
		
	}
	
	/**
	 * Verifica se uma entidade agenda deve ser executada (considerando dia do mês ou dia da semana, hora e minuto.)
	 * 
	 * @param agenda
	 *	Recebe uma entidade agenda para verificação
	 * 
	 * @return 
	 * <p>True se o comando da agenda deve ser executado</p>
	 * <p>False se o comando da agenda não deve ser executado</p>
	 * 
	 * @since Release 1.0
	 * @version 1.0
	 * */
	public boolean verificarAgenda(Agenda agenda) {
		
		List<Integer> info = informarDataHora();
		
		if(agenda.getAtivo() == true) {
			// Ativo
			if(agenda.getHora() == info.get(1) && agenda.getMinuto() == info.get(2)) {
				// Mesma hora e minuto
				
				if(agenda.getDia() == info.get(0) && agenda.getDiaSemana() == -1) {
					return true;
				}
				if((agenda.getDiaSemana() == info.get(3) || agenda.getDiaSemana() == 0) && agenda.getDia() == -1) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	/**
	 * Método responsável por reunir informações de dia, hora, minuto e dia da semana.
	 * 
	 * @return
	 * Retorna uma Lista com os seguintes elementos :
	 * <ul>
		 * 	<li>int data (Dia do mês)</li>
		 * <li>int hora</li>
		 * <li>int minuto</li>
		 * <li>int dia da semana (1-Domingo, 2-Segunda, 3-Terça, 4-Quarta, 5-Quinta, 6-Sexta, 7-Sábado)</li>
	 * </ul>
	 * 
	 * @since Release 1.0
	 * @version 1.0
	 * */
	public List<Integer> informarDataHora() {
		
		int data = LocalDate.now().getDayOfMonth();
		int hora = LocalTime.now().getHour();
		int minuto = LocalTime.now().getMinute();
		String diaSemanaString = String.valueOf(LocalDate.now().getDayOfWeek().toString());
		int diaSemanaInt = -1;
		
		switch(diaSemanaString.toLowerCase().trim()) {
			case "sunday": 
				diaSemanaInt = 1;
				break;
			case "monday":
				diaSemanaInt = 2;
				break;
			case "tuesday":
				diaSemanaInt = 3;
				break;
			case "wednesday":
				diaSemanaInt = 4;
				break;
			case "thursday":
				diaSemanaInt = 5;
				break;
			case "friday":
				diaSemanaInt = 6;
				break;
			case "saturday":
				diaSemanaInt = 7;
				break;
			default:
				break;
				
		}
		
		ArrayList<Integer> info = new ArrayList<Integer>();
		
		info.add(data);
		info.add(hora);
		info.add(minuto);
		info.add(diaSemanaInt);
		
		return info;
		
	}
	
	/**
	 * Método responsável por executar os comandos das agendas
	 * <p>Ainda em implementação</p>
	 * */
	// Arrumar função de executarAgendamentos
	public void agendar() {
		List<Agenda> agendas = agendaService.buscarAgendas();
		List<Integer> horario = informarDataHora();
		
		System.out.println("\nVerificação : Horário : "+ horario.get(1)+ ":"+horario.get(2));
		
		for(Agenda agenda : agendas) {
			
			boolean result = verificarAgenda(agenda);
			
			System.out.println("\n" + agenda.getDescricao() + "\n" + result);
			
			if(result == true) {
				
				/*for(Equipamento e : agenda.getEquipamentos()) {
					Modelo m = e.getModelo();
					String url = "http://"+e.getIp()+"/comandos?comando="+agenda.getTipoComando().getNome()+"&modelo="+m.getNome();
					
					HttpClient client = HttpClient.newBuilder()
		                        .build();

					try {
						HttpRequest request = HttpRequest.newBuilder()
							            .uri(new URI(url))
							                    .build();
					} catch (URISyntaxException e1) {
						System.out.println(e1.toString());
					}
					System.out.print(url);
				}*/
			}
		}
	}
	
	/**
	 * Método responsável por transformar os status de todos os equipamentos em false
	 * <p>Serve para verificar se os microcontroladores estão 'vivos'</p>
	 * 
	 * @since Release 1.0
	 * @version 1.0
	 * */
	public void resetarStatus() {
		for(EquipConnect i : macService.buscarEquipConnects()) {
			i.setStatus(false);
			macService.salvarEquipConnect(i);
		}
	}
}


