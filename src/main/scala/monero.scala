import scala.io.StdIn.readLine
import scala.language.postfixOps
import sys.process._
import io.circe.{ACursor, Decoder, HCursor, Json, parser}
import io.circe.parser.parse
import scala.math.pow

object monero extends App {
  // 6 questions to answer:
  // Who is using XMR? What kinds of activity? What kind of data? Timing Patterns? Centralization? Fraud Analysis?
  // possibly answer "activity associated with bounties"

  def quest1(): Unit = {
    // What kind of fees does XMR have?
    val get_fee = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_fee_estimate\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!

    val c_fee: Json = parse(get_fee).getOrElse(Json.Null)

    val fee_cursor: HCursor = c_fee.hcursor
    val fee_json: Decoder.Result[Double] = {
      fee_cursor.downField("result").get[Double]("fee")
    }
    val fee_result = fee_json.productElement(0).toString
    val fee = fee_result.toDouble / pow(10, 12)
    println("The total fee for executing a single transaction with Monero: " + fee + " XMR")
    println("This is worth about: $" + (fee * 130))

  }

  def quest2(): Unit = {

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


  }

  def quest3(): Unit = {

    // What kind of activity is happening on the XMR blockchain?
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


  }

  def quest4(): Unit = {
    // What kind of data can we extract from the XMR blockchain?
    val get_con = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_connections\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_blk = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_block\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!

    val c_curled: Json = parse(get_con).getOrElse(Json.Null)
    val c_blk: Json = parse(get_blk).getOrElse(Json.Null)

    // must use external source of tx hash for relay_tx
    println("Here we will check the error status of a transaction.")
    println("Unfortunately, it is not possible to automatically retrieve transactions from this node.")
    println("An external source must be used to find transaction hashes; recommended here is xmrchain.net.")
    val tx_hash = readLine("Please enter a transaction hash here: ")

    val relay_tx = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"relay_tx\", \"params\":{\"txids\":[\"'$tx_hash'\"]}}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
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

    println(rtx_result)
    println("Identifying information:")
    val g_map = Map("IP: " -> g_json1.productElement(0).toString, "City: " -> g_json2.productElement(0).toString,
      "Country: " -> g_json3.productElement(0).toString, "Internet Service Provider: " -> g_json4.productElement(0).toString)

    for ((k, v) <- g_map) {
      println(k + v)
    }

    val blk_cursor: HCursor = c_blk.hcursor
    val blk_json: Decoder.Result[Double] = {
      blk_cursor.downField("result").downField("block_header").get[Double]("reward")
    }
    val blk_result = blk_json.productElement(0).toString
    val blk_math = blk_result.toDouble / pow(10, 13)
    val usd = blk_math * 130

    println("")
    println("Blocks make up the blockchain; a block is basically just a list of transactions.")
    println("Each block is identified by its hash, and cryptocurrency mining is all about searching for the hashes.")
    println("When a hash is found, a reward is paid to the miner who discovered it")
    println("Last block mining reward: " + blk_math + " XMR")
    println("Value in USD: $" + usd)

  }

  def quest5(): Unit = {
    // Are there any obvious patterns in the timing of transactions?
    val get_sync = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"sync_info\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!


    val c_sync: Json = parse(get_sync).getOrElse(Json.Null)

    val sync_cursor: HCursor = c_sync.hcursor
    val sync_json1: Decoder.Result[Int] = {
      sync_cursor.downField("result").downField("peers").downArray.downField("info").get[Int]("recv_count")
    }
    val sync_result1 = sync_json1.productElement(0).toString
    val sync_json2: Decoder.Result[Int] = {
      sync_cursor.downField("result").downField("peers").downArray.downField("info").get[Int]("send_count")
    }
    val sync_result2 = sync_json2.productElement(0).toString

    println("Here, we query the node to see how many transactions are sent and received at a given time.")
    println("It may be advisable to run this command repeatedly to more clearly see any patterns.")
    println("Transaction receive count: " + sync_result1)
    println("Transaction send count: " + sync_result2)

  }



  def quest6(): Unit = {
    // How might Law Enforcement analyze the XMR blockchain for fraud-related activities?
    val get_info = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_info\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_con = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_connections\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_blk = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_block\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_lbh = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_last_block_header\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!

    val c_info: Json = parse(get_info).getOrElse(Json.Null)

    val info_cursor: HCursor = c_info.hcursor
    val info_ocx_json: Decoder.Result[Long] = {
      info_cursor.downField("result").get[Long]("outgoing_connections_count")
    }
    val ocx_result = info_ocx_json.productElement(0).toString

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

    println("Outgoing connections to a node can tell about who else might be connected to this node;" +
      "the connections aren't just other nodes, but users on the network as well.")
    println("Outgoing connections from this node: " + ocx_result)
    println("Again, we have the identifying information of another connected node:")
    val g_map = Map("IP: " -> g_json1.productElement(0).toString, "City: " -> g_json2.productElement(0).toString,
      "Country: " -> g_json3.productElement(0).toString, "Internet Service Provider: " -> g_json4.productElement(0).toString)

    for ((k, v) <- g_map) {
      println(k + v)
    }

    val c_lbh: Json = parse(get_lbh).getOrElse(Json.Null)

    val lbh_cursor: HCursor = c_lbh.hcursor
    val lbh_json: Decoder.Result[Long] = {
      lbh_cursor.downField("result").downField("block_header").get[Long]("height")
    }
    val lbh_result = lbh_json.productElement(0).toString

    println("This number is the current height of the blockchain - it is the number of blocks that exist in total.")
    println("This number may be used to reference previously confirmed blocks.")
    println("Current blockchain height: " + lbh_result)


    println("This function, the output histogram, will give a count of the number of times")
    println("a given face value has been used in a transaction, i.e. 20 XMR has a total of 381483 usage instances.")

    val amount = readLine("Please enter the XMR amount you'd like to check: ")
    val math = amount.toInt * 1000000
    val get_oh = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_output_histogram\", \"params\":{\"amounts\":[\"'$math'\"]}}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!

    val c_hist: Json = parse(get_oh).getOrElse(Json.Null)

    val hist_cursor: HCursor = c_hist.hcursor
    val hist_json: Decoder.Result[Long] = {
      hist_cursor.downField("result").downField("histogram").downArray.downField("total_instances").as[Long]
    }

    val hist_result = hist_json.productElement(0).toString

    println("")
    println("The amount of " + amount + " XMR has a total of " + hist_result + " usage instances" )
    println("")
    println("And with this, we have a number of different methods to attempt to analyze the Monero blockchain,")
    println("as well as perhaps serving as a starting point for a more in-depth analysis.")
    println("")
    println("Thank you for using CRYPTOQUERY")

  }

}
