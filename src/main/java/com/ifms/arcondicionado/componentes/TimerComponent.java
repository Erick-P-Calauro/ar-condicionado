package com.ifms.arcondicionado.componentes;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.ifms.arcondicionado.modelos.Agenda;
import com.ifms.arcondicionado.modelos.Comando;
import com.ifms.arcondicionado.modelos.Equipamento;
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
		
		TimerTask tarefa = new TimerTask() {			
			@Override
            public void run() {
				agendar();
				resetarStatus();
            }
        };
        
        timer.schedule(tarefa, 0, 60000);	
		
	}
	
	public boolean verificarAgenda(Agenda agenda) {
		
		List<Integer> info = informarDataHora();
		
		if(agenda.getAtivo() == true) {

			if(agenda.getHora() == info.get(1) && agenda.getMinuto() == info.get(2)) {

				if(agenda.getDia() == info.get(0) && agenda.getDiaSemana() == -1) {
					return true;
				}

				if((agenda.getDiaSemana() == info.get(3) || agenda.getDiaSemana() == 0) && agenda.getDia() == -1) {
					return true;
				}

				if(agenda.getDia() == 0 || agenda.getDiaSemana() == 0) {
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

		//System.out.print(data + " " + hora + " " + minuto + " " + diaSemanaInt);
		
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
			
			// Caso alguma agenda deva ser executada
			if(result == true) {
				System.out.print("Começou a executar...");

				// Executar passo a passo para cada comando
				for(int x = 0; x < agenda.getEquipamentos().size(); x++) {
					// Passo a passo : 
						// IPV4 Do Equipamento
						// Comando correspondente ao tipo do comando da agenda
						// Modelo do equipamento
						// Montar Url
						// Executar Comando
						// Receber resposta
						// Gerar Log

					Equipamento equipamento = agenda.getEquipamentos().get(x);
					String ipv4 = equipamento.getMicrocontrolador().getIpv4();
					Comando comando = null;
					
					for(int i = 0; i < equipamento.getModelo().getComando().size(); i++) {
						Comando comandoTeste = equipamento.getModelo().getComando().get(i);

						if(comandoTeste.getTipoComando() == agenda.getTipoComando()) {
							comando = comandoTeste;
							break;
						}
					}

					if(comando == null) {
						System.out.println(equipamento.getId() + "não apresenta comando do tipo necessário.");
					}else {
						String comandoParam;
						
						if(comando.getRaw().isEmpty()) {
							comandoParam = comando.getHexadecimal();
						}else {
							comandoParam = comando.getRaw();
						}

						try {
							HttpClient client = HttpClient.newBuilder().build();

							HttpRequest request = HttpRequest.newBuilder()
											.uri(new URI("http://"+ipv4+"/command?comando="+comandoParam+"&modelo="+equipamento.getModelo().getNome()))
													.build();
							System.out.print("\nRequisição de "+ equipamento.getId() +" lançada, aguardando resposta...");

							CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
							String corpoResponse = response.join().body();
							int codigoResponse = response.join().statusCode();
							
							System.out.println("\nResposta : " + corpoResponse + " - "+ codigoResponse);

						} catch (Exception e ) {
							System.out.println("\nRequisição de " + equipamento.getId() + " apresentou erro.");
						} 
					}
				}
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


