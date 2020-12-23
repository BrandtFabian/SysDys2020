using System;
using System.Net.Http;
using Microsoft.AspNetCore.Mvc;
using SysDisClient.Models;
using System.Collections.Generic;
using System.Text;
using System.Text.Json.Serialization;
using Microsoft.AspNetCore.Routing;
using Newtonsoft.Json;
using SysDisClient.Models.DTO;
using JsonSerializer = System.Text.Json.JsonSerializer;


namespace SysDisClient.Controllers
{
    public class StockController : Controller
    {
        private HttpClient _httpClient;

        public StockController()
        {
            _httpClient = new HttpClient();
        }
        
        // GET 

        public IActionResult Index()
        {
           
            var path = "http://localhost:8090/server/stock/all";
            HttpResponseMessage responseMessage = _httpClient.GetAsync(path).Result;
            String response = responseMessage.Content.ReadAsStringAsync().Result;
            StockReponse stock = JsonSerializer.Deserialize<StockReponse>(response);
            
           
            
            if (stock.Liste != null)
            {
                ViewData["StockReponse"] = stock;
                return View();
            }

            return View("Error");
        }

        [HttpPost]
        public RedirectToActionResult Submit()
        {
            Console.WriteLine("Controller stock");
     
            int quantite = Int32.Parse(HttpContext.Request.Form["quantite"]);
            int iditem =  Int32.Parse(HttpContext.Request.Form["iditem"]);
            int iduser = Int32.Parse(HttpContext.Request.Form["iduser"]);
            int idcart = 0;
            
            
            // recup panier current
            String currentpanier = "http://localhost:8090/server/user/panier/"+iduser;

            HttpResponseMessage responseMessagere = _httpClient.GetAsync(currentpanier).Result;
            String response = responseMessagere.Content.ReadAsStringAsync().Result;
             idcart = JsonSerializer.Deserialize<int>(response);
            Console.WriteLine("ICI ta mere "+idcart);
            if (idcart == 0)
            {
                var getpanier = "http://localhost:8090/server/create/cart/"+iduser;
                HttpResponseMessage responseMessage = _httpClient.GetAsync(getpanier).Result;
                String responsegetpanier = responseMessage.Content.ReadAsStringAsync().Result;
                idcart = JsonSerializer.Deserialize<int>(responsegetpanier);
            }
           

            var path = "http://localhost:8090/server/cart/add/"+idcart+"/"+iduser+"/"+iditem+"/"+quantite;
           
            HttpResponseMessage responseMessagereSend = _httpClient.GetAsync(path).Result;
            String responseRes = responseMessagereSend.Content.ReadAsStringAsync().Result;
            
            
            if(responseMessagereSend.IsSuccessStatusCode)
                return RedirectToAction("CommandeUSer","Cart", new RouteValueDictionary(
                    new { controller = "Cart", action= "CommandeUSer", id = HttpContext.Request.Form["iduser"]}));



            return RedirectToAction();
        }
        
        [HttpGet]
        //[Route("CurrentPanier/{id?}")]
        public int CurrentPanier(int? id)
        {
            String path = "http://localhost:8090/server/user/panier/"+id;

            HttpResponseMessage responseMessage = _httpClient.GetAsync(path).Result;
            String response = responseMessage.Content.ReadAsStringAsync().Result;
            int idcart = JsonSerializer.Deserialize<int>(response);
            return idcart;
        }
    
    }
}