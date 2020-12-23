using System;
using System.Net.Http;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using JsonSerializer = System.Text.Json.JsonSerializer;
using SysDisClient.Models;

namespace SysDisClient.Controllers
{
    public class OrderController :Controller
    {
        private HttpClient _httpClient;

        public OrderController()
        {
            _httpClient = new HttpClient();
        }
        
        // GET 
        
        
        public IActionResult SeeOrder(int id)
        {

            var path = "http://localhost:8090/server/get/order/"+id;
            HttpResponseMessage responseMessage = _httpClient.GetAsync(path).Result;
            String response = responseMessage.Content.ReadAsStringAsync().Result;
            OrderReponse order = JsonSerializer.Deserialize<OrderReponse>(response);
            
            if (order.Liste != null)
            {
                ViewData["OrderReponse"] = order;
                return View();
            }

            return View("Error");
        }
    }
}