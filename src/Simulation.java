//Dao Mai - 1575296
//Operating system
//Programming project 2

import java.util.Random;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;


public class Simulation {
    public static final int capacity = 10;
    public static int mutex = 1;
    public static int empty = capacity;
    public static int full = 0;
    public static LinkedList<Integer> buffer = new LinkedList<>();
    public static Random random = new Random();
    public static boolean isRunning = true;
    
    public static boolean P (int Semaphores){
        if (Semaphores > 0){
            Semaphores--;
            return true;
        }else
            return false;
    }
    
    public static void V (int Semaphores){
        if(Semaphores < capacity)
        Semaphores++;
    }
    
    public static void main(String[] args) throws IOException {
        
        long startTime = System.currentTimeMillis();
        //The file will be created and saved in the default directory of this project
        FileWriter producerFile = new FileWriter("producer.txt");
        PrintWriter producerPrint = new PrintWriter(producerFile);
        Thread producer = new Thread() {
        public void run() {
                int itemProduce = 0;
                int insertion = 0;
                producerPrint.println("Producer");
            	while((System.currentTimeMillis() - startTime) < 15000) {
                        try {
                            Thread.sleep(random.nextInt(91) + 10);
                            if(buffer.size() < capacity){
                            if(P(mutex)){
                            P(empty);
                            buffer.add(itemProduce);
                            System.out.println(System.currentTimeMillis()+ ", Placing " + itemProduce + 
                                        " in the buffer location " + insertion + ".");
                            producerPrint.println(System.currentTimeMillis()+ ", Placing " + itemProduce + 
                                        " in the buffer location " + insertion + ".");
                            itemProduce = (itemProduce + 1) % 15;
                            insertion = (insertion + 1) % 10;
                            V(mutex);
                            V(full);
                            }
                            
                        }

			} catch (InterruptedException e) {
			}
                        
            
		} 
                producerPrint.close();
        }
        
    };
        //The file will be created and saved in the default directory of this project
        FileWriter consumerFile = new FileWriter("consumer.txt");
        PrintWriter consumerPrint = new PrintWriter(consumerFile);
        Thread consumer = new Thread() {
        public void run() {
                int itemConsume;
                int retrieval = 0;
                consumerPrint.println("Consumer");
            	while((System.currentTimeMillis() - startTime) < 15000) {
                        try {
                            Thread.sleep(random.nextInt(91) + 10);
                                
                            if(buffer.size() == 0){
                            Thread.sleep(random.nextInt(91) + 10);
                            }
                            else if (buffer.size() > 0){
                            if(P(mutex)){
                            P(full);
                            itemConsume = buffer.removeFirst();
                            System.out.println(System.currentTimeMillis()+ ", Consuming " + itemConsume + 
                                        " in the buffer location " + retrieval + ".");
                            consumerPrint.println(System.currentTimeMillis()+ ", Consuming " + itemConsume + 
                                        " in the buffer location " + retrieval + ".");
                            retrieval = (retrieval + 1) % 10;
                            V(empty);
                            V(mutex);
                        }
                        }

			} catch (InterruptedException e) {
			}

                        }
                        consumerPrint.close();
                        
		}
        
        
    };
        producer.start();
        consumer.start();
}
    
}
