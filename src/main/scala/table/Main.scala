package table

import slick.jdbc.PostgresProfile.api._

object Main extends App {

  val mainDb = Database.forConfig("maindb")

  println("HelloWorld")
}
