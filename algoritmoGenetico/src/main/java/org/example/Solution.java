package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Solution implements Comparable<Solution>
{
    private StringBuffer palavra; //o conteúdo da solução
    private int total=0; //total de letras iguais durante a funcao de fitness


    /**
     * construtor principal, que cria uma solução (palavra) de TAM caracteres randoms
     */
    public Solution()
    {
        this.palavra = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < Main.TAM; i++)
        {
            int index = random.nextInt(Main.chars.length());
            char randomChar = Main.chars.charAt(index);

            this.palavra.append(randomChar);
        }
    }


    /**
     * constructor de cópia
     * @param sol
     */
    public Solution(Solution sol)
    {
        this.palavra = new StringBuffer(Main.TAM);

        for(int i=0; i < Main.TAM; i++)
        {
            this.palavra.append(sol.getSol().charAt(i));
        }
    }


    /**
     * obter a palavra atual
     * @return palavra atual
     */
    public StringBuffer getSol()
    {
        return this.palavra;
    }


    /**
     * ordena as solucoes com base em sua funcao de fitness
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Solution o)
    {
        try
        {
            if (this.getFitnessFunction() > o.getFitnessFunction())
            {
                return 1;
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        try
        {
            if (this.getFitnessFunction() < o.getFitnessFunction())
            {
                return -1;
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return 0;
    }


    /**
     * representar a solucao como uma string
     * @return
     */
    @Override
    public String toString()
    {
        try
        {
            return "Solution{" +
                    "word = " + palavra +
                    ", function fitness = " + getFitnessFunction() + " "+percentage(total)+"%" +
                    '}';
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }


    /**
     * função de fitness que conta o número de letras iguais entre a palavra atual e a palavra secreta.
     */
    public int getFitnessFunction() throws Exception
    {
        total=0;

        for (int i=0; i < Main.TAM; i++)
        {
            if (this.palavra.charAt(i) == Main.wordSecret.charAt(i))
            {
                total++;
            }
        }

        return total;
    }


    /**
     * calcular a porcentagem de semelhança entre a palavra atual e a palavra secreta
     */
    private float percentage(int total)
    {
        float percent = ((float) (total - (-Main.TAM)) / (Main.TAM - (-Main.TAM))) * 100;

        return Float.parseFloat(String.format("%.2f", percent));
    }


    /**
     * implementar a mutação na solução atual.
     */
    public void mutate()
    {
        for (int i=0; i < Main.mutationRate; i++)
        {
            Random random = new Random();

            //gerar um caracter random
            int indexChar = random.nextInt(Main.chars.length());
            char randomChar = Main.chars.charAt(indexChar);

            int index = random.nextInt(Main.TAM); //gerar uma posição random para alterar na palavra

            this.palavra.setCharAt(index, randomChar);
        }
    }


    /**
     * realizar o cruzamento entre esta solução e outra solução
     */
    public Solution[] cross(Solution mae)
    {
        Random random = new Random();

        int pos = random.nextInt(Main.TAM); //gerar uma posição random
        Solution temp = new Solution(mae); //criar cópia temp

        for (int i=pos; i < Main.TAM; i++)//preencher o primeiro filho
        {
            mae.palavra.setCharAt(i, this.palavra.charAt(i));
        }

        for (int i=pos; i < Main.TAM; i++)//preencher o segundo filho
        {
            this.palavra.setCharAt(i, temp.palavra.charAt(i));
        }

        Solution[] filhos = new Solution[]{this, mae};

        return filhos;
    }
}
