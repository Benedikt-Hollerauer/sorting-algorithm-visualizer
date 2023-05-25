package test

object TestUtil:

	def assertRight[L, R](either: Either[L, R])(checkRight: Seq[R => Boolean]): Unit =
		either match
			case Left(actual) =>
				throw new IllegalArgumentException("Left value received, but no expected Left value was provided.")
			case Right(actual) =>
				checkRight.foreach: func =>
					assert(func(actual), s"Assertion failed. Got: $actual")

	def assertLeft[L, R](either: Either[L, R])(expectedLeft: L): Unit =
		either match
			case Left(actual) =>
				assert(actual == expectedLeft, s"Expected Left value: $expectedLeft, but got: $actual")
			case Right(actual) =>
				throw new IllegalArgumentException("Right value received, but no function to check the Right value was provided.")