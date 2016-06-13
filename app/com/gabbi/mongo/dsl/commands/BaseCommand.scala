package com.gabbi.mongo.dsl.commands

import com.gabbi.mongo.dsl.query.selectors.{IndexSelector, MultiSelector}
import reactivemongo.play.json.JSONSerializationPack._

/**
  * Created by gabbi on 24/04/16.
  */
sealed trait BaseCommand {
  def collectionName: String
}

case class Insert[T](collectionName: String, entity: T) extends BaseCommand

case class GetMany[T](collectionName: String, selector: MultiSelector)(implicit reader: Reader[T]) extends BaseCommand

case class GetOne[T](collectionName: String, indexSelector: IndexSelector)(implicit reader: Reader[T]) extends BaseCommand