package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Solution implements Comparable<Solution>
{
    private final int SIZEWORD = 6;
    String wordSecret;
    StringBuffer sol;
    int total = 0;


    /**
     * construtor para inicializar a população com o tamanho de n individuos
     * @param wordSecret
     */
    public Solution(StringBuffer wordSecret)
    {
        this.wordSecret = String.valueOf(wordSecret);
        this.sol = new StringBuffer();

        Random random = new Random();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";


        for(int i=0; i < SIZEWORD; i++) //percorrer a palavra
        {
            //gere random letra
            int index = random.nextInt(chars.length());
            char randomChar = chars.charAt(index);

            sol.append(randomChar);//coloca na palavra
        }
    }


    public String getWordSecret() {
        return wordSecret;
    }

    public StringBuffer getSol() {
        return sol;
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
            if(this.getFitnessFunction() > o.getFitnessFunction()) //se a funcao fitness atual for maior que a seguinte (melhor solucao)
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
            if(this.getFitnessFunction() < o.getFitnessFunction())
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
                    "word = " + sol +
                    ", function fitness = " + getFitnessFunction() + " "+percentage(total)+"%" +
                    '}';
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }


    /** FUNCAO DE FITNESS
     * procurar a palavra mais semelhante a palavra escondida
     * @return a quantidade de letras iguais
     */
    private int getFitnessFunction() throws Exception {
        total=0;
        /*for(int i=0; i < this.sol.length(); i++)
        {
            if(sol.charAt(i) == wordSecret.charAt(i))
            {
                total++;
            }
        }   */

        List<Integer> result = evaluate(sol);

        for(int i=0; i < result.size(); i++) //somar a metrica de cada letra para assim se saber a metrica da palavra (-6 - pior cenario | 6 - melhor cenario)
        {
            total += result.get(i);
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
        float percent = ((float) (total - (-6)) / (6 - (-6))) * 100;

        return Float.parseFloat(String.format("%.2f", percent));
    }



    /**
     * devolve uma metrica de semelhança entre 2 palavras ao estilo do jogo wordle
     * @param sol
     * @return array de inteiros com o mesmo nº de posições da palavra a adivinhar em que:
     * -1 - o caracter nessa posicao nao existe na palavra a adivinhar
     * 0 - o caracter nessa posicao existe na palavra a adivinhar, mas está na posicao errada
     * 1 - o caracter nessa posicao existe na palavra a adivinhar e está na posicao correta
     */
    public List<Integer> evaluate(StringBuffer sol) throws Exception
    {
        if(sol.length() != wordSecret.length())
        {
            new Exception("ERRO: palavras com tamanhos diferentes !");
        }

        List<Integer> result = new ArrayList<>();


        for(int i=0; i < wordSecret.length(); i++)
        {
            if(sol.charAt(i) == wordSecret.charAt(i))
            {
                result.add(1);
            }

            else if(wordSecret.contains(String.valueOf(sol.charAt(i)))) //existe mas ta na posicao errada
            {
                result.add(0);
            }

            else //n existe
            {
                result.add(-1);
            }
        }

        return result;
    }
}
