using System;
using System.Net.Http;
using Microsoft.AspNetCore.Mvc;
using SysDisClient.Models;
using System.Collections.Generic;
using System.Data;
using System.Data.Common;
using System.Linq;
using System.Net.NetworkInformation;
using System.Text;
using System.Text.Json.Serialization;
using Microsoft.AspNetCore.Routing;
using Microsoft.CodeAnalysis;
using MySql.Data.MySqlClient;
using Newtonsoft.Json;
using SysDisClient.Models.DTO;
using JsonSerializer = System.Text.Json.JsonSerializer;


namespace SysDisClient.Controllers
{
    public class StockController : Controller
    {
        private HttpClient _httpClient;
        private static int _currentid=0;
        private Context ctx;
        private ReponseMenu menu = new ReponseMenu();

        public StockController()
        {
            _httpClient = new HttpClient();


        }

      
        // GET 

        [HttpGet]
        public IActionResult Index()
        {
            // Obtenez de l'objet Connection qui se connecte à DB.
            string sql = "Select user_id from users where enabled=0 ";
          
            
            _currentid=getCurrentIdOrVisiteur(sql);
            
            
            if (_currentid == 0)
            {
                
                int nbre=CountUser();
                nbre = nbre + 1;
                string sqlinsert = "Insert into users (user_id, username,password,role,enabled) "
                                   + " values (@id, @username,@password,@role,@enabled)";
                AddUser(sqlinsert, nbre);
            }
            else
            {
                int vistorornot = 0;
                string sqls = "Select user_id from users where user_id=@id AND username=@name ";
                MySqlCommand cmd = new MySqlCommand();
                cmd.CommandText = sqls;
                cmd.Parameters.AddWithValue("@id", _currentid);
                cmd.Parameters.AddWithValue("@name", "visiteur");
                vistorornot=LookIfNotAVisitor(cmd);

                if (vistorornot == 0)
                {
                    menu.Connected = true;
                }
                else
                {
                    menu.Connected = false;
                }
            }
            var path = "http://localhost:8090/server/stock/all";
            HttpResponseMessage responseMessage = _httpClient.GetAsync(path).Result;
            String response = responseMessage.Content.ReadAsStringAsync().Result;
            StockReponse stock = JsonSerializer.Deserialize<StockReponse>(response);
            
           
            
            if (stock.Liste != null)
            {
                ViewData["StockReponse"] = stock;
                ViewData["ReponseMenu"] = menu;
                return View();
            }
            

            return View("Error");
        }

        [HttpPost]
        public RedirectToActionResult Submit()
        {
            int vistorornot = 0;
            string sqls = "Select user_id from users where user_id=@id AND username=@name ";
            MySqlCommand cmd = new MySqlCommand();
            cmd.CommandText = sqls;
            cmd.Parameters.AddWithValue("@id", _currentid);
            cmd.Parameters.AddWithValue("@name", "visiteur");
            vistorornot=LookIfNotAVisitor(cmd);

            if (vistorornot == 0)
            {
                menu.Connected = true;
            }
            else
            {
                menu.Connected = false;
            }
            Console.WriteLine("Controller stock");
     
            int quantite = Int32.Parse(HttpContext.Request.Form["quantite"]);
            int iditem =  Int32.Parse(HttpContext.Request.Form["iditem"]);
            //int iduser = Int32.Parse(HttpContext.Request.Form["iduser"]);
            int idcart = 0;
            
            
            // recup panier current
            String currentpanier = "http://localhost:8090/server/user/panier/"+_currentid;

            HttpResponseMessage responseMessagere = _httpClient.GetAsync(currentpanier).Result;
            String response = responseMessagere.Content.ReadAsStringAsync().Result;
             idcart = JsonSerializer.Deserialize<int>(response);
            Console.WriteLine("ICI ta mere "+idcart);
            if (idcart == 0)
            {
                var getpanier = "http://localhost:8090/server/create/cart/"+_currentid;
                HttpResponseMessage responseMessage = _httpClient.GetAsync(getpanier).Result;
                String responsegetpanier = responseMessage.Content.ReadAsStringAsync().Result;
                idcart = JsonSerializer.Deserialize<int>(responsegetpanier);
            }
           
            // suprimer
            var updatepath = "http://localhost:8090/server/update/stockitemsminus/"+iditem+"/"+quantite;
            HttpResponseMessage responseupdatepath = _httpClient.GetAsync(updatepath).Result;
            String reponseupdate = responseupdatepath.Content.ReadAsStringAsync().Result;
            

            var path = "http://localhost:8090/server/cart/add/"+idcart+"/"+_currentid+"/"+iditem+"/"+quantite;
           
            HttpResponseMessage responseMessagereSend = _httpClient.GetAsync(path).Result;
            String responseRes = responseMessagereSend.Content.ReadAsStringAsync().Result;
            
            
            if(responseMessagereSend.IsSuccessStatusCode)
                return RedirectToAction("CommandeUSer","Cart", new RouteValueDictionary(
                    new { controller = "Cart", action= "CommandeUSer"}));



            return RedirectToAction();
        }
        
        
         [HttpPost]
        public RedirectToActionResult Recommander()
        {
            int vistorornot = 0;
            string sqls = "Select user_id from users where user_id=@id AND username=@name ";
            MySqlCommand cmd = new MySqlCommand();
            cmd.CommandText = sqls;
            cmd.Parameters.AddWithValue("@id", _currentid);
            cmd.Parameters.AddWithValue("@name", "visiteur");
            vistorornot=LookIfNotAVisitor(cmd);

            if (vistorornot == 0)
            {
                menu.Connected = true;
            }
            else
            {
                menu.Connected = false;
            }
            Console.WriteLine("Controller stock");
     
            //int quantite = Int32.Parse(HttpContext.Request.Form["quantite"]);
           string libellerestock= HttpContext.Request.Form["libelleitem"];
            //int iduser = Int32.Parse(HttpContext.Request.Form["iduser"]);
            int idcart = 0;

            var path = "http://localhost:8090/server/restock/"+libellerestock;
           
            HttpResponseMessage responseMessagereSend = _httpClient.GetAsync(path).Result;
            String responseRes = responseMessagereSend.Content.ReadAsStringAsync().Result;
            
            
            if(responseMessagereSend.IsSuccessStatusCode)
                return RedirectToAction("Index", new RouteValueDictionary(
                    new { controller = "Stock", action= "Index"}));



            return RedirectToAction();
        }
        
        
        
