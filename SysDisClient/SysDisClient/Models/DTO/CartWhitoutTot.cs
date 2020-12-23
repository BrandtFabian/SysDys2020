using System.Text.Json.Serialization;

namespace SysDisClient.Models.DTO
{
    public class CartWhitoutTot
    {
        [JsonPropertyName("idCart")]
        public int idcart { get; set; }
        
        
        [JsonPropertyName("idClient")]
        public int idclient { get; set; }

        
        [JsonPropertyName("idProduit")]
        public int idproduit { get; set; }
        
       
        [JsonPropertyName("quantite")]
        public int quantite { get; set; }
        
        [JsonPropertyName("virtual")]
        public bool isvirtual { get; set; }

        public CartWhitoutTot()
        {
        }

        public CartWhitoutTot(int idcart, int idclient, int idproduit, int quantite, bool isvirtual)
        {
            this.idcart = idcart;
            this.idclient = idclient;
            this.idproduit = idproduit;
            this.quantite = quantite;
            this.isvirtual = isvirtual;
        }
        
    }
}