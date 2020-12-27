using Microsoft.EntityFrameworkCore;
using MySql.Data.MySqlClient;
using SysDisClient.Models.DTO;

namespace SysDisClient.Controllers
{
    public class Context : DbContext
    {
        public static MySqlConnection GetDBConnection()
        {
            string host = "localhost";
            int port = 3306;
            string database = "userasp.net";
            string username = "root";
            string password = "root";
 
            return DbManager.GetDBConnection(host, port, database, username, password);
        }
    }
}