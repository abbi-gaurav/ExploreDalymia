package com.gabbi.mongo.dsl.query

import play.api.libs.json.Json.JsValueWrapper
import play.api.libs.json.{JsObject, Json}

/**
  * Created by gabbi on 24/04/16.
  */
case class Comparison(compType: ComparisonType, fieldName: String, fieldValue: JsValueWrapper) {
  def compJson: JsObject = Json.obj(
    fieldName -> Json.obj(compType.operator -> fieldValue)
  )
}
