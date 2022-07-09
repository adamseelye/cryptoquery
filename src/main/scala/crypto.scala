import com.Bcrypt._
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{SaveMode, SparkSession}

import scala.language.postfixOps

object crypto extends App {
  def hashPassword(p: String): Unit = {
    p.bcryptSafeBounded
  }

  def checkHash(c: String): String = {
    val hash = c.bcryptSafeBounded // will need to match with DB hash
    c.isBcryptedSafeBounded(hash.get).get.toString




  }

  // def <update hive/spark function>

}
