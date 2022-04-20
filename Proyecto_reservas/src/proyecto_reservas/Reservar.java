package proyecto_reservas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Reservar {

    public static void main(String[] args) {
        CrearReserva();
    }

    public static void CrearReserva() {
        Scanner sc = new Scanner(System.in);
        FileReader fr = null;

        try {

            System.out.println("Escribe el nombre del fichero: ");
            String nombreFichero = sc.nextLine();

            File doc = new File(nombreFichero+".txt");
            Scanner obj = new Scanner(doc);
            ArrayList<String> fichero = new ArrayList();
            while (obj.hasNextLine()) {
                fichero.add(obj.nextLine());
            }
            
            
            String accion = "";
            

            while (!(accion.equals("SALIR"))) {

                System.out.println("¿Quieres RESERVAR, ANULAR RESERVA, CONSULTAR o SALIR?");
                accion = sc.nextLine();

                if (accion.toUpperCase().equals("RESERVAR")) {
                    
                    String fecha;
                    
                    for (int i = 0; i < fichero.size(); i++) {
                        if(fichero.get(i).charAt(1)=='D'){
                            fecha="";
                            for (int j = 3; j < fichero.get(i).length(); j++) {
                                fecha+= fichero.get(i).charAt(j);
                            }
                            System.out.println(fecha);
                        }
                    }
                    do {
                        do {
                            System.out.println("¿Que dia quieres reservar?");
                            fecha = sc.nextLine();
                        } while (comprobarFecha(fecha) == false);
                        if (fichero.contains("%D$"+fecha) == false) {
                            System.out.println("Error, no se ha encontrado ese dia");
                        }

                    } while (fichero.contains("%D$"+fecha) == false);

                    
                    
                    
                    String hora;
                    for (int i = fichero.indexOf("%D$"+fecha)+1; i < fichero.size(); i++) {
                        if(fichero.get(i).charAt(1)=='H'){
                            hora="";
                            for (int j = 3; j < fichero.get(i).length(); j++) {
                                hora+= fichero.get(i).charAt(j);
                            }
                            System.out.println(hora);
                        }else{
                            break;
                        }
                    }
                    
                    
                    do {
                        do {
                            System.out.println("¿Que hora quieres reservar?");
                            hora = sc.nextLine();
                        } while (comprobarHora(hora) == false);
                        if(hora.length()==1){
                            hora="0"+hora;
                        }
                        if (fichero.contains("%H$"+hora) == false) {
                            System.out.println("Error, no se ha encontrado esa hora");
                        }

                    } while (fichero.contains("%H$"+hora) == false);


                    System.out.println(hora);

                    System.out.println("Escribe tu nombre:");
                    String nombre = sc.nextLine();
                    System.out.println("Escribe tus apellidos");
                    String apellidos = sc.nextLine();
                    System.out.println("Escribe tu telefono");
                    String telefono = sc.nextLine();
                    System.out.println("Escribe tu correo electronico");
                    String correo = sc.nextLine();
                    String reserva = "%C$"+nombre+" "+apellidos+"$"+telefono+"$"+correo;
                    
                    for (int i = fichero.indexOf("%D$"+fecha)+1; i < fichero.size(); i++) {
                        if(fichero.get(i).equals("%H$"+hora)){
                            fichero.add(i+1,reserva);
                        }
                        if(fichero.get(i).charAt(1)=='D'){
                            break;
                        }
                    }
                    
                    
                    for (int i = 0; i < fichero.size(); i++) {
                        System.out.println(fichero.get(i));
                    }
                    

                }
                
                
                 if (accion.toUpperCase().equals("ANULAR")) {
                     System.out.println("Escribe tu telefono");
                     String telefono = sc.nextLine();
                     
                     for (int i = 0; i < fichero.size(); i++) {
                         if(fichero.get(i).contains(telefono)){
                             for (int j = i; j > 0; j--) {
                                 if(fichero.get(j).charAt(1)=='D'){
                                     System.out.println("Dia: "+fichero.get(j) + " Hora:" + fichero.get(i-1) + " Reserva: " + fichero.get(i));
                                     break;
                                 }
                             }
                         }
                     }
                     System.out.println("¿Cual quieres anular?");
                 }
                
            }

        } catch (Exception e) {
            System.out.println("No se ha encontrado el fichero");
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static boolean comprobarFecha(String entrada) {

        if (entrada.equals("")) {
            return false;
        } else {
            if (entrada.equals("FIN")) {
                return true;
            }
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            formato.setLenient(false);

            try {
                Date javaDate = formato.parse(entrada);
            } catch (Exception e) {
                System.out.println("Entrada no valida, intentalo de nuevo");
                return false;
            }

            return true;
        }
    }

    public static boolean comprobarHora(String entrada) {
        if (entrada.equals("")) {
            return false;
        } else {
            int hora = Integer.parseInt(entrada);
            if (hora >= 0 && hora <= 23) {
                return true;
            } else {
                System.out.println("Entrada no valida, la hora debe ser entre 0 y 23");
                return false;
            }
        }
    }
}
