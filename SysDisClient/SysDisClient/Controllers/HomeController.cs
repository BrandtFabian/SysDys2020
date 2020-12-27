using System;
using System.Diagnostics;
using System.Net.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using SysDisClient.Models;
using JsonSerializer = System.Text.Json.JsonSerializer;

namespace SysDisClient.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;
        private static int _currentid = 0;
        private HttpClient _httpClient;

        public HomeController(ILogger<HomeController> logger)
        {
            _logger = logger;
            _httpClient = new HttpClient();
        }

        [HttpGet]
        public IActionResult Index()
        {
            if (_currentid == 0)
            {
                var getactiveuser = "http://localhost:8090/server/users/getactive";
                HttpResponseMessage message = _httpClient.GetAsync(getactiveuser).Result;
                String result = message.Content.ReadAsStringAsync().Result;
                _currentid = JsonSerializer.Deserialize<int>(result);
            }
            return View();
        }

        public IActionResult Privacy()
        {
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel {RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier});
        }
    }
}