package com.gabbi.model.dao

import play.api.libs.json.Json

/**
  * Created by gabbi on 19/07/16.
  */
case class Horcrux(name: String)

object Horcrux {
  implicit val horcruxFormat = Json.format[Horcrux]
}
