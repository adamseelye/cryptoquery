import scala.io.StdIn.readLine
import scala.language.postfixOps
import sys.process._

object monero extends App {
  // should load monero data into hive
  // could perform operations
  // 6 questions to answer:
  // Who is using XMR? What kinds of activity? What kind of data? Timing Patterns? Centralization? Fraud Analysis?
  // possibly answer "activity associated with bounties"

  def quest1(): Unit = {
    // Who is using XMR?
    // get_connections, relay_tx, get_block
    val get_con = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_connections\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val relay_tx = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"relay_tx\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_blk = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_block\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!


    println(get_con)
    println(relay_tx)
    println(get_blk)

  }

  def quest2(): Unit = {
    // What kind of activity is happening on the XMR blockchain?
    // get_output_histogram, get_last_block_header, get_info
    val get_oh = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_output_histogram\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_lbh = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_last_block_header\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_info = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_info\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!



  }

  def quest3(): Unit = {
    // What kind of data can we extract from the XMR blockchain?
    // get_connections, others
    val get_con = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_connections\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val relay_tx = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"relay_tx\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_blk = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_block\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!

  }

  def quest4(): Unit = {
    // Are there any obvious patterns in the timing of transactions?
    // get_output_histogram, get_block, relay_tx, get_output_distribution
    val get_oh = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_output_histogram\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_blk = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_block\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val relay_tx = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"relay_tx\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!
    val get_od = s"curl --digest -u monero:5rHEQJYmGqq0jcvnVvEzkg== -X post -d '{\"jsonrpc\":\"2.0\",\"id\":\"0\",\"method\":\"get_output_distribution\"}' -H 'Content-Type: application/json' http://127.0.0.1:18081/json_rpc" !!

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
