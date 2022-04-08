# Miniature static page generator written in Java

People on the Internet were smack talking and said this couldn't be done. Oh it's on now, I thought. So here is a Static Site Generator, written in 50 lines of Java
in an hour.

## Build Instructions
Have jdk17 installed somewhere

```
$ ./gradlew clean ass shadowJar
```

## Run Instructions

It takes the templates in argv[0], applies the settings in argv[2], and writes the output in argv[1]. 
Does partials and fragments and all that sort of stuff too because ya boy used a competent library. 

```
$ java -jar build/libs/MiniSSG-1.0-SNAPSHOT-all.jar example/template/ example/output/ example/params.yaml
```

