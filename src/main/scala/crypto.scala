import com.Bcrypt._


object crypto extends App {
  def hashPassword(): Unit = {
    val salted = "password".bcryptSafeBounded
    println(salted)

  }
  
  def checkHash(): Unit = {
    //"password".isbcryptSafeBounded("<password hash>")

  }

  hashPassword()

  // def <update hive/spark function>

}
