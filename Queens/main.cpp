#include <iostream>

void InitChessBoard(bool ** &chessBoard, int n) {
    chessBoard = new bool *[n];
    for(int i = 0; i < n; i++) {
        chessBoard[i] = new bool[n];
        for (int j = 0; j < n; j++) {
            chessBoard[i][j] = false;  // Initialize each element to 0
        }
    }

}

void PrintBoard(bool **chessBoard, int n) {
    for (int i=0 ; i<n ; i++) {
        for (int j=0; j<n; j++) {
            std::cout << chessBoard[i][j] << " ";
        }
        std::cout << std::endl;
    }
    std::cout << std::endl;
    std::cout << std::endl;

}


bool isSafe(bool ** chessBoard, int row, int col, int n) {

    for (int i = 0; i < row; i++) {
        if (chessBoard[i][col]) {
            return false;
        }
    }

    for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
        if (chessBoard[i][j]) {
            return false;
        }
    }

    for (int i = row, j = col; i >= 0 && j < n; j++, i--) {
        if (chessBoard[i][j]) {
            return false;
        }
    }

    return true;
}

bool solve(bool **chessBoard, int n, int row) {
    if (row == n) {
        PrintBoard(chessBoard, n);
        return true;
    }

    for (int col = 0; col < n; col++) {
        if (isSafe(chessBoard, row, col, n)) {
            // Place the queen at (row, col)
            chessBoard[row][col] = 1;

            // Recursively place queens in the next rows
            if (solve(chessBoard, n, row + 1))
                return true;

            // If the current placement leads to no solution, backtrack
            chessBoard[row][col] = 0;
        }
    }

    // No solution found
    return false;
}
int main(int, char**){

    bool **chessBoard;
    int n = 8;
    InitChessBoard(chessBoard, n);
    bool res = false;
    res = solve(chessBoard, n, 0);
}
