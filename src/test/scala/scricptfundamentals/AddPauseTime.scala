package scricptfundamentals

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt

class AddPauseTime extends Simulation{

  val httpprotocal = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  val scn = scenario("video game db - 3 calls")
    .exec(http("Get all the video game name - 1st call")
      .get("/videogame"))
    .pause(duration = 2)

    .exec(http("get a specific game - 2nd call")
      .get("/videogame/1")
    )
    .pause(1,10)

    .exec(http("Get all the game - 3rd call")
      .get("/videogame")
    )
    .pause(3000.milliseconds)

  setUp(scn.inject(atOnceUsers(1)).protocols(httpprotocal))
}
