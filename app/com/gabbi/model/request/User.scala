package com.gabbi.model.request

import play.api.data.Form
import play.api.data.Forms._

/**
  * Created by gabbi on 17/04/16.
  */
case class User(email: String)

object User {
  val userForm: Form[User] = Form(mapping = mapping(
    "email" -> email
  )(User.apply)(User.unapply))
}
