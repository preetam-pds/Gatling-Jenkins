package scricptfundamentals

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt

class CheckResponseCode extends Simulation{

  val httpprotocal = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  val scn = scenario("video game db - 3 calls")
    .exec(http("Get all the video game name - 1st call")
      .get("/videogame")
    .check(status.is(200)))
    .pause(duration = 2)

    .exec(http("get a specific game - 2nd call")
      .get("/videogame/1")
    .check(status.in(200 to 210)))
    .pause(1,10)

    .exec(http("Get all the game - 3rd call")
      .get("/videogame")
      .check(status.not(expected =404),status.not(500))
    )
    .pause(3000.milliseconds)

  setUp(scn.inject(atOnceUsers(1)).protocols(httpprotocal))
}