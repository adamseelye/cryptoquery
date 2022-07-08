package com

import org.mindrot.jbcrypt.{BCrypt => B}

/**
 * @author Yaroslav Klymko
 */
object BCrypt {
  def gensalt(rounds: Int = 10): String = B.gensalt(rounds)
}