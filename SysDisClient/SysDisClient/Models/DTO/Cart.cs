using System.Text.Json.Serialization;

namespace SysDisClient.Models.DTO
{
    public class Cart
    {
        
        
        [JsonPropertyName("libelle")]
        public string Libelle { get; set; }
        
        
        [JsonPropertyName("quantite")]
        public int Quantite { get; set; }

        
        [JsonPropertyName("prixunitaire")]
        public float PrixUnitaire { get; set; }
        
       
        [JsonPropertyName("prixtotal")]
        public float PrixTotal { get; set; }

        public Cart()
        {
        }

        public Cart(string libelle, int quantite, float prixUnitaire, float prixTotal)
        {
            Libelle = libelle;
            Quantite = quantite;
            PrixUnitaire = prixUnitaire;
            PrixTotal = prixTotal;
        }
    }
}