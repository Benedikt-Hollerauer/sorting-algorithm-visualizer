import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.nodes.ReactiveElement
import core.input.SortByBubbleSortInput
import core.model.{OrderModel, SortableModel}
import core.useCase.GenerateSortableUseCase
import error.useCaseError.GenerateSortableUseCaseError
import org.scalajs.dom
import useCase.SortByBubbleSortUseCase
import mock.ToBeSortedMock
import mock.inputMock.GenerateSortableInputMock

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
                res.map(item =>
                    () =>render(
                        dom.document.body,
                        div(item.list.map(_.toString).reduce((x, y) => x + " " + y), whiteSpace := "nowrap")
                    )
                ).foldLeft(0)((delay, f) =>
                    dom.window.setTimeout(() => f(), delay)
                    delay + 100
                )