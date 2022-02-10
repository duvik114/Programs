package ru.itmo.wp.web.page;

import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        HttpSession session = request.getSession();
        if (session.getAttribute("state") == null)
            session.setAttribute("state", new State(3));
        State state = (State) session.getAttribute("state");
        view.put("state", state);
    }

    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        State state = (State) request.getSession().getAttribute("state");
        action(request, view);

        // isValid
        if (request.getParameterMap().entrySet().stream().filter(i -> i.getKey().startsWith("cell_")).count() != 1
                || !state.phase.equals("RUNNING")) {
            throw new RedirectException("/ticTacToe");
        } else {
            state.count--;
        }

        //noinspection OptionalGetWithoutIsPresent
        String cellNum = request.getParameterMap().entrySet().stream().filter(i
                -> i.getKey().startsWith("cell_")).findAny().get().getKey();
        int x = cellNum.charAt(cellNum.length() - 2) - 48;
        int y = cellNum.charAt(cellNum.length() - 1) - 48;
        if (!(x >= 0 && x < state.size && y >= 0 && y < state.size) ||
                (state.cells[x][y] != null)) {
            throw new RedirectException("/ticTacToe");
        }
        state.cells[x][y] = state.crossesMove ? 'X' : 'O';
        state.crossesMove ^= true;

        //checking
        int countRow = 1, countCol = 1, countDiag1 = 1, countDiag2 = 1;
        for (int i = 0; i < state.size; i++) {
            for (int j = 1; j < state.size; j++) {
                if (state.cells[i][j] != null && state.cells[i][j] == state.cells[i][j - 1])
                    countRow++;
                if (state.cells[j][i] != null && state.cells[j][i] == state.cells[j - 1][i])
                    countCol++;
            }

            if (i != 0) {
                if (state.cells[i][i] != null && state.cells[i][i] == state.cells[i - 1][i - 1]) {
                    countDiag1++;
                }
                if (state.cells[i][state.size - i - 1] != null
                        && state.cells[i][state.size - i - 1] == state.cells[i - 1][state.size - i]) {
                    countDiag2++;
                }
            }

            if (countRow == state.size) {
                state.phase = "WON_" + state.cells[i][0];
            } else if (countCol == state.size) {
                state.phase = "WON_" + state.cells[0][i];
            } else if (countDiag1 == state.size) {
                state.phase = "WON_" + state.cells[0][0];
            } else if (countDiag2 == state.size) {
                state.phase = "WON_" + state.cells[0][state.size - 1];
            }
            countRow = 1;
            countCol = 1;
        }
        if (state.phase.equals("RUNNING") && state.count == 0) {
            state.phase = "DRAW";
        }
        view.put("state", state);
        throw new RedirectException("/ticTacToe"); //
    }

    private void newGame(HttpServletRequest request, Map<String, Object> view) {
        State state = new State(3);
        request.getSession().setAttribute("state", state);
        view.put("state", state);
    }

    public static class State {
        private int size;
        private Character[][] cells;
        private String phase;
        private boolean crossesMove;
        private int count;

        State(int size) {
            this.size = size;
            cells = new Character[size][size];
            phase = "RUNNING";
            crossesMove = true;
            count = size * size;
        }

        public long getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public Character[][] getCells() {
            return cells;
        }

        public void setCells(Character[][] cells) {
            this.cells = cells;
        }

        public String getPhase() {
            return phase;
        }

        public void setPhase(String phase) {
            this.phase = phase;
        }

        public boolean isCrossesMove() {
            return crossesMove;
        }

        public void setCrossesMove(boolean crossesMove) {
            this.crossesMove = crossesMove;
        }
    }
}
