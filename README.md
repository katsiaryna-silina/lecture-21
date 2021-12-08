# Home task #21
1. git repo and best practices
2. base on #20
3. add DAO and hibernate tiers with spring integration
4. all configurations in Java code (don't use xml)
5. deploy to servlet container per student 


###How to deploy:

1.
Build the project: 
```sh
mvn clean install
```
2.
then:
- Copy war file into Tomcat root directory (apache-tomcat-X.x.xx/webapps)
- Run Tomcat bat file bin/startup.bat

or:

- Use Tomcat Manager from browser
- Click on browse and chose war file to deploy it.