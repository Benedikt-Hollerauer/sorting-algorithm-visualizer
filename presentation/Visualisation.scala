import com.raquo.airstream.timing
import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.modifiers.KeySetter
import com.raquo.laminar.nodes.ReactiveHtmlElement
import core.model.*
import org.scalajs.dom.HTMLDivElement

object Content:

	val visualisationStream = EventStream.periodic(1000)

	def getHtml(visualizeModel: VisualizeModel): ReactiveHtmlElement[HTMLDivElement] =
		div(
			ContentStyle.pageContentStyle,
			child <-- SideMenu.sliderSpeedSignal.map: intervalMs =>
				getBarArray(visualizeModel, intervalMs)
		)

	private def getBarArray(visualizeModel: VisualizeModel, intervalMs: Int): ReactiveHtmlElement[HTMLDivElement] =
		div(
			ContentStyle.barArrayStyle,
			children <-- EventStream.periodic(intervalMs)
				.withCurrentValueOf(SideMenu.startStopButtonSignal)
				.scanLeft((0, List.empty[ReactiveHtmlElement[HTMLDivElement]])):
					case (acc, (tick, false)) => (
						acc._1,
						if(acc._1 == 0) getBars(visualizeModel.notStartedSorting)
						else visualizeModel.changes.lift(acc._1) match
							case Some(bar) => getBars(bar)
							case None => getBars(visualizeModel.finishedSorting)
					)
					case (acc, (tick, true)) => (
						acc._1 + 1,
						if(acc._1 == 0) getBars(visualizeModel.notStartedSorting)
						else visualizeModel.changes.lift(acc._1) match
							case Some(bar) => getBars(bar)
							case None => getBars(visualizeModel.finishedSorting)
					)
				.map(_._2)
	)

	private def getBars(toBeBars: SortableModel[BarModel]): List[ReactiveHtmlElement[HTMLDivElement]] =
		val bars = toBeBars.list
		val count = math.max(1, bars.size)
		val maxValue = math.max(1, bars.map(_.value).maxOption.getOrElse(1))
		val widthPercent = 100.0 / count
		bars
			.map: bar =>
				val heightPercent = (bar.value.toDouble / maxValue.toDouble) * 100.0
				div(
					ContentStyle.singleBar(bar, widthPercent, heightPercent)
				)

object ContentStyle:

	def singleBar(bar: BarModel, widthPercent: Double, heightPercent: Double) = Seq(
		width.percent := widthPercent,
		height.percent := heightPercent,
		boxSizing.borderBox,
		backgroundColor := (
			bar.barState match
				case BarStateModel.Normal => "#390099" // TODO other colors here maybe
				case BarStateModel.Focused => "#c1121f"
				case BarStateModel.Swapped => "#008000"
				case BarStateModel.AlreadySorted => "#4cc9f0"
				case BarStateModel.FinishedSorting => "#f72585"
				case BarStateModel.CurrentPivot => "#008000"
		),
		borderRadius.px := 8
	)

	val pageContentStyle = Seq(
		position.relative,
		width.percent <-- NavigationBar.extendCollapseSideMenuVar.signal.map: //TODO make public to fulfill dry principle
			if(_) 75
			else 100,
		height := s"calc(100% - ${NavigationBarStyle.navigationBarHeight.value} - ${LegendStyle.legendHeight.value})",
		display.flex,
		justifyContent.center,
		alignItems.flexEnd,
		overflow.hidden
	)

	val barArrayStyle = Seq(
		display.flex,
		flexWrap.nowrap,
		justifyContent.spaceBetween,
		alignItems.flexEnd,
		width.percent := 100,
		height.percent := 100,
		overflow.hidden
	)