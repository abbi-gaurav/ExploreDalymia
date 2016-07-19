package com.gabbi.controllers

import com.gabbi.model.status.OpsFailed
import play.api.libs.json.Json._
import play.api.libs.json.Writes
import play.api.mvc.Result
import play.api.mvc.Results._

/**
  * Created by gabbi on 19/07/16.
  */

object RestUtils {
  def toResult[T](either: Either[OpsFailed, T])(onSuccess: T => Result) = either match {
    case Left(opsFailed) => InternalServerError(toJson(opsFailed))
    case Right(t) => onSuccess(t)
  }

  def toJsonResult[T](either: Either[OpsFailed, T])(implicit writes: Writes[T]) = toResult(either)(x => Ok(toJson(x)))
}
