package scricptfundamentals

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CheckResponseCodeAndExtract extends Simulation{

  val httpprotocal = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  val scn = scenario("check the json response")
    .exec(http("get video game name")
    .get("/videogame/2")
      .check(jsonPath("$.name").is("Gran Turismo 3"))
    )
    .exec(http("get all the game")
    .get("/videogame")
    .check(jsonPath("$[1].id").saveAs("gameid")))
    .exec{session => println(session);session}

    .exec(http("get a specific game")
    .get("/videogame/#{gameid}")
    .check(jsonPath("$.name").is("Gran Turismo 3"))
    .check(bodyString.saveAs("responsebody")))

    .exec{ session => println(session("responsebody").as[String]);session }

    setUp(scn.inject(atOnceUsers(1))).protocols(httpprotocal)
}
