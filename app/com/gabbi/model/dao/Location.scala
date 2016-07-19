package com.gabbi.model.dao

import play.api.data.Form

/**
  * Created by gabbi on 19/07/16.
  */
case class Location(latitude: Double, longitude: Double)

object Location {

  import play.api.data.Forms._

  private val xtf: ((BigDecimal) => Double, (Double) => BigDecimal) = ((x: BigDecimal) => x.doubleValue(), (d: Double) => BigDecimal.apply(d))

  val locationForm = Form(
    mapping(
      "latitude" -> bigDecimal(precision = 4, scale = 2).transform(xtf._1, xtf._2),
      "longitude" -> bigDecimal(precision = 4, scale = 2).transform(xtf._1, xtf._2)
    )(Location.apply)(Location.unapply)
  )

}
