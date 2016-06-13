package com.gabbi.mongo.helpers

import com.gabbi.model.status.{OpsFailed, OpsSucceeded}
import com.gabbi.mongo.dsl.commands.{BaseCommand, GetOne, Insert}
import com.gabbi.mongo.servies.ServiceUtils._
import com.google.inject.Inject
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.play.json.JSONSerializationPack._

import scala.concurrent.{ExecutionContext, Future}

// BSON-JSON conversions/collection
import play.modules.reactivemongo.json.collection._
import reactivemongo.play.json._

/**
  * Created by gabbi on 24/04/16.
  */
class DBWorker @Inject()(reactiveMongoApi: ReactiveMongoApi) {
  private def collection(baseCommand: BaseCommand): JSONCollection = reactiveMongoApi.db.collection[JSONCollection](baseCommand.collectionName)

  def insert[T](command: Insert[T])(implicit executionContext: ExecutionContext, writer: Writer[T]): Future[Either[OpsFailed, OpsSucceeded]] = {
    collection(command).insert(command.entity) map (x => convertResult(x)(_ => OpsSucceeded("created")))
  }

  def findOne[T](commad: GetOne[T])(implicit executionContext: ExecutionContext, reader: Reader[T]): Future[Option[T]] = {
    collection(commad).find(commad.indexSelector.toJson).one[T]
  }

}
