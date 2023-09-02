## How to stage builds for this project:

Docker is used in conjunction with Maven to construct different builds based upon Maven profiles.

Each docker file sets a different `spring.profiles.active` environment variable that Spring uses to establish the active profile.

==========================

To build docker images for dev, staging, production, use the following docker CLI commands:

- `docker build -t thing -f Dockerfile.local.dev`
- `docker build -t thing -f Dockerfile.local.stage`
- `docker build -t thing -f Dockerfile.render.prod`

Once built, to run on port 80, execute: (choose any port you want)

`docker run -p 80:8080 thing`

Maven Profiles: (default 'dev')
- **'dev'** - local development, local DB connection
    - Local development outside of Docker
- **'docker-dev'** - local docker dev image, local DB connection
    - Local development image with DB outside container
    - Corresponding Dockerfile: `Dockerfile.local.dev`
- **'docker-stage'** - local docker stage, _remote_ DB
    - Local devleopment image with DB located elsewhere (Render?)
    - Corresponding Dockerfile: `Dockerfile.local.stage`
- **'render-prod'** - remote production build, _remote_ DB
    - Meant for deployment onto remote service (Render?)
    - Will not work for local deployment
    - Corresponding Dockerfile: `Dockerfile.render.prod`

