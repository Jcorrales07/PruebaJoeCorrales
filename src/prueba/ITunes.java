package prueba;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

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
    
    //Dudoso
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
    
    public void reviewSong(int code, int stars) throws IOException {
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
                if(stars <= 0 && stars >= 5) {
                    estrellas += stars;
                    review = ++review;
                    songs.seek(pos);
                    songs.writeInt(estrellas);
                    songs.writeDouble(review);
                } else {
                    //Aca debe tirar el exception
                    System.out.println("No se pudo añadir, su rating: " + stars);
                }
            } else {
                System.out.println("No se encontro el codigo");
            }
        } 
    }
      
    public void downloadSong(int codigoSong, String cliente) throws IOException {
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
                if() {
                    
                }
            }
        }
        
        
        
        
        //se obtiene el proximo codiggo disponible para una descarga
        //
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
