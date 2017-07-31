package org.ssm.parser.util

class Pipe[A](a: A) {
  def |>[B](f: A => B): B =
    f(a)
}

object Pipe {
  def apply[A](a: A) = new Pipe(a)
}

object PipeOps {
  implicit def toPipe[A](a: A): Pipe[A] =
    Pipe(a)
}
