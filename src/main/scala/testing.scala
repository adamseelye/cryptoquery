import scala.collection.immutable.ListMap

import scala.io.StdIn.readLine
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.log4j.{Level, Logger}

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object testing extends App {


  println("Thank you for logging in.\n" +
    "We can now begin to analyze the Monero (XMR) blockchain.")
  println("Monero is a cryptocurrency, much like Bitcoin. " +
    "However, the key difference between Monero and Bitcoin is")
  println("that Monero transactions cannot be traced (supposedly) like " +
    "Bitcoin transactions can. This is great for privacy advocates")
  println("around the world, but causes problems for law enforcement " +
    "agencies trying to combat fraud.")
  println("Here, we will ask some questions based on data we get from " +
    "interacting with a self-hosted node on the Monero blockchain.\n")

  val q_map = Map(1 -> "Who is using XMR?", 2 -> "What kind of activity is happening on the XMR blockchain?",
    3 -> "What kind of data can we extract from the XMR blockchain?", 4 -> "Are there any obvious patterns in the timing of transactions?",
    5 -> "Is there evidence for centralization of XMR activity?", 6 -> "How might Law Enforcement analyze the XMR blockchain for fraud-related activities?")

  println(q_map(1))

  val choice_int = readLine("\nWhich would you like to ask? Please enter the number here: ").toInt

  if (choice_int == 1) {
    monero.quest1()
  } else if (choice_int == 2) {
    monero.quest2()
  } else if (choice_int == 3) {
    monero.quest2()
  } else if (choice_int == 4) {
    monero.quest2()
  } else if (choice_int == 5) {
    monero.quest2()
  } else if (choice_int == 6) {
    monero.quest2()
  } else {
    println("Input error")
    sys.exit(1)
  }


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
