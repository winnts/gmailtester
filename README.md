# gmailtester
This project is made as Test Exercise.

Configuration:
- can be configured by Maven Profiles.
- has a predefined profile 'test'

to add your own gmail credentials edit src/test/resources/env/config.test.properties:

```
gmail.login=email

gmail.password=password

gmail.subject=Hello Friend!

gmail.messageText=Test message
```


To run the project use:
```
mvn clean -Ptest package
```

Allure report will be available in **/target/allure-results**. Report generation can be done by:
```
cd target
allure serve allure-results
```

Tested with Chrome Browser v 78.0
