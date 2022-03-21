package prueba;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Corra
 */
public class Prueba {
    public static void main(String[] args) throws IOException, IllegalAccessException {
        // TODO code application logic here
        ITunes i = new ITunes();
        Scanner input = new Scanner(System.in);
        input.useDelimiter("\n");
        int op;
        do {
            menu();
            op = input.nextInt();
            
            switch(op) {
                case 1:
                    System.out.println("Nombre de cancion: ");
                    String nombre = input.next();
                    System.out.println("Nombre cantante: ");
                    String cantante = input.next();
                    System.out.println("Precio de cancion: ");
                    double precio = input.nextDouble();
                    i.addSong(nombre, cantante, precio);
                    break;
                    
                case 2:
                    System.out.println("Codigo de cancion: ");
                    int code = input.nextInt();
                    System.out.println("Ingrese las estrellas (0 - 5) no decimales");
                    int estrellas = input.nextInt();
                    try {
                        i.reviewSong(code, estrellas);
                    } catch(IllegalAccessException e) {
                        e.printStackTrace(System.out);
                        System.out.println("No se encontro la cancion");
                    }
                    break;
                    
                case 3:
                    
                    System.out.println("Ingrese el codigo de la cancion");
                    int codeC = input.nextInt();
                    System.out.println("Nombre del cliente: ");
                    String cliente = input.next();
                    i.downloadSong(codeC, cliente);
                    break;
                    
                case 4:
                    i.reportSongs("Archivos/reportSongs.txt");
                    break;
                    
                case 5:
                    System.out.println("Ingrese el codigo de una cancion: ");
                    int codeCan = input.nextInt();
                    i.informacionCancion(codeCan);
                    break;
                    
                case 6:
                    System.out.println("Adios!!");
                    System.exit(0);
                    break;
                    
                default:
                    System.out.println("Opcion invalida");
            }
        } while(true);
        
    }
    
    public static void menu() {
        System.out.print("\n\nITUNES"
                + "\n1. Add song"
                + "\n2. Review a song"
                + "\n3. download a song"
                + "\n4. reports songs"
                + "\n5. information of a song"
                + "\n6. EXIT"
                + "\nOpcion: ");
    }
}
