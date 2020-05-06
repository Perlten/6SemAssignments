# Opgave 1

match (a:Person)-[r:Like]->(b:Person) 
with b, count(r) as likes
order by likes desc limit 1
return  b

# Opgave 2

CALL algo.beta.louvain.stream('Person', 'Like', {
 graph: 'huge',
 direction: 'BOTH'
}) YIELD nodeId, community, communities
RETURN algo.asNode(nodeId).name as name, community, communities
ORDER BY name ASC



# Opgave 3

### Baseret på deres relations, jo flere ens man har desto mere ens er man.
CALL gds.nodeSimilarity.stream('peopleGraph')
YIELD node1, node2, similarity
RETURN gds.util.asNode(node1).name AS Person1, gds.util.asNode(node2).name AS Person2, similarity
ORDER BY similarity DESCENDING, Person1, Person2

# Opgave 4

MATCH (start:Person {name: 'Nikolai'}), (end:Person {name: 'Jesper'})
CALL gds.alpha.shortestPath.stream({
  nodeProjection: 'Person',
  relationshipProjection: {
    Like: {
      type: 'Like',
      properties: 'amount',
      orientation: 'DIRECTED'
    }
  },
  startNode: start,
  endNode: end,
  weightProperty: 'amount'

})
YIELD nodeId, amount
RETURN gds.util.asNode(nodeId).name AS name, amount