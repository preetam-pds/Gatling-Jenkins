package scricptfundamentals.feeders

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CsvFeeders extends Simulation{

  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  val csvfeeder = csv("data/gamecsvfile.csv").circular

  def getSpecificvideogame()={
    repeat(10){
      feed(csvfeeder)
        .exec(http("Get videogame name - #{gameName}")
        .get("/videogame/#{gameId}")
        .check(jsonPath("$.name").is("#{gameName}"))
        .check(status.is(200)))
        .pause(2)
    }
  }


  val scn = scenario("csv feeder test")
    .exec(getSpecificvideogame())


  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
