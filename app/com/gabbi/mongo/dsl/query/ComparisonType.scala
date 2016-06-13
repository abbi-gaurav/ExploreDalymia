package com.gabbi.mongo.dsl.query

/**
  * Created by gabbi on 24/04/16.
  */
sealed trait ComparisonType {
  def operator: String
}

case object LT extends ComparisonType {
  override val operator = "$lt"
}

case object LTE extends ComparisonType {
  override val operator = "$lte"
}

case object GT extends ComparisonType {
  override val operator = "$gt"
}

case object GTE extends ComparisonType {
  override val operator = "$gte"
}
