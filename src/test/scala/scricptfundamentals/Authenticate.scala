package scricptfundamentals

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class Authenticate extends Simulation{

  val httpprotocal = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")

  def Authenticate() ={
    exec(http("Authenticate")
    .post("/authenticate")
    .body(StringBody(
      string = "{\n  \"password\": \"admin\",\n  \"username\": \"admin\"\n}"
    ))
    .check(jsonPath(path="$.token").saveAs("jwttoken")))
  }

  def createnewgame() ={
    exec(http("Create a new game")
    .post("/videogame")
      .header(name ="Authorization",value = "Bearer #{jwttoken}")
    .body(StringBody(
      string="{\n  \"category\": \"Platform\",\n  \"name\": \"Mario\",\n  \"rating\": \"Mature\",\n  \"releaseDate\": \"2012-05-04\",\n  \"reviewScore\": 85\n}"
    )))
  }

  val scn = scenario("Authenticate")
    .exec(Authenticate())
    .exec(createnewgame())

  setUp(scn.inject(atOnceUsers(1))).protocols(httpprotocal)

}
