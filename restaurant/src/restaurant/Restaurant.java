package restaurant;
import java.util.concurrent.Semaphore;

public class Restaurant {
    public static void main(String[] args) {
        int M = 5; //numero de mesas
        int C = 50; //numero de clientes
        Semaphore[] tables = new Semaphore[M]; //lista de mesas
        Client[] clientes = new Client[C]; //lista de clientes

        for (int i = 0; i < M; i++) {
        	tables[i] = new Semaphore(1); //inicializa os semaforos (mesas) com 1 vaga disponivel em cada
        }
        
        for (int i = 0; i < C; i++) {
            clientes[i] = new Client(i, tables); // inicializa cada cliente, passando a lista de mesas para cada um
            clientes[i].start();
        }
    }
}
