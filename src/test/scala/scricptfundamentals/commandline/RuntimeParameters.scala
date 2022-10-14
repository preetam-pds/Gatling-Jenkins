package scricptfundamentals.commandline

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RuntimeParameters extends Simulation{

  val httpProtocal = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  def getAllvideogames()={
    exec(http("Get all the video game name")
    .get("/videogame"))
      .pause(1)

  }

  val scn = scenario("Run from commandline")
    .forever{
      exec(getAllvideogames())
    }

  setUp(
    scn.inject(
      nothingFor(5),
      rampUsers(10).during(20)
    )

  ).protocols(httpProtocal)
    .maxDuration(30)

}
