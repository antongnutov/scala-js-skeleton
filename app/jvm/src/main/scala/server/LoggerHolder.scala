package server

import org.slf4j.{Logger, LoggerFactory}

/**
  * @author Anton Gnutov
  */
trait LoggerHolder {
  protected final val loggerName: String = getClass.getName

  protected def logger: Logger
}

/**
  * Defines `logger` as a lazy value
  */
trait LazyLogging extends LoggerHolder {
  protected lazy val logger: Logger = LoggerFactory.getLogger(loggerName)
}

/**
  * Defines `logger` as a value
  */
trait StrictLogging extends LoggerHolder {
  protected val logger: Logger = LoggerFactory.getLogger(loggerName)
}