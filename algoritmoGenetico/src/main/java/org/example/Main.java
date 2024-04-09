package org.example;

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    private static StringBuffer wordSecret = new StringBuffer("ABCDEF");


    /**
     * criar n individuos ou genes, com palavra
     * @param n
     * @return
     */
    public static ArrayList<Solution> inicializa(int n)
    {
        ArrayList<Solution> gen0 = new ArrayList<Solution>(n);

        for(int i=0; i<n; i++)
        {
            gen0.add(new Solution(wordSecret));
        }

        return gen0;
    }

    public static void main(String[] args)
    {
        ArrayList<Solution> gen0 = inicializa(100);

        Collections.sort(gen0, Collections.reverseOrder()); //individuos ou genes, com o fitness maior (mais semelhante da palavra misterio)

        for(int i=0;i<5;i++) //mostra os 5 + proximos da palavra misterio
        {
            System.out.println(gen0.get(i));
        }
    }
}