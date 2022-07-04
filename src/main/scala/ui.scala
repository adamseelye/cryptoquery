import scala.io.StdIn.readLine

object ui {
  def greeting(args: Array[String]): Unit = {
    println("~~~CRYPTOQUERY~~~\n")
    println("~Asking questions of the Monero blockchain~\n")
    println("What would you like to do?\n")

  }

  def main(args: Array[String]): Unit = {
    println("(L)ogin")
    println("(C)reate Account")
    println("(U)pdate or (D)elete Account")
    println("(E)xit\n")

    val choice = readLine("Please enter here: ")

    if (choice == "l" || choice == "L")
      println("choice was L")
    else if (choice == "c" || choice == "C")
      println("choice was C")
    else if (choice == "u" || choice == "U")
      println("choice was U")
    else if (choice == "d" || choice == "D")
      println("choice was D")
    else if (choice == "e" || choice == "E")
      println("choice was E")
    else
      println("something else: " + choice)

  }

  greeting(Array())

}
