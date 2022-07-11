import scala.collection.immutable.ListMap
import scala.io.StdIn.readLine
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.log4j.{Level, Logger}
import org.apache.parquet.format.LogicalType.JSON

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

  val get_lbh = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_last_block_header\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
  val get_info = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_info\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!

  val c_lbh: Json = parse(get_lbh).getOrElse(Json.Null)

  val lbh_cursor: HCursor = c_lbh.hcursor
  val lbh_json: Decoder.Result[Long] = {
    lbh_cursor.downField("result").downField("block_header").get[Long]("difficulty")
  }
  val j_result1 = lbh_json.productElement(0).toString


  val c_info: Json = parse(get_info).getOrElse(Json.Null)

  val info_cursor: HCursor = c_info.hcursor
  val info_tx_json: Decoder.Result[Long] = {
    info_cursor.downField("result").get[Long]("tx_pool_size")
  }
  val info_dbsize_json: Decoder.Result[Long] = {
    info_cursor.downField("result").get[Long]("database_size")
  }
  val j_result2 = info_tx_json.productElement(0).toString
  val j_result3 = info_dbsize_json.productElement(0).toString
  val db_size = j_result3.toDouble / 1000000000 + " GB"

  println("This number tells us the current mining difficulty of the Monero blockchain.")
  println("It is measured in 'hashes per second', or the amount of cryptographic hashes")
  println("generated per second. Should a computer manage to generate that amount, it is guaranteed")
  println("to generate a block on the network. Otherwise, generating the correct hash is left to chance.")
  println(s"Current difficulty: $j_result1")
  println("")
  println("This number is the number of transactions written into the current block that will be")
  println("confirmed once the next hash is found. Blocks on Monero are generated approximately every 2 minutes.")
  println(s"Current transaction count: $j_result2")
  println("")
  println("This number is the current size of the entire Monero blockchain database, in gigabytes.")
  println("The database includes all actions and metadata. Transactions (most of the data) are around 2kb each.")
  println(s"Current database size: $db_size")


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
