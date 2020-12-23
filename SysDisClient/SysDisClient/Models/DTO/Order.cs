using System.Text.Json.Serialization;

namespace SysDisClient.Models.DTO
{
    public class Order
    {
        [JsonPropertyName("idOrder")]
        public int IdOrder { get; set; } //equivaut a id caddie pour plus de facilite
        [JsonPropertyName("idClient")]
        public int IdClient { get; set; }
        [JsonPropertyName("status")]
        public string Status { get; set; }
        [JsonPropertyName("prixTotal")]
        public double PrixTotal { get; set; }

        public Order()
        {
            
        }

        public Order(int idOrder, int idClient, string status, double prixTotal)
        {
            IdOrder = idOrder;
            IdClient = idClient;
            Status = status;
            PrixTotal = prixTotal;
        }
        
        
    }
}