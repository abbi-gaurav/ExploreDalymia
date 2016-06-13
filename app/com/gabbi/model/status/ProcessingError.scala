package com.gabbi.model.status

import play.api.libs.json.Json

/**
  * Created by gabbi on 08/05/16.
  */
case class ProcessingError(error: String)

object ProcessingError {
  implicit val processingErrorFormat = Json.format[ProcessingError]
}
