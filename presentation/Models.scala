case class VisualModel(
	src: String,
	alt: String,
	link: Option[String] = None
)

enum SortingAlgorithm:
	
	case BubbleSort