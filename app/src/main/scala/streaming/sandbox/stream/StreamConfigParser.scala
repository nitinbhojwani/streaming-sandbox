package streaming.sandbox.stream

import java.lang.reflect.{Constructor}
import java.util.NoSuchElementException

import org.rogach.scallop.{ScallopConf, ScallopOption, stringConverter}
import streaming.sandbox.readers.BaseReader
import streaming.sandbox.transformers.BaseTransformer
import streaming.sandbox.writers.BaseWriter

class StreamConfigParser(arguments: Seq[String]) extends ScallopConf(arguments, null) with Serializable {
  val READERS_PACKAGE_PATH = "streaming.sandbox.readers";
  val WRITERS_PACKAGE_PATH = "streaming.sandbox.writers";
  val TRANSFORMERS_PACKAGE_PATH = "streaming.sandbox.transformers";
  val KEY_VALUE_SEPARATOR = "="
  val ARGS_SEPARATOR = ","

  val readerClassName = opt[String]("reader", required = true)
  val writerClassName = opt[String]("writer", required = true)
  val checkpointLocation = opt[String]("checkpointLocation", required = true)
  val transformerClassName = opt[String]("transformer", required = true)
  val readerClassInitArgs = opt[String]("readerArgs", required = false)
  val writerClassInitArgs = opt[String]("writerArgs", required = false)
  verify()

  def parse(): StreamConfig = {
    val reader = {
      val readerArgsMap = getMapFromOptionalString(readerClassInitArgs)
      val readerClass = Class.forName(s"$READERS_PACKAGE_PATH.${readerClassName()}")
      try {
        createInstance(readerClass, readerArgsMap).asInstanceOf[BaseReader]
      } catch {
        case e: NoSuchElementException =>
          println(s"Provide all the arguments for reader class - ${getParametersStringToConstructClazz(readerClass)}")
          throw e
      }
    }

    val writer = {
      val writerArgsMap = getMapFromOptionalString(writerClassInitArgs)
      val writerClass = Class.forName(s"$WRITERS_PACKAGE_PATH.${writerClassName()}")
      try {
        createInstance(writerClass, writerArgsMap).asInstanceOf[BaseWriter]
      } catch {
        case e: NoSuchElementException =>
          println(s"Provide all the arguments for writer class - ${getParametersStringToConstructClazz(writerClass)}")
          throw e
      }
    }

    val transformer = {
      val transformerClass = Class.forName(s"$TRANSFORMERS_PACKAGE_PATH.${transformerClassName()}")
      transformerClass.getConstructors()(0).newInstance().asInstanceOf[BaseTransformer]
    }

    StreamConfig(reader = reader, transformer = transformer, writer = writer, checkpointLocation=checkpointLocation())
  }

  def getMapFromOptionalString(optionalStringArg: ScallopOption[String]): Map[String, String] =
    try {
      parseToKeyValue(optionalStringArg())
    } catch {
      case _ : NoSuchElementException => Map.empty[String, String]
    }

  def parseToKeyValue(arg: String, argsSep: String = ARGS_SEPARATOR, keyValueSep: String = KEY_VALUE_SEPARATOR): Map[String, String] = {
    arg.split(argsSep).map(_.trim().split(keyValueSep).map(_.trim())).map { case Array(x, y) => (x, y) }.toMap
  }

  def createInstance[T](clazz: Class[T], argsMap: Map[String, String]): T = {
    val mainParameterObject = getMainParameterObject(clazz, argsMap)
    val constructorForClazz = getPrimaryConstructor(clazz)
    constructorForClazz.newInstance(mainParameterObject)
  }

  def getMainParameterObject[T](clazz: Class[T], argsMap: Map[String, String]) = {
    val parameterConstructor = getMainParameterConstructor(clazz)
    val parameters = getParametersRequiredToConstructClazz(clazz)
      .map ( parameter => argsMap.get(parameter.getName()).get )
    parameterConstructor.newInstance(parameters:_*)
  }

  def getPrimaryConstructor[T](clazz: Class[T]): Constructor[T] = clazz.getConstructors()(0).asInstanceOf[Constructor[T]]

  def getParametersRequiredToConstructClazz[T](clazz: Class[T]) =
    getMainParameterConstructor(clazz).getParameters()

  def getParametersStringToConstructClazz[T](clazz: Class[T]): String =
    getParametersRequiredToConstructClazz(clazz).map(_.toString()).mkString(", ")

  def getMainParameterConstructor[T](clazz: Class[T]) =
    getPrimaryConstructor(getPrimaryConstructor(clazz).getParameters()(0).getType())

}
