import scala.io.StdIn.readLine

object hiveQueries {
  def login(): Unit = {
    /* val user_pass = readLine("Please enter your password: ")
    val checkpassword = // pass check password function into this variable

    if (checkpassword = true)
      set db login field to true
      continue program (display 'success')
    else
      exit program (display 'wrong password')

     */
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

    // <password hashing function>

    // <SQL INSERT function> name, uid, password VALUES ? ? ?
    // hive / spark cxn

  }

  def checkPwd(): Unit = {
    /*
    run password hash on user supplied input
    check db to see if hash matches stored hash
    login if match, logout if not

     */

  }

  def deleteUser (): Unit = {
    // ?????

  }

  def updateUser (): Unit = {
    // ?????

  }


}
