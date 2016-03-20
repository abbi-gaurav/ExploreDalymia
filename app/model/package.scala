import play.api.data.Form

/**
  * Created by gabbi on 06/03/16.
  */

import play.api.data.Forms._

package object model {

  case class User(name: String)

  val userForm: Form[User] = Form(mapping = mapping(
    "name" -> nonEmptyText
  )(User.apply)(User.unapply))
}
