import com.Bcrypt._
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{SaveMode, SparkSession}

import scala.language.postfixOps

object crypto extends App {
  def hashPassword(p: String): String = {
    p.bcryptSafeBounded.get
  }

  def checkHash(pwd: String, hash: String): String = {
    //val hash = c.bcryptSafeBounded // will need to match with DB hash
    pwd.isBcryptedSafeBounded(hash).get.toString

  }

  // def <update hive/spark function>

}
