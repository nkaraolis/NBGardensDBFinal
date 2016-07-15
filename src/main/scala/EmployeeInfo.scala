
/**
  * Created by Administrator on 29/06/2016.
  */

case class EmployeeInfo(employeeID: String, password: String, name: String, role :String){}

object EmployeeInfo {

  val employee1 = new EmployeeInfo("e01", "daniel", "Daniel", "warehouse")
  val employee2 = new EmployeeInfo("e02", "yang", "Yang", "warehouse")
  val employee3 = new EmployeeInfo("e03", "tevyn", "Tevyn", "accounts")
  val employee4 = new EmployeeInfo("e04", "nick", "Nick", "accounts")
  val employee5 = new EmployeeInfo("e05", "yuan", "Yuan", "warehouse")

  val employeeList = Array(employee1, employee2, employee3, employee4, employee5)

  def getDetails (employID :String) = employeeList.find(_.employeeID == employID)
}

