package com.gabbi.mongo.dsl.query.sort

import play.api.libs.json.{JsNumber, JsObject}

/**
  * Created by gabbi on 24/04/16.
  */
sealed trait SortType {
  def sortOption: JsNumber
}

case object ASC extends SortType {
  override val sortOption: JsNumber = JsNumber(1)
}

case object DESC extends SortType {
  override val sortOption: JsNumber = JsNumber(-1)
}

case class SortField(fieldName: String, sortType: SortType) {
  def tuple: (String, JsNumber) = (fieldName, sortType.sortOption)
}

sealed trait SortInfo

case object NoSort extends SortInfo

case class SortWithFields(fields: List[SortField]) extends SortInfo {

  def getSortJson: JsObject = {
    fields.foldRight(JsObject(Seq.empty)) {
      case (field: SortField, acc) => acc + field.tuple
    }
  }

}
