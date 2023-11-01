import java.util.concurrent.Semaphore;

class Main {
    public static void main(String[] args) {
        Cliente cliente1 = new Cliente();
        cliente1.nome = "Tadioto";
        cliente1.localizacao = (int) (Math.random() * 100); // Localização aleatória

        Cliente cliente2 = new Cliente();
        cliente2.nome = "CalsaCurta";
        cliente2.localizacao = (int) (Math.random() * 100); // Localização aleatória

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

        cliente1.requererCorrida(gerenciador);
        cliente2.requererCorrida(gerenciador);

        GlobalVals.corrida = true;
    }
}

class GlobalVals {
  static volatile boolean corrida = false;
  static volatile boolean mutex_flag = true;
  static volatile boolean corrida_ja_executada = false;
  Semaphore s;
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
        while(GlobalVals.corrida == false); // do nothing

		if (disponivel && GlobalVals.mutex_flag){
				try {
					this.disponivel = false;
          GlobalVals.mutex_flag = false;
					System.out.println("Corrida de " + nome_cliente + " aceita por: " + nome);
					Thread.sleep(3000);
			    } catch (InterruptedException e) {
					System.out.println("Deu errado");
		    }
			this.disponivel = true;
		} else {
			System.out.println("Corrida de " + nome_cliente + " RECUSADA por: " + nome);;
		}
    }
}

class Gerenciador {
     Motorista[] vetorMotorista;

	public void ChamaMotoristas(String nome_cliente) {

        //corrida_ja_executada = false;
        Thread th1 = new Thread(() -> vetorMotorista[0].aceitarCorrida(nome_cliente));
        Thread th2 = new Thread(() -> vetorMotorista[1].aceitarCorrida(nome_cliente));
        Thread th3 = new Thread(() -> vetorMotorista[2].aceitarCorrida(nome_cliente));
        Thread th4 = new Thread(() -> vetorMotorista[3].aceitarCorrida(nome_cliente));

        th1.start();
        th2.start();
        th3.start();
        th4.start();
	}
}

