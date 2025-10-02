# ---------- Builder: build Scala.js artifacts ----------
FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

# Install sbt
ARG SBT_VERSION=1.9.9
RUN apt-get update -y \
    && apt-get install -y --no-install-recommends curl gnupg ca-certificates \
    && rm -rf /var/lib/apt/lists/* \
    && curl -sL https://repo.scala-sbt.org/scalasbt/debian/sbt-${SBT_VERSION}.deb -o /tmp/sbt.deb \
    && apt-get update -y \
    && apt-get install -y /tmp/sbt.deb \
    && rm -f /tmp/sbt.deb \
    && sbt --version

# Copy build definition and sources
COPY build.sbt ./
COPY project ./project
COPY core ./core
COPY presentation ./presentation
COPY testImpl ./testImpl

# Pre-fetch dependencies and compile
RUN sbt -batch presentation/fastLinkJS

# ---------- Runtime: serve static site via Nginx ----------
FROM nginx:1.27-alpine AS runtime

WORKDIR /usr/share/nginx/html

# Copy generated JS bundle directory and static site
COPY --from=builder /app/presentation/presentation-fastopt ./presentation/presentation-fastopt
COPY index.html ./index.html
COPY presentation/assets ./assets

# Minimal Nginx config (use default) and expose port
EXPOSE 80

HEALTHCHECK --interval=30s --timeout=3s CMD wget -qO- http://localhost/ >/dev/null 2>&1 || exit 1

# Run nginx
CMD ["nginx", "-g", "daemon off;"]


