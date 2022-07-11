import scala.collection.immutable.ListMap
import scala.io.StdIn.readLine
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.log4j.{Level, Logger}
import org.apache.parquet.format.LogicalType.JSON
import scala.math._
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.language.postfixOps
import sys.process._
import cats.syntax.either._
import io.circe._, io.circe.parser._


object testing extends App {
  val spark = SparkSession
    .builder
    .appName("Spark Queries")
    .master("spark://trainingsrv:7077")
    .config("spark.master", "local[*]")
    .config("spark.driver.allowMultipleContexts", "true")
    .enableHiveSupport()
    .getOrCreate()
  Logger.getLogger("org").setLevel(Level.ERROR)
  println("created spark session")

  val url = "jdbc:mysql://trainingsrv:3306/practice"
  val user = "gentooadmin"
  val pass = "MN3ttXP9LE#?"

  val get_fee = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_fee_estimate\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!

  val c_fee: Json = parse(get_fee).getOrElse(Json.Null)

  val fee_cursor: HCursor = c_fee.hcursor
  val fee_json: Decoder.Result[Double] = {
    fee_cursor.downField("result").get[Double]("fee")
  }
  val fee_result = fee_json.productElement(0).toString
  val fee = fee_result.toDouble / pow(10, 12)
  println("The total fee for executing a single transaction with Monero: " + fee + " XMR")
  println("This is worth about: $" + (fee * 130))

  //val g_map = Map("IP: " -> g_json1.productElement(0).toString, "City: " -> g_json2.productElement(0).toString,
    //"Country: " -> g_json3.productElement(0).toString, "Internet Service Provider: " -> g_json4.productElement(0).toString)

  //for ((k, v) <- g_map) {
    //println(k + v)  }


  //val get_blk = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_block\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!

  //val result = JSON.parseFull(get_blk)

  //println(result)


  /*
  val spark = SparkSession
    .builder
    .appName("hello hive")
    .master("spark://trainingsrv:7077")
    .config("spark.master", "local[*]")
    .config("spark.driver.allowMultipleContexts", "true")
    .enableHiveSupport()
    .getOrCreate()
  Logger.getLogger("org").setLevel(Level.ERROR)
  println("created spark session")
  spark.sparkContext.setLogLevel("ERROR")

  val url = "jdbc:mysql://trainingsrv:3306/practice"
  val user = "gentooadmin"
  val pass = "MN3ttXP9LE#?"

  val sourceDf=spark.read.format("jdbc").option("url",url)
    .option("dbtable","users").option("user",user)
    .option("password",pass).load()

  sourceDf.createOrReplaceTempView("ck_ad_table")
  val is_admin = spark.sql(s"SELECT IF (`is_admin` > 0, 1, 0) FROM ck_ad_table WHERE logged_in = '1'").toDF.first()//.getInt(0)

  println(is_admin)

   */

  /*
  //first example we just append the new dataframe with one tuple or row onto users table
  df4.write.mode(SaveMode.Append).format("jdbc").option("url",url)
    .option("dbtable","users2").option("user",user)
    .option("password",pass).save()
  sourceDf.show()

   */


  /*
    // Use this for monero queries; inserts data into Hive
    val spark = SparkSession
      .builder
      .appName("hello hive")
      .master("spark://trainingsrv:7077")
      .config("spark.master", "local[*]")
      .config("spark.driver.allowMultipleContexts", "true")
      .enableHiveSupport()
      .getOrCreate()
    Logger.getLogger("org").setLevel(Level.ERROR)
    println("created spark session")
    spark.sparkContext.setLogLevel("ERROR")
    //spark.sql("CREATE SCHEMA users;").show()
    spark.sql("SHOW DATABASES;").show()



    val spark = SparkSession
      .builder
      .appName("Spark Queries")
      .master("spark://trainingsrv:7077")
      .config("spark.master", "local[*]")
      .config("spark.driver.allowMultipleContexts", "true")
      .enableHiveSupport()
      .getOrCreate()
    Logger.getLogger("org").setLevel(Level.ERROR)
    println("created spark session")

    val url = "jdbc:mysql://trainingsrv:3306/practice"
    val user = "gentooadmin"
    val pass = "MN3ttXP9LE#?"

    val sourceDf = spark.read.format("jdbc").option("url", url)
      .option("dbtable", "users").option("user", user)
      .option("password", pass).load()
    println("sourceDf:")
    //sourceDf.show()

    println("\n")

    val df4 = sourceDf.toDF
    df4.show()

    df4.write.mode(SaveMode.Overwrite).format("jdbc").option("url",url)
      //df4.write.mode(SaveMode.Ignore).format("jdbc").option("url",url)
      .option("dbtable","monero").option("user",user)
      .option("password",pass).save()
    sourceDf.show()

    sourceDf.createOrReplaceTempView("users1")
    println("spark.sql query:")
    //spark.sql("SELECT * FROM users1 where user_id=1").show()
    spark.sql("SELECT * FROM users1 WHERE id = 1").show()

   */

}
