using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace MinefieldSolver
{
    [TestClass]
    public class SolverTest
    {
        private Solver solver = new Solver(null, null);

        [TestMethod]
        public void DecodeResult()
        {
            int neighbouringMines = solver.NumberOfMines("y=1,x=2,result=0");
            Assert.AreEqual(0, neighbouringMines);
            neighbouringMines = solver.NumberOfMines("y=1,x=2,result=4");
            Assert.AreEqual(4, neighbouringMines);
        }
    }
}
