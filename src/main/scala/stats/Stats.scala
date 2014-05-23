package stats

import com.typesafe.config.{ ConfigFactory, Config }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.async.Async.{ async, await }
import providers._

object Stats {
  
  private val config = ConfigFactory.load().getConfig("stats")

  lazy val provider: StatsProvider = config getString "provider" match {
    case "stathat" => new StatHatProvider(config)
    case _ => new NoOpProvider
  }

  def count[C](c: C)(implicit stat: String, num: Numeric[C]) = async { provider count c }
  def value[V](v: V)(implicit stat: String, num: Numeric[V]) = async { provider value v }
//  def increment(implicit stat: String) = count(1.0)
//  def decrement(implicit stat: String) = count(-1.0)
//  def ++()(implicit stat: String) = increment
//  def --()(implicit stat: String) = decrement
//  def +=[V](v: V)(implicit stat: String, num: Numeric[V]) = value(v)
//  def -=[V](v: V)(implicit stat: String, num: Numeric[V]) = value(v*-1)
}
