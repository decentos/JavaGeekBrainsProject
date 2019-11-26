import java.util.Scanner;
import java.util.Random;

public class ConsoleTicTacToe {
    /*
    блок настроек
     */
    private static char[][] map;                //матрица игры
    private static final int SIZE = 3;          //размерность поля
    private static final char DOT_EMPTY = '*';  //свободное поле
    private static final char DOT_X = 'x';      //крестик
    private static final char DOT_O = 'o';      //нолик
    private static final boolean SILLY_MODE = false;    //выбор режима компьютера: true - глупый, false - умный
    private static final boolean FIRST_TURN = true;     //первый ход: true - человек, false - компьютер
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        initMap();
        printMap();

        while (true) {
            if (FIRST_TURN == true) {
                humanTurn();    //ход человека
                if (isEndGame(DOT_X)) {
                    break;
                }
                computerTurn();   //ход компьютера
                if (isEndGame(DOT_O)) {
                    break;
                }
            } else {
                computerTurn();   //ход компьютера
                if (isEndGame(DOT_O)) {
                    break;
                }
                humanTurn();    //ход человека
                if (isEndGame(DOT_X)) {
                    break;
                }
            }

        }
        System.out.println("Игра закончена.");
    }

    /*
    создание игрового поля
     */
    private static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    /*
    вывод игрового поля на экран
     */
    private static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /*
    ход человека
     */
    private static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите координаты ячейки через пробел (строка столбец):");
            y = scanner.nextInt() - 1; // Считывание номера строки
            x = scanner.nextInt() - 1; // Считывание номера столбца
        }
        while (!isCellValid(x, y));
        map[y][x] = DOT_X;
