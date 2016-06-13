package com.gabbi.model.status

import play.api.libs.json.Json

/**
  * Created by gabbi on 23/04/16.
  */
sealed trait OpsResult

case class OpsFailed(errors: List[ProcessingError] = Nil) extends OpsResult {
  def +(processingError: ProcessingError): OpsFailed = OpsFailed(processingError :: errors)

  def +(string: String): OpsFailed = this.+(ProcessingError(string))
}

object OpsFailed {
  def create(string: String) = OpsFailed(List(ProcessingError(string)))

  implicit val opsFailedFormat = Json.format[OpsFailed]
}

case class OpsSucceeded(message: String) extends OpsResult
