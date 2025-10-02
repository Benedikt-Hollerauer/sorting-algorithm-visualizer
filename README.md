# Sorting Algorithm Visualizer

The Sorting Algorithm Visualizer project showcases multiple sorting algorithms exclusively employing higher-order functions, adhering to pure functional programming principles, and is implemented in Scala with compilation via Scala.js. This project is constructed following the tenets of Test-Driven Development (TDD) and Clean Architecture to visually illustrate the algorithms' functioning.

## Subprojects

The project is divided into the following subprojects:

### Core

The `core` subproject contains the core logic of the Sorting Algorithm Visualizer, adhering to the principles of Clean Architecture. It provides the necessary data structures, sorting algorithms, and business rules to facilitate sorting visualizations. The `core` project is designed to be independent of external libraries and relies solely on the Scala programming language.

### Presentation

The `presentation` subproject encompasses the presentation layer of the Sorting Algorithm Visualizer website. It includes the user interface, user interactions, and rendering logic required to visualize sorting algorithms in an interactive manner.

### TestImpl

The `testImpl` subproject consists of the implementation of tests for the core logic. These tests validate the correctness and functionality of the sorting algorithms and associated components defined in the `core` subproject.

## Features

- Interactive visualization of various sorting algorithms (in progress)
- Real-time visualization updates during sorting process (in progress)
- Adjustable speed control for sorting animations (in progress)
- Multiple sorting algorithm options to choose from (in progress)

## Run with Docker

You don't need a local Scala or sbt installation. The Docker image builds the Scala.js bundle and serves the site with Nginx.

### Build the image

```bash
docker build -t sorting-visualizer .
```

### Run the container

```bash
docker run -p 8080:80 sorting-visualizer
```

Then open `http://localhost:8080` in your browser.

### Development tips

- On code changes, rebuild the image to refresh the bundle:
  ```bash
  docker build -t sorting-visualizer . && docker run --rm -p 8080:80 sorting-visualizer
  ```
- The site expects the Scala.js output at `presentation/presentation-fastopt/main.js`, which the Docker build generates via `presentation/fastLinkJS`.