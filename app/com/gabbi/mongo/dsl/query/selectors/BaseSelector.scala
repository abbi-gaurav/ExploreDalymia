package com.gabbi.mongo.dsl.query.selectors

import play.api.libs.json.Json
import play.api.libs.json.Json.JsValueWrapper

/**
  * Created by gabbi on 24/04/16.
  */
case class SelectorField(label: String, value: JsValueWrapper)

sealed trait BaseSelector {
  def selectorFields: List[SelectorField]

  def toJson = {
    Json.obj(selectorFields map (x => (x.label, x.value)): _*)
  }
}

case class MultiSelector(selectorFields: List[SelectorField]) extends BaseSelector

case class IndexSelector(selectorFields: List[SelectorField]) extends BaseSelector
