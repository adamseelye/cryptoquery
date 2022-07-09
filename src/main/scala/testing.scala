import scala.io.StdIn.readLine
import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Level, Logger}

object testing extends App {
  sparkQueries.createUser()

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
  spark.sql("CREATE SCHEMA users;").show()
  spark.sql("SHOW DATABASES;").show()


   */

}
