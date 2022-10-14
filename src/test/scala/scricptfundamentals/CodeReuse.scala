package scricptfundamentals

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CodeReuse extends Simulation{

  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  def getAllVideogames()={
    repeat(3){
      exec(http("get all videogames")
        .get("/videogame")
        .check(status.is(200)))
    }

  }

  def getSpecificGame()={
    repeat(5,counterName = "counter"){
      exec(http("get specific videogame with id: #{counter}")
        .get("/videogame/#{counter}")
        .check(status.in(200 to 210)))
    }

  }

  val scn = scenario("code reuse")
    .exec(getAllVideogames())
    .pause(5)
    .exec(getSpecificGame())

    .repeat(2) {
      getAllVideogames()
    }


  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
