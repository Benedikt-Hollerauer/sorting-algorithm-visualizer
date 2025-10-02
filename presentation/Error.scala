import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

object Error:

	def getHtml(errorMessage: String) =
		div(
			ErrorStyle.errorWrapperDiv,
			getErrorMessageWrapper(errorMessage)
		)

	private def getErrorMessageWrapper(errorMessage: String) =
		div(ErrorStyle.errorMessageWrapperDiv,
			div(
				ErrorStyle.cross,
				onClick --> (_ =>
					dom.window.location.href = AppConfig.appUrl
				),
				"X"
			),
			//"An error occurred - ", errorMessage
			"\uD83D\uDCBB Additional algorithms are currently under development \uD83D\uDCBB"
		)

object ErrorStyle:

	val errorWrapperDiv = Seq(
		position.fixed,
		top := "0",
		left := "0",
		width := "100vw",
		height := "100vh",
		backgroundColor := "rgba(2, 6, 23, 0.6)",
		display.flex,
		justifyContent.center,
		alignItems.center,
		zIndex := "10000000000000"
	)

	val errorMessageWrapperDiv = Seq(
		width := "80%",
		height := "80%",
		backgroundColor := "var(--surface)",
		position.relative,
		display.flex,
		justifyContent.center,
		alignItems.center,
		border := "1px solid var(--border)",
		borderRadius := "12px",
		fontSize := "2em",
		textAlign.center,
		boxShadow := "var(--shadow)"
	)

	val cross = Seq(
		position.absolute,
		top := "10px",
		right := "10px",
		cursor.pointer,
		color := "var(--accent)"
	)