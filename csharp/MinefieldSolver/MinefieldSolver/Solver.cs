using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace MinefieldSolver
{
    public class Solver
    {
        private string applicationUrl;
        private string playerId;
        private int remainingCells;

        public Solver(string url, string playerId)
        {
            this.applicationUrl = url;
            this.playerId = playerId;
        }

        public void Solve()
        {
            NewBoard();
            while (remainingCells > 0)
            {
                remainingCells--;
                var url = applicationUrl + "/hint?id=" + this.playerId;
                string response = ReadUrl(url);
                Console.Out.WriteLine(response);
            }
            Console.In.Read();
        }

        private string ReadUrl(string url)
        {
            var client = new WebClient();
            var data = client.OpenRead(url);
            using (StreamReader sr = new StreamReader(data))
            {
                return sr.ReadToEnd();
            }
        }

        private void NewBoard()
        {
            this.remainingCells = 16 * 30 - 99;
        }

        public int NumberOfMines(string p)
        {
            throw new NotImplementedException();
        }
    }
}
