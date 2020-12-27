using System;
using System.Collections.Generic;
using System.Globalization;
using System.Net.Http;
using System.Text.Json;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Routing;
using Microsoft.CodeAnalysis.CSharp.Syntax;
using MySql.Data.MySqlClient;
using SysDisClient.Models;

namespace SysDisClient.Controllers
{
    public class CartController : Controller
    {
        private HttpClient _httpClient;
        private static int _currentid;
        private static int _currentorder;
        private ReponseMenu menu = new ReponseMenu();
        private StockController st = new StockController();

        public CartController()
        {
            _httpClient = new HttpClient();
        }

        // GET 

        [HttpGet]
        [Route("/server/panier")]
        public IActionResult CommandeUSer()
        {

            string sql = "Select user_id from users where enabled=0";
            _currentid = st.getCurrentIdOrVisiteur(sql);

            Console.WriteLine("Commande du client");
            
            int vistorornot = 0;
            string sqls = "Select user_id from users where user_id=@id AND username=@name ";
            MySqlCommand cmd = new MySqlCommand();
            cmd.CommandText = sqls;
            cmd.Parameters.AddWithValue("@id", _currentid);
            cmd.Parameters.AddWithValue("@name", "visiteur");
            vistorornot=st.LookIfNotAVisitor(cmd);

            if (vistorornot == 0)
            {
                menu.Connected = true;
            }
            else
            {
                menu.Connected = false;
            }
            /*
            String getpanier = "http://localhost:8090/server/user/panier/"+id;
            HttpResponseMessage responseMessage = _httpClient.GetAsync(getpanier).Result;
            String response = responseMessage.Content.ReadAsStringAsync().Result;
            int cart = JsonSerializer.Deserialize<int>(response);
            */

            String path = "http://localhost:8090/server/panier/" + _currentid;
            HttpResponseMessage message = _httpClient.GetAsync(path).Result;
            String r = message.Content.ReadAsStringAsync().Result;
            CartReponse cartR = JsonSerializer.Deserialize<CartReponse>(r);

            if (_currentid != 0)
            {


                if (cartR.Liste != null)
                {
                    ViewData["CartReponse"] = cartR;
                    ViewData["ReponseMenu"] = menu;
                    return View();
                }
            }

            return View("Error");
        }

        [HttpPost]
        public RedirectToActionResult DeleteItemFromCart()
        {
            Console.WriteLine("Controller cart");

            // int iduser = Int32.Parse(HttpContext.Request.Form["iduser"]);
            string libelle = HttpContext.Request.Form["libelle"];
            int iditem = 0;

            Console.WriteLine($"{libelle}");
            // Console.WriteLine($"{iduser}");


            // get all items and get iditem

            var getallitem = "http://localhost:8090/server/stock/all";
            HttpResponseMessage responseMessageREs = _httpClient.GetAsync(getallitem).Result;
            String reponseitem = responseMessageREs.Content.ReadAsStringAsync().Result;
            StockReponse stock = JsonSerializer.Deserialize<StockReponse>(reponseitem);

            foreach (var x in stock.Liste)
            {
                if (x.Libelle == libelle)
                {
                    iditem = x.Id;
                }
            }


            Console.WriteLine($"{iditem}");

            // get current panier for an user

            String currentpanier = "http://localhost:8090/server/user/panier/" + _currentid;

            HttpResponseMessage responseMessage = _httpClient.GetAsync(currentpanier).Result;
            String response = responseMessage.Content.ReadAsStringAsync().Result;
            int idcart = JsonSerializer.Deserialize<int>(response);

            Console.WriteLine($"{idcart}");

            var path = "http://localhost:8090/server/delete/cart/" + idcart + "/" + _currentid + "/" + iditem;
            HttpResponseMessage responseMessagereSend = _httpClient.GetAsync(path).Result;
            String responseRes = responseMessagereSend.Content.ReadAsStringAsync().Result;


            if (responseMessagereSend.IsSuccessStatusCode)
                return RedirectToAction("CommandeUSer", new RouteValueDictionary(
                    new {controller = "Cart", action = "CommandeUSer"}));


            return RedirectToAction();

        }



        [HttpPost]
        public RedirectToActionResult CheckOut()
        {
            int r = 0;
            string sql = "Select user_id from users where username='visiteur'";
            r = st.getCurrentIdOrVisiteur(sql);
            if (r == _currentid)
            {
                return RedirectToAction("LoginRappel", "Stock", new RouteValueDictionary(
                    new {controller = "Stock", action = "MustLog"}));
            }

            return RedirectToAction("CheckOutView", new RouteValueDictionary(
                new {controller = "Cart", action = "CheckOutView"}));
        }



