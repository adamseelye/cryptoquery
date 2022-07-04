import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{SaveMode, SparkSession}

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object dbtest {
  def main(args: Array[String]): Unit = {
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

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val loadingDate = LocalDateTime.of(2001, 3, 9, 0, 0, 0)
    val x = loadingDate.format(formatter)

    val sourceDf=spark.read.format("jdbc").option("url",url)
      .option("dbtable","users").option("user",user)
      .option("password",pass).load()
    sourceDf.show()

    import spark.implicits._

    val df4 = Seq((13, "start", x)).toDF("user_id", "action", "date")
    df4.show()

    //first example we just append the new dataframe with one tuple or row onto users table
    df4.write.mode(SaveMode.Append).format("jdbc").option("url",url)
      .option("dbtable","users").option("user",user)
      .option("password",pass).save()
    sourceDf.show()

    val loadingDate2 = LocalDateTime.of(2002, 4, 10, 0, 0, 0)
    val y =loadingDate2.format(formatter)

    val df5 = Seq((14, "start", y)).toDF("user_id", "action", "date")
    df5.show()

    //second example we create and overwrite (if it exists) a new table, users2,
    //this table is just the original table as a dataframe with a new dataframe of one row added on
    //with union below
    val sourceDf2=sourceDf.union(df5)

    sourceDf2.write.mode(SaveMode.Overwrite).format("jdbc").option("url",url)
      .option("dbtable","users2").option("user",user)
      .option("password",pass).save()

    sourceDf2.show()

    //we can create other dataframes using CreateOrReplaceTempView
    //now lets query this view using hiveql

    sourceDf2.createOrReplaceTempView("usersView")
    val Df3 = spark.sql("SELECT * FROM usersView WHERE user_id IN (1,13,14)")
    Df3.show()
  }
}

//below in mysql for testing
/*
USE test;
DROP TABLE IF EXISTS Users;
CREATE TABLE Users(
user_id int NOT NULL,
action varchar(255),
date date
);

INSERT INTO Users(user_id,action,date)
VALUES (1,'start','2000/01/01'),
(1,'cancel','2000/01/02'),
(2,'start','2000/01/03'),
(2,'publish','2000/01/04'),
(3,'start','2000/01/05'),
(3,'cancel','2000/01/06'),
(4,'start','2000/01/07');
DROP TABLE IF EXISTS users2;
*/
