using System.Collections.Generic;
using System.Text.Json.Serialization;
using SysDisClient.Models.DTO;

namespace SysDisClient.Models
{
    public class CartReponseID
    {
        [JsonPropertyName("list")]
        public IList<CartWhitoutTot> Liste { set; get; }

        public CartReponseID()
        {
        }

        public CartReponseID(IList<CartWhitoutTot> liste)
        {
            Liste = liste;
        }
    }
}