        [HttpGet]
        public IActionResult CheckOutView(int id)
        {
            menu.Connected = true;
            string sql = "Select user_id from users where enabled=0 ";
            _currentid = st.getCurrentIdOrVisiteur(sql);
            String path = "http://localhost:8090/server/panier/" + _currentid;
            HttpResponseMessage message = _httpClient.GetAsync(path).Result;
            String r = message.Content.ReadAsStringAsync().Result;
            CartReponse cartR = JsonSerializer.Deserialize<CartReponse>(r);

            if (_currentid != 0)
            {


                if (cartR.Liste != null)
                {
                    ViewData["CartReponse"] = cartR;
                    ViewData["ReponseMenu"] = menu;
                    return View();
                }
            }

            return View("Error");
        }



        [HttpPost]
        public RedirectToActionResult Pay()
        {

            menu.Connected = true;
            string type = HttpContext.Request.Form["drone"].ToString();
            double prix = Double.Parse(HttpContext.Request.Form["prix"], CultureInfo.CurrentUICulture);

            string status = "En attente de payement";


            string path = "http://localhost:8090/server/create/order/" + _currentid + "/" +
                          prix.ToString("0.00", System.Globalization.CultureInfo.InvariantCulture) + "/" + status;
            // http://localhost:8090/server/create/order/1/20000/Preparation
            HttpResponseMessage message = _httpClient.GetAsync(path).Result;
            String r = message.Content.ReadAsStringAsync().Result;
            int idorder = JsonSerializer.Deserialize<int>(r);

            _currentorder = idorder;

            bool expressCheck = false;
            string livraison = "";
            if (type == "express")
            {
                livraison = "express";
            }
            else
            {
                if (type == "normal")
                {
                    livraison = "normal";
                }
                else
                {
                    return RedirectToAction("CheckOutView", new RouteValueDictionary(
                        new {controller = "Cart", action = "CheckOutView"}));
                }


            }

            string upprice = "http://localhost:8090/server/delivery/amount/" + idorder + "/" + livraison;
            HttpResponseMessage Rmessage = _httpClient.GetAsync(upprice).Result;
            String reponse = Rmessage.Content.ReadAsStringAsync().Result;
            bool upok = JsonSerializer.Deserialize<bool>(reponse);



            return RedirectToAction("RecapView", new RouteValueDictionary(
                new {controller = "Cart", action = "RecapView"}));
        }


        [HttpGet]
        public IActionResult RecapView()
        {

            menu.Connected = true;
            String path = "http://localhost:8090/server/panier/" + _currentid;
            HttpResponseMessage message = _httpClient.GetAsync(path).Result;
            String r = message.Content.ReadAsStringAsync().Result;
            CartReponse cartR = JsonSerializer.Deserialize<CartReponse>(r);


            String getprice = "http://localhost:8090/server/order/prix/" + _currentorder;
            HttpResponseMessage messageprice = _httpClient.GetAsync(getprice).Result;
            String rprice = messageprice.Content.ReadAsStringAsync().Result;
            double pricewithtransport = JsonSerializer.Deserialize<double>(rprice);
            cartR.PrixTotal = pricewithtransport;
            if (_currentid != 0)
            {


                if (cartR.Liste != null)
                {
                    ViewData["CartReponse"] = cartR;
                    ViewData["ReponseMenu"] = menu;
                    return View();
                }
            }

            return View("Error");
        }


        [HttpPost]
        public IActionResult FinishCommand()
        {
            menu.Connected = true;
            String path = "http://localhost:8090/server/finishorder/" + _currentorder;
            HttpResponseMessage message = _httpClient.GetAsync(path).Result;
            String r = message.Content.ReadAsStringAsync().Result;
            bool order = JsonSerializer.Deserialize<bool>(r);
            if (order == true)
            {
                String suppath = "http://localhost:8090/server/deletecart/" + _currentid;
                HttpResponseMessage suppmessage = _httpClient.GetAsync(suppath).Result;
                String suppreponse = suppmessage.Content.ReadAsStringAsync().Result;
                bool supp = JsonSerializer.Deserialize<bool>(suppreponse);
                if (supp == true)
                {
                    return RedirectToAction("SeeOrder", "Order", new RouteValueDictionary(
                        new {controller = "Order", action = "SeeOrder", id = _currentid}));
                }

            }
            else
            {
                return RedirectToAction("RecapView", new RouteValueDictionary(
                    new {controller = "Cart", action = "RecapView"}));
            }
            return RedirectToAction("RecapView", new RouteValueDictionary(
                new {controller = "Cart", action = "RecapView"}));
        }
    }

}
    
    
    
    
