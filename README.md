![Build & Unit Test](https://github.com/randycasburn/things/actions/workflows/maven.yml/badge.svg)
[![CodeQL](https://github.com/randycasburn/things/actions/workflows/codeql.yml/badge.svg?branch=main)](https://github.com/randycasburn/things/actions/workflows/codeql.yml)
## things project

### A PostgreSQL + MyBatis + Docker project

See [STAGING](./STAGING.MD) for building Docker images for dev, stage, production.

See [DEPLOYMENT](./DEPLOYMENT.MD) for instructions on deploying the production build to [Render.com](https://render.com)

See [Coverage Report](https://randycasburn.com/things/)  -100% Code Coverage
#### To change DB backends: 
- change the *.properties files where necessary.
- add the Maven dependency for your DB

