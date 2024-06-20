package com.ifms.arcondicionado.componentes;

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
import com.ifms.arcondicionado.modelos.Microcontrolador;
import com.ifms.arcondicionado.servicos.AgendaService;
import com.ifms.arcondicionado.servicos.MicrocontroladorService;
import jakarta.transaction.Transactional;

@Component
@Transactional
public class TimerComponent implements CommandLineRunner{
	
	@Autowired
	AgendaService agendaService;
	
	@Autowired
	MicrocontroladorService macService;

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

	public void resetarStatus() {
		for(Microcontrolador i : macService.buscarEquipConnects()) {
			i.setStatus(false);
			macService.salvar(i);
		}
	}
}


