# nutriplus-back
This is the backend for the nutriplus WebSite. It uses Neo4j as a graph database and the Java Spring as its web API framework.
The system manages the work of a nutritoinist, by maintaining control of patients and their records. It also generates diets according to each patient nutrional necessities.

To run the application, one must set the environment variables:
```
EMAIL="gmailForTheApplication"
EMAIL_PASS="emailPassword"
SECRET="keyForGeneratingAuthorizationTokens"
```
If there is a local Neo4j database, its access configuration should be:
```
user = neo4j
password = 123456
uri = bolt://localhost
```
Otherwise, the cloud database should be set on the following evironment variables:
```
GRAPHENEDB_BOLT_USER="Data base user"
GRAPHENEDB_BOLT_PASSWORD="Data base user password"
GRAPHENEDB_BOLT_URL="Data base url"
```

To run the application, use gradle and run the command:
```
./gradlew bootRun
```
