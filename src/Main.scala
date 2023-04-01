import com.raquo.laminar.api.L.{*, given}
import core.model.{OrderModel, SortableModel}
import core.input.SortByBubbleSortInput
import org.scalajs.dom
import useCase.SortByBubbleSortUseCase

object Main:

    def main(args: Array[String]): Unit =
        SortByBubbleSortUseCase(
            SortByBubbleSortInput(
                SortableModel.from(mock.ToBeSortedMock.ascendingOrder.unsorted),
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
                renderOnDomContentLoaded(
                    dom.document.body,
                    div(
                        res.map(x => div(x.toString))
                    )
                )
