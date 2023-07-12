import com.raquo.laminar.api.L.{*, given}

object Legend:

	def getHtml =
		div(
			LegendStyle.legendStyle,
			getLegendItems(
				List(
					("#390099", "Unsorted"),
					("#c1121f", "Focused"),
					("#008000", "Swapped"),
					("#4cc9f0", "Already Sorted"),
					("#f72585", "Finished Sorting")
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

	val legendHeight = height.percent := 12

	def getColorCircleStyle(color: String) = Seq(
		borderRadius.percent := 50,
		width.px := 15,
		height.px := 15,
		display.inlineBlock,
		backgroundColor := color,
		marginRight.px := 5
	)

	val legendStyle = Seq(
		width.percent <-- NavigationBar.menuVisibleVar.signal.map: //TODO make public to fulfill dry principle
			if(_) 75
			else 100,
		legendHeight,
		display.flex,
		justifyContent.center,
		alignItems.center
	)

	val liStyle = Seq(
		display.inlineBlock,
		marginRight.px := 20
	)

	val ulStyle = Seq(
		listStyleType.none,
		fontSize.px := 20
	)