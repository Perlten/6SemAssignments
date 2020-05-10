const MongoClient = require('mongodb').MongoClient;
const neo4j = require('neo4j-driver')
const process = require('process');

const driver = neo4j.driver(
  'neo4j://localhost',
  neo4j.auth.basic('neo4j', 'admin')
)
const session = driver.session()

const URL = "mongodb://localhost:27017/";

async function start() {
  await loadMongoData({});
  await neoLoadAllShowAndPerson();
  
  console.log("\n");
  
  await loadMongoData({ title: "The Crown" });
  await neoGetShow("The Crown");

  await session.close()
}


async function neoLoadAllShowAndPerson() {
  let query = "match (person:Person)-[relationship]->(show:Show) return person, show, relationship";
  return loadNeo4jData(query);
}


async function neoGetShow(title) {
  let query = 'match (person:Person)-[relationship]->(show:Show {title: $title}) return person, show, relationship';
  let params = { title };
  return loadNeo4jData(query, params);
}

async function loadNeo4jData(query, params) {
  let hrstart = process.hrtime()
  const result = await session.run(
    query,
    params
  )

  let resArray = [];
  for (let record of result.records) {
    let recordObject = record.toObject();
    resArray.push(recordObject);
  }

  let hrend = process.hrtime(hrstart)
  console.log("Neo4j:")
  console.info('Execution time (hr): %ds %dms', hrend[0], hrend[1] / 1000000)
  return resArray;
}

async function loadMongoData(query) {
  return new Promise((resolve, reject) => {
    let hrstart = process.hrtime()
    MongoClient.connect(URL, function (err, db) {
      if (err) {
        reject(err);
      }
      var dbo = db.db("miniProject2");
      dbo.collection("shows").find(query).toArray(function (err, result) {
        if (err) {
          reject(err);
        }
        db.close();
        let hrend = process.hrtime(hrstart)
        console.log("MongoDB:");
        console.info('Execution time (hr): %ds %dms', hrend[0], hrend[1] / 1000000)
        resolve(result);
      });
    });
  })
}


start();