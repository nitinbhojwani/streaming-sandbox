/*
 * This Scala source file was generated by the Gradle 'init' task.
 */
package streaming.sandbox

import streaming.sandbox.commons.SparkSessionProvider
import streaming.sandbox.stream.{GenericStream, StreamConfigParser}


object App {
  def main(args: Array[String]): Unit = {
    val streamConfig = new StreamConfigParser(args).parse()
    val sparkSession = SparkSessionProvider.getSparkSession()
    val stream = new GenericStream(sparkSession, streamConfig)
    stream.stream()
  }

  def greeting(): String = "Hello, world!"
}
