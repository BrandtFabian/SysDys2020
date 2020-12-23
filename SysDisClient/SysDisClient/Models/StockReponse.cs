using System.Collections.Generic;
using System.Text.Json.Serialization;
using SysDisClient.Models.DTO;

namespace SysDisClient.Models
{
    public class StockReponse : ReferenceReponse
    {
        [JsonPropertyName("userStock")]
        public IList<Stock> Liste { set; get; }


        public StockReponse()
        {
        }

        public StockReponse(IList<Stock> liste)
        {
            Liste = liste;
        }
    }
}