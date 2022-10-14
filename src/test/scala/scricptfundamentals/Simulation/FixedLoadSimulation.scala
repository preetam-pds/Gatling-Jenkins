package scricptfundamentals.Simulation

import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import io.gatling.core.Predef._

class FixedLoadSimulation extends Simulation{

  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  def getAllvideogamename()={
    exec(http("get all the game name")
      .get("/videogame"))
  }

  def getSpecificVideogame()={
    exec(http("get a specific game")
      .get("/videogame/1"))
  }

  val scn = scenario("Ramp User Load Simulation")
    .forever {
      exec(getAllvideogamename())
        .pause(5)
        .exec(getSpecificVideogame())
        .pause(5)
    }
  setUp(
    scn.inject(
      nothingFor(5),
      atOnceUsers(5),
      rampUsers(20).during(30)
    ).protocols(httpProtocol)
  ).maxDuration(60)

}
