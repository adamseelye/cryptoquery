import scala.io.StdIn.readLine

object testing extends App {
  sparkQueries.login()

  /*
  // DB Connection to check hash
    val url = "jdbc:mysql://trainingsrv:3306/practice"
    val user = "gentooadmin"
    val pass = "MN3ttXP9LE#?"

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

    import spark.implicits._

    val sourceDf=spark.read.format("jdbc").option("url",url)
      .option("dbtable","monero").option("user",user)
      .option("password",pass).load()
    sourceDf.show()

    val df4 = Seq((hash)).toDF("id", "user", "catchall")
    df4.show()

    df4.write.mode(SaveMode.Append).format("jdbc").option("url",url)
      //df4.write.mode(SaveMode.Ignore).format("jdbc").option("url",url)
      .option("dbtable","monero").option("user",user)
      .option("password",pass).save()
    sourceDf.show()
   */

}
