import scala.io.StdIn.readLine
import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Level, Logger}

object testing extends App {
  //sparkQueries.createUser()

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

 */

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
  sourceDf.show()

  println("\n")

  sourceDf.createOrReplaceTempView("users1")
  println("spark.sql query:")
  //spark.sql("SELECT * FROM users1 where user_id=1").show()
  spark.sql("SELECT * FROM users1 WHERE id = 1").show()

}
