package searching.demo;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlagalicaDemo {

    public static void main(String[] args) {
        Slagalica slagalica = new Slagalica(
                new KonfiguracijaSlagalice(new int[]{2, 3, 0, 1, 4, 6, 7, 5, 8})
        );

        Node<KonfiguracijaSlagalice> rjesenje =
                SearchUtil.bfs(slagalica.s0, slagalica.succ, slagalica.goal);

        if(rjesenje==null) {
            System.out.println("Nisam uspio pronaći rješenje.");
        } else {
            System.out.println(
                    "Imam rješenje. Broj poteza je: " + rjesenje.getCost()
            );

            List<KonfiguracijaSlagalice> lista = new ArrayList<>();
            Node<KonfiguracijaSlagalice> trenutni = rjesenje;

            while(trenutni != null) {
                lista.add(trenutni.getState());
                trenutni = trenutni.getParent();
            }

            Collections.reverse(lista);
            lista.stream().forEach(k -> {
                System.out.println(k);
                System.out.println();
            });
        }

    }
}
