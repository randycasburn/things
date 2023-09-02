## Deploying to Render

#### Assumes you have a [Render.com](https://render.com) account

1. Create a PostgreSQL instance per [Render instructions](https://render.com/docs/databases#creating-a-database)
2. Connect to the new PostgreSQL instance per  [Render instructions](https://render.com/docs/databases#connecting-to-your-database)
   1. Modify the `application-docker-stage.properties` file url property:
   2. Modify the `application-render-prod.properties` file url property:
      1. URLs must be in the form: `jdbc:postgresql://[hostname]:5432/[dbName]`
      2. Retrieve the `[hostname]` and `[dbName]` from your Render dashboard
      3. The `Internal Database URL` is used within `render-prod.properties`
      4. The `External Database URL` is used within `docker-stage.properties`
      5. **_DO NOT_** copy and paste the URLs - they are in the wrong format
3. Click the blue **_New+_** button and **_Web Service_**
4. Link your GitHub account to Render and select your clone of this repository from your GitHub Account
5. Once the GitHub project is linked, Enter a name for your web service
6. Select **_Docker_** as the Runtime
7. Change **_Dockerfile Path_** to `./Dockerfile.render.prod`
8. Click the blue button at the bottom
9. The repository will be cloned and checked out and the docker file build and launch.

**NOTE:** the red messages that speed by are not errors. They are progress messages.

This will take a long time - go get some Chick-fil-a and comeback in 5 minutes.

10. Click the link at the top left of the _Web Service_ window (under the project name) 
