function acionarDia() {
	
	var input1 = document.getElementById('input_1');
	var input2 = document.getElementById('input_2');
	
	input1.removeAttribute("disabled");
	
	if(!(input2.hasAttribute("disabled"))) {
		input2.setAttribute("disabled", true);
	}
}

function acionarDiaSemana() {
	
	var input1 = document.getElementById('input_1');
	var input2 = document.getElementById('input_2');
	
	if(!(input1.hasAttribute("disabled"))) {
		input1.setAttribute("disabled", true);
	}
	
	input2.removeAttribute("disabled");
}