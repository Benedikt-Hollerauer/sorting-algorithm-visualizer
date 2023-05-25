package test

object TestUtil:

	def assertRight[L, R](either: Either[L, R])(checkRight: R => Seq[Boolean]): Unit =
		either match
			case Left(actual) =>
				throw new IllegalArgumentException(s"Left value received, but no expected Left value was provided: $actual")
			case Right(actual) =>
				checkRight(actual).foreach: assertionResult =>
					assert(assertionResult, s"Assertion failed. Got: $actual")

	def assertLeft[L, R](either: Either[L, R])(expectedLeft: L): Unit =
		either match
			case Left(actual) =>
				assert(actual == expectedLeft, s"Expected Left value: $expectedLeft, but got: $actual")
			case Right(actual) =>
				throw new IllegalArgumentException(s"Right value received, but no function to check the Left value was provided: $actual")