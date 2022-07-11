import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import scala.io.StdIn.readLine
import scala.language.postfixOps
import sys.process._
import io.circe.{ACursor, Decoder, HCursor, Json, parser}
import io.circe.generic.semiauto.deriveDecoder
import io.circe.parser.parse

object monero extends App {
  // should load monero data into hive
  // could perform operations
  // 6 questions to answer:
  // Who is using XMR? What kinds of activity? What kind of data? Timing Patterns? Centralization? Fraud Analysis?
  // possibly answer "activity associated with bounties"

  def quest1(): Unit = {
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

    // Who is using XMR?
    val get_con = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_connections\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!

    val c_curled: Json = parse(get_con).getOrElse(Json.Null)

    val c_cursor: HCursor = c_curled.hcursor
    val c_json: Decoder.Result[String] = {
      c_cursor.downField("result").downField("connections").downArray.downField("address").as[String]
    }
    val j_result = c_json.productElement(0).toString
    val j_split = j_result.split(":")(0)


    val get_geo = s"curl ipinfo.io/$j_split" !!
    val g_curled: Json = parse(get_geo).getOrElse(Json.Null)

    val g_cursor: HCursor = g_curled.hcursor
    val g_json1: Decoder.Result[String] = {
      g_cursor.downField("ip").as[String]
    }
    val g_json2: Decoder.Result[String] = {
      g_cursor.downField("city").as[String]
    }
    val g_json3: Decoder.Result[String] = {
      g_cursor.downField("country").as[String]
    }
    val g_json4: Decoder.Result[String] = {
      g_cursor.downField("org").as[String]
    }

    println("We have just queried one of the 12 connected nodes.")
    println("We can see its IP address and location information here:\n")

    val g_map = Map("IP: " -> g_json1.productElement(0).toString, "City: " -> g_json2.productElement(0).toString,
      "Country: " -> g_json3.productElement(0).toString, "Internet Service Provider: " -> g_json4.productElement(0).toString)

    for ((k, v) <- g_map) {
      println(k + v)
    }
    println("\n")
    println("This doesn't say much about who is using Monero, but it's the only identifying information directly")
    println("extractable from the Monero blockchain. There are no commands available that display Monero user location information.")
    println("In essence, we can try to find out the identity of those who run Monero nodes, but this will not help unmask")
    println("its daily users.")

/*
    val sourceDf = spark.read.format("jdbc").option("url", url)
      .option("dbtable", "monero").option("user", user)
      .option("password", pass).load()
    sourceDf.show()

    sourceDf.createOrReplaceTempView("dataView")
    spark.sql(s"INSERT INTO dataView (id, uid, data) VALUES (NULL, 'placeholder', '$get_ver')")

    val df1 = spark.sql("SELECT * FROM dataView")
    df1.show()

 */

  }

  def quest2(): Unit = {
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

    // What kind of activity is happening on the XMR blockchain?
    // get_output_histogram, get_last_block_header, get_info
    val get_lbh = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_last_block_header\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_info = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_info\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!

    val c_lbh: Json = parse(get_lbh).getOrElse(Json.Null)

    val lbh_cursor: HCursor = c_lbh.hcursor
    val lbh_json: Decoder.Result[Long] = {
      lbh_cursor.downField("result").downField("block_header").get[Long]("difficulty")
    }
    val j_result1 = lbh_json.productElement(0).toString


    val c_info: Json = parse(get_info).getOrElse(Json.Null)

    val info_cursor: HCursor = c_info.hcursor
    val info_tx_json: Decoder.Result[Long] = {
      info_cursor.downField("result").get[Long]("tx_count")
    }
    val info_dbsize_json: Decoder.Result[Long] = {
      info_cursor.downField("result").get[Long]("database_size")
    }
    val j_result2 = info_tx_json.productElement(0).toString
    val j_result3 = info_dbsize_json.productElement(0).toString
    val db_size = j_result3.toDouble / 1000000000 + "GB"

    println("This number tells us the current mining difficulty of the Monero blockchain.")
    println("It is measured in 'hashes per second', or the amount of cryptographic hashes")
    println("generated per second. Should a computer manage to generate that amount, it is guaranteed")
    println("to generate a block on the network. Otherwise, generating the correct hash is left to chance.")
    println(s"Current difficulty: $j_result1")
    println("")
    println("This number is the number of transactions written into the current block that will be")
    println("confirmed once the next hash is found. Blocks on Monero are generated approximately every 2 minutes.")
    println(s"Current transaction count: $j_result2")
    println("")
    println("This number is the current size of the entire Monero blockchain database, in gigabytes.")
    println("The database includes all actions and metadata. Transactions (most of the data) are around 2kb each.")
    println(s"Current database size: $db_size")


    /*
    val sourceDf = spark.read.format("jdbc").option("url", url)
      .option("dbtable", "users").option("user", user)
      .option("password", pass).load()
    sourceDf.show()

     */

  }

