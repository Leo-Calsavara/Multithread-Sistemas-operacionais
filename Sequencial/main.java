import java.util.concurrent.Semaphore;

class Main {
    public static void main(String[] args) {
        Cliente cliente1 = new Cliente();
        System.out.println("Criando Tadioto");
        cliente1.nome = "Tadioto";
        cliente1.localizacao = (int) (Math.random() * 100); // Localização aleatória

        Cliente cliente2 = new Cliente();
        System.out.println("Criando Calsavara");
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
        gerenciador.vetorMotorista = new Motorista[]{motorista1, motorista2, motorista3, motorista4}; // Sempre colocar os motoristar no vetorMotorista
        gerenciador.len_mot = 4; // Informar para o gerenciador quantos motoristas tem

        Corrida Corr1, Corr2;
        Corr1 = new Corrida();
        Corr2 = new Corrida();

        System.out.println("Requerendo corridas...");
        cliente1.requererCorrida(gerenciador, Corr1);
        cliente2.requererCorrida(gerenciador, Corr2);
    }
}

class Corrida { // Classe criada pensando em como controlar o acesso as corridas
  boolean espera_threads = false; // Esperar para todas as threads executarem ao mesmo tempo
  boolean corrida_ja_executada = false; // Corrida ja foi feita
  boolean mutex_flag = false; // Alguem ja esta fazendo a corrida
}

class Cliente {
    String nome;
    int localizacao; // Não foi utilizado, se tiverem alguma ideia é só falar

    public void requererCorrida(Gerenciador gerente, Corrida corrida) {
		gerente.ChamaMotoristas(this.nome, corrida);
    }
}

class Motorista {
    String nome;
    boolean disponivel = true;

    public void aceitarCorrida(String nome_cliente, Corrida corrida) {

		if (disponivel && !corrida.corrida_ja_executada){ // Se o motorista estiver diponivel and a corrida ainda não foi executada...
				try {
					this.disponivel = false; // Motorista não esta mais disponivel
          corrida.corrida_ja_executada = true; // Ativa a flag de já executada
					System.out.println("Corrida de " + nome_cliente + " aceita por: " + nome);
					Thread.sleep(3000); // Espera 3 segs
			    } catch (InterruptedException e) {
					System.out.println("Deu errado");
		    }
			this.disponivel = true;
		} else {
			System.out.println("Corrida de " + nome_cliente + " RECUSADA por: " + nome);
		}
    }
}

class Gerenciador {
     Motorista[] vetorMotorista;
     int len_mot; 
     Semaphore s;

	public void ChamaMotoristas(String nome, Corrida corrida) {
        for(int i=0; i < len_mot; i++)
            vetorMotorista[i].aceitarCorrida(nome, corrida); // Pede a todos os motoristas para aceitarem a corrida
	}
}

