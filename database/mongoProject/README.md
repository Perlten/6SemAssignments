
```javascript
db.countries.mapReduce(
  function(){ emit(this['Country Name'], 1); }, 
  function(key, value){ return Array.sum(value) },
  {out: "counter"}
).find().sort({value: -1}).limit(10)



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