  def quest3(): Unit = {
    // What kind of data can we extract from the XMR blockchain?
    // get_connections, others
    val get_con = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_connections\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_blk = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_block\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!

    val c_curled: Json = parse(get_con).getOrElse(Json.Null)
    val c_blk: Json = parse(get_blk).getOrElse(Json.Null)

    // must use external source of tx hash for relay_tx

    println("Unfortunately, it is not possible to automatically retrieve transactions from this node.")
    println("An external source must be used to find transaction hashes; recommended here is xmrchain.net.")
    val tx_hash = readLine("Please enter a transaction hash here: ")

    val relay_tx = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{jsonrpc:2.0,id:0,method:relay_tx, 'params':{'txids':['$tx_hash']}}' -H 'Content-Type: application/json'}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val c_rtx: Json = parse(relay_tx).getOrElse(Json.Null)

    val rtx_cursor: HCursor = c_rtx.hcursor
    val rtx_json: Decoder.Result[Int] = {
      rtx_cursor.downField("error").get[Int]("code")
    }
    val rtx_result = "Transaction error code (0 for no error): " + rtx_json.productElement(0).toString

    val c_cursor: HCursor = c_curled.hcursor
    val c_json: Decoder.Result[String] = {
      c_cursor.downField("result").downField("connections").downArray.downField("address").as[String]
    }
    val j_result = c_json.productElement(0).toString
    val j_split = j_result.split(":")(0)

    val get_geo = s"curl ipinfo.io/$j_split" !!
    val g_curled: Json = parse(get_geo).getOrElse(Json.Null)

    val g_cursor: HCursor = g_curled.hcursor
    val g_json1: Decoder.Result[String] = {
      g_cursor.downField("ip").as[String] }
    val g_json2: Decoder.Result[String] = {
      g_cursor.downField("city").as[String] }
    val g_json3: Decoder.Result[String] = {
      g_cursor.downField("country").as[String] }
    val g_json4: Decoder.Result[String] = {
      g_cursor.downField("org").as[String] }

    println("Transaction error code: ")
    println(rtx_result)
    println("Identifying information:")
    val g_map = Map("IP: " -> g_json1.productElement(0).toString, "City: " -> g_json2.productElement(0).toString,
      "Country: " -> g_json3.productElement(0).toString, "Internet Service Provider: " -> g_json4.productElement(0).toString)

    for ((k, v) <- g_map) {
      println(k + v)
    }

    val blk_cursor: HCursor = c_blk.hcursor
    val blk_json: Decoder.Result[Long] = {
      blk_cursor.downField("result").get[Long]("hash")
    }
    val blk_result = blk_json.productElement(0).toString

    println("")
    println("Blocks make up the blockchain; a block is basically just a list of transactions.")
    println("Each block is identified by its hash, and cryptocurrency mining is all about searching for the hashes.")
    println("Hash of last block: " + blk_result)

  }

  def quest4(): Unit = {
    // Are there any obvious patterns in the timing of transactions?
    // get_output_histogram, get_block, relay_tx
    val get_blk = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_block\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val relay_tx = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"relay_tx\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!

  }

  def quest5(): Unit = {
    // Is there evidence for centralization of XMR activity?
    // get_block_count, get_connections, get_info, get_fee_estimate, relay_tx, get_output_distribution
    val get_blk = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_block_count\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_con = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_connections\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_info = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_info\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val relay_tx = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"relay_tx\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_od = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_output_distribution\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_fe = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_fee_estimate\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!

  }

  def quest6(): Unit = {
    // How might Law Enforcement analyze the XMR blockchain for fraud-related activities?
    // basically all commands
    val get_info = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_info\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_con = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_connections\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_blk = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_block_count\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val relay_tx = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"relay_tx\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_od = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_output_distribution\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_fe = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_fee_estimate\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_oh = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_output_histogram\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_lbh = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_last_block_header\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!


  }

  def summary(): Unit = {
    // summarize data gathering

  }

}
