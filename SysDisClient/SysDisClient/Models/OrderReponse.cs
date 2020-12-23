using System.Collections.Generic;
using System.Text.Json.Serialization;
using SysDisClient.Models.DTO;

namespace SysDisClient.Models
{
    public class OrderReponse
    {
      
        [JsonPropertyName("list")]
        public IList<Order> Liste { set; get; }
        
        public OrderReponse()
        {
        }

        public OrderReponse(IList<Order> liste)
        {
            Liste = liste;
        }
    }
}