//        System.out.println("Количество нуликов: " + countZero(x, y));

    }

    /*
    проверка правильности ввода
     */
    private static boolean isCellValid(int x, int y) {
        boolean result = true;
        //проверка координаты
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            result = false;
        }
        //проверка заполненности
        if (map[y][x] != DOT_EMPTY) {
            result = false;
        }
        return result;
    }

    /*
    проверка окончания игры
     */
    private static boolean isEndGame(char playerSymbol) {
        boolean result = false;
        printMap();
        if (checkWin(playerSymbol)) {
            System.out.println("Победили " + playerSymbol);
            result = true;
        }
        if (isMapFull() & result == false) {
            System.out.println("Ничья!");
            result = true;
        }
        return result;
    }

    /*
    проверка победных комбинаций
     */
    private static boolean checkWin(char playerSymbol) {
        boolean result = false;
        int countHor = 0;   //счётчик сиволов PlayerSymbol по горизонтали
        int countVer = 0;   //счётчик сиволов PlayerSymbol по вертикали
        int countDiagLeft = 0;   //счётчик сиволов PlayerSymbol по диагонали с верхнего левого угла
        int countDiagRight = 0;   //счётчик сиволов PlayerSymbol по диагонали с верхнего правого угла
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                //проверка по горизонтали
                if (map[i][j] == playerSymbol) {
                    countHor++;
                }
                //проверка по вертикали
                if (map[j][i] == playerSymbol) {
                    countVer++;
                }
            }
            //проверка по диагонали с верхнего левого угла
            if (map[i][i] == playerSymbol) {
                countDiagLeft++;
            }
            //проверка по диагонали с верхнего правого угла
            if (map[i][SIZE - i - 1] == playerSymbol) {
                countDiagRight++;
            }
            //проверка на условие заполнености сиволом playerSymbol
            if (countHor == SIZE || countVer == SIZE || countDiagLeft == SIZE || countDiagRight == SIZE) {
                result = true;
            } else {
                countHor = 0;
                countVer = 0;
            }

        }
        return result;
    }

    /*
    проверка заполненности игрового поля
     */
    private static boolean isMapFull() {
        boolean result = true;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    result = false;
                    break;
                }
            }
            if (!result) break;
        }
        return result;
    }

    /*
    ход компьютера
     */
    private static void computerTurn() {
        if (SILLY_MODE) {
            stupidComputer();
        } else {
            cleverComputer();
        }
    }

    /*
    тупой компьютер
     */
    private static void stupidComputer() {
        int x, y;
        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (!isCellValid(x, y));
        System.out.println("Компьютер выбрал ячейку " + (y + 1) + " " + (x + 1));
        map[y][x] = DOT_O;
    }
    /*
    умный компьютер
     */
    private static void cleverComputer() {
        int tempY = 0;  //переменная для запоминания номера строки
        int tempX = 0;  //переменная для запоминания номера столбца
        int centerMap = SIZE / 2;
        int max = 0;
        if (map[centerMap][centerMap] == DOT_EMPTY) {
            map[centerMap][centerMap] = DOT_O;
            System.out.println("Компьютер выбрал ячейку " + (centerMap + 1) + " " + (centerMap + 1));
            System.out.println("Занять центральную ячейку");
        } else if (countZero(centerMap, centerMap) == 0) {
            stupidComputer();
            System.out.println("Рандомный выбор.");
        } else if (random.nextInt(10) <= 2) {
            stupidComputer();
            System.out.println("Рандомный выбор 20%.");
        } else {
            for (int y = 0; y < SIZE; y++) {
                boolean result = false; //для выхода из внешнего цикла
                for (int x = 0; x < SIZE; x++) {
                    //ход на выигрышную ячейку
                    if (map[y][x] == DOT_EMPTY) {
                        map[y][x] = DOT_O;
                        if (checkWin(DOT_O)) {
                            result = true;
                            tempX = x;
                            tempY = y;
                            System.out.println("Xод на выигрышную ячейку");
                            break;
                        } else {
                            map[y][x] = DOT_EMPTY;
                        }
                    }
                    //ход на ячейку выигрушную для противника
                    if (map[y][x] == DOT_EMPTY) {
                        map[y][x] = DOT_X;
                        if (checkWin(DOT_X)) {
                            result = true;
                            tempX = x;
                            tempY = y;
                            System.out.println("Xод на ячейку выигрушную для противника");
                            break;
                        } else {
                            map[y][x] = DOT_EMPTY;
                        }
                    }
                    // ход на ячейку с максимальным количеством нулей вокруг нее
                    if (map[y][x] == DOT_EMPTY & max < countZero (x, y)){
                        max = countZero(x, y);
                        tempX = x;
                        tempY = y;
                        System.out.println("Количество нуликов: " + countZero(x, y));
                    }
                }
                if (result) break;  //выход из внешнего массива
            }
            System.out.println("Компьютер выбрал ячейку " + (tempY + 1) + " " + (tempX + 1));
            map[tempY][tempX] = DOT_O;
        }


    }
    /*
    определение количества ноликов в матрице с центром в x, y
     */
    private static int countZero(int x, int y){
        int y2 = 0;    //номер строки
        int x2 = 0;    //номер столбца
        int sizeY = 0;  //количество строк
        int sizeX = 0;  //количество столбцов
        //проверка строк
        if (y == 0) {
            y2 = 0;
            sizeY = 2;
        } else if (y == SIZE - 1) {
            y2 = y - 1;
            sizeY = 2;
        } else {
            y2 = y - 1;
            sizeY = 3;
        }
//        System.out.println("номер строки: " + y2 + " / " + "количество строк " + sizeY);
        if (x == 0) {
            x2 = 0;
            sizeX = 2;
        } else if (x == SIZE - 1) {
            x2 = x - 1;
            sizeX = 2;
        } else {
            x2 = x - 1;
            sizeX = 3;
        }
//        System.out.println("номер столбца: " + x2 + " / " + "количество столбцов " + sizeX);
        int count_O = 0;
        for (int i = y2; i < y2 + sizeY; i++) {
            for (int k = x2; k < x2 + sizeX; k++) {
                if (map[i][k] == DOT_O) {
                    count_O++;
                }
//                System.out.println("i/k " + i + "/" + k);
            }
        }
//        System.out.println("Количество нуликов: " + count_O);
        return count_O;
    }
}
