package stats

import org.specs2.mutable.Specification
import play.api.test.TestServer

class StatsSpecs extends Specification {

  "The 'StatHat' provder" should {
    
    val port = findFreePort
    val s = new TestServer(port)
    System.setProperty("stats.provider", "stathat")
    System.setProperty("stats.stathat.ezUrl", s"http://localhost:$port/ez")
    
    "construct and configure itself correctly" in {
      val p = Stats.provider
      success
    }
    
    "properly send 'count' and 'value' stats" in {
      implicit val testStat = "test-stat"
      Stats value 400
      Stats count 400
//      Stats ++
      success
    }
  }
  
  def findFreePort = {
    val server = new java.net.ServerSocket(0)
    val port = server.getLocalPort()
    server.close()
    port
  }
  
}
