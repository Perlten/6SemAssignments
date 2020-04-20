# Create a Movie node for the movie with a title Forrest Gump.
## Add the following properties to the movie Forrest Gump:
## released: 1995
## tagline: Life is like a box of chocolates…​you never know what you’re gonna get.
CREATE(n:Movie {title: "Forrest Gump", tagline: "life and lemons", released: 1995})

# Update the released property of movie Forrest Gump, as it has actually been released in 1994.
match(n:Movie {title: "Forrest Gump"})
set n.released = 1994
# Find the movie with the tagline Free your mind.
match(n:Movie {tagline: "Free your mind"}) return n
# Retrieve the movie The Matrix and all its relationships.
match(n:Movie {title: "The Matrix"})<-[:ACTED_IN|:DIRECTED]-(person) return n
# Find the names and relationship type of all people who have any type of relationship to the movie The Matrix.
match(n:Movie {title: "The Matrix"})<-[r]-(p:Person) return p.name ,type(r)
# Find all people born in the previous century.
match(n:Person) where n.born < 2000 return n
# Find all people who gave the movie The Da Vinci Code a rating of 65, returning their names.
match(p:Person)-[r:REVIEWED]->(m:Movie) where r.rating >= 65 and m.title = "The Da Vinci Code" return p, m
# Find all people who follow Angela Scope and those who Angela Scope follows.
match(p:Person)-[:FOLLOWS]->(f:Person) where f.name = "Angela Scope"
match(p2:Person)-[:FOLLOWS]->(f2:Person) where p2.name ="Angela Scope" 
return p, f, f2
# Find all people who follow anybody who follows Jessica Thompson returning them as nodes.
match(p:Person)-[r:FOLLOWS]->(f:Person)-[r2:FOLLOWS]->(f2:Person) where f2.name = "Jessica Thompson" return p
# Tom Hanks hasn’t HELPED Gary Sinise in a research. Remove this property from the relation.
This relationship does not exist
If it existed we would find given relationship and delete the property
# Delete the whole person-to-person relationship HELPED from the graph.
This relationship does not exist
If it existed we would find given relationship and delete it
