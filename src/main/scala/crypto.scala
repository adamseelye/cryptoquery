import com.Bcrypt._
import scala.io.StdIn.readLine


object crypto extends App {
  def hashPassword(): Unit = {
    val salted = "password".bcryptSafeBounded
    println(salted)

  }
  
  def checkHash(c: String): Unit = {
    //val pass = readLine("Please enter your password: ")
    val pass = c
    val hash = pass.bcryptSafeBounded
    val check = c.isBcryptedSafeBounded(hash.get)
    print(check)
  }

  //hashPassword()
  checkHash("password")

  // def <update hive/spark function>

}
