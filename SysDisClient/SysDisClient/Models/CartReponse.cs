using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;
using SysDisClient.Models.DTO;

namespace SysDisClient.Models
{
    public class CartReponse
    {
        [JsonPropertyName("list")]
        public IList<Cart> Liste { set; get; }
        
        [JsonPropertyName("prixtotal")]
        public double PrixTotal { get; set; }


        public CartReponse()
        {
        }

        public CartReponse(IList<Cart> liste, double prixtot)
        {
            Liste = liste;
            PrixTotal = prixtot;
        }
    }
}