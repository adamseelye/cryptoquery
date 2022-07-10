import scala.io.StdIn.readLine
import sparkQueries._

import scala.collection.immutable.ListMap

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
      login()

    } else if (choice == "c" || choice == "C") {
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


    } else if (choice == "d" || choice == "D") {
      println("Please log in to continue")

      if (login() == "Success") {
        deleteUser()
      } else {
        println("Login Error")
        sys.exit(1)
      }


    /*} else if (choice == "o" && logged_in = true || choice == "O" && logged_in = true) {
        println("Log Out")
        logout()
    */
    } else if (choice == "e" || choice == "E") {
      println("Exiting program")
      sys.exit(0)

    } else {
      println("Input not accepted, exiting program")
      sys.exit(1)
    }

  }

  def xmrQueries(): Unit = {
    println("Thank you for logging in.")
    println("We can now begin to analyze the Monero (XMR) blockchain.")
    println("Monero is a cryptocurrency, much like Bitcoin.")
    println("However, the key difference between Monero and Bitcoin is")
    println("that Monero transactions cannot be traced (supposedly) like")
    println("Bitcoin transactions can. This is great for privacy advocates")
    println("around the world, but causes problems for law enforcement")
    println("agencies trying to combat fraud.")
    println("Here, we will ask some questions based on data we get from")
    println("interacting with a self-hosted node on the Monero blockchain.")
    println("~~~\n")

    val q_map = Map(1 -> "Who is using XMR?", 2 -> "What kind of activity is happening on the XMR blockchain?",
      3 -> "What kind of data can we extract from the XMR blockchain?", 4 -> "Are there any obvious patterns in the timing of transactions?",
      5 -> "Is there evidence for centralization of XMR activity?", 6 -> "How might Law Enforcement analyze the XMR blockchain for fraud-related activities?")

    println("We have a list of questions to ask: ")

    val res = ListMap(q_map.toSeq.sortBy(_._1): _*)

    for ((k, v) <- res) {
      println(k + ". " + v)
    }

    val choice_int = readLine("\nWhich would you like to ask? Please enter the number here: ").toInt

    if (choice_int == 1) {
      monero.quest1()
    } else if (choice_int == 2) {
      monero.quest2()
    } else if (choice_int == 3) {
      monero.quest2()
    } else if (choice_int == 4) {
      monero.quest2()
    } else if (choice_int == 5) {
      monero.quest2()
    } else if (choice_int == 6) {
      monero.quest2()
    } else {
      println("Input error")
      sys.exit(1)
    }

  }

}
