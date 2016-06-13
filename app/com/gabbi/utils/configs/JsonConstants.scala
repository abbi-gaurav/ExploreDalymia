package com.gabbi.utils.configs

import play.api.libs.json.{JsObject, Json}

/**
  * Created by gabbi on 23/04/16.
  */
object JsonConstants {
  val emptySelector: JsObject = Json.obj()

  val dupEmail: JsObject = Json.obj("error" -> "emailTaken")

  val successCreation = Json.obj("success" -> "created")
}
