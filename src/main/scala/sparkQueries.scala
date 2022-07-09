import scala.io.StdIn.readLine
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object sparkQueries extends App {
  // def <constructor>

  def sparkCxn (): Unit = {
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
    sourceDf.show()
  }

  sparkCxn()



  def login(): Unit = {
    val checkedPwd = checkPwd()

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

    crypto.hashPassword(password)

    // <SQL INSERT function> name, uid, password VALUES ? ? ?
    // hive / spark cxn

  }

  def checkPwd(): String = {
    val check_pass = readLine("Please enter your password: ")
    crypto.checkHash(check_pass)
  }

  def deleteUser (): Unit = {
    // ?????

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
