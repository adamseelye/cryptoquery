import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object sparkQueries extends App {
  // def <constructor>

  def sparkCxn (args: Array[String]): Unit = {
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

    val url = "jdbc:mysql://trainingsrv:3306/practice"
    val user = "gentooadmin"
    val pass = "MN3ttXP9LE#?"

    val sourceDf = spark.read.format("jdbc").option("url", url)
      .option("dbtable", "users").option("user", user)
      .option("password", pass).load()
    sourceDf.show()
  }
}
