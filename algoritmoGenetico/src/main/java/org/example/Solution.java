package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Solution implements Comparable<Solution>
{
    private StringBuffer palavra; //o conteúdo da solução
    private int total=0;


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


    public Solution(Solution sol)
    {
        this.palavra = new StringBuffer(Main.TAM);

        for(int i=0; i<Main.TAM; i++)
        {
            this.palavra.append(sol.getSol().charAt(i));
        }
    }

    public StringBuffer getSol()
    {
        return this.palavra;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum
     * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
     * all {@code x} and {@code y}.  (This implies that {@code
     * x.compareTo(y)} must throw an exception if and only if {@code
     * y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code
     * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
     * == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
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


    /** Função de fitness
     *
     * procurar a palavra mais semelhante a palavra escondida
     * @return a quantidade de letras iguais
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
     * calcula a percentagem de semelhança da palavra com a palavra misterio
     * @param total
     * @return
     */
    private float percentage(int total)
    {
        float percent = ((float) (total - (-Main.TAM)) / (Main.TAM - (-Main.TAM))) * 100;

        return Float.parseFloat(String.format("%.2f", percent));
    }


    /**
     * Função que implementa a mutação.
     *
     * Altera uma posição random da solução atual para conter um caracter gerado random.
     * 'mutationRate' permite controlar quantos caracteres pode alterar em cada mutação.
     * Não valida se altera múltiplas vezes o mesmo caracter ou se troca pelo mesmo caracter.
     */
    public void mutate()
    {
        for (int i=0; i < Main.mutationRate; i++)
        {
            Random random = new Random();

            //gerar um caracter random
            int indexChar = random.nextInt(Main.chars.length());
            char randomChar = Main.chars.charAt(indexChar);

            int index = random.nextInt(Main.TAM);//gerar uma posição a alterar random

            this.palavra.setCharAt(index, randomChar);
        }
    }


    /**
     * Implementa o cruzamento entre esta solução e uma outra solução recebida como parâmetro. Assume
     * que tanto esta solução como a outra foram já alvo de deep copy
     * @param mae a "mãe", segunda solução usada no cruzamento com esta (pai)
     * @return um array contendo as duas soluções "filho" que resultam do cruzamento
     */
    public Solution[] cross(Solution mae)
    {
        Random random = new Random();

        int pos = random.nextInt(Main.TAM); //gerar uma posição random
        Solution temp = new Solution(mae); //criar cópia temp

        for (int i=pos; i < Main.TAM; i++)//preencher filho 1
        {
            mae.palavra.setCharAt(i, this.palavra.charAt(i));
        }

        for (int i=pos; i < Main.TAM; i++)//preencher filho 2
        {
            this.palavra.setCharAt(i, temp.palavra.charAt(i));
        }

        Solution[] filhos = new Solution[]{this, mae};

        return filhos;
    }
}
