package org.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    //region VARIAVEIS GLOBAIS

    /**
     * palavra a adivinhar
     */
    public final static StringBuffer wordSecret = new StringBuffer("BRUNO MIGUEL MARTINS PINHEIRO");

    /**
     * tamanho da palavra
     */
    public final static int TAM = 29;

    /**
     * conj. de caracteres disponíveis, usado para gerar caracteres random
     */
    public final static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";

    //endregion

    //region configurações do algoritmo genético

    /**
    *  qual o número de caracteres a alterar em cada mutação
    */
    public static int mutationRate;

    /**
     * tamanho da população
     */
    public static int popSize;

    /**
     * soluções a manter
     */
    public static int popHereditary;

    /**
     * soluções a gerar por mutação
     */
    public static int popMutation;

    /**
     * soluções a gerar por cruzamento
     */
    public static int popCross;

    /**
     * critério de paragem
     */
    public static int maxIterations;

    /**
     * depois de terminar todas as geracoes, mostra os top n solucoes
     */
    public static int top;

    //endregion


    /**
     * criar n individuos ou genes, com palavra
     * @return
     */
    public static ArrayList<Solution> inicializa()
    {
        ArrayList<Solution> gen0 = new ArrayList<Solution>(popSize);

        for(int i=0; i < popSize; i++)
        {
            gen0.add(new Solution());
        }

        return gen0;
    }

    public static void main(String[] args) throws Exception {
        readConfigFile(); //ficheiro de configuracoes

        //inicializa
        ArrayList<Solution> gen0 = inicializa();

        for(int i=1; i <= maxIterations; i++)
        {
            Collections.sort(gen0, Collections.reverseOrder()); //ordenar individuos com o fitness maior (mais semelhante da palavra misterio)

            System.out.println("GEN: "+i+", Best Fitness: "+gen0.get(0).getFitnessFunction() + "\n");

            //Seleção + Reprodução
            //Estratégia: manter os top 50 soluções, gerar 20 por mutação e 30 por cruzamento
            //TODO: podia ser configurável
            ArrayList<Solution> newGen = new ArrayList<>();

            //Manter o top 50
            for(int j=0; j < popHereditary; j++)
            {
                newGen.add(gen0.get(j)); //adicionar à nova geração
            }

            //Mutação das top 20
            for(int j=0; j < popMutation; j++)
            {
                Solution copia = new Solution(gen0.get(j)); //deep copy
                copia.mutate(); //mutacao da cópia
                newGen.add(copia); //adicionar à nova geração
            }

            //Gerar 30 por cruzamento com base nas top 30
            //Mutação é feita entre cada duas soluções consecutivas, poderiam ser escolhidas random...
            for(int j=0; j < popCross; j+=2)
            {
                Solution pai = new Solution(gen0.get(j)); //deep copy
                Solution mae = new Solution(gen0.get(j+1)); //deep copy

                Solution[] filhos = pai.cross(mae); //cruzamento

                newGen.add(filhos[0]);
                newGen.add(filhos[1]);
            }

            //atualizar geração para a próxima iteração
            gen0 = newGen;
        }

        Collections.sort(gen0, Collections.reverseOrder());

        //apos de percorrer as n geracoes, mostra os top 10
        for(int i=0; i < top; i++)
        {
            System.out.println(gen0.get(i));
        }
    }


    public static void readConfigFile()
    {
        try
        {
            //ler o JSON
            FileReader fileReader = new FileReader("D:\\githubProjects\\algoritmoGenetico\\algoritmoGenetico\\src\\main\\java\\org\\example\\GeneticAlgorithmConfig.json");

            //parse o JSON
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(fileReader).getAsJsonObject();

            //aceder cada variável
            mutationRate = jsonObject.get("mutationRate").getAsInt();
            popSize = jsonObject.get("popSize").getAsInt();
            popHereditary = jsonObject.get("popHereditary").getAsInt();
            popMutation = jsonObject.get("popMutation").getAsInt();
            popCross = jsonObject.get("popCross").getAsInt();
            maxIterations = jsonObject.get("maxIterations").getAsInt();
            top = jsonObject.get("top").getAsInt();

            //fechar FileReader
            fileReader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}