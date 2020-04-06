# Made by Jesper Rusbjerg, Nikolai Perlt


# Sharding	in	MongoDB	
## What is sharding in mongoDB?
  Sharding udføres når en collections vokser sig for stor i forhold til den plads der er tilgængelig. Når man sharder uddeler man den givne collection i mindre collections derved kaldet shards. Disse shards indeholder replicasets så dataen hurtiger kan tilgås. Måden man tilgår hver shard er ved en internal hash function som anvendes på den key som bruges til at sprede dataen på de forskellige shards. 

## What are the different components required to implement sharding?
  Man skal bruge en key som skal køres igennem en internal hash function for dele daten ud på de forksellige shards

## Explain architecture of sharding in mongoDB? 
  Arkitekturen består af flere komponenter. Når et request rammer serveren bliver den først sendt til en mongoshard manager. Manageren anvender skalerings nøglen og laver en hash der fortæller hvilken shard den skal tilgå. Disse shards kan være delt ud på flere forskellige servere. På hver af disse shards indeholder replicasets så dataen hurtiger kan tilgås.

# Exercise:	MapReduce	with	mongoDB

use earth

```javascript
db.countries.mapReduce(
  function(){ emit(this['Country Name'], 1); }, 
  function(key, value){ return Array.sum(value) },
  {out: "counter"}
).find().sort({value: -1}).limit(10)
```

# Exercise:	MapReduce	with	mongoDB	(hashtag	query)

use twitter

```javascript
db.tweet.mapReduce(
  function(){ 
    if(!this.entities){
      return
    }

    for(let i = 0; i < this.entities.hashtags.length; i++){
      emit(this.entities.hashtags[i].text ,1); 
    }
  }, 
  function(key, value){ return Array.sum(value) },
  {out: "counter"}
).find().sort({value: -1}).limit(10)
```


