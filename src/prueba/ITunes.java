package prueba;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Scanner;

public class ITunes {
    File carpeta;
    RandomAccessFile codigos;
    RandomAccessFile songs;
    RandomAccessFile downloads;
    
    
    public ITunes() throws IOException {
        carpeta = new File("Archivos");
        carpeta.mkdir();
        
        codigos = new RandomAccessFile(carpeta + "/codigos.itn", "rw");
        songs = new RandomAccessFile(carpeta + "/songs.itn", "rw");
        downloads = new RandomAccessFile(carpeta + "/downloads.itn", "rw");
        initCodes();
    }
    
    private void initCodes() throws IOException {
        if(codigos.length() == 0) {
            codigos.writeInt(1);
            codigos.writeInt(1);
        }
    }
    
    public void addSong(String nombre, String cantante, double precio)
            throws IOException {
        songs.seek(songs.length());
        int codigo = getCodigo(0); //No se como es esta funcion ahorita
        songs.writeInt(codigo); // Codigo
        songs.writeUTF(nombre); // Nombre Cancion
        songs.writeUTF(cantante); // Nombre Cantante
        songs.writeDouble(precio); // Precio Cancion
        songs.writeDouble(0); // Estrellas dadas
        songs.writeDouble(0); // Reviews
    }
    
    public void reviewSong(int code, int stars) throws IOException, IllegalAccessException {
        //Se busca el codigo
        songs.seek(0);
        while(songs.getFilePointer() < songs.length()) {
            int codigo = songs.readInt();
            String nombre = songs.readUTF();
            String cantante = songs.readUTF();
            double precio = songs.readDouble();
            long pos = songs.getFilePointer(); //posicion del registro
            int estrellas = songs.readInt();
            double review = songs.readDouble();
            
            //Si lo encuentra... que escriba 
            if(code == codigo) {
                if(stars >= 0 && stars <= 5) {
                    estrellas += stars;
                    review = ++review;
                    songs.seek(pos);
                    songs.writeInt(estrellas);
                    songs.writeDouble(review);
                } else {
                    //Aca debe tirar el exception
                    throw new IllegalAccessException();
                }
            } else System.out.println("No se encontro el codigo deseado: "+ code);
        } 
    }
      
    private int getCodigo(long offset) throws IOException {
        codigos.seek(0);
        int codigoSong = codigos.readInt();
        int codigoDownLoad = codigos.readInt();
        
        if(songs.length() != 0) {
            
            if(offset == 0) return ++codigoSong;
            else if(offset == 4) return ++codigoDownLoad;
            
        } else if(songs.length() != 0 && downloads.length() == 0) {
            
            if(offset == 0) return ++codigoSong;
            else if(offset == 4) return codigoDownLoad;

        } else {

            if(offset == 0) return codigoSong;
            else if(offset == 4) return codigoDownLoad;

        }
        
        return -1;
    }
    
    public void downloadSong(int codigoSong, String cliente) throws IOException {
        codigos.seek(0); //reseteamos el puntero
        int codigoCancion = songs.readInt();
        int codigoDescarga = songs.readInt();
        int sumaCDescarga = ++codigoDescarga;

        //Validar que el codigoSong exista
        songs.seek(0);
        while(songs.getFilePointer() < songs.length()) {
            int codigo = songs.readInt();
            songs.readUTF();
            songs.readUTF();
            double precio = songs.readDouble();
            songs.readInt();
            songs.readDouble();
            
            if(codigoSong == codigo) {
                downloads.seek(downloads.length()); //Ultimo registro de descarga
                if(downloads.length() != 0)
                    downloads.writeInt(sumaCDescarga);
                else
                    downloads.writeInt(codigoDescarga);
                
                long fecha = Calendar.getInstance().getTimeInMillis();
                downloads.writeLong(fecha);
                downloads.writeInt(codigo);
                downloads.writeDouble(precio);
                return; //Truco para detener una funcion void, el compilador lo agrega hasta el final
            } else System.out.println("Codigo no existente: "+ codigoSong);
        }
    }
    
    public void reportSongs(String pathNameFile) throws FileNotFoundException, IOException {
        //codigo - titulo - cantante - precio - rating
        
        PrintWriter pw = new PrintWriter(new FileWriter(pathNameFile, true));
        pw.print(""); //se borra el contenido
        
        while(songs.getFilePointer() < songs.length()) {
            int codigo = songs.readInt();
            pw.println(codigo+"\n");
            String nombre = songs.readUTF();
            pw.println(nombre+"\n");
            String cantante = songs.readUTF();
            pw.println(cantante+"\n");
            double precio = songs.readDouble();
            pw.println(precio+"\n");
            int estrellas = songs.readInt();
            double reviews = songs.readDouble();
            double rateOfSong = estrellas/reviews;
            pw.println(rateOfSong+"\n\n");
        }
        pw.close();
        Scanner readFile = new Scanner(pathNameFile);
        readFile.useDelimiter("\n");
        
        while(readFile.hasNextLine()) {
            System.out.println(readFile.nextLine());
        }
    }
    
    public void informacionCancion(int codeSong) throws IOException {
        int descargasHechas = 0;
        songs.seek(0);
        downloads.seek(0);
        
        while(songs.getFilePointer() < songs.length()) {
            int codigo = songs.readInt();
            String nombre = songs.readUTF();
            String cantante = songs.readUTF();
            double precio = songs.readDouble();
            int estrellas = songs.readInt();
            double review = songs.readDouble();
            double rating = (estrellas/review);
            if(codeSong == codigo) {
                downloads.seek(0);
                while(downloads.getFilePointer() < downloads.length()) {
                    int codigoDownLoad = downloads.readInt();
                    long fecha = downloads.readLong();
                    int codigoSong = downloads.readInt();
                    String nombreCliente = downloads.readUTF();
                    double precioC = downloads.readDouble();
                    if(codigoSong == codeSong) {
                        descargasHechas++;
                    }
                }
                
                System.out.println("\nCodigo de la cancion: "+ codigo
                    +"\nNombre: "+ nombre
                    +"\nCantante: "+ cantante
                    +"\nPrecio: "+ precio
                    +"\nEstrellas acumuladas: "+ estrellas
                    +"\nCantidad de reviews: "+ review
                    +"\nRating: "+ rating
                );
            }
            
        }
    }
    
    
    
    private boolean buscarCodigo(int codigo) throws IOException {
        while(codigos.getFilePointer() < codigos.length()) {
            int codeSong = codigos.readInt();
            int codeDownload = codigos.readInt();
            if(codeSong == codigo) {
                return true;
            }
        }
        return false;
    }
}
