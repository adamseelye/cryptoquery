import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{SaveMode, SparkSession}
import scala.language.postfixOps
import sys.process._

object api extends App {
  try {
    def rpcXMR(): Unit = {
      println("monero rpc testing\n")
    }

    val getBlockCount = "get_block_count"
    val onGetBlockHash = "on_get_block_hash"
    val getLastBlockHeader = "get_last_block_header"
    val getBlock = "get_block"
    val getConnections = "get_connections"
    val getInfo = "get_info"
    val getOutputHistogram = "get_output_histogram"
    val getFeeEstimate = "get_fee_estimate"
    val relayTx = "relay_tx"
    val getOutputDistribution = "get_output_distribution"

    //val curlXmr = "curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_info\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!

    var curlCmd = "get_block_count"
    var curlXmr = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"$curlCmd\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!

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
    }

      rpcXMR()

      sparkCxn()

    } catch {
      case e: Throwable => throw e      // need to write a better method for catching problems
      //case e => println("An Exception Occurred")
    }
  }