import scala.io.StdIn.readLine
import scala.util.Try

object hiveQueries {
  def login(): Unit = {
    val checkedPwd = checkPwd()

    if (checkedPwd == "true") { // use this for login logic
      println("Password Success").toString
    } else {
      println("Password Failure").toString
    }
  }

  def logout(): Unit = {
    /*
    set db login field to false
    display ('logged out')
    ui.main(Array())
     */
  }

  def createUser (): Unit = {
    val uid = readLine("Please enter a username: ")
    val name = readLine("Please enter your name: ")
    val password = readLine("Please choose a strong password: ")

    crypto.hashPassword(password)

    // <SQL INSERT function> name, uid, password VALUES ? ? ?
    // hive / spark cxn

  }

  def checkPwd(): String = {
    val check_pass = readLine("Please enter your password: ")
    crypto.checkHash(check_pass)
  }

  def deleteUser (): Unit = {
    // ?????

  }

  def updateUser (): Unit = {
    // ?????

  }


}
