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
        gerenciador.vetorMotorista = new Motorista[]{motorista1, motorista2, motorista3, motorista4};

        Corrida Corr1, Corr2;
        Corr1 = new Corrida();
        Corr2 = new Corrida();

        System.out.println("Requerendo corridas...");
        cliente1.requererCorrida(gerenciador, Corr1);
        cliente2.requererCorrida(gerenciador, Corr2);
  
        Corr1.espera_threads = true;
        Corr2.espera_threads = true;
    }
}

class Corrida {
  private Semaphore semaforo = new Semaphore(1);
  boolean espera_threads = false; // Esperar para todas as threads executarem ao mesmo tempo
  boolean corrida_ja_executada = false; // Corrida ja foi feita
  boolean mutex_flag = false; // Alguem ja esta fazendo a corrida

  public void acquire() throws InterruptedException {
    semaforo.acquire();
  }

  public void release() {
    semaforo.release();
  }
}

class Cliente {
    String nome;
    int localizacao;

    public void requererCorrida(Gerenciador gerente, Corrida corrida) {
		gerente.ChamaMotoristas(this.nome, corrida);
    }
}

class Motorista {
    String nome;
    boolean disponivel = true;

    public void aceitarCorrida(String nome_cliente, Corrida corrida) {
      while(corrida.espera_threads == false); // do nothing

      try {
        corrida.acquire();
        if (disponivel && !corrida.corrida_ja_executada){
            try {
              this.disponivel = false;
              corrida.corrida_ja_executada = true;
              corrida.release();
              System.out.println("Corrida de " + nome_cliente + " aceita por: " + nome);
              Thread.sleep(500);
              } catch (InterruptedException e) {
              System.out.println("Deu errado");
            }
          this.disponivel = true;
        } else {
          System.out.println("Corrida de " + nome_cliente + " RECUSADA por: " + nome);
          corrida.release();
        }
        } catch (InterruptedException e) {
          System.out.println("Deu errado");
        }
    }
}

class Gerenciador {
     Motorista[] vetorMotorista;

	public void ChamaMotoristas(String nome_cliente, Corrida corrida) {
        Thread th1 = new Thread(() -> vetorMotorista[0].aceitarCorrida(nome_cliente, corrida));
        Thread th2 = new Thread(() -> vetorMotorista[1].aceitarCorrida(nome_cliente, corrida));
        Thread th3 = new Thread(() -> vetorMotorista[2].aceitarCorrida(nome_cliente, corrida));
        Thread th4 = new Thread(() -> vetorMotorista[3].aceitarCorrida(nome_cliente, corrida));

        th1.start();
        th2.start();
        th3.start();
        th4.start();
	}
}

