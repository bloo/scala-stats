package stats

import com.typesafe.config.{ ConfigFactory, Config }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.async.Async.{ async, await }
import providers._

object Stats {

  private val config = ConfigFactory.load().atPath("stats")

  private val provider: StatsProvider = config getString "provider" match {
    case "stathat" => new StatHatProvider(config)
    case _ => new NoOpProvider
  }

  def count(c: Double)(implicit stat: String) = async { provider count c }
  def value(v: Double)(implicit stat: String) = async { provider value v }
  def increment(implicit stat: String) = count(1.0)
  def decrement(implicit stat: String) = count(-1.0)
}