        [HttpGet]
       
        public int CurrentPanier(int? id)
        {
            String path = "http://localhost:8090/server/user/panier/"+id;

            HttpResponseMessage responseMessage = _httpClient.GetAsync(path).Result;
            String response = responseMessage.Content.ReadAsStringAsync().Result;
            int idcart = JsonSerializer.Deserialize<int>(response);
            return idcart;
        }
        
        [HttpGet]
        [Route("/login")]
        public ViewResult Login()
        {
            StockReponse stock = new StockReponse();
            ViewData["StockReponse"] = stock;
            ViewData["ReponseMenu"] = menu;
            return View();
        }
        public ActionResult LogOut()
        {
            menu.Connected = false;
            int vistorornot = 0;
            string sqls = "Select user_id from users where username=@name ";
            MySqlCommand cmd = new MySqlCommand();
            cmd.CommandText = sqls;
            cmd.Parameters.AddWithValue("@name", "visiteur");
            vistorornot=LookIfNotAVisitor(cmd);
            
            string sqlA = "Update users set enabled = @enabled where user_id = @id";
            string sqlN = "Update users set enabled = @enabled where user_id = @id";
            
            Update(sqlA,_currentid,1);
            
            Update(sqlA,vistorornot,0);
            
            _currentid = vistorornot;
            return RedirectToAction("Index", "Stock",new RouteValueDictionary(
                new {controller = "Stock", action = "Index"}));
        }

