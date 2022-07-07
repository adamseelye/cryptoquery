import com.github.t3hnar.bcrypt._


object crypto extends App {
  def hashPassword(args: Array[String]): Unit = {
    //"password".bcryptSafeBounded()    // simple hashing (doesn't work yet)

    //val bcryptAndVerify = for {       // advanced hashing (doesn't work yet)
    //      bcrypted <- "hello".bcryptBounded(12)
    //      result <- "hello".isBcryptedSafeBounded(bcrypted)
    //    } yield result
  }
  
  def checkHash(args: Array[String]): Unit = {
    // "password".isbcryptSafeBounded("<password hash>")    // doesn't work yet

  }

  // def <update hive/spark function>

}
