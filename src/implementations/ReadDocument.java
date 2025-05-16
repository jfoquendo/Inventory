package implementations;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class ReadDocument {

    /*public static void main(String[] args) {
        String nameFile = "ejemplo.txt";
        try{
            FileWriter file = new FileWriter(nameFile);
            BufferedWriter write = new BufferedWriter(file);
            write.write("Mi primer archivo en java");
            write.newLine(); //Genera un salto de linea
            write.write("Otra linea");
            write.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}*/
// este es para leer el archivo
    public static void main(String[] args) {
        String nameFile = "ejemplo.txt";
        try {
            FileReader file = new FileReader(nameFile);         //forma larga
            BufferedReader reader = new BufferedReader(file); // forma larga
            //BufferedReader reader = new BufferedReader(new FileReader(nameFile));   Esta es la forma corta
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
