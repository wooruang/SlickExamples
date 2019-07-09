package table

import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.util.Try
import scala.concurrent.duration.Duration

case class User1(index: Option[Long],
                id: String,
                password: String)

class Users1(tag: Tag) extends Table[User1](tag, "users1") {
  def index      = column[Long]("idx", O.PrimaryKey, O.AutoInc)
  def id         = column[String]("id", O.Unique)
  def password   = column[String]("pw")
  def * =
    (index.?, id, password) <> ((User1.apply _).tupled, User1.unapply)
}

case class User2(index: Option[Long],
                id: String,
                password: String)

class Users2(tag: Tag) extends Table[User2](tag, "users2") {
  def index      = column[Long]("idx", O.PrimaryKey, O.AutoInc)
  def id         = column[String]("id", O.Unique)
  def password   = column[String]("pw")
  def * =
    (index.?, id, password) <> ((User2.apply _).tupled, User2.unapply)
}

object Main extends App {

  println("HelloWorld")
  val mainDb = Database.forConfig("maindb")

  val createF = mainDb.run(sqlu"""CREATE DATABASE exampledb;""")
  val result = Try(Await.result(createF, Duration.Inf))
  println(s"Create exampledb : ${result.isSuccess}")

  if (result.isFailure) println(s"Create exampledb Fail : ${result.failed}")

  val commonDb = Database.forConfig("exampledb")

  val users1 = TableQuery[Users1]
  val users2 = TableQuery[Users2]

  val createQ = users1.schema.create
  val createIfNotExistsQ = users2.schema.createIfNotExists

  println(">>> create")
  println(s"${createQ.statements}")
  println(">>> createIfNotExists")
  println(s"${createIfNotExistsQ.statements}")

  val table1F = commonDb.run(DBIO.seq(createQ))
  val table2F = commonDb.run(DBIO.seq(createIfNotExistsQ))

  val r1 = Try(Await.result(table1F, Duration.Inf))
  val r2 = Try(Await.result(table2F, Duration.Inf))
  println(s"Create a user1 table : ${r1.isSuccess}")
  println(s"Create a user2 table : ${r2.isSuccess}")

  if (r1.isFailure) println(s"Create a user1 table Fail : ${r1.failed}")
  if (r2.isFailure) println(s"Create a user2 table Fail : ${r2.failed}")

  //  val dropF = mainDb.run(sqlu"""DROP DATABASE slick;""")

}
