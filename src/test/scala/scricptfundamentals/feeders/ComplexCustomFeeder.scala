package scricptfundamentals.feeders

import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef.*
import io.gatling.core.Predef.*

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.compiletime.ops.string.Length
import scala.util.Random
import io.gatling.core.session


class ComplexCustomFeeder extends Simulation{

  val httpProtocal = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")

  var idNumbers = (1 to 10).iterator
  val rand = new Random()
  val now = LocalDate.now()
  val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  def randomString(length: Int)={
    rand.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  def getRandomDate(startDate:LocalDate, random: Random):String = {
    startDate.minusDays(random.nextInt(30)).format(pattern)
  }

  val customFeeder = Iterator.continually(Map(
    "gameId" -> idNumbers.next(),
    "name"  -> ("Game-" + randomString(length=5)),
    "category" -> ("Category-" +randomString(length=6)),
    "releaseDate" -> getRandomDate(now,rand),
    "rating" -> ("Rating-" +randomString(length = 4)),
    "reviewScore" -> rand.nextInt(100)
  ))

  def authenicate()={
    exec(http("authenticate user")
    .post("/authenticate")
    .body(StringBody("{\n  \"password\": \"admin\",\n  \"username\": \"admin\"\n}"))
    .check(jsonPath("$.token").saveAs("jwtToken")))
  }

  def CreateNewGame()={
    repeat(10){
      feed(customFeeder)
        .exec(http("create a new game - #{name}")
        .post("/videogame")
        .header(name ="authorization",value = "Bearer #{jwtToken}")
        .body(ElFileBody("bodies/newgameTemplate.json")).asJson
        .check(bodyString.saveAs("responseBody")))
        .exec {session => println(session("responseBody").as[String]);session}
        .pause(1)
    }
  }





  val scn = scenario("Complex custom feeder")
    .exec(authenicate())
    .exec(CreateNewGame())

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocal)

}
