
public class p {
    public static void main(String[] args){
        int[][] balance = new int[7][7];
        int[] paises = {0,4,2,3,4,4,4};
        int pais = 3;
        int miPais = 21;

        if(miPais>pais)
            for(int i=0; i<paises.length;i++)
                balance[i][i]=miPais-pais-(paises[i]+paises[i]);
            for (int gap = 1; gap < paises.length; ++gap)  
                for (int i = 0, j = gap; j < paises.length; ++i, ++j)    
                {
                    System.out.println(miPais+ "-" +pais + "-" +paises[i]+"+" + paises[j]);
                    balance[i][j]=Math.min(Math.min(Math.abs(balance[i+1][j]), Math.abs(balance[i][j-1])), Math.abs(miPais-pais-2*(paises[i]+paises[j])));
                }
        
        for(int i = 0; i<paises.length; i++)
        {
            for(int j = 0; j<paises.length; j++)
            {
                System.out.print(balance[i][j] + "\t");
            }
            System.out.println();
        }
    }
}