        [HttpPost]
        public ActionResult MustLog()
        {
            /*
            int newid = 0;
            string sql= "SELECT user_id from users WHERE username = @name AND password=@password";
            newid=ExistInDbUser(sql, HttpContext.Request.Form["name"], HttpContext.Request.Form["pwd"]);

            if (newid == 0)
            {
                Console.WriteLine("Pas le bon user");
                return View("Error");
            }

            int cart=CurrentPanier(_currentid);
            String path = "http://localhost:8090/server/user/panier/"+cart+"/"+newid;

            HttpResponseMessage responseMessage = _httpClient.GetAsync(path).Result;
            String response = responseMessage.Content.ReadAsStringAsync().Result;
            bool change = JsonSerializer.Deserialize<bool>(response);
            string sqlA = "Update users set enabled = @enabled where user_id = @id";
            string sqlN = "Update users set enabled = @enabled where user_id = @id";
            
            Update(sqlA,_currentid,1);
            
            Update(sqlA,newid,0);
            
            if (change == true)
            {
                _currentid=newid;
                menu.Connected = true;
                return RedirectToAction("CheckOutView", "Cart",new RouteValueDictionary(
                    new {controller = "Cart", action = "CheckOutView"}));
            }
         */
            int vistorornot = 0;
            string sqls = "Select user_id from users where user_id=@id AND username=@name ";
            MySqlCommand cmd = new MySqlCommand();
            cmd.CommandText = sqls;
            cmd.Parameters.AddWithValue("@id", _currentid);
            cmd.Parameters.AddWithValue("@name", "visiteur");
            vistorornot=LookIfNotAVisitor(cmd);

            if (vistorornot != _currentid)
            {
                menu.Connected = true;
            }
            else
            {
                menu.Connected = false;
            }
            
            int newid = 0;
            string sql= "SELECT user_id from users WHERE username = @name AND password=@password";
            newid=ExistInDbUser(sql, HttpContext.Request.Form["name"], HttpContext.Request.Form["pwd"]);

            if (newid == 0)
            {
                Console.WriteLine("Pas le bon user");
                return View("Error");
            }

            int cart=CurrentPanier(_currentid);
            
            String cartnbre = "http://localhost:8090/server/panier/" + _currentid;
            HttpResponseMessage message = _httpClient.GetAsync(cartnbre).Result;
            String r = message.Content.ReadAsStringAsync().Result;
            CartReponse cartR = JsonSerializer.Deserialize<CartReponse>(r);
            
            // si vistor devrait recup son panier pour le transferer sun son compte
            if (vistorornot != 0 && cartR.Liste.Count!=0)
            {
            

            String path = "http://localhost:8090/server/user/panier/"+cart+"/"+newid;

                HttpResponseMessage responseMessage = _httpClient.GetAsync(path).Result;
                String response = responseMessage.Content.ReadAsStringAsync().Result;
                bool change = JsonSerializer.Deserialize<bool>(response);
               
            }
            else
            {
               
            }
            
            
            string sqlA = "Update users set enabled = @enabled where user_id = @id";
            string sqlN = "Update users set enabled = @enabled where user_id = @id";
            
            Update(sqlA,_currentid,1);
            
            Update(sqlA,newid,0);

            _currentid=newid;
            return RedirectToAction("CheckOutView", "Cart",new RouteValueDictionary(
                new {controller = "Cart", action = "CheckOutView"}));
        }
        

        [HttpPost]
        public ActionResult Log()
        {
            int vistorornot = 0;
            string sqls = "Select user_id from users where user_id=@id AND username=@name ";
            MySqlCommand cmd = new MySqlCommand();
            cmd.CommandText = sqls;
            cmd.Parameters.AddWithValue("@id", _currentid);
            cmd.Parameters.AddWithValue("@name", "visiteur");
            vistorornot=LookIfNotAVisitor(cmd);

            if (vistorornot ==0)
            {
                menu.Connected = true;
            }
            else
            {
                menu.Connected = false;
            }
            
            int newid = 0;
            string sql= "SELECT user_id from users WHERE username = @name AND password=@password";
            newid=ExistInDbUser(sql, HttpContext.Request.Form["name"], HttpContext.Request.Form["pwd"]);

            if (newid == 0)
            {
                Console.WriteLine("Pas le bon user");
                return View("Error");
            }

            int cart=CurrentPanier(_currentid);
            
            String cartnbre = "http://localhost:8090/server/panier/" + cart;
            HttpResponseMessage message = _httpClient.GetAsync(cartnbre).Result;
            String r = message.Content.ReadAsStringAsync().Result;
            CartReponse cartR = JsonSerializer.Deserialize<CartReponse>(r);
            
            // si vistor devrait recup son panier pour le transferer sun son compte
            if (vistorornot != 0 && cartR.Liste.Count!=0)
            {
              
                String path = "http://localhost:8090/server/user/panier/"+cart+"/"+newid;

                HttpResponseMessage responseMessage = _httpClient.GetAsync(path).Result;
                String response = responseMessage.Content.ReadAsStringAsync().Result;
                bool change = JsonSerializer.Deserialize<bool>(response);
               
            }
            else
            {
               
            }
            
            
            string sqlA = "Update users set enabled = @enabled where user_id = @id";
            string sqlN = "Update users set enabled = @enabled where user_id = @id";
            
            Update(sqlA,_currentid,1);
            
            Update(sqlA,newid,0);

            _currentid=newid;
            
            return RedirectToAction("Index", "Stock",new RouteValueDictionary(
                    new {controller = "Stock", action = "Index"}));
        }
        
        
        public ViewResult LoginRappel()
        {
            StockReponse stock = new StockReponse();
            ViewData["StockReponse"] = stock;
            ViewData["ReponseMenu"] = menu;
            return View();
        }
        
        
        public int ExistInDbUser(string sql,string name, string password)
        {
            MySqlConnection conn = Context.GetDBConnection();
            conn.Open();
            MySqlCommand cmd = new MySqlCommand();
            cmd.Connection = conn;
            cmd.CommandText = sql;
            cmd.Parameters.AddWithValue("@name", name);
            cmd.Parameters.AddWithValue("@password", password);
            int empId = 0;
            using (DbDataReader reader = cmd.ExecuteReader())
            {
                if (reader.HasRows)
                {
                     
                    while (reader.Read())
                    {
                        // Récupérez l'indexe (index) de colonne id dans l'instruction de requête SQL.
                        int empIdIndex = reader.GetOrdinal("user_id"); // 0
                        empId =  Convert.ToInt32(reader.GetValue(0));


                    }
                }
            }
            // Terminez la connexion.
            conn.Close();
            // Disposez un objet, libérez des ressources.
            conn.Dispose();
            return empId;
        }
        public void Update(string sql,int id, int current)
        {
            MySqlConnection conn = Context.GetDBConnection();
            conn.Open();
            MySqlCommand cmd = new MySqlCommand();
            cmd.Connection = conn;
            cmd.CommandText = sql;
            cmd.Parameters.AddWithValue("@enabled", current);
            cmd.Parameters.AddWithValue("@id", id);
            int rowCount = cmd.ExecuteNonQuery();
           
            // Terminez la connexion.
            conn.Close();
            // Disposez un objet, libérez des ressources.
            conn.Dispose();
            conn = null;
        }
    
