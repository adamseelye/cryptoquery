import scala.io.StdIn.readLine
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{SaveMode, SparkSession}

object sparkQueries extends App {
  // def <constructor>

  def sparkCxn (uid: String, name: String, password: String): Unit = {
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

    val url = "jdbc:mysql://trainingsrv:3306/project_1"
    val user = "gentooadmin"
    val pass = "MN3ttXP9LE#?"

    import spark.implicits._
    val df4 = Seq((name, uid, password)).toDF("name", "uid", "password")
    df4.show()


    val sourceDf = spark.read.format("jdbc").option("url", url)
      .option("dbtable", "users").option("user", user)
      .option("password", pass).load()
      sourceDf.show()
  }

  def login(): String = {
    val uid_in = readLine("Please enter your username: ")
    val check_pass = readLine("Please enter your password: ")

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

    val url = "jdbc:mysql://trainingsrv:3306/project_1"
    val user = "gentooadmin"
    val pass = "MN3ttXP9LE#?"

    val sourceDf = spark.read.format("jdbc").option("url", url)
      .option("dbtable", "users").option("user", user)
      .option("password", pass).load()

    sourceDf.createOrReplaceTempView("users1")
    //spark.sql(s"SELECT * FROM users1 WHERE uid = '$uid_in'").show()
    val db_pass = spark.sql(s"SELECT `password` FROM users1 WHERE uid = '$uid_in'").toDF.first().getString(0)
    //spark.sql(s"UPDATE `users1` SET `logged_in` = 1 WHERE `uid` = '$uid_in'")


    val checkedPwd = crypto.checkHash(check_pass, db_pass)

    if (checkedPwd == "true") { // use this for login logic
      val success = "Success"
      val login = spark.sql(s"SELECT uid, logged_in FROM users1 WHERE uid = '$uid_in'").toDF().first()
      println(login)
      println(success)

      return success
    } else {
      val failure = "Failure"
      println(failure)
      return failure
    }

  }

  def createUser (): Unit = {
    val uid = readLine("Please enter a username: ")
    val name = readLine("Please enter your name: ")
    val password = readLine("Please choose a strong password: ")

    val hashed = crypto.hashPassword(password)

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

    val url = "jdbc:mysql://trainingsrv:3306/project_1"
    val user = "gentooadmin"
    val pass = "MN3ttXP9LE#?"

    import spark.implicits._
    val df4 = Seq((name, uid, hashed)).toDF("name", "uid", "password")

    val sourceDf=spark.read.format("jdbc").option("url",url)
      .option("dbtable","users").option("user",user)
      .option("password",pass).load()

    df4.write.mode(SaveMode.Append).format("jdbc").option("url",url)
      .option("dbtable","users").option("user",user)
      .option("password",pass).save()
    sourceDf.show()

  }

  def createAdmin(): Unit = {
    println("This command may only be run once")
    println("The program will exit if an Admin user already exists\n")

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

    val url = "jdbc:mysql://trainingsrv:3306/project_1"
    val user = "gentooadmin"
    val pass = "MN3ttXP9LE#?"

    val sourceDf=spark.read.format("jdbc").option("url",url)
      .option("dbtable","users").option("user",user)
      .option("password",pass).load()

    sourceDf.createOrReplaceTempView("ck_table")
    val is_admin = 0
    //val is_admin = spark.sql(s"SELECT IF (`is_admin` > 0, 1, 0) FROM ck_table WHERE logged_in = '1'").toDF.first().getInt(0)

    if (is_admin != 1) {
      println("The Administrator username is 'admin'.")
      val uid = "admin"
      val name = "admin"
      val password = readLine("Please choose a strong password: ")
      val hashed = crypto.hashPassword(password)

      import spark.implicits._
      val df4 = Seq((name, uid, hashed, 1)).toDF("name", "uid", "password", "is_admin")

      df4.write.mode(SaveMode.Append).format("jdbc").option("url",url)
        .option("dbtable","users").option("user",user)
        .option("password",pass).save()
      sourceDf.show()
    } else if (is_admin == 1) {
      println("Error, Administrator account already exists")
      sys.exit(1)
    } else {
      println("Logic Error")
    }



  }

  def deleteUser (): Unit = {
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
    spark.sparkContext.setLogLevel("ERROR")

    val url = "jdbc:mysql://trainingsrv:3306/project_1"
    val user = "gentooadmin"
    val pass = "MN3ttXP9LE#?"

    val sourceDf = spark.read.format("jdbc").option("url", url)
      .option("dbtable", "users").option("user", user)
      .option("password", pass).load()
    println("sourceDf:")
    sourceDf.show()

    sourceDf.createOrReplaceTempView("ck_admin")
    val is_admin = spark.sql(s"SELECT IF (`is_admin` > 0, 1, 0) FROM ck_admin WHERE logged_in = '1'").toDF.first().getInt(0)

    if (is_admin == 1) {

      val uid = readLine("Please enter a username: ")
      println("\n")

      val df1 = sourceDf.toDF.filter(s"uid != '$uid'")
      df1.write.mode(SaveMode.Overwrite).format("jdbc").option("url",url)
        .option("dbtable","users").option("user",user)
        .option("password",pass).save()
      sourceDf.show()

    } else if (is_admin == 0) {
      println("Error, must be administrator to delete users")
      sys.exit(1)
    } else {
      println("Error")
      sys.exit(1)
    }

  }

}
