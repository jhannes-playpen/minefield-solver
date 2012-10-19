import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;


public class SolverTest {

    private final Solver solver = new Solver(null, null);

    @Test
    public void shouldMarkCellAsOpened() throws Exception {
        Position position = new Position(10, 12);

        assertEquals(16*30-99, solver.getRemainingFreeCells());
        assertEquals(solver.getStatus(position), CellStatus.unknown);
        solver.processResponse("y=12,x=10,result=3");
        assertEquals(16*30-99-1, solver.getRemainingFreeCells());
        assertEquals(solver.getStatus(position), CellStatus.opened);
        assertEquals(solver.getHint(position), 3);
    }

    @Test
    public void shouldOpenNeighboursOfZeroHintCell() {
        solver.opened(new Position(11, 11), 0);
        Collection<Position> safeCells = solver.getSafeCells();
        assertEquals(asSet(
                new Position(10,10), new Position(10, 11), new Position(10, 12),
                new Position(11,10),                       new Position(11, 12),
                new Position(12,10), new Position(12, 11), new Position(12, 12)).toString(),
                safeCells.toString());
    }

    @Test
    public void shouldNotOpenNeighboursOfNonZeroHintCell() throws Exception {
        solver.opened(new Position(11, 11), 8);
        assertEquals(new HashSet<>(), solver.getSafeCells());
    }

    @Test
    public void shouldDetectCornersWhenOpeningCells() throws Exception {
        solver.opened(new Position(0, 0), 0);
        solver.opened(new Position(29, 15), 0);
        assertEquals(asSet(
                                   new Position(0, 1),
                new Position(1,0), new Position(1, 1),
                                   new Position(28,14), new Position(28, 15),
                                   new Position(29,14)).toString(),
                solver.getSafeCells().toString());
    }

    @Test
    public void shouldNotIncludeOpenedCellsWhenOpeningCells() throws Exception {
        solver.opened(new Position(0, 0), 0);
        solver.opened(new Position(0, 1), 1);
        assertEquals(asSet(new Position(1,0), new Position(1, 1)), solver.getSafeCells());
    }

    @Test
    public void shouldFlagKnownMines() throws Exception {
        solver.opened(new Position(0, 0), 1);
        solver.opened(new Position(0, 1), 1);
        solver.opened(new Position(1, 1), 1);

        assertEquals(CellStatus.flaggedMine, solver.getStatus(new Position(1, 0)));
    }

    @Test
    public void shouldNotCountFlaggedMinesAsSafe() throws Exception {
        // 11????
        // F1????
        // !1????
        solver.opened(new Position(0, 0), 1);
        solver.opened(new Position(0, 1), 1);
        solver.opened(new Position(1, 1), 1);
        solver.opened(new Position(1, 2), 1);

        assertEquals(CellStatus.unknown, solver.getStatus(new Position(0, 2)));
    }

    @Test
    public void shouldFindClearedNeighbours() throws Exception {
        // 11????
        // F1????
        // ??????
        // means we can open 0x2, 1x2, 2x0, 2x1, 2x2
        // Around 0x1 there are two cleared cells

        solver.opened(new Position(0, 0), 1);
        solver.opened(new Position(0, 1), 1);
        solver.opened(new Position(1, 1), 1);

        assertEquals(asSet(new Position(0, 2), new Position(1, 2)),
                solver.getClearedNeighboursAround(new Position(0, 1)));
    }

    public static<T> Collection<T> asSet(@SuppressWarnings("unchecked") T... objs) {
        return new HashSet<>(Arrays.asList(objs));
    }

}
