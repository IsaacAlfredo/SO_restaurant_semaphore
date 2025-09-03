package restaurant;
import java.util.concurrent.Semaphore;

public class Client extends Thread{
	private int id;
    private Semaphore table; // mesa escolhida pelo cliente
    private Semaphore[] tables; // lista de mesas disponiveis


    public Client(int id, Semaphore[] tables) {
        this.id = id;
        this.tables = tables;
    }

    //cliente aguarda
    private void waitTable() throws InterruptedException {
        System.out.println("Cliente " + id + " está aguardando...");
        Thread.sleep((int)(Math.random() * 50000));
    }
    
    //cliente busca por uma mesa
    private Semaphore searchTable() throws InterruptedException {
    	
    	while(true) {
    		waitTable(); // cliente aguarda antes de procurar (ajuda a evitar varios clientes tentando acessar a mesma mesa de uma vez na primeria execução)
    		System.out.println("Cliente " + id + " está procurando uma mesa...");
            for (int i = 0; i < tables.length; i++) {
            	if(tables[i].availablePermits()>0) {
            		this.table = tables[i];
                    System.out.println("Cliente " + id + " encontrou uma mesa!");
                    return tables[i]; // retorna a mesa encontrada para que o cliente solicite acesso (acquire)
// os clientes buscam uma mesa percorrendo a lista inteira sempre depois de aguardar, até que encontrem uma
// pode acontecer de dois clientes encontrarem a mesma mesa ao mesmo tempo, nesse caso o segundo a solicitar acesso irá aguardar o primeiro finalizar, ficando na fila do semaforo                  
            	}
            }
    	} 
    }
    //cliente senta na mesa e come
    private void eat() throws InterruptedException {
        System.out.println("Cliente " + id + " está comendo!");
        Thread.sleep((int)(Math.random() * 30000));
    }
    
    // executa tudo o que foi implementado anteriormente: cliente procura a mesa até encontrar uma, solicita acesso, come e libera a mesa em seguida, encerrando a thread
    @Override
    public void run() {
        try {
            	this.table = searchTable();
            	table.acquire();
                eat();
                table.release();
                System.out.println("Cliente " + id + " saiu da mesa.");       
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}