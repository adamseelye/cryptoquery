import scala.io.StdIn.readLine

object ui {
  def greeting(args: Array[String]): Unit = {
    println("~~~CRYPTOQUERY~~~\n")
    println("~Asking questions of the Monero blockchain~")
    println("\n")
    println("What would you like to do?\n")

  }

  def main(args: Array[String]): Unit = {
    println("(L)ogin")
    println("(C)reate Account")
    println("(U)pdate or (D)elete Account")
    println("(E)xit\n")

    val choice = readLine("Please enter here: ")

    println(choice)

  }

  greeting(Array())

}