      public int getCurrentIdOrVisiteur(string sql)
        {
            MySqlConnection conn = Context.GetDBConnection();
            conn.Open();
            MySqlCommand cmd = new MySqlCommand();
            cmd.Connection = conn;
            cmd.CommandText = sql;
            int empId = 0;
             using (DbDataReader reader = cmd.ExecuteReader())
            {
                if (reader.HasRows)
                {
                     
                    while (reader.Read())
                    {
                        // Récupérez l'indexe (index) de colonne id dans l'instruction de requête SQL.
                        int empIdIndex = reader.GetOrdinal("user_id"); // 0
                        empId =  Convert.ToInt32(reader.GetValue(0));


                    }
                }
            }
            // Terminez la connexion.
            conn.Close();
            // Disposez un objet, libérez des ressources.
            conn.Dispose();
            return empId;
        }
      
      public int LookIfNotAVisitor(MySqlCommand cmd)
      {
          MySqlConnection conn = Context.GetDBConnection();
          conn.Open();
          cmd.Connection = conn;
          int empId = 0;
          using (DbDataReader reader = cmd.ExecuteReader())
          {
              if (reader.HasRows)
              {
                     
                  while (reader.Read())
                  {
                      // Récupérez l'indexe (index) de colonne id dans l'instruction de requête SQL.
                      int empIdIndex = reader.GetOrdinal("user_id"); // 0
                      empId =  Convert.ToInt32(reader.GetValue(0));


                  }
              }
          }
          // Terminez la connexion.
          conn.Close();
          // Disposez un objet, libérez des ressources.
          conn.Dispose();
          return empId;
      }
        
        
        public int AddUser(string sql,int id)
        {
            MySqlConnection conn = Context.GetDBConnection();
            conn.Open();
            MySqlCommand cmd = new MySqlCommand();
            cmd.Connection = conn;
            cmd.CommandText = sql;
            int empId = 0;
             MySqlParameter paramid = new MySqlParameter("@id",MySqlDbType.Int32);
             paramid.Value = id;
             cmd.Parameters.Add(paramid);
             
             cmd.Parameters.Add("@username",MySqlDbType.String).Value="visiteur";
             cmd.Parameters.Add("@password",MySqlDbType.String).Value="password";
             cmd.Parameters.Add("@role",MySqlDbType.String).Value="user";
             cmd.Parameters.Add("@enabled",MySqlDbType.Int32).Value=0;
             int rowCount = cmd.ExecuteNonQuery();
             // Terminez la connexion.
            conn.Close();
            // Disposez un objet, libérez des ressources.
            conn.Dispose();
            return rowCount;
        }
        public int CountUser()
        {
            string sql = "Select * from users ";
            MySqlConnection conn = Context.GetDBConnection();
            conn.Open();
            MySqlCommand cmd = new MySqlCommand();
            cmd.Connection = conn;
            cmd.CommandText = sql;
            int empId = 0;
             using (DbDataReader reader = cmd.ExecuteReader())
            {
                if (reader.HasRows)
                {
                   
                     
                    while (reader.Read())
                    {
                        empId++;
                       
                    }
                }
            }
            // Terminez la connexion.
            conn.Close();
            // Disposez un objet, libérez des ressources.
            conn.Dispose();
            return empId;
        }
        
    
    }

    
  
}