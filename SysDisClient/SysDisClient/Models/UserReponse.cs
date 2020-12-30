using System;

namespace SysDisClient.Models
{
    public class UserReponse
    {
        public string Nom { set; get; }
        public double Portefeuille { set; get; }
        public string Adresse { set; get; }

        public UserReponse()
        {
        }

        public UserReponse(string nom, double portefeuille, string adresse)
        {
            Nom = nom;
            Portefeuille = portefeuille;
            Adresse = adresse;
        }

      
    }
}