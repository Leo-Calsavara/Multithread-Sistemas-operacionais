import java.util.concurrent.Semaphore;

class Main {
    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        cliente.nome = "Tadioto";
        cliente.localizacao = (int) (Math.random() * 100); // Localização aleatória

        Motorista motorista1 = new Motorista();
        motorista1.nome = "SpongeBob";

        Motorista motorista2 = new Motorista();
        motorista2.nome = "Barba Negra";

		Motorista motorista3 = new Motorista();
        motorista3.nome = "Sandy";

        Motorista motorista4 = new Motorista();
        motorista4.nome = "Patrick";

        Gerenciador gerenciador = new Gerenciador();
        gerenciador.vetorMotorista = new Motorista[]{motorista1, motorista2, motorista3, motorista4};
        gerenciador.len_mot = 4;

        cliente.requererCorrida(gerenciador);
    }
}

class Cliente {
    String nome;
    int localizacao;

    public void requererCorrida(Gerenciador gerente) {
		gerente.ChamaMotoristas(this.nome);
    }
}

class Motorista {
    String nome;
    boolean disponivel = true;

    public void aceitarCorrida(String nome_cliente) {

		if (disponivel){
				try {
					this.disponivel = false;
					System.out.println("Corrida de " + nome_cliente + " aceita por: " + nome);
					Thread.sleep(3000);
			    } catch (InterruptedException e) {
					System.out.println("Deu errado");
		    }
			this.disponivel = true;
		} else {
			System.out.println("Corrida recusada por: " + nome);;
		}
    }
}

class Gerenciador {
     Motorista[] vetorMotorista;
     int len_mot; 
     Semaphore s;

	public void ChamaMotoristas(String nome) {
        for(int i=0; i < len_mot; i++) {
            vetorMotorista[i].aceitarCorrida(nome);
        }

	}
}

