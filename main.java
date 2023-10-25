class Cliente {
    String nome;
    int localizacao; // Supondo que localização seja um valor numérico

    public void requererCorrida(int localizacao, Gerenciador gerente) {
		gerente.ChamaMotoristas();
        // Lógica para requerer uma corrida com a localização fornecida
    }
}

class Motorista {
    String nome;
    boolean disponivel = true;

    public void aceitarCorrida() {
		if (disponivel){
				try {
					this.disponivel = false;
					System.out.println("Corrida aceita por: " + nome);
					Thread.sleep(3000);
			} catch (InterruptedException e) {
					// Lidar com a exceção, se necessário
					e.printStackTrace();
			}
			this.disponivel = true;
		} else {
			System.out.println("Corrida recusada por: " + nome);
		}
    }
}

class Gerenciador {
    Motorista[] vetorMotorista;

	public void ChamaMotoristas() {
		for (int i =0; i < vetorMotorista.length; i++) {
			vetorMotorista[i].aceitarCorrida();	
		}
	}
}

class Main {
    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        cliente.nome = "Cliente 1";
        cliente.localizacao = (int) (Math.random() * 100); // Localização aleatória

        Motorista motorista1 = new Motorista();
        motorista1.nome = "Alex";

        Motorista motorista2 = new Motorista();
        motorista2.nome = "Barba Negra";

		Motorista motorista3 = new Motorista();
        motorista3.nome = "Carlos";
		motorista3.disponivel = false;

        Gerenciador gerenciador = new Gerenciador();
        gerenciador.vetorMotorista = new Motorista[]{motorista1, motorista2, motorista3};

        // Requerer uma corrida
        cliente.requererCorrida(cliente.localizacao, gerenciador);
    }
}
