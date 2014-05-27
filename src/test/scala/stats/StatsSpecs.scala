package stats

import org.specs2.mutable.Specification
import play.api.test.TestServer
import org.specs2.specification.{ Before, After }
import com.typesafe.config.{ ConfigFactory, Config }

class StatsSpecs extends Specification with After with Before {

  val port = findFreePort
  val s = new TestServer(port)

  val ezUrl = s"http://localhost:$port/ez"
  val ezKey = "dummykey"

  val cfg = s""" stats {
	    provider = stathat
        stathat {
	      ezUrl = "$ezUrl"
	      ezKey = $ezKey
        }
	  }
	"""

  def before = {
    Stats.init(ConfigFactory.parseString(cfg).withFallback(ConfigFactory.load()))
    s.start()
  }
  
  def after = s.stop()

  "The 'StatHat' provider" should {

    "construct and configure itself correctly" in {
      implicit val testStat = "test-stat"
      val p = Stats.init()
      val shP = p.asInstanceOf[stats.providers.StatHatProvider]
      shP.ezUrl must_== ezUrl
      shP.ezKey must_== ezKey
      Stats value 900
      success
    }

    "properly send 'count' and 'value' stats" in {
      implicit val testStat = "test-stat"
      Stats value 400
      Stats count 400
      Stats++;
      Stats--;
      Stats += 2
      Stats++;
      success
    }
  }

  def findFreePort = {
    val server = new java.net.ServerSocket(0)
    val port = server getLocalPort ()
    server close ()
    port
  }

}
