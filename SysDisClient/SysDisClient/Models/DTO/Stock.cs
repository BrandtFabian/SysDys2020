using System.Text.Json.Serialization;

namespace SysDisClient.Models.DTO
{
    public class Stock
    {
        [JsonPropertyName("idstock")]
        public int Id { get; set; }
        
        [JsonPropertyName("libelle")]
        public string Libelle { get; set; }
        
        [JsonPropertyName("quantite")]
        public int Quantite { get; set; }
        
        [JsonPropertyName("prix")]
        public double Prix { get; set; }

        public Stock()
        {
        }

        public Stock(int id, string libelle, int quantite, double prix)
        {
            Id = id;
            Libelle = libelle;
            Quantite = quantite;
            Prix = prix;
        }

        public override string ToString()
        {
            return $"{nameof(Id)}: {Id}, {nameof(Libelle)}: {Libelle}" +
                   $", {nameof(Quantite)}: {Quantite}" + $", {nameof(Prix)}: {Prix}";
        }
    }
}