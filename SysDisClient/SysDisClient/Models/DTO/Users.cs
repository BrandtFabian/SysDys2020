using System.Text.Json.Serialization;

namespace SysDisClient.Models.DTO
{
    public class Users
    {

      
        public int Id { get; set; }
        
      
        public string Name { get; set; }
        
       
        public string Password { get; set; }
        public string Role { get; set; }
       
        public bool Enbale { get; set; }
        

        public Users(){}
        
        public Users(int id,string nom,string pwd,string role,bool a) {
            this.Id=id;
            this.Name = nom;
            this.Password=pwd;
            this.Role = role;
            this.Enbale=a;

        }


        public override string ToString()
        {
            return $"{nameof(Id)}: {Id}, {nameof(Name)}: {Name}" +
                   $", {nameof(Password)}: {Password}" + $", {nameof(Enbale)}: {Enbale}";
        }
    }
}