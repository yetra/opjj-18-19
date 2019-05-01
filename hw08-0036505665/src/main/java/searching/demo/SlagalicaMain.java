package searching.demo;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Demonstrates the {@link Slagalica} and {@link SearchUtil} classes with GUI output.
 */
public class SlagalicaMain {

    /**
     * Main method. Finds the solution for the {@link Slagalica} game for a given
     * configuration specified through the command-line arguments,
     *
     * @param args command-line arguments - one representing the game's initial
     *             configuration is expected
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected 1 argument, "
                    + args.length + "were given.");
        }

        try {
            Slagalica slagalica = new Slagalica(
                    new KonfiguracijaSlagalice(toIntArray(args[0]))
            );

            Node<KonfiguracijaSlagalice> rjesenje =
                    SearchUtil.bfsv(slagalica.s0, slagalica.succ, slagalica.goal);

            if (rjesenje == null) {
                System.out.println("Nisam uspio pronaći rješenje.");
            } else {
                System.out.println(
                        "Imam rješenje. Broj poteza je: " + rjesenje.getCost()
                );

                List<KonfiguracijaSlagalice> lista = new ArrayList<>();
                Node<KonfiguracijaSlagalice> trenutni = rjesenje;

                while (trenutni != null) {
                    lista.add(trenutni.getState());
                    trenutni = trenutni.getParent();
                }

                Collections.reverse(lista);
                lista.forEach(k -> {
                    System.out.println(k);
                    System.out.println();
                });

                SlagalicaViewer.display(rjesenje);
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Converts a given string's characters to an array of integers.
     *
     * @param s the string to convert
     * @return the array of integers converted from a string
     */
    private static int[] toIntArray(String s) {
        char[] chars = s.toCharArray();
        int[] ints = new int[chars.length];

        for (int i = 0; i < ints.length; i++) {
            ints[i] = Character.getNumericValue(chars[i]);
        }

        return ints;
    }
}
