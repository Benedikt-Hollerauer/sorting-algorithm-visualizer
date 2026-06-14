import com.raquo.laminar.api.L.{*, given}

object Legend:

	def getHtml =
		div(
			LegendStyle.legendStyle,
			getLegendItems(
				List(
					("#c084fc", "Unsorted"),
					("#ff3a3a", "Focused"),
					("#00ff7f", "Swapped"),
					("#ffe600", "Current Pivot"),
					("#00d4ff", "Already Sorted"),
					("#ff40cb", "Finished Sorting")
				)
			)
		)

	private def getLegendItems(items: List[(String, String)]) =
		ul(
			LegendStyle.ulStyle,
			items.map: item =>
				li(
					LegendStyle.liStyle,
					div(
						LegendStyle.getColorCircleStyle(item._1)
					),
					item._2
				)
		)

object LegendStyle:

	val legendHeight = height.percent := 10

	def getColorCircleStyle(color: String) = Seq(
		borderRadius.percent := 50,
		width.px := 15,
		height.px := 15,
		display.inlineBlock,
		backgroundColor := color,
		marginRight.px := 5
	)

	val legendStyle = Seq(
		className := "legend-bar",
		width <-- NavigationBar.extendCollapseSideMenuVar.signal.map:
			if(_) "calc(100% - 280px)" else "100%",
		legendHeight,
		backgroundColor := "var(--bg)",
		borderTop := "1px solid var(--border)",
		display.flex,
		justifyContent.center,
		alignItems.center,
		columnGap.px := 12
	)

	val liStyle = Seq(
		display.flex,
		alignItems.center
	)

	private val gap: StyleProp[String] = new StyleProp[String]("gap")

	val ulStyle = Seq(
		listStyleType.none,
		fontSize.px := 13,
		color := "var(--muted)",
		backgroundColor := "var(--surface)",
		borderRadius.px := 10,
		border := "1px solid var(--border)",
		padding := "8px 14px",
		margin.px := 0,
		boxSizing.borderBox,
		display.flex,
		flexWrap.wrap,
		gap := "6px 16px"
	)