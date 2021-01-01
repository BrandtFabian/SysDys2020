namespace SysDisClient.Models
{
    public class ReponseError
    {
        public string NomService { get; set; }

        public ReponseError()
        {
        }

        public ReponseError(string nomService)
        {
            NomService = nomService;
        }
    }
}