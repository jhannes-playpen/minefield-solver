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
            new MinefieldSolver.Solver("http://localhost:1337", "523111").Solve();
        }
    }
}
