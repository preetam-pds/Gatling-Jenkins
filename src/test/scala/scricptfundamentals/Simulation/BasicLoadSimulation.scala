package scricptfundamentals.Simulation

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class BasicLoadSimulation extends Simulation {

  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  def getAllthegame()={
  exec(http("get all the game name")
    .get("/videogame"))
  }
  def getSpecificGame()={
    exec(http("get a specific game")
    .get("/videogame/1"))
  }

  val scn = scenario("Load Simulation")
    .exec(getAllthegame())
    .pause(5)
    .exec(getSpecificGame())
    .pause(5)

  setUp(
    scn.inject(
      atOnceUsers(5),
      rampUsers(10).during(10)
    )
  ).protocols(httpProtocol)
}
