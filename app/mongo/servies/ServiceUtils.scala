package mongo.servies

import play.api.libs.json.{JsObject, Json}
import reactivemongo.api.commands.WriteResult

/**
  * Created by gabbi on 18/04/16.
  */
object ServiceUtils {
  def convert[T](writeResult: WriteResult)(change: WriteResult => T): Either[JsObject, T] = {
    if (writeResult.hasErrors) {
      Left(Json.obj("error" -> writeResult.errmsg))
    } else Right(change(writeResult))
  }
}
