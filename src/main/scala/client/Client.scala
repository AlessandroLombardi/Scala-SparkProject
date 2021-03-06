package client

import akka.actor.{ActorRef, ActorSystem, Props}
import client.actors.v1.MasterGraph1
import client.actors.v2.MasterGraph2


/**
 * The client used to create an Akka [[ActorSystem]] and initialize masters in order to download data. Developed to
 * be executed locally. Paths were resulting files are saved are configured in [[client.writer.CSVWriter]].
 */
object Client {

  /**
   * Execute the client.
   * @param args The arguments passed in the command line.
   *             The first one indicates the version of the master to use (1 or 2).
   *             The second the starting block.
   *             The third the number of iterations (the meaning must be searched in the masters scaladoc).
   */
  def main(args: Array[String]): Unit = {
    val system: ActorSystem = ActorSystem("Client")

    val version = args(0).toInt
    val block = args(1).toLong
    val iterations = args(2).toInt

    version match {
      case 1 =>
        val master: ActorRef = system.actorOf(Props(new MasterGraph1(block, iterations)),
          name = "Master")
        master ! client.actors.v1.MasterGraph1.Start(0, 0)
      case 2 =>
        val master: ActorRef = system.actorOf(Props(new MasterGraph2(block, iterations)),
          name = "Master")
        master ! client.actors.v2.MasterGraph2.Start(0)
      case _ =>
        println("Unexisting version")
        system.terminate()
    }

  }
}
