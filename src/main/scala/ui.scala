import scala.io.StdIn.readLine
import sparkQueries._

object ui {
  def greeting(): Unit = {
    println("~~~CRYPTOQUERY~~~")
    println("~Asking questions of the Monero blockchain~\n")
    println("What would you like to do?\n")

  }

  def main(args: Array[String]): Unit = {
    println("(L)ogin")
    println("(C)reate Account")
    println("(U)pdate or (D)elete Account")
    /*
    if (logged_in = true)
      println("Log (O)ut")
     */
    println("(E)xit\n")

    val choice = readLine("Please enter here: ")

    if (choice == "l" || choice == "L") {
      println("Log In")
      login()

    } else if (choice == "c" || choice == "C") {
      println("Create a user account")
      createUser()

    } else if (choice == "u" || choice == "U") {
      println("Update user information")
      updateUser()

    } else if (choice == "d" || choice == "D") {
      println("Delete a user account")
      deleteUser()

    /*} else if (choice == "o" && logged_in = true || choice == "O" && logged_in = true) {
        println("Log Out")
        logout()
    */
    } else if (choice == "e" || choice == "E") {
      println("Exiting program")
      sys.exit(0)

    } else {
      println("something else: " + choice)
      println("Input not accepted, exiting program")
      sys.exit(1)
    }

  }

}
