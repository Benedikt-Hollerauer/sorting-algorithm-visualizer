import com.raquo.laminar.api.L.{*, given}

object Error:

	def getHtml(errorMessage: String, onDismiss: () => Unit) =
		div(
			ErrorStyle.errorWrapperDiv,
			div(
				ErrorStyle.errorMessageWrapperDiv,
				div(
					className := "modal-close",
					onClick --> (_ => onDismiss()),
					"✕"
				),
				div(ErrorStyle.errorTitle, "Coming Soon"),
				div(ErrorStyle.errorSubtitle, "This algorithm is currently under development")
			)
		)

object ErrorStyle:

	val errorWrapperDiv = Seq(
		position.fixed,
		top := "0",
		left := "0",
		width := "100vw",
		height := "100vh",
		backgroundColor := "rgba(0, 0, 0, 0.72)",
		display.flex,
		justifyContent.center,
		alignItems.center,
		zIndex := "10000000000000"
	)

	val errorMessageWrapperDiv = Seq(
		width := "360px",
		backgroundColor := "var(--surface)",
		border := "1px solid var(--border)",
		position.relative,
		display.flex,
		flexDirection.column,
		justifyContent.center,
		alignItems.center,
		borderRadius := "16px",
		padding := "48px 32px 40px",
		textAlign := "center"
	)

	val errorTitle = Seq(
		fontSize.px := 18,
		fontWeight := "600",
		color := "var(--text)",
		marginBottom.px := 8
	)

	val errorSubtitle = Seq(
		fontSize.px := 14,
		color := "var(--muted)"
	)
