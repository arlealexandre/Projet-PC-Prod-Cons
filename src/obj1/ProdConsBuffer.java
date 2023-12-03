package obj1;
public class ProdConsBuffer implements IProdConsBuffer {
    Message[] buffer;
    int nbMessage;
    int totalNbMessage;
    
    int in;
    int out;
    
    int size;

    int maxProdTime;
    int maxConsTime;

    /* 
     * Parameter : 
     * - size : The size of the buffer
     * - prodTime : The average time a production of a message take
     * - consTime : The average time a consommation of a message take 
     */
    public ProdConsBuffer (int size, int prodTime, int consTime) {
        this.buffer = new Message[size];
        this.nbMessage = 0;
        this.in = 0;
        this.out = 0;
        this.size = size;
        this.maxProdTime = (prodTime * 2 ) - 1;
        this.maxConsTime = (consTime * 2) - 1;
    }

    public synchronized void put(Message m) {
    
        while (this.nbMessage == size) {
            try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        int prodTime = (int)Math.floor(Math.random() * this.maxProdTime); // We randomised prodTime for better result but we keep an average of "prodTime"
        try { Thread.sleep(prodTime); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println("Prod"+Thread.currentThread().getName()+" a écrit "+m.toString()+" dans la case n°"+Integer.toString(in));

        this.buffer[in] = m;
        this.in = (in + 1) % size;
        this.nbMessage++;
        this.totalNbMessage++;

        notifyAll();
    }

    public synchronized Message get() throws InterruptedException {

        while (this.nbMessage == 0) {
            wait();
        }

        int consTime = (int)Math.floor(Math.random() * this.maxConsTime); // We randomised consTime for better result but we keep an average of "consTime"
        try { Thread.sleep(consTime); } catch (InterruptedException e) { e.printStackTrace(); }

        Message res = this.buffer[out];

        System.out.println("Cons"+Thread.currentThread().getName()+" a lu "+res.toString()+" dans la case n°"+Integer.toString(out));

        this.out = (out + 1) % size;
        this.nbMessage--;

        notifyAll();
        
        return res;
    }

    public int nmsg() {
        return this.nbMessage;
    }

    public int totmsg() {
        return this.totalNbMessage;
    }

}