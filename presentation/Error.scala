import com.raquo.laminar.api.L.{*, given}

object Error:

	def getHtmlDiv(errorMessage: String) =
		div(
			ErrorStyle.errorWrapperDiv,
			getErrorMessageWrapperDiv(errorMessage)
		)

	private def getErrorMessageWrapperDiv(errorMessage: String) =
		div(
			ErrorStyle.errorMessageWrapperDiv,
			"An error occurred - ",
			errorMessage
		)

object ErrorStyle:

	val errorWrapperDiv = Seq(
		position.fixed,
		top := "0",
		left := "0",
		width := "100vw",
		height := "100vh",
		backgroundColor.black,
		opacity := "0.75",
		display.flex,
		justifyContent.center,
		alignItems.center,
		zIndex := "10000000000000"
	)

	val errorMessageWrapperDiv = Seq(
		width := "80%",
		height := "80%",
		backgroundColor.white,
		position.relative,
		display.flex,
		justifyContent.center,
		alignItems.center,
		borderRadius := "10px",
		fontSize := "2em",
		textAlign.center
	)