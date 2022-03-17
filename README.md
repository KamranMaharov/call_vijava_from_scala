# call_vijava_from_scala


to run with java command, vijava jar should be provided during compile time.
and vijave and dom4j jars should be provided during runtime

javac -cp out:lib/compile/vijava55b20130927.jar:lib/compile/dom4j-1.6.1.jar  com.kamran.scala.Runner
java -cp out:lib/compile/vijava55b20130927.jar:lib/compile/dom4j-1.6.1.jar:lib/compile/scala-library-2.13.8.jar  com.kamran.scala.Runner


to build jar file, run following:

jar cvfm ScalaApp.jar scala-manifest.txt -C out com
java -jar ScalaApp.jar

above approach taken from:
https://benmosheron.gitlab.io/blog/2019/04/16/scala-for-java.html
