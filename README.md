## Author
Igor Meira de Jesus<br>
meira.igor@gmail.com

## Descrição
Sistema de análise de dados de venda que importa lotes de arquivos e produz
um relatório baseado em informações presentes no mesmo.
Existem 3 tipos de dados dentro dos arquivos e eles podem ser distinguidos pelo seu
identificador que estará presente na primeira coluna de cada linha, onde o separador de
colunas é o character “ç”.

## Dados do vendedor
Os dados do vendedor possuem o identificador 001 e seguem o seguinte formato:<br>
001çCPFçNameçSalary

## Dados do cliente
Os dados do cliente possuem o identificador 002 e seguem o seguinte formato:<br>
002çCNPJçNameçBusiness Area

## Dados de venda
Os dados de venda possuem o identificador 003 e seguem o seguinte formato:<br>
003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name

## Exemplo de conteúdo total do arquivo:
001ç1234567891234çPedroç50000<br>
001ç3245678865434çPauloç40000.99<br>
002ç2345675434544345çJose da SilvaçRural<br>
002ç2345675433444345çEduardo PereiraçRural<br>
003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro<br>
003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo<br>

## Funcionamento / Instruções
Startar o projeto SpringBoot conforme comando abaixo:
<br>
mvn spring-boot:run (linux) 
<br>ou <br>
mvnw spring-boot:run (windows)
<br>
<br>
*** Importante ***: Substituir a variável %HOMEPATH% no application.properties dentro da pasta resources, caso algum problema ao obter a variável ocorra ao subir a aplicação. 
<br>
<br>
O sistema vai criar, caso nao exista o diretorio HOMEPATH/data/in e le continuamente todos os arquivos dentro deste diretório.
O arquivo de saída eh movido para HOMEPATH/data/out.<br>
Um segundo arquivo de saída eh criado com os seguintes dados:<br>
• Quantidade de clientes no arquivo de entrada<br>
• Quantidade de vendedores no arquivo de entrada<br>
• ID da venda mais cara<br>
• O pior vendedor<br>


## Biblioteca Utilizada
Spring Integration Reference Guide<br>
https://docs.spring.io/spring-integration/reference/html/
<br>Documentation<br>
https://docs.spring.io/spring-integration/api/org/springframework/integration/file/package-summary.html
