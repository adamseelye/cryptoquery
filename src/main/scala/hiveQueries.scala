object hiveQueries {
  def login(args: Array[String]): Unit = {
    /* val password = // pass user password into this variable
    val checkpassword = // pass check password function into this variable

    if (checkpassword = true)
      set db login field to true
      continue program (display 'success')
    else
      exit program (display 'wrong password')

     */
  }

  def logout(args: Array[String]): Unit = {
    /*
    set db login field to false
    display ('logged out')

     */
  }

  def createUser(args: Array[String]): Unit = {
    /*
    insert user into db with id, name
    run password hashing on user supplied pword
    insert pword into db user row

     */

  }

  def checkPwd(args: Array[String]): Unit = {
    /*
    run password hash on user supplied input
    check db to see if hash matches stored hash
    login if match, logout if not

     */

  }
}
