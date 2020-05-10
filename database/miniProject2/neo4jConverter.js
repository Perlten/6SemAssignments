const MongoClient = require('mongodb').MongoClient;
const fs = require("fs");
const crypto = require('crypto');


const PATH = "./neo4jScript.txt";
const URL = "mongodb://localhost:27017/";


async function convert() {
  let res = await loadCsv();
  // res = res.slice(0, 2000);
  let personSet = new Set();

  for (let show of res) {
    if (show) {
      let dis = show["description"].replace(/\"/g, "");

      let title = show["title"].toString().replace(/\"/g, "");

      let text = `CREATE(s:Show {title: "${title}", type: "${show["type"]}", description: "${dis}"})`
      writeToFile(text);
    }

    let actors = show["cast"].split(",");
    for (let actor of actors) {
      personSet.add(actor.trim());
    }

    let directors = show["director"].split(",");
    for (let director of directors) {
      personSet.add(director.trim());
    }
  }

  for (let person of personSet) {
    if (person) {
      let text = `CREATE(p:Person {name: "${person.replace(/\"/g, "")}"})`
      writeToFile(text)
    }
  }

  for (let show of res) {
    let actors = show["cast"].split(",");
    for (let actor of actors) {
      if(!actor){
        continue;
      }
      let title = show["title"].toString().replace(/\"/g, "");

      let text = `MATCH(p:Person), (s:Show) WHERE p.name = "${actor.trim().replace(/\"/g, "")}" AND s.title = "${title}" CREATE(p)-[:Actor]->(s)`
      writeToFile(text);
    }

    let directors = show["director"].split(",");
    for (let director of directors) {
      if (!director) {
        continue;
      }
      let title = show["title"].toString().replace(/\"/g, "");
      let text = `MATCH(p:Person), (s:Show) WHERE p.name = "${director.trim().replace(/\"/g, "")}" AND s.title = "${title}" CREATE(p)-[:Director]->(s)`
      writeToFile(text);
    }
  }
}

async function loadCsv() {
  return new Promise((resolve, reject) => {
    MongoClient.connect(URL, function (err, db) {
      if (err) {
        reject(err);
      }
      var dbo = db.db("miniProject2");
      var query = {};
      dbo.collection("shows").find(query).toArray(function (err, result) {
        if (err) {
          reject(err);
        }
        db.close();
        resolve(result);
      });
    });
  })
}

function writeToFile(text, path = PATH) {
  text += ";\n";
  fs.appendFileSync(path, text);
}

fs.writeFileSync(PATH, "");
convert();




// MATCH(p:Person), (s:Show) WHERE p.name = "Kid Ink" AND s.title = "Love" CREATE(p)-[:Actor]->(s);
// MATCH(p:Person), (s:Show) WHERE p.name = "Peter Cullen" AND s.title = "Good People" CREATE(p)-[:Actor]->(s);

// MATCH(t: table), (m: model) WHERE t.tid = "T0526" AND m.mid = "M001" CREATE(t) - [R00865: APPEARS_IN] -> (m);


// match(p: Person { name: "Kid Ink" }), (s: Show { title: "Love" })
// CREATE(p) - [r: Cast] -> (s)
// with p, s
// match(p: Person { name: "Peter Cullen" }), (s: Show { title: "Good People" })
// CREATE(p) - [r: Cast] -> (s)