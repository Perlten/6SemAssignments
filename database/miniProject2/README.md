### selecting relevant database operations, which can be used to compare the databases
Koden til denne konvertering kan findes i
Neo4jconverter.js
Neo4jScript.txt

Vi har valgt at kigge på disse oprationer. Single read, mass read, space complexity. Til disse tager vi udgangspunkt i data der omhandler netflix serie og shows. Der er cirka 6000 shows, samt deres skuespiller og instruktører. Mongo kan loade dataen ind som den er. Med neo4j har vi været nød til at lave et script der kan converter dette om til cypher instruktioner, som neo4j kan forstå.

### selecting appropriate criteria for comparison, such as access time, storage space, complexity, versioning, security, or similar

#### - Single read
Koden til dette kan findes i main.js

![alt text](./images/mongoNeoSingleRead.png "Single read times")
Her kan vi ses at neo4j kan læse et enkelt dokument hurtigere op end mongo. Dette skyldes at neo4j ofre noget plads for ekstra hastighed.

#### - Mass read

Koden til dette kan findes i main.js
![alt text](./images/mongoNeoMassRead.png "Mass read times")
Her vinder mongo klart. Dette skyldes at mongo kan læse et show samt dens "actors" or "directors" op i et hug, hvorimod neo4j skal læse et show op og derefter findes dens relations til "actors" og "directors" og samle det i et enkelt svar.

#### - Space complexity

![alt text](./images/spaceComp.png "Space complexity read times")
Som nævnt oven for bruger neo4j mere plads end mongo for at op nå bedre hastighed. Dette gør den blandt andet ved at gemme sine relations mellem noderne, dette anvendes også til neo4j algoritmer. Mongo på den anden side har "kun" brug for at gemme selve daten samt index. Mongo kan dog godt komme til at fylde meget da det er okay at have duplikerende og embeded data.

#### - Scaling

Vi har undersøgt hvordan en mongoDB skalere ved hjælp af en shard manager samt brug af mange shards og replica sets, hvilket gør at dataen altid er available selv hvis et enkelt cluster går ned

Ligeledes gør Neo4j det samme, dog har vi fortsat behov for at opnå yderliger kendskab til hvordan neo4j henter sin data fra sine respektive shards, hvilket vi vil undersøge nærmere inden vores eksamensprojekt
![alt text](https://media.discordapp.net/attachments/419171682612412417/710403828964392960/scaling.png "space comp")

#### - Security

Neo4j & Mongo har begge en adaptiv security struktur. Man kan customize og editere sin security som man har behov for. Man kan give adgang til specifikke collections eller dokumenter i databaser, gemme visse collections fra users man ikke ønsker har adgang m.m.. Ligeledes kan man opnå det samme i Neo4j, hvor man kan gå så langt som at en user kun kan se dele af en graph

Der er ikke rigtigt nogle limits til ens security, man kan gå så langt og være så sikker, som man har behov for, for at sikre sin data.

![alt text](https://media.discordapp.net/attachments/419171682612412417/710403833121079376/security.png?width=1440&height=398 "stuff")

#### creating demo code for testing the selected database operations against the selected comparison criteria
Inde i file main.js kan man se eksempler på single read, og mass reads, med tilsvarende speed test. samt kan man se vores Script converters i Neo4jconverter.js og Neo4jScript.txt.

#### reporting the results and conclusions.

Disse databaser deler principer, f.eks skalerer de begge to ved hjælp af sharding. De er begge schemaless og høre til no-sql kategorien, dog har de også deres egne usecases. Har du brug for at opbevare data og have det let og hurtigt tilgængeligt, er mongodb at fortrække. Hvis du derimod har brug for at lave mange single read, og ikke er så bekymret med henblik på plads er neo4j noget for dig. Dog er den helt store fordel ved at bruge neo4j dens indbyggede mulighed for at bruge mange populare algroritmer til at under søge eller analyser dataen f.eks. dijkstra's.
