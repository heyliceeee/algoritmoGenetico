package org.example;

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    private static StringBuffer wordSecret = new StringBuffer("CASACO");


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

        for(int i=1; i <= 100; i++)
        {
            System.out.println("\n\nGEN: " + i);

            Collections.sort(gen0, Collections.reverseOrder()); //individuos ou genes, com o fitness maior (mais semelhante da palavra misterio)

            // Armazena os top 50 da geração
            ArrayList<Solution> top50 = new ArrayList<>(gen0.subList(0, 50));

            for (Solution solution : top50)
            {
                System.out.println(solution);
            }

            // Cria 50 novas mutações a partir dos top 50
            ArrayList<Solution> newGeneration = new ArrayList<>();

            for (int j = 0; j < 50; j++)
            {
                Solution original = top50.get(j);
                Solution mutated = new Solution(original); // Faz deep copy

                mutated.mutate(); // Aplica a mutação no indivíduo copiado
                newGeneration.add(mutated);
            }

            // Adiciona as novas mutações à nova geração
            gen0.addAll(newGeneration);
        }
    }
}