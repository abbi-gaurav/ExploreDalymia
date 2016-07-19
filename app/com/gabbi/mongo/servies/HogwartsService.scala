package com.gabbi.mongo.servies

import javax.inject.Singleton

import com.gabbi.model.dao.{Horcrux, Location}
import com.gabbi.model.status.OpsFailed

import scala.concurrent.Future

/**
  * Created by gabbi on 19/07/16.
  */

@Singleton
class HogwartsService {
  def fetchHorcrux(location: Location): Future[Either[OpsFailed, Horcrux]] = Future.successful(Right(Horcrux("diadem")))
}
