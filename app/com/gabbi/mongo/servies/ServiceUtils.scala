package com.gabbi.mongo.servies

import com.gabbi.model.status.OpsFailed
import reactivemongo.api.commands.WriteResult
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by gabbi on 18/04/16.
  */
object ServiceUtils {
  def convertResult[T](writeResult: WriteResult)(change: WriteResult => T): Either[OpsFailed, T] = {
    if (writeResult.hasErrors) {
      Left(OpsFailed.create(writeResult.errmsg.getOrElse("unknow error")))
    } else Right(change(writeResult))
  }

  def dbOps[T](f: JSONCollection => Future[T])(implicit futureCollection: Future[JSONCollection], executionContext: ExecutionContext): Future[T] = for {
    collection <- futureCollection
    res <- f(collection)
  } yield res
}
