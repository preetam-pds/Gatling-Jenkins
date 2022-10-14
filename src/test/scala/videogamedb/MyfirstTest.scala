package videogamedb

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class MyfirstTest extends Simulation{

  //http configuration
  val httpconf = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  //scenario definition
  val scn = scenario("My first test")
    .exec(http(requestName = "get all video game name")
      .get("/videogame"))

  //Load scenario
  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpconf)
  )

}
