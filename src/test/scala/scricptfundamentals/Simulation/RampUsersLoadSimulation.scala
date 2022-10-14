package scricptfundamentals.Simulation

import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import io.gatling.core.Predef._

class RampUsersLoadSimulation extends Simulation{

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
    .exec(getAllvideogamename())
    .pause(5)
    .exec(getSpecificVideogame())
    .pause(5)

setUp(
  scn.inject(
    nothingFor(5),
    constantUsersPerSec(10).during(10),
    rampUsersPerSec(1).to(5).during(20))).protocols(httpProtocol)
}
