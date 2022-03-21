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
    
    private int getCodigo(long offset) throws IOException {
        codigos.seek(offset);
        
        
        return codigo;
    }
    
    public void addSong(String nombre, String cantante, double precio)
            throws IOException {
        songs.seek(songs.length());
//        int codigo = getCodigo(0); //No se como es esta funcion ahorita
        songs.writeUTF(nombre); // Nombre Cancion
        songs.writeUTF(cantante);
        songs.writeDouble(precio);
        

        
        
    }
}
