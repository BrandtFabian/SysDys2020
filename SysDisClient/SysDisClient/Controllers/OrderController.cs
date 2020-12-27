using System;
using System.Net.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Routing;
using MySql.Data.MySqlClient;
using Newtonsoft.Json;
using JsonSerializer = System.Text.Json.JsonSerializer;
using SysDisClient.Models;

namespace SysDisClient.Controllers
{
    public class OrderController :Controller
    {
        private HttpClient _httpClient;
        private static int _currentid = 0;
        private ReponseMenu menu = new ReponseMenu();
        private StockController st = new StockController();

        public OrderController()
        {
            _httpClient = new HttpClient();
        }
        
        

        [HttpGet]
        [Route("/server/order")]
        public RedirectToActionResult WantSeeOrder()
        {

            int currentid = 0;
                string sql = "Select user_id from users where enabled=0 AND username=@name ";
                MySqlCommand cmd = new MySqlCommand();
                cmd.CommandText = sql;
                cmd.Parameters.AddWithValue("@name", "visiteur");
                currentid=st.LookIfNotAVisitor(cmd);
                
                if (currentid == 0)
                {
                    menu.Connected = true;
                   
                    string sqlre = "Select user_id from users where enabled=0 ";
                    _currentid=st.getCurrentIdOrVisiteur(sqlre);
                }
                else
                {
                    return RedirectToAction("Login", "Stock", new RouteValueDictionary(
                        new {controller = "Stock", action = "LoginRappel"}));
                }
            
            return RedirectToAction("SeeOrder", new RouteValueDictionary(
                new {controller = "Order", action = "SeeOrder"}));
        }
        // GET 
        
        
        public IActionResult SeeOrder()
        {

            int currentid = 0;
            string sql = "Select user_id from users where enabled=0 AND username=@name ";
            MySqlCommand cmd = new MySqlCommand();
            cmd.CommandText = sql;
            cmd.Parameters.AddWithValue("@name", "visiteur");
            currentid=st.LookIfNotAVisitor(cmd);
                
            if (currentid == 0)
            {
                menu.Connected = true;
                string sqlre = "Select user_id from users where enabled=0 ";
                _currentid=st.getCurrentIdOrVisiteur(sqlre);
            }
            
            var path = "http://localhost:8090/server/get/order/"+_currentid;
            HttpResponseMessage responseMessage = _httpClient.GetAsync(path).Result;
            String response = responseMessage.Content.ReadAsStringAsync().Result;
            OrderReponse order = JsonSerializer.Deserialize<OrderReponse>(response);
            
            if (order.Liste != null)
            {
                ViewData["OrderReponse"] = order;
                ViewData["ReponseMenu"] = menu;
                return View();
            }

            return View("Error");
        }
    }
}