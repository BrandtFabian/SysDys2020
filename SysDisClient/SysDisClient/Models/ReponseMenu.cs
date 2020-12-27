namespace SysDisClient.Models
{
    public class ReponseMenu
    {
        public bool Connected { set; get; }

        public ReponseMenu()
        {
        }

        public ReponseMenu(bool connected)
        {
            Connected = connected;
        }
    }
}