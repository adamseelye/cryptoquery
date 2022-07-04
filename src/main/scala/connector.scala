import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{SaveMode, SparkSession}

import java.sql.SQLException
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement
import java.sql.DriverManager

object connector {
  def hiveCxn (args: Array[String]): Unit = {
    val hiveDriver = "org.apache.hadoop.hive.jdbc.HiveDriver"

    // Set connection and SQL variable
    val cxn = DriverManager.getConnection("jdbc:hive2://<address>:<port>/<dir>", "", "")
    val stmt = cxn.createStatement()
    val table = "testTable"

    // Create table
    stmt.executeQuery(s"DROP TABLE $table IF EXISTS;")
    var res  = stmt.executeQuery(s"CREATE TABLE $table" + " (KEY int, VALUE string);")

    // Select query
    val sql = s"SELECT * FROM $table;"
    res = stmt.executeQuery(sql)
    while (res.next()) {
      println(res.getString(1))

    }
  }

  def sparkCxn (args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("hello hive")
      .master("spark://trainingsrv:7077")
      .config("spark.master", "local[*]")
      .config("spark.driver.allowMultipleContexts","true")
      .enableHiveSupport()
      .getOrCreate()
    Logger.getLogger("org").setLevel(Level.ERROR)
    println("created spark session")

    val url = "jdbc:mysql://trainingsrv:3306/practice"
    val user = "gentooadmin"
    val pass = "MN3ttXP9LE#?"

    val sourceDf=spark.read.format("jdbc").option("url",url)
      .option("dbtable","users").option("user",user)
      .option("password",pass).load()
    sourceDf.show()



  }
}
