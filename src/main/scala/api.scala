import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{SaveMode, SparkSession}
import scala.io.StdIn.readLine
import scala.language.postfixOps
import sys.process._

object api extends App {
  try {

    println("There are a number of options available")
    println("for querying the Monero blockchain.")
    println("They are as follows:")

    val cmd_map = Map(1 -> "get_block_count" , 2 ->"on_get_block_hash", 3 -> "get_last_block_header",
      4 -> "get_block", 5 -> "get_connections", 6 -> "get_info", 7 -> "get_output_histogram",
      8 -> "get_fee_estimate", 9 -> "relay_tx", 10 -> "get_output_distribution")

    println("1. " + "getBlockCount")
    println("2. " + "onGetBlockHash")
    println("3. " + "getLastBlockHeader")
    println("4. " + "getBlock")
    println("5. " + "getConnections")
    println("6. " + "getInfo")
    println("8. " + "getFeeEstimate")
    println("9. " + "relayTx")
    println("10. " + "getOutputDistribution")

    println("\n")

    val choice = readLine("Please choose which curl query to use: ").toInt

    val curl_cmd = cmd_map(choice)
    val curl_xmr = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"$curl_cmd\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!

    println(curl_xmr)

    def sparkCxn(): Unit = {
      val spark = SparkSession
        .builder
        .appName("Project One Spark")
        .master("spark://trainingsrv:7077")
        .config("spark.master", "local[*]")
        .config("spark.driver.allowMultipleContexts", "true")
        .enableHiveSupport()
        .getOrCreate()
      Logger.getLogger("org").setLevel(Level.ERROR)
      println("Spark Session Created")

      val url = "jdbc:mysql://trainingsrv:3306/practice"
      val user = "gentooadmin"
      val pass = "MN3ttXP9LE#?"

      val sourceDf = spark.read.format("jdbc").option("url", url)
        .option("dbtable", "monero").option("user", user)
        .option("password", pass).load()
      sourceDf.show()

/*
      import spark.implicits._

      val col = curlCmd

      // val df4 = Seq((id, userid, curlXmr)).toDF("id", "user", "catchall")
      val df4 = List(curlXmr).toDF("id", s"$col")
      df4.show()

      df4.write.mode(SaveMode.Append).format("jdbc").option("url", url)
        //df4.write.mode(SaveMode.Ignore).format("jdbc").option("url",url)
        .option("dbtable", "monero").option("user", user)
        .option("password", pass).save()
      sourceDf.show()

 */
    }

      //sparkCxn()

    } catch {
      case e: Throwable => throw e      // need to write a better method for catching problems
      //case e => println("An Exception Occurred")
    }
  }