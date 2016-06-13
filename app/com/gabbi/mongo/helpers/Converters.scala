package com.gabbi.mongo.helpers

import org.joda.time.DateTime
import reactivemongo.bson.{BSONDateTime, BSONHandler}

/**
  * Created by gabbi on 17/04/16.
  */
object Converters {

  implicit object BsonDateTimeHandler extends BSONHandler[BSONDateTime, DateTime] {
    override def write(t: DateTime): BSONDateTime = BSONDateTime(t.getMillis)

    override def read(bson: BSONDateTime): DateTime = new DateTime(bson.value)
  }

}
