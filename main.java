import java.util.concurrent.Semaphore;

class GlobalVals {
  static boolean corrida = false;
  static boolean mutex_flag = true;
}

class Cliente {
    String nome;
    int localizacao;

    public void requererCorrida(Gerenciador gerente) {
		gerente.ChamaMotoristas();
    }
}

class Motorista {
    String nome;
    boolean disponivel = true;

    public void aceitarCorrida() {
        while(GlobalVals.corrida == false); // do nothing

		if (disponivel && GlobalVals.mutex_flag){
				try {
                    GlobalVals.mutex_flag = false;
					this.disponivel = false;
					System.out.println("Corrida aceita por: " + nome);
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

	public void ChamaMotoristas() {

        Thread th1 = new Thread(vetorMotorista[0]::aceitarCorrida);
		Thread th2 = new Thread(vetorMotorista[1]::aceitarCorrida);
        Thread th3 = new Thread(vetorMotorista[2]::aceitarCorrida);
        Thread th4 = new Thread(vetorMotorista[3]::aceitarCorrida);

        th1.start();
        th2.start();
        th3.start();
        th4.start();

	}
}

class Main {
    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        cliente.nome = "Cliente 1";
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

        cliente.requererCorrida(gerenciador);

        GlobalVals.corrida = true;
    }
}
