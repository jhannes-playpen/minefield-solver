using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MinefieldSolverApp
{
    class SolverApp
    {

        public static void Main(string[] args)
        {
            new MinefieldSolver.Solver("http://192.168.135.127:1337", "738811").Solve();
        }
    }
}
