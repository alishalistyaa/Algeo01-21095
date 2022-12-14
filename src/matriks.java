
/* Kelas ADT Matriks */
// Import library
import java.util.Scanner;
import java.io.File; 
import java.io.BufferedWriter; 
import java.io.FileWriter; 
import java.io.FileNotFoundException;
import java.io.IOException;

public class matriks {
    
    /* CreateMatriks dengan maximal CAPACITY */
    // Atribut
    Scanner in = new Scanner (System.in);
    int CAPACITY = 100;
    double[][] Mat = new double[CAPACITY][CAPACITY];
    
    
    public int jumlahKolom = 0;
    public int jumlahBaris = 0;


    // Method:
    public void bacaMatriks(int m, int n){
        /* Fungsi untuk mengisi elemen Matriks */
        // untuk baca matriks maksimal input 50 kolom, walaupun agak gila kalo mau masukin segitu pake terminal
        // Kamus Lokal
        String line;
        String[] row  = new String[100];
        double[] cache = new double[50];

        int i, j;
        boolean valid = false;
        // Algoritma
        
        this.jumlahBaris = m;
        this.jumlahKolom = n;

        for(i=0; i<m; i++){
            do {
                valid = false;
                line = in.nextLine();
                row = line.split(" ");

                try {
                    for(j = 0; j < row.length; j++){
                        cache[j] = Double.parseDouble(row[j]);
                    }

                    if (row.length == jumlahKolom){
                        for (j = 0; j < row.length; j++){
                            this.Mat[i][j] = cache[j];
                        }
                        valid = true;
                    }
                    else{
                        System.out.println("Jumlah input tidak valid, masukkan " + n + " buah bilangan.");
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Terdapat input yang tidak valid, mohon ulangi input");
                    valid = false;
                }
            } while (!valid);
        }
    }

    /* Baca Matriks dari File */
    public void bacaFileMatriks(String filename){
        // Kamus Lokal
        File file = new File(filename);
        int i,j;
        int countElmt;

        // Algoritma
        
        try{ // Untuk validasi dan dapat error message
            Scanner bacafile = new Scanner (file);
            countElmt = 0;
    
            // Menghitung banyaknya kolom dan baris
            while(bacafile.hasNextLine()){
                this.jumlahBaris++;
                
                // Membaca banyak double
                Scanner bacakolom = new Scanner(bacafile.nextLine());
                    while(bacakolom.hasNextDouble()){
                        countElmt++;
                        bacakolom.nextDouble();
                    }
                }
    
            // Testing
            this.jumlahKolom = (countElmt + this.jumlahBaris -1) / this.jumlahBaris ;
    
            // close scanner
            bacafile.close();
    
            // Membaca integer dari file
            bacafile = new Scanner (file); // refresh dr atas
            for(i=0; i<this.jumlahBaris; i++){
                for(j=0; j<this.jumlahKolom; j++){
                    if(bacafile.hasNextDouble()){
                        this.Mat[i][j] = bacafile.nextDouble();
                    }
                }
            }

        // Jika file tidak ditemukan, maka output error mess
        } catch (FileNotFoundException e) {
        System.out.println(e.getMessage());}
    }
    
    public void bacaFileMatriksBolong(String filename, int nKosong){
        // Membuat matriks dengan bagian baris terbawah tidak lengkap sebanyak nbBolong element
        /* DEFAULT nKosong
         * Interpolasi Polinom: 1
         * Bicubic Interpolation: 2
         * Regresi Linear Berganda: 1
         */
        // PREKONDISI: nbBolong < jumlahKolom
        // Kamus Lokal
        File file = new File(filename);
        int i,j;
        int countElmt;

        // Algoritma
        
        try{ // Untuk validasi dan dapat error message
            Scanner bacafile = new Scanner (file);
            countElmt = 0;
    
            // Menghitung banyaknya kolom dan baris
            while(bacafile.hasNextLine()){
                this.jumlahBaris++;
                
                // Membaca banyak double
                Scanner bacakolom = new Scanner(bacafile.nextLine());
                    while(bacakolom.hasNextDouble()){
                        countElmt++;
                        bacakolom.nextDouble();
                    }
                }
    
            // Testing
            this.jumlahKolom = (countElmt + nKosong) / this.jumlahBaris ;
    
            // close scanner
            bacafile.close();
    
            // Membaca integer dari file
            bacafile = new Scanner (file); // refresh dr atas
            for(i=0; i<this.jumlahBaris; i++){
                for(j=0; j<this.jumlahKolom; j++){
                    if(bacafile.hasNextDouble()){
                        this.Mat[i][j] = bacafile.nextDouble();
                    }
                }
            }

            // Mengisi bagian yang kosong dengan -999.0
            for (int k = this.jumlahKolom - 1; k >= this.jumlahKolom - nKosong; k--) {
                this.Mat[this.jumlahBaris - 1][k] = -999.0;
            }

        // Jika file tidak ditemukan, maka output error mess
        } catch (FileNotFoundException e) {
        System.out.println(e.getMessage());}
    }

    /* Write File dari Matriks */
    public void writeMatrixFile( matriks m){
        // Kamus Lokal
        int i, j;
        String filename;

        // Algoritma
        System.out.print("\nMasukkan nama file: ");
        filename = in.nextLine() + ".txt";
        try {
            // Buat file
            BufferedWriter bw = new BufferedWriter(new FileWriter("./test/" + filename));

            // Write Perline
            for (i= 0; i<m.jumlahBaris; i++){
                for (j=0; j<m.jumlahKolom; j++){
                    bw.write(m.Mat[i][j] + ((j == m.jumlahKolom-1) ? "" : " "));
                }
            bw.newLine();
            }
            bw.flush();
            bw.close();

        // Handling Error
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }


    /* sebenernya ini gaperlu sih */
    public double getComponent(int n, int m){
        /* Fungsi untuk mendapatkan komponen matriks */
        // Kamus Lokal

        // Algoritma
        return this.Mat[n][m];
    }

    /* Apakah matriks penuh */
    public boolean penuhRow() {
        return (this.jumlahKolom == CAPACITY);
    }
    public boolean penuhCol() {
        return (this.jumlahBaris == CAPACITY);
    }

    public boolean isAllZero() {
        // Mengecek apakah semua elemen dalam matriks bernilai nol
        int i, j;
        boolean foundNonZero;
        foundNonZero = false;
        for (i = 0; i < this.jumlahBaris && !foundNonZero; i++) {
            for (j = 0; j < this.jumlahKolom && !foundNonZero; j++) {
                foundNonZero = (this.Mat[i][j] != 0);
            }
        }
        return !foundNonZero;
    }
    
    public void tulisMatriks(){
        /* I.S. Matriks terdefinisi */
        /* Menuliskan matriks pada layar */
        // Kamus Lokal
        int i, j;
        // Algoritma
        for(i = 0; i < this.jumlahBaris; i++) {
            System.out.print("| ");
            for (j = 0; j < this.jumlahKolom; j++) {
                System.out.print(this.Mat[i][j]);
                System.out.print(" ");
            }
            System.out.print("|\n");
        }  
    }  

    public void resetCap(int newCap){
        //mengubah kapasitas matrix
        //matrix dikosongkan
        this.CAPACITY = newCap;
        this.Mat = new double[CAPACITY][CAPACITY];
        this.jumlahKolom = 0;
        this.jumlahBaris = 0;
    }
}