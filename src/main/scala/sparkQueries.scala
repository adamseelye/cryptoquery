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

    val url = "jdbc:mysql://trainingsrv:3306/practice"
    val user = "gentooadmin"
    val pass = "MN3ttXP9LE#?"

    import spark.implicits._
    val df4 = Seq((name, uid, password)).toDF("name", "uid", "password")
    //df4.show()


    val sourceDf = spark.read.format("jdbc").option("url", url)
      .option("dbtable", "users").option("user", user)
      .option("password", pass).load()
      sourceDf.show()
  }


  /*
  val spark = SparkSession
      .builder
      .appName("hello hive")
      .config("spark.master", "local[*]")
      //.config("spark.driver.allowMultipleContexts","true")
      .enableHiveSupport()
      .getOrCreate()
    Logger.getLogger("org").setLevel(Level.ERROR)
    println("created spark session")
    var dfload = spark.read.csv("hdfs://localhost:9000/user/will/people.csv")
    dfload.createOrReplaceTempView("people")
    dfload.show()
    spark.sql("SELECT * FROM people").show()

    //val driver = com.mysql.jdbc.driver
    val url = "jdbc:mysql://localhost:3306/test"
    val user = "root"
    val pass = "p4ssword"

    val sourceDf=spark.read.format("jdbc").option("url",url)
      .option("dbtable","users").option("user",user)
      .option("password",pass).load()
    sourceDf.show()

    sourceDf.createOrReplaceTempView("users1")
    spark.sql("SELECT * FROM users1 where user_id=1").show()

    val sql="select * from users where user_id=2"
    val sourceDf2=spark.read.format("jdbc").option("url",url)
      .option("dbtable",s"( $sql ) as t").option("user",user)
      .option("password",pass).load()
    sourceDf2.show()
   */


  def login(): Unit = {
    val uid_in = readLine("Please enter your username: ")
    //val uid_in = "adam"
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

    val url = "jdbc:mysql://trainingsrv:3306/practice"
    val user = "gentooadmin"
    val pass = "MN3ttXP9LE#?"

    val sourceDf = spark.read.format("jdbc").option("url", url)
      .option("dbtable", "users").option("user", user)
      .option("password", pass).load()

    sourceDf.createOrReplaceTempView("users1")
    spark.sql(s"SELECT * FROM users1 WHERE uid = '$uid_in'").show()
    val db_pass = spark.sql(s"SELECT `password` FROM users1 WHERE uid = '$uid_in'").toDF.first().getString(0)

    val checkedPwd = crypto.checkHash(check_pass, db_pass)

    if (checkedPwd == "true") { // use this for login logic
      println("Password Success").toString
    } else {
      println("Password Failure").toString
    }

  }

  def logout(): Unit = {
    /*
    set db login field to false
    display ('logged out')
    ui.main(Array())
     */
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

    val url = "jdbc:mysql://trainingsrv:3306/practice"
    val user = "gentooadmin"
    val pass = "MN3ttXP9LE#?"

    import spark.implicits._
    val df4 = Seq((name, uid, hashed)).toDF("name", "uid", "password")

    val sourceDf=spark.read.format("jdbc").option("url",url)
      .option("dbtable","users").option("user",user)
      .option("password",pass).load()

    df4.write.mode(SaveMode.Append).format("jdbc").option("url",url)
      //df4.write.mode(SaveMode.Ignore).format("jdbc").option("url",url)
      .option("dbtable","users").option("user",user)
      .option("password",pass).save()
    sourceDf.show()

  }

  def createAdmin(): Unit = {
    println("This command may only be run once")
    println("The program will exit if an Admin user already exists")

  }

  def deleteUser (): Unit = {
    // DELETE FROM `practice`.`users` WHERE (`id` = '4');
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
      .option("dbtable", "users").option("user",user)
      .option("password", pass).load()
    sourceDf.show()


  }

  def updateUser (): Unit = {
    // ?????

  }


  // Reading from a CSV with Spark:

  /*
  import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object test2 {
  def main(args: Array[String]): Unit = {
    // create a spark session
    val spark = SparkSession
      .builder
      .appName("hello hive")
      .config("spark.master", "local[*]")
      .enableHiveSupport()
      .getOrCreate()
    Logger.getLogger("org").setLevel(Level.ERROR)
    println("created spark session")
    val df1 = spark.read.csv("hdfs://localhost:9000/user/will/people.csv")
    df1.createOrReplaceTempView("people")
    spark.sql("SELECT * from people WHERE _c1=23; ").show()

    df1.write.option("path","hdfs://localhost:9000/user/will/test10").saveAsTable("PeopleTable10000")
    spark.sql("SELECT * FROM PeopleTable10000").show()


  }
}
   */

}
