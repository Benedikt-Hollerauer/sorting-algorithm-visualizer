import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.nodes.{ReactiveElement, ReactiveHtmlElement}
import core.input.SortByBubbleSortInput
import core.model.{OrderModel, SortedModel}
import core.useCase.GenerateSortableUseCase
import error.useCaseError.GenerateSortableUseCaseError
import org.scalajs.dom
import useCase.SortByBubbleSortUseCase
import mock.ToBeSortedMock
import mock.inputMock.GenerateSortableInputMock
import org.scalajs.dom.HTMLDivElement

import java.time.Instant

object Main:

    @main
    def main: Unit =
        val sortable = GenerateSortableUseCase(
            GenerateSortableInputMock.success
        ).left.map(_ match
            case GenerateSortableUseCaseError.InputFailure(value) => value
        )
        SortByBubbleSortUseCase(
            SortByBubbleSortInput(
                sortable,
                OrderModel.Ascending
            )
        ) match
            case Left(error) =>
                renderOnDomContentLoaded(
                    dom.document.body,
                    h1(
                        "there was an error: " + error.toString
                    )
                )
            case Right(res) =>
                render(
                    dom.document.body,
                    div(
                        child <-- EventStream.periodic(50).map(x =>
                            if(res.lift(x).isDefined)
                                getBarArray(res(x))
                            else div(
                                "finished: ",
                                getBarArray(res.last)
                            )
                        )
                    )
                )

    def getBarArray(sorted: SortedModel): ReactiveHtmlElement[HTMLDivElement] =
        div(
            display := "flex",
            flexWrap := "wrap",
            alignItems := "flex-end",
            sorted.sortable.list.map(i =>
                div(
                    width := "20px",
                    height := s"${i}px",
                    (if(sorted.changedIndices.contains(i))
                        backgroundColor := "blue"
                    else backgroundColor := "red"),
                    margin := "5px"
                )
            )
        )