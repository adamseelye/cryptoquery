import com.Bcrypt._
import scala.language.postfixOps

object crypto extends App {
  def hashPassword(p: String): String = {
    p.bcryptSafeBounded.get
  }

  def checkHash(pwd: String, hash: String): String = {
    pwd.isBcryptedSafeBounded(hash).get.toString

  }

}
