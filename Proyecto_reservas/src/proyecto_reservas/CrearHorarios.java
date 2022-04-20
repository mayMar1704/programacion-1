/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_reservas;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author elbad
 */
public class CrearHorarios {

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
            for (int i = 0; i < entrada.length(); i++) {
                if (!(Character.isDigit(entrada.charAt(i)))) {
                    if (entrada.equals("FIN")) {
                        return true;
                    } else {
                        System.out.println("Entrada no valida, solo se pueden poner numeros");
                        return false;
                    }
                }
            }
            int hora = Integer.parseInt(entrada);
            if (hora >= 8 && hora <= 20) {
                return true;
            } else {
                System.out.println("Entrada no valida, la hora debe ser entre 8 y 20");
                return false;
            }
        }
    }

    public static void ContructorHorarios() {
        Scanner sc = new Scanner(System.in);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("> Contrucción de horarios de tutorias >");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        System.out.println("Nombre del profesor: ");
        String nombre = sc.nextLine();

        System.out.println("Escribe en lineas diferentes las fechas de los dias de tu horario con formato 01/01/22");
        System.out.println("Cuando no quieras introducir mas dias escribe FIN");
        String dia = "";
        ArrayList<String> dias = new ArrayList();
        while (!dia.equals("FIN")) {
            do {
                dia = sc.nextLine();
                if (dias.contains(dia)) {
                    System.out.println("Ya has introducido ese dia");
                }
            } while (!comprobarFecha(dia) || dias.contains(dia));

            if (!dia.equals("FIN")) {
                dias.add(dia);
            }
        }

        System.out.println("Ahora indica las horas de inicio de sesion de reserva");
        System.out.println("Cuando no quieras introducir mas horas escribe FIN");

        String hora = "";
        ArrayList<String> horas = new ArrayList();
        while (!hora.equals("FIN")) {
            do {
                hora = sc.nextLine();
                if (horas.contains(hora) || horas.contains("0" + hora)) {
                    System.out.println("Ya has introducido esa hora");
                }
            } while (!comprobarHora(hora) || horas.contains("0" + hora));

            if (!hora.equals("FIN")) {
                if (hora.length() == 1) {
                    hora = "0" + hora;
                }
                horas.add(hora);
            }
        }
        Collections.sort(horas);

        System.out.println("Por ultimo escribe el nombre del fichero en el que quieres que sea guardado");

        String nombreFichero = sc.nextLine();

        PrintWriter escribir = null;

        try {
            escribir = new PrintWriter(nombreFichero + ".txt");
            escribir.println("%N$" + nombre);
            for (int i = 0; i < dias.size(); i++) {
                escribir.println("%D$" + dias.get(i));
                for (int j = 0; j < horas.size(); j++) {
                    escribir.println("%H$" + horas.get(j));
                }
            }

            System.out.println("Fichero creado. Muchas gracias");

        } catch (Exception e) {
            System.out.println("Ha habido un error");
        } finally {
            escribir.close();
        }

    }
}
