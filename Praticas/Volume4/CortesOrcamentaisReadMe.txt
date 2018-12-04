/*Estado:terminado
|MooshackDAA-Vol4-Prog G-Cortes CortesOrcamentais (2018)   |
|  Objetivo: Saber se é possível transportar o animal,     |
|            SEM GASTAR MAIS que quantia predefinida.      |
|                                                          |
|  Considerações:                                          |
|      -O valor a pagar em cada ramo é o seu peso          |
|      -Validos apemas os Ramos que garantem condições     |
|        impostas de temperatura suportadas pelo animal    |
|  Nota: Semelhante a Transporte de Luxo (Prob F)          |
|    -Posso aplicar um alg de Dijsktra para heapMin (1x) e |
|       ver se o valor obtido é menor ou igual ao orçamento|
|-------------------------//-----------------------------  |
|    INPUT:                                                |
|      L1:tempMin tempMax origem destino (origem != destino|
|      L2: #nos #ramos da rede (grafo de 2 pesos)          |
|      -->Desc de ramos bidirecionais  n<=20000 r<=5000    |
|          -exInicial exFinal tempRamo custoTransporte     |
|      L(n+1): nCenarios--#cenarios a considerar >=1       |
|      -->Desc de cenários:                                |
|          -cada linha diz valor maximo a dispensar <10000 |
|    OUTPUT: para cada cenario                             |
|      -"Sim"-pode transportado sem ultrapassar montante   |
|      -"Nao"-caso contrário                               |
*/
