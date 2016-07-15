import java.sql.{Connection, Date, DriverManager}

import scala.collection.mutable.Queue

/**
  * Created by Nick on 28/06/2016.
  */
//Superclass for the database tables
trait Entity {

  //Variables required for database connection
  val url = "jdbc:mysql://localhost:3306/nbgardensv0.2?autoReconnect=true&useSSL=false"
  val driver = "com.mysql.jdbc.Driver"
  val username = "root"
  val password = "password"
  var connection: Connection = _

  //Table names, column names and types which are set differently for the different tables
  var dbTableName: String
  var dbTableColumns: Queue[String]
  var dbTableTypes: Queue[String]

  //Parameters required for object creation in tables
  var stringParams = new Queue[String]
  var intParams = new Queue[Int]
  var doubleParams = new Queue[Double]
  var dateParams = new Queue[Date]

  //Implemented in subclasses to populate their collections
  def createEntity(): Unit = {}

  //Gets the data from the database for the required table and passes the data to add to the collections
  def populateEntity(entity: Entity): Unit = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      val rs = statement.executeQuery("Select * from " + entity.dbTableName)
      while (rs.next) {
        //Collect data for each row
        var counter = 0
        while (counter < entity.dbTableColumns.length) {
          if (entity.dbTableTypes(counter).equals("int")) {
            intParams += rs.getInt(dbTableColumns(counter))
          }
          if (entity.dbTableTypes(counter).equals("string")) {
            stringParams += rs.getString(dbTableColumns(counter))
          }
          if (entity.dbTableTypes(counter).equals("double")) {
            doubleParams += rs.getDouble(dbTableColumns(counter))
          }
          if (entity.dbTableTypes(counter).equals("date")) {
            dateParams += rs.getDate(dbTableColumns(counter))
          }
          counter += 1
        }
        entity.stringParams = stringParams
        entity.intParams = intParams
        entity.doubleParams = doubleParams
        entity.dateParams = dateParams

        entity.createEntity

        stringParams.clear
        intParams.clear
        doubleParams.clear
        dateParams.clear
      }
    }
    catch {
      case e: Exception => e.printStackTrace
    }
    connection.close
  }

  def findByID(entityDescID: String, entityDescList: Queue[EntityDescription]): Unit = {
  }
}
