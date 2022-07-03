import java.sql.SQLException
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement
import java.sql.DriverManager

class connector extends App {
  def hiveCxn (args: Array[String]): Unit = {
    val hiveDriver = "org.apache.hadoop.hive.jdbc.HiveDriver"

    // Set connection and SQL variable
    val cxn = DriverManager.getConnection("jdbc:hive2://<address>:<port>/<dir>", "", "")
    val stmt = cxn.createStatement()
    val table = "testTable"

    // Create table
    stmt.executeQuery(s"DROP TABLE ${table} IF EXISTS;")
    var res  = stmt.executeQuery(s"CREATE TABLE ${table}" + " (KEY int, VALUE string);")

    // Select query
    val sql = s"SELECT * FROM ${table};"
    res = stmt.executeQuery(sql)
    while (res.next()) {
      println(res.getString(1))

    }
  }
}
