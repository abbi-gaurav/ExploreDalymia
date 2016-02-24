package servies.read

import com.google.inject.Inject
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json.collection.JSONCollection

/**
  * Created by gabbi on 22/02/16.
  */
class UserReadService @Inject()(reactiveMongoApi: ReactiveMongoApi) {
  private val collection: JSONCollection = reactiveMongoApi.db.collection[JSONCollection]("users")

  val name = collection.name
}
