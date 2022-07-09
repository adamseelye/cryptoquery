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
      println("Would you like to create an " +
        "(A)dministrator account or a (N)ormal user account?")
      println("Please bear in mind that only one admin account " +
        "may exist in the database at a time.")

      val u_type = readLine("Please enter your choice here: ")

      if (u_type == "a" || u_type == "A") {
        createAdmin()
      } else if (u_type == "n" || u_type == "N") {
        createUser()
      } else {
        println("Invalid input")
        sys.exit(1)
      }


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
