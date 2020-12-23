using System.Text.Json.Serialization;

namespace SysDisClient.Models.DTO
{
    public class StructureCart
    {
         
        [JsonPropertyName("idCart")]
        public int Idcart { get; set; }
        
        
        [JsonPropertyName("idClient")]
        public int Idclient { get; set; }

        
        [JsonPropertyName("idProduit")]
        public int Idproduit { get; set; }
        
       
        [JsonPropertyName("quantite")]
        public int quantite { get; set; }
        
        [JsonPropertyName("isVirtual")]
        public bool Isvirtual { get; set; }

        public StructureCart(int idcart, int idclient, int idproduit, int quantite, bool isvirtual)
        {
            this.Idcart = idcart;
            this.Idclient = idclient;
            this.Idproduit = idproduit;
            this.quantite = quantite;
            this.Isvirtual = isvirtual;
        }

        public StructureCart()
        {
        }
        
        public override string ToString()
        {
            return $"{nameof(Idcart)}: {Idcart}, {nameof(Idclient)}: {Idclient}" +
                   $", {nameof(Idproduit)}: {Idproduit}" + $", {nameof(quantite)}: {quantite}"
                   + $", {nameof(Isvirtual)}: {Isvirtual}";
        }
    